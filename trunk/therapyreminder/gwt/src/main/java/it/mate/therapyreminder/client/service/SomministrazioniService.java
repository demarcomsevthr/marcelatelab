package it.mate.therapyreminder.client.service;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.plugins.CalendarPlugin;
import it.mate.phgcommons.client.plugins.CalendarPlugin.Event;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.ui.client.MGWT;

public class SomministrazioniService {

  private AppSqlDao dao = AppClientFactory.IMPL.getGinjector().getAppSqlDao();
  
  private static SomministrazioniService instance;
  
  private static final int DEFAULT_SVILUPPO_GIORNALIERO = 5;
  private static final int DEFAULT_SVILUPPO_SETTIMANALE = 5;
  private static final int DEFAULT_SVILUPPO_MENSILE = 5;
  
  public static SomministrazioniService getInstance() {
    if (instance == null)
      instance = new SomministrazioniService();
    return instance;
  }
  
  protected SomministrazioniService() {

  }
  
  public void cancellaSomministrazioni(final Prescrizione prescrizione, final Delegate<Void> endDelegate) {
    dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioni) {
        // TODO: cancellazione eventi calendario
        dao.deleteSomministrazioni(somministrazioni, endDelegate);
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

    Date dataLimiteSviluppoSomministrazioni = new Date();
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
      
      PhonegapUtils.log("dataSomministrazione = " + dataSomministrazione + " dataLimiteSviluppoSomministrazioni = " + dataLimiteSviluppoSomministrazioni);
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
          PhonegapUtils.log("created " + somministrazione);
          saveCalEvent(prescrizione, somministrazione);
        }
      });
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
  
  private void saveCalEvent (Prescrizione prescrizione, Somministrazione somministrazione) {
    CalendarPlugin.Event calEvent = instantiateCalEvent(prescrizione, somministrazione);
    PhonegapUtils.log("creating " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.createEvent(calEvent);
  }
  
  public void findCalEvents (Prescrizione prescrizione) {
    CalendarPlugin.Event calEvent = instantiateCalEvent(prescrizione, null);
    PhonegapUtils.log("finding " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.findEvent(calEvent, new Delegate<List<CalendarPlugin.Event>>() {
      public void execute(List<Event> events) {
        for (Event event : events) {
          PhonegapUtils.log("found " + event);
        }
      }
    });
  }  
  
}