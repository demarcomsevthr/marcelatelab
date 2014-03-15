package it.mate.postscriptum.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.postscriptum.server.model.DevInfoDs;
import it.mate.postscriptum.server.model.StickMailDs;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.impl.StickMailTx;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

@Service
public class StickAdapterImpl implements StickAdapter {
  
  private static Logger logger = Logger.getLogger(StickFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  private static final String TWILIO_ACCOUNT_SID = "ACcf0a6793d764c92b5be2260293a282bb";
  
  private static final String TWILIO_AUTH_TOKEN = "fdc676c52e47bc27ee31d4a91a2094c9";
  
  private static final String TWILIO_FROM_NUMBER = "+390909100305";
  
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  /** 
   * 
   * TO DO: check max number scheduled email per account
   * 
   * "In the free version you can have up to 10 scheduled emails per account"
   * 
   */
  
  @Override
  public StickMail create(StickMail entity, String devInfoId) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    if (devInfoId != null) {
      ds.setDevInfoId(devInfoId);
    }
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }

  @Override
  public void checkScheduledMails() {
    Date NOW = new Date();
    LoggingUtils.debug(getClass(), "NOW IS " + NOW);
    List<StickMail> mails = findAllMails();
    for (StickMail mail : mails) {
      if (StickMail.Utils.isScheduled(mail)) {
        LoggingUtils.debug(getClass(), "found mail scheduled on " + mail.getScheduled());
        if (mail.getScheduled().before(NOW)) {
          LoggingUtils.debug(getClass(), "SENDING MAIL " + mail);
          try {
            AdapterUtil.getMailAdapter().sendStickMail(mail);
            mail.setState(StickMail.STATE_NOTIFIED);
            update(mail);
          } catch (MessagingException ex) {
            LoggingUtils.error(getClass(), "error", ex);
          }
        }
      }
    }
  }
  
  public List<StickMail> findAllMails() {
    List<StickMailDs> mails = dao.findAll(StickMailDs.class);
    return CloneUtils.clone(mails, StickMailTx.class, StickMail.class);
  }

  public StickMail update(StickMail entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    try {
      StickMailDs origDs = dao.findById(StickMailDs.class, entity.getId());
      if (origDs.getDevInfoId() != null) {
        ds.setDevInfoId(origDs.getDevInfoId());
      }
    } catch (Exception ex) { }
    ds = dao.update(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }
  
  @Override
  public List<StickMail> findMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam", String.class.getName() + " userIdParam", null, user.getUserId() );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }

  @Override
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickMail.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }
  
  public void delete(List<StickMail> entities) {
    if (entities == null)
      return;
    for (StickMail entity : entities) {
      StickMailDs mail = dao.findById(StickMailDs.class, entity.getId());
      dao.delete(mail);
    }
  }

  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
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
      ds = dao.create(ds);
    }
    if (ds != null) {
      return ds.getId();
    } else {
      return null;
    }
  }
  
  
  public void sendSmsTest(String to, String msg) {
    
    try {
      LoggingUtils.debug(getClass(), "starting twilio rest client - to = " + to);
      TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
      
      // Build a filter for the SmsList
      Map<String, String> params = new HashMap<String, String>();
      params.put("Body", "From Twilio: " + msg);
      params.put("To", to);
      params.put("From", TWILIO_FROM_NUMBER);
   
      LoggingUtils.debug(getClass(), "getting sms factory");
      SmsFactory messageFactory = client.getAccount().getSmsFactory();
      LoggingUtils.debug(getClass(), "creating message");
      Sms message = messageFactory.create(params);
      LoggingUtils.debug(getClass(), "message created - sid = " + message.getSid());
      
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), "error", ex);
      logger.error("error", ex);
    }
  }
  
}
