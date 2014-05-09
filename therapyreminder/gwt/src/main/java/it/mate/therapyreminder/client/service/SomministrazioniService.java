package it.mate.therapyreminder.client.service;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.CalendarPlugin;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.ui.client.MGWT;

public class SomministrazioniService {

  private AppSqlDao appSqlDao = AppClientFactory.IMPL.getGinjector().getAppSqlDao();
  
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
  
  public void sviluppoSomministrazioni(final Prescrizione prescrizione) {
    appSqlDao.findLastSomministrazioneByPrescrizione(prescrizione, new Delegate<Somministrazione>() {
      public void execute(Somministrazione ultimaSomministrazione) {
        Date dataUltimaSomministrazione = null;
        if (ultimaSomministrazione != null) {
          dataUltimaSomministrazione = ultimaSomministrazione.getData();
        }
        sviluppoSomministrazioni(prescrizione, dataUltimaSomministrazione);
      }
    });
  }

  private void sviluppoSomministrazioni(Prescrizione prescrizione, Date dataUltimaSomministrazione) {
    
    if (dataUltimaSomministrazione == null) {
      dataUltimaSomministrazione = prescrizione.getDataInizio();
      sviluppoSomministrazioniPerGiorno(prescrizione, dataUltimaSomministrazione);
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
      
      sviluppoSomministrazioniPerGiorno(prescrizione, dataSomministrazione);
      
      dataUltimaSomministrazione = dataSomministrazione;
      
    }
    
  }
  
  private void sviluppoSomministrazioniPerGiorno(final Prescrizione prescrizione, Date dataSomministrazione) {
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
      appSqlDao.saveSomministrazione(somministrazione, new Delegate<Somministrazione>() {
        public void execute(Somministrazione somministrazione) {
          PhonegapUtils.log("created " + somministrazione);
          createCalEvent(prescrizione, somministrazione);
        }
      });
    }
  }
  
  private void createCalEvent (Prescrizione prescrizione, Somministrazione somministrazione) {
    Date startDate = somministrazione.getData();
    
    Date endDate = CalendarUtil.copyDate(somministrazione.getData());
    Time endTime = Time.fromDate(endDate);
    endTime.incMinutes(5).setInDate(endDate);
    
    CalendarPlugin.Event calEvent = new CalendarPlugin.Event();
    calEvent.setTitle(prescrizione.getNome() + " at " + somministrazione.getOrario());
    if (MGWT.getOsDetection().isIOs()) {
      calEvent.setNotes("Tap here: therapy://open");
    } else {
      calEvent.setNotes("Keep the pill!");
    }
    calEvent.setStartDate(startDate);
    calEvent.setEndDate(endDate);
    
    PhonegapUtils.log("creating " + calEvent);
    
    CalendarPlugin.createEvent(calEvent);
    
  }
  
}