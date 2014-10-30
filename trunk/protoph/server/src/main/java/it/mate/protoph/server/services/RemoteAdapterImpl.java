package it.mate.protoph.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.FindContext;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.protoph.server.model.AccountDs;
import it.mate.protoph.server.model.DevInfoDs;
import it.mate.protoph.server.model.SomministrazioneDs;
import it.mate.protoph.server.utils.Country;
import it.mate.protoph.shared.model.Account;
import it.mate.protoph.shared.model.Contatto;
import it.mate.protoph.shared.model.Somministrazione;
import it.mate.protoph.shared.model.impl.AccountTx;
import it.mate.protoph.shared.model.impl.SomministrazioneTx;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

@Service
public class RemoteAdapterImpl implements RemoteAdapter {
  
  private static Logger logger = Logger.getLogger(RemoteFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  private static final int MINUTI_CONTROLLO_SOMMINISTRAZIONI_SCADUTE = 30;
  
  private static final String TWILIO_ACCOUNT_SID = "ACcf0a6793d764c92b5be2260293a282bb";
  
  private static final String TWILIO_AUTH_TOKEN = "fdc676c52e47bc27ee31d4a91a2094c9";
  
  private static final String TWILIO_FROM_NUMBER = "+16469821337";
  
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
        Integer statoSomministrazione = somministrazione.getStato();
        AccountDs account = dao.findById(AccountDs.class, somministrazione.getAccountId());
        if (somministrazione.getEmailTutor() != null) {
          MailAdapter mailAdapter = AdapterUtil.getMailAdapter();
          try {
            LoggingUtils.debug(getClass(), "sending mail notification for somministrazione " + somministrazione);
            mailAdapter.sendMailNotification(composeNotificationMessage(somministrazione, account), somministrazione.getEmailTutor());
            statoSomministrazione = Somministrazione.STATO_NOTIFICATA_AL_TUTOR;
          } catch (MessagingException ex) {
            LoggingUtils.error(getClass(), "error", ex);
            statoSomministrazione = Somministrazione.STATO_NOTIFICATION_FAILURE;
          }
        }
        if (somministrazione.getTelefonoTutor() != null && somministrazione.getTelefonoTutor().trim().length() > 0) {
          try {
            LoggingUtils.debug(getClass(), "sending sms notification for somministrazione " + somministrazione);
            sendSmsNotification(composeNotificationMessage(somministrazione, account), somministrazione.getTelefonoTutor());
          } catch (Exception ex) {
            LoggingUtils.error(getClass(), "error", ex);
            statoSomministrazione = Somministrazione.STATO_NOTIFICATION_FAILURE;
          }
        }
        if (statoSomministrazione != null) {
          somministrazione.setStato(statoSomministrazione);
          dao.update(somministrazione);
        }
      }
    }
  }
  
  private String composeNotificationMessage(SomministrazioneDs somministrazione, AccountDs account) {
    StringBuffer text = new StringBuffer();
    text.append("La somministrazione del farmaco " + somministrazione.getNomeFarmaco());
    text.append(" schedulata dall'utente " + account.getName());
    text.append(" per le ore " + somministrazione.getOrario());
    text.append(" non e' stata eseguita.");
    text.append("\n");
    text.append("Si prega di avvisare il paziente.");
    return text.toString();
  }
  
  private void sendSmsNotification(String messageBody, String phoneNumber) throws Exception {
    TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    Map<String, String> params = new HashMap<String, String>();
    params.put("Body", messageBody);
    params.put("To", phoneNumber);
    params.put("From", TWILIO_FROM_NUMBER);
    LoggingUtils.debug(getClass(), "getting sms factory");
    SmsFactory messageFactory = client.getAccount().getSmsFactory();
    LoggingUtils.debug(getClass(), "creating message");
    Sms message = messageFactory.create(params);
    LoggingUtils.debug(getClass(), "message created - sid = " + message.getSid());
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
      correggiPrefissoInternazionale(exSomministrazione);
      dsSomministrazione = dao.update(exSomministrazione);
      LoggingUtils.debug(getClass(), "updated " + dsSomministrazione);
    } else {
      correggiPrefissoInternazionale(dsSomministrazione);
      dsSomministrazione = dao.create(dsSomministrazione);
      LoggingUtils.debug(getClass(), "created " + dsSomministrazione);
    }
    
    txSomministrazione = CloneUtils.clone(dsSomministrazione, SomministrazioneTx.class);
    return txSomministrazione;
  }
  
  private void correggiPrefissoInternazionale(SomministrazioneDs somministrazione) {
    if (somministrazione.getTelefonoTutor() != null && somministrazione.getTelefonoTutor().trim().length() > 0) {
      if (!somministrazione.getTelefonoTutor().trim().startsWith("+")) {
        //manca il prefisso
        if (somministrazione.getLanguage() != null) {
          List<Country> countries = Country.getList();
          for (Country country : countries) {
            if (somministrazione.getLanguage().toLowerCase().contains(country.getCode().toLowerCase())) {
              String prefix = country.getInternationalPrefix();
              if (prefix != null) {
                somministrazione.setTelefonoTutor("+" + prefix + somministrazione.getTelefonoTutor());
                return;
              }
            }
          }
        }
      }
    }
  }
  
  @Override
  public void deleteSomministrazione(Somministrazione txSomministrazione) {
    SomministrazioneDs dsSomministrazione = CloneUtils.clone(txSomministrazione, SomministrazioneDs.class);
    LoggingUtils.debug(getClass(), "deleting somministrazione = " + dsSomministrazione);
    dao.delete(dsSomministrazione);
  }

  @Override
  public void updateDatiContatto(Contatto txTutor, Account txAccount) {
    Date now = new Date();
    AccountDs dsAccount = CloneUtils.clone(txAccount, AccountDs.class);
    List<SomministrazioneDs> somministrazioniDaAggiornare = dao.findList(new FindContext<SomministrazioneDs>(SomministrazioneDs.class)
        .setResultAsList(true)
        .setFilter("data > dataParam && accountId == accountIdParam")
        .setParameters(Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(Date.class, "dataParam"),
            new ParameterDefinition(Key.class, "accountIdParam")
        }))
        .setParamValues(new Object[] {
            now,
            dsAccount.getKey()
        })
      );
    if (somministrazioniDaAggiornare != null) {
      for (SomministrazioneDs dsSomministrazione : somministrazioniDaAggiornare) {
        dsSomministrazione.setTutor(txTutor);
        dao.update(dsSomministrazione);
      }
    }
  }
  
}
