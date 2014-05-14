package it.mate.therapyreminder.client.service;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.phgcommons.client.plugins.CalendarPlugin;
import it.mate.phgcommons.client.plugins.CalendarPlugin.Event;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapLog;
import it.mate.phgcommons.client.utils.Time;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.ui.client.MGWT;

public class SomministrazioniService {

  private AppSqlDao dao = AppClientFactory.IMPL.getGinjector().getAppSqlDao();
  
  private static SomministrazioniService instance;
  
  public static SomministrazioniService getInstance() {
    if (instance == null)
      instance = new SomministrazioniService();
    return instance;
  }
  
  protected SomministrazioniService() {

  }
  
  // TODO
  public void findPrimaSomministrazioneScaduta(Delegate<Somministrazione> delegate) {
    
  }
  
  // TODO
  public void sviluppaSomministrazioniAsynch() {
    dao.findAllPrescrizioniAttive(new Date(), new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioni) {
        if (prescrizioni == null || prescrizioni.size() == 0) {
          return;
        }
        for (Prescrizione prescrizione : prescrizioni) {
          sviluppaSomministrazioni(prescrizione);
        }
      }
    });
  }
  
  public void sviluppaSomministrazioni(final Prescrizione prescrizione) {
    dao.findLastSomministrazioneByPrescrizione(prescrizione, new Delegate<Somministrazione>() {
      public void execute(Somministrazione ultimaSomministrazione) {
        Date dataUltimaSomministrazione = null;
        if (ultimaSomministrazione != null) {
          dataUltimaSomministrazione = ultimaSomministrazione.getData();
        }
        sviluppaSomministrazioniAPartireDa(prescrizione, dataUltimaSomministrazione);
      }
    });
  }
  
  private void sviluppaSomministrazioniAPartireDa(Prescrizione prescrizione, Date dataUltimaSomministrazione) {
    
    if (dataUltimaSomministrazione == null) {
      dataUltimaSomministrazione = prescrizione.getDataInizio();
      sviluppaSomministrazioniPerGiorno(prescrizione, dataUltimaSomministrazione);
    }

    Date dataLimiteSviluppoSomministrazioni = getDataLimiteSviluppoSomministrazioni(prescrizione);
    
    while (true) {

      Date dataSomministrazione = CalendarUtil.copyDate(dataUltimaSomministrazione);
      
      if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_GIORNALIERA)) {
        int incremento = prescrizione.getValoreRicorrenza();
        CalendarUtil.addDaysToDate(dataSomministrazione, incremento);
      } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_SETTIMANALE)) {
        int incremento = 7 * prescrizione.getValoreRicorrenza();
        CalendarUtil.addDaysToDate(dataSomministrazione, incremento);
      } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_MENSILE)) {
        int incremento = prescrizione.getValoreRicorrenza();
        CalendarUtil.addMonthsToDate(dataSomministrazione, incremento);
      }
      
      if (dataSomministrazione.after(dataLimiteSviluppoSomministrazioni)) {
        return;
      }
      
      if (prescrizione.getDataFine() != null && dataSomministrazione.after(prescrizione.getDataFine())) {
        return;
      }
      
      sviluppaSomministrazioniPerGiorno(prescrizione, dataSomministrazione);
      
      dataUltimaSomministrazione = dataSomministrazione;
      
    }
    
  }
  
  private void sviluppaSomministrazioniPerGiorno(final Prescrizione prescrizione, Date dataSomministrazione) {
    Date NOW = new Date();
    for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
      Somministrazione somministrazione = new SomministrazioneTx(prescrizione);
      Time.fromString(dosaggio.getOrario()).setInDate(dataSomministrazione);
      if (dataSomministrazione.before(NOW)) {
        continue;
      }
      somministrazione.setData(dataSomministrazione);
      somministrazione.setOrario(dosaggio.getOrario());
      Double qta = dosaggio.getQuantita();
      if (qta == null)
        qta = prescrizione.getQuantita();
      somministrazione.setQuantita(qta);
      somministrazione.setSchedulata();
      
      dao.saveSomministrazione(somministrazione, new Delegate<Somministrazione>() {
        public void execute(Somministrazione somministrazione) {
          saveCalEvent(somministrazione);
          saveRemoteSomministrazione(somministrazione, new Delegate<Somministrazione>() {
            public void execute(Somministrazione somministrazione) {

            }
          });
        }
      });
      
    }
  }
  
  private Date getDataLimiteSviluppoSomministrazioni(Prescrizione prescrizione) {
    Date dataLimiteSviluppoSomministrazioni = new Date();
    if (prescrizione.getValoreRicorrenza() == null || prescrizione.getValoreRicorrenza() <= 0) {
      throw new ServiceException("Date range not set");
    }
    int limite = 5 * prescrizione.getValoreRicorrenza();
    CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, limite);
    
    /*
    final int DEFAULT_SVILUPPO_GIORNALIERO = 5;
    final int DEFAULT_SVILUPPO_SETTIMANALE = 5;
    final int DEFAULT_SVILUPPO_MENSILE = 5;
    if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_GIORNALIERA)) {
      int limite = Math.max(DEFAULT_SVILUPPO_GIORNALIERO, prescrizione.getValoreRicorrenza());
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, limite);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_SETTIMANALE)) {
      int limite = Math.max(DEFAULT_SVILUPPO_SETTIMANALE, prescrizione.getValoreRicorrenza());
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, limite * 7);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_MENSILE)) {
      int limite = Math.max(DEFAULT_SVILUPPO_MENSILE, prescrizione.getValoreRicorrenza());
      CalendarUtil.addMonthsToDate(dataLimiteSviluppoSomministrazioni, limite);
    }
    */
    
    return dataLimiteSviluppoSomministrazioni;
  }

  public void cancellaSomministrazioni(final Prescrizione prescrizione, final Delegate<Void> endDelegate) {
    dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
      public void execute(final List<Somministrazione> somministrazioni) {
        if (somministrazioni == null || somministrazioni.size() == 0) {
          endDelegate.execute(null);
          return;
        }
        iterateSomministrazioniForDelete(somministrazioni.iterator(), new Delegate<Void>() {
          public void execute(Void element) {
            dao.deleteSomministrazioni(somministrazioni, endDelegate);
          }
        });
      }
    });
  }
  
  private void iterateSomministrazioniForDelete(Iterator<Somministrazione> it, Delegate<Void> endDelegate) {
    if (it.hasNext()) {
      Somministrazione somministrazione = it.next();
      deleteRemoteSomministrazione(somministrazione, new Delegate<Somministrazione>() {
        public void execute(Somministrazione somministrazione) {
          deleteCalEvent(somministrazione);
        }
      });
      iterateSomministrazioniForDelete(it, endDelegate);
    } else {
      endDelegate.execute(null);
    }
  }
  
  private CalendarPlugin.Event instantiateCalEvent(Prescrizione prescrizione, Somministrazione somministrazione) {
    CalendarPlugin.Event calEvent = new CalendarPlugin.Event();
    
    if (somministrazione != null) {
      calEvent.setStartDate(somministrazione.getData());
    } else {
      calEvent.setStartDate(prescrizione.getDataInizio());
    }
    
    if (somministrazione != null) {
      calEvent.setEndDate(CalendarUtil.copyDate(somministrazione.getData()));
      Time endTime = Time.fromDate(calEvent.getEndDate());
      endTime.incMinutes(5).setInDate(calEvent.getEndDate());
    } else {
      if (prescrizione.getDataFine() != null) {
        calEvent.setEndDate(prescrizione.getDataFine());
      } else {
        calEvent.setEndDate(GwtUtils.getDate(31, 12, 2099));
      }
    }
    
    if (somministrazione != null) {
      calEvent.setTitle(prescrizione.getNome() + " at " + somministrazione.getOrario());
      if (MGWT.getOsDetection().isIOs()) {
        calEvent.setNotes("Tap here: therapy://open");
      } else {
        calEvent.setNotes("Keep the pill!");
      }
    }
    
    calEvent.setLocation("#"+prescrizione.getId());
    
    return calEvent;
  }
  
  // TODO
  private void saveRemoteSomministrazione (Somministrazione somministrazione, final Delegate<Somministrazione> delegate) {
    PhonegapLog.log("saving remote " + somministrazione);
    delegate.execute(somministrazione);
  }
  
  private void saveCalEvent (Somministrazione somministrazione) {
    CalendarPlugin.Event calEvent = instantiateCalEvent(somministrazione.getPrescrizione(), somministrazione);
    PhonegapLog.log("creating " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.createEvent(calEvent);
  }
  
  // TODO
  private void deleteRemoteSomministrazione (Somministrazione somministrazione, final Delegate<Somministrazione> delegate) {
    PhonegapLog.log("deleting remote " + somministrazione);
    delegate.execute(somministrazione);
  }
  
  private void deleteCalEvent (Somministrazione somministrazione) {
    CalendarPlugin.Event calEvent = instantiateCalEvent(somministrazione.getPrescrizione(), somministrazione);
    PhonegapLog.log("deleting " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.deleteEvent(calEvent);
  }

  public void findCalEvents (Prescrizione prescrizione) {
    CalendarPlugin.Event calEvent = instantiateCalEvent(prescrizione, null);
    PhonegapLog.log("finding " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.findEvent(calEvent, new Delegate<List<CalendarPlugin.Event>>() {
      public void execute(List<Event> events) {
        for (Event event : events) {
          PhonegapLog.log("found " + event);
        }
      }
    });
  }  
  
}