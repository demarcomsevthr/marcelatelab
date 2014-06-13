package it.mate.therapyreminder.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.FindContext;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.therapyreminder.server.model.AccountDs;
import it.mate.therapyreminder.server.model.DevInfoDs;
import it.mate.therapyreminder.server.model.SomministrazioneDs;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Service
public class RemoteAdapterImpl implements RemoteAdapter {
  
  private static Logger logger = Logger.getLogger(RemoteFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  private static final int MINUTI_CONTROLLO_SOMMINISTRAZIONI_SCADUTE = 30;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  @Override
  public void scheduledChecks() {
    Date somministrazioniScaduteCheckTime = addMinutesToDate(new Date(), (-1) * MINUTI_CONTROLLO_SOMMINISTRAZIONI_SCADUTE) ;
    List<SomministrazioneDs> somministrazioniScadute = dao.findList(new FindContext<SomministrazioneDs>(SomministrazioneDs.class)
          .setResultAsList(true)
          .setFilter("data < dataParam && stato == statoParam")
          .setParameters(Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(Date.class, "dataParam"),
              new ParameterDefinition(Integer.class, "statoParam")
          }))
          .setParamValues(new Object[] {
              somministrazioniScaduteCheckTime,
              Somministrazione.STATO_SCHEDULATA
          })
        );
    if (somministrazioniScadute != null) {
      for (SomministrazioneDs somministrazione : somministrazioniScadute) {
        LoggingUtils.debug(getClass(), "FOUND SOMMINISTRAZIONE SCADUTA " + somministrazione);
        if (somministrazione.getEmailTutor() != null) {
          AccountDs account = dao.findById(AccountDs.class, somministrazione.getAccountId());
          MailAdapter mailAdapter = AdapterUtil.getMailAdapter();
          try {
            mailAdapter.sendNotificationMail(somministrazione, account);
            somministrazione.setStato(Somministrazione.STATO_NOTIFICATA_AL_TUTOR);
            dao.update(somministrazione);
          } catch (MessagingException ex) {
            LoggingUtils.error(getClass(), "error", ex);
            somministrazione.setStato(Somministrazione.STATO_NOTIFICATION_FAILURE);
            dao.update(somministrazione);
          }
        }
      }
    }
  }
  
  public void debugAnticipaDataSomministrazioni() {
    List<SomministrazioneDs> allSomministrazioni = dao.findAll(SomministrazioneDs.class);
    if (allSomministrazioni != null) {
      for (SomministrazioneDs somministrazione : allSomministrazioni) {
        somministrazione.setData(addMinutesToDate(somministrazione.getData(), -60));
        somministrazione = dao.update(somministrazione);
        LoggingUtils.debug(getClass(), "ANTICIPATA DATA SOMMINISTRAZIONE " + somministrazione);
      }
    }
  }
  
  private Date addMinutesToDate(Date date, int amount) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.add(GregorianCalendar.MINUTE, amount);
    date = cal.getTime();
    return date;
  }
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp) {
    DevInfoDs ds = null;
    if (devUuid != null) {
      ds = dao.findSingle(DevInfoDs.class, "devUuid == devUuidParam" ,
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(String.class, "devUuidParam")
          }) , null, devUuid);
    }
    if (ds == null) {
      ds = new DevInfoDs();
      ds.setOs(os);
      ds.setLayout(layout);
      ds.setDevName(devName);
      ds.setPhgVersion(phgVersion);
      ds.setPlatform(platform);
      ds.setDevUuid(devUuid);
      ds.setDevVersion(devVersion);
      ds.setCreated(new Date());
      ds.setDevIp(devIp);
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    }
    if (ds != null) {
      return ds.getId();
    } else {
      return null;
    }
  }
  
  public Account createAccount(Account entity) {
    AccountDs ds = null;
    // controllo se per errore arrivano due create dallo stesso device
    if (entity.getDevInfoId() != null) {
      Key devInfoKey = KeyFactory.stringToKey(entity.getDevInfoId());
      ds = dao.findSingle(AccountDs.class, "devInfoId == devInfoIdParam", 
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(Key.class, "devInfoIdParam")
          }), 
        null, devInfoKey );
      if (ds != null) {
        ds.setName(entity.getName());
        ds.setEmail(entity.getEmail());
        ds = dao.update(ds);
      }
    }
    if (ds == null) {
      ds = CloneUtils.clone(entity, AccountDs.class);
      ds = dao.create(ds);
      LoggingUtils.debug(getClass(), "created account " + ds);
    }
    return CloneUtils.clone (ds, AccountTx.class);
  }
  
  public Account updateAccount(Account entity) {
    AccountDs ds = CloneUtils.clone(entity, AccountDs.class);
    ds = dao.update(ds);
    return CloneUtils.clone (ds, AccountTx.class);
  }
  
  public Somministrazione saveSomministrazione(Somministrazione txSomministrazione, Account txAccount, String devInfoId) {
    SomministrazioneDs dsSomministrazione = CloneUtils.clone(txSomministrazione, SomministrazioneDs.class);
    AccountDs dsAccount = CloneUtils.clone(txAccount, AccountDs.class);
    dsSomministrazione.setAccountId(dsAccount.getKey());
    dsSomministrazione.setDevInfoId(devInfoId);
    
    LoggingUtils.debug(getClass(), "received devInfoId = " + devInfoId + " somministrazione = " + dsSomministrazione + " account = " + dsAccount);
    
    SomministrazioneDs exSomministrazione = null;
    if (dsSomministrazione.getKey() != null) {
      exSomministrazione = dao.findById(SomministrazioneDs.class, dsSomministrazione.getKey());
    }
    if (exSomministrazione != null) {
      exSomministrazione.setLocalId(dsSomministrazione.getLocalId());
      exSomministrazione.setData(dsSomministrazione.getData());
      exSomministrazione.setQuantita(dsSomministrazione.getQuantita());
      exSomministrazione.setOrario(dsSomministrazione.getOrario());
      exSomministrazione.setStato(dsSomministrazione.getStato());
      exSomministrazione.setDevInfoId(dsSomministrazione.getDevInfoId());
      exSomministrazione.setAccountId(dsSomministrazione.getAccountId());
      exSomministrazione.setPrescrizione(txSomministrazione.getPrescrizione());
      dsSomministrazione = dao.update(exSomministrazione);
      LoggingUtils.debug(getClass(), "updated " + dsSomministrazione);
    } else {
      dsSomministrazione = dao.create(dsSomministrazione);
      LoggingUtils.debug(getClass(), "created " + dsSomministrazione);
    }
    
    txSomministrazione = CloneUtils.clone(dsSomministrazione, SomministrazioneTx.class);
    return txSomministrazione;
  }
  
}
