package it.mate.postscriptum.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.DateUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.postscriptum.server.model.DevInfoDs;
import it.mate.postscriptum.server.model.StickMailDs;
import it.mate.postscriptum.server.model.StickSmsDs;
import it.mate.postscriptum.server.model.UserInfoDs;
import it.mate.postscriptum.server.utils.Countries;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.model.impl.StickMailTx;
import it.mate.postscriptum.shared.model.impl.StickMailTx2;
import it.mate.postscriptum.shared.model.impl.StickSmsTx;
import it.mate.postscriptum.shared.model.impl.StickSmsTx2;
import it.mate.postscriptum.shared.service.AdapterException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.instance.Sms;

@Service
public class StickAdapterImpl implements StickAdapter {
  
  private static Logger logger = Logger.getLogger(StickFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  private static final String TWILIO_ACCOUNT_SID = "ACcf0a6793d764c92b5be2260293a282bb";
  
  private static final String TWILIO_AUTH_TOKEN = "fdc676c52e47bc27ee31d4a91a2094c9";
  
  private static final String TWILIO_FROM_NUMBER = "+16469821337";
  
  private static final int MAX_SCHEDULED_SMS_FREE_QUOTA = 5;
  
  private static final int MAX_SMS_PER_DAY_FREE_QUOTA = 5;
  
  // TODO
  // ATTENZIONE: PER IL MOMENTO NON IMPONGO IL CHECK (METTO UN LIMITE ALTISSIMO)
  // QUANDO DECIDERO' DI METTERLO BASTERA' RIPRISTINARE UN VALORE ADEGUATO (10)
  private static final int MAX_TOTAL_SMS_FREE_QUOTA = 9999;
//private static final int MAX_TOTAL_SMS_FREE_QUOTA = 10;
  
  private static final String PAID_CLIENT_TYPE_1 = "P1";
  
  private static final Date v2Date = DateUtils.stringToDate("01/07/2014", "dd/MM/yyyy");
  
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  @Override
  public void checkScheduledMails() {
    Date NOW = new Date();
    LoggingUtils.debug(getClass(), "NOW IS " + NOW);
    List<StickMail> mails = findAllScheduledMails();
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
  
  /** 
   * 
   * TO DO: check max number scheduled email per account
   * 
   * "In the free version you can have up to 10 scheduled emails per account"
   * 
   */
  
  @Override
  @SuppressWarnings("deprecation")
  public StickMail create(StickMail entity, String devInfoId) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    if (devInfoId != null) {
      ds.setDevInfoId(devInfoId);
    }
    LoggingUtils.debug(getClass(), "creating " + entity);
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }

  @Override
  public StickMail2 createV2(StickMail2 entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    ds.setReceiverEmail(entity.getReceiverEmail());
    LoggingUtils.debug(getClass(), "creating " + entity);
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickMailTx2.class);
  }

  public List<StickMail> findAllMails() {
    List<StickMailDs> mails = dao.findAll(StickMailDs.class);
    return CloneUtils.clone(mails, StickMailTx2.class, StickMail.class);
  }
  
  public List<StickMail> findAllScheduledMails() {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, StickMail.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickMailTx2.class, StickMail.class);
  }
  
  public StickMail2 update(StickMail entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    try {
      StickMailDs origDs = dao.findById(StickMailDs.class, entity.getId());
      if (origDs.getDevInfoId() != null) {
        ds.setDevInfoId(origDs.getDevInfoId());
      }
    } catch (Exception ex) { }
    ds = dao.update(ds);
    return CloneUtils.clone (ds, StickMailTx2.class);
  }
  
  @Override
  @SuppressWarnings("deprecation")
  public List<StickMail> findMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam", String.class.getName() + " userIdParam", null, user.getUserId() );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }

  @Override
  public List<StickMail2> findMailsByUserV2(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam", String.class.getName() + " userIdParam", null, user.getUserId() );
    return CloneUtils.clone(results, StickMailTx2.class, StickMail2.class);
  }

  @Override
  @SuppressWarnings("deprecation")
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickMail.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }

  @Override
  public List<StickMail2> findScheduledMailsByUserV2(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickMail.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickMailTx2.class, StickMail2.class);
  }
  
  @Override
  public void delete(List<StickMail> entities) {
    if (entities == null)
      return;
    for (StickMail entity : entities) {
      LoggingUtils.debug(getClass(), "deleting " + entity);
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
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    }
    if (ds != null) {
      return ds.getId();
    } else {
      return null;
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  @Override
  @SuppressWarnings("deprecation")
  public StickSms createSMS(StickSms sms) throws AdapterException {
    List<StickSms> userSMSs = findSMSsByUserAndCreatedAfter(sms.getUser(), getMidnight());
    LoggingUtils.debug(getClass(), "createdSms.size " + (userSMSs != null ? userSMSs.size() : "null"));
    if (userSMSs != null && userSMSs.size() >= MAX_SMS_PER_DAY_FREE_QUOTA) {
      throw new AdapterException(String.format("You cannot submit more than %s SMS per day in this version", MAX_SMS_PER_DAY_FREE_QUOTA));
    }
    if (sms.getBody() == null || sms.getBody().length() <= 0) {
      throw new AdapterException("The text of the message cannot be empty");
    }
    /*
    if (sms.getBody().length() > 120) {
      throw new AdapterException("The lenght of the message cannot be more than 120 characters");
    }
    */
    StickSmsDs ds = CloneUtils.clone(sms, StickSmsDs.class);
    LoggingUtils.debug(getClass(), "creating " + ds);
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickSmsTx.class);
  }

  @Override
  public StickSms2 createOrUpdateSMSV2(StickSms2 sms) throws AdapterException {

    
    List<StickSms> userSMSListAfterV2 = findSMSsByUserAndCreatedAfter(sms.getUser(), v2Date);
    LoggingUtils.debug(getClass(), "createdSms.size " + (userSMSListAfterV2 != null ? userSMSListAfterV2.size() : "null"));
    if (PAID_CLIENT_TYPE_1.equals(sms.getClientType())) {
      //OK
    } else {
      if (userSMSListAfterV2 != null && userSMSListAfterV2.size() >= MAX_TOTAL_SMS_FREE_QUOTA) {
        throw new AdapterException("You reached the max number of allowed SMS for the free version");
      }
    }
    

    // 30/10/2014 - ripristinato
    List<StickSms> userSMSListAfterMidnight = findSMSsByUserAndCreatedAfter(sms.getUser(), getMidnight());
    LoggingUtils.debug(getClass(), "createdSms.size " + (userSMSListAfterMidnight != null ? userSMSListAfterMidnight.size() : "null"));
    if (userSMSListAfterMidnight != null && userSMSListAfterMidnight.size() >= MAX_SMS_PER_DAY_FREE_QUOTA) {
      throw new AdapterException(String.format("You cannot submit more than %s SMS per day in this version", MAX_SMS_PER_DAY_FREE_QUOTA));
    }
    
    
    if (sms.getBody() == null || sms.getBody().length() <= 0) {
      throw new AdapterException("The text of the message cannot be empty");
    }
    /*
    if (sms.getBody().length() > 120) {
      throw new AdapterException("The lenght of the message cannot be more than 120 characters");
    }
    */
    
    //TODO: quando sara pubblicata la PAID version:
    
    // se clientType = F0 && countSmsF0 > 10 >> BLOCK MESSAGE 'You have to upgrade version'
    
    // se clientType = F1 && countSmsF0 < 10 && countSmsF1 > 10 >> BLOCK MESSAGE 'You have to upgrade to PAID version'
    
    if (sms.getBody().trim().equalsIgnoreCase("test exception")) {
//    throw new AdapterException("Test exception: <a href='https://itunes.apple.com/us/app/post-scriptum-free/id830580894?ls=1&mt=8'>Go to App Store</a>");
      throw new AdapterException("In this version you can send up to 10 free text messages. If you want to send more, please upgrade to paid version (search 'Post Scriptum Paid' on the App Store). Thank you!");
    }

    if (sms.getBody().startsWith("testuser=")) {
      String realEmail = sms.getUser().getEmail();
      String testEmail = sms.getBody().substring(sms.getBody().indexOf("=") + 1);
      UserInfoDs realUserInfo = findUserInfoByEmail(realEmail);
      UserInfoDs testUserInfo = findUserInfoByEmail(testEmail);
      LoggingUtils.debug(getClass(), "realUserInfo = " + realUserInfo);
      LoggingUtils.debug(getClass(), "testUserInfo = " + testUserInfo);
    }
    
    if (sms.getCreated() == null) {
      throw new AdapterException("Creation date not set");
    }
    
    if (sms.getScheduled() == null) {
      throw new AdapterException("Scheduled date not set");
    }
    
    if (sms.getScheduled().before(sms.getCreated())) {
      throw new AdapterException("The scheduled date is in the past");
    }
    
    if (getMinutesBetween(sms.getCreated(), sms.getScheduled()) < 30) {
      throw new AdapterException("You have to set the scheduled time at least 30 minutes in the future");
    }
    
    // 14/10/2014
    if (sms.getReceiverNumber() != null && !(sms.getReceiverNumber().trim().startsWith("+"))) {
      if (sms.getLanguage() != null) {
        String prefix = Countries.getInternationalPrefixFromLanguage(sms.getLanguage());
        if (prefix != null) {
          sms.setReceiverNumber("+" + prefix + sms.getReceiverNumber());
        }
      }
    }
    
    if (sms.getReceiverName() != null && "undefined".equals(sms.getReceiverName().trim().toLowerCase())) {
      sms.setReceiverName("");
    }
    
    StickSmsDs ds = CloneUtils.clone(sms, StickSmsDs.class);
    
    if (PAID_CLIENT_TYPE_1.equals(sms.getClientType())) {
      LoggingUtils.debug(getClass(), "PAID USER");
    } else {
      try {
        UserInfoDs userInfo = findUserInfoByEmail(ds.getUserEmail());
        LoggingUtils.debug(getClass(), "found " + userInfo);
        Date loyalUserLimit = DateUtils.stringToDate("20141001", "yyyyMMdd");
        if (userInfo != null && userInfo.getFirstSms() != null && userInfo.getFirstSms().before(loyalUserLimit)) {
          LoggingUtils.debug(getClass(), "LOYAL USER");
        } else {
          LoggingUtils.debug(getClass(), "NEWBIE USER");
        }
      } catch (Exception ex) {
        LoggingUtils.error(getClass(), ex.getMessage());
      }
    }
    
    if (ds.getKey() == null) {
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    } else {
      LoggingUtils.debug(getClass(), "updating " + ds);
      ds = dao.update(ds);
    }
    
    return CloneUtils.clone (ds, StickSmsTx2.class);
  }
  
  private long getMinutesBetween(Date d1, Date d2) {
    long m2 = (d2.getTime() / 60000);
    long m1 = (d1.getTime() / 60000);
    return m2 - m1;
  }

  @Override
  @SuppressWarnings("deprecation")
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user) {
    List<StickSmsDs> results = dao.findList(StickSmsDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickSms.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickSmsTx.class, StickSms.class);
  }

  @Override
  public List<StickSms2> findScheduledSMSsByUserV2(RemoteUser user) {
    List<StickSmsDs> results = dao.findList(StickSmsDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickSms.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickSmsTx2.class, StickSms2.class);
  }
  
  @SuppressWarnings("deprecation")
  public List<StickSms> findSMSsByUserAndCreatedAfter(RemoteUser user, Date data) {
    List<StickSmsDs> results = dao.findList(StickSmsDs.class, "userId == userIdParam && created >= createdParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(Date.class, "createdParam")
        }), 
        null, user.getUserId(), data );
    return CloneUtils.clone(results, StickSmsTx.class, StickSms.class);
  }
  
  @Override
  public void checkScheduledSMSs() {
    Date NOW = new Date();
    LoggingUtils.debug(getClass(), "NOW IS " + NOW);
//  List<StickSms> smss = findAllSMSs();
    List<StickSms> smss = findAllScheduledSMSs();
    for (StickSms sms : smss) {
      if (StickSms.Utils.isScheduled(sms)) {
        LoggingUtils.debug(getClass(), "found sms scheduled on " + sms.getScheduled());
        if (sms.getScheduled().before(NOW)) {
          LoggingUtils.debug(getClass(), "SENDING SMS " + sms);
          try {
            sendSms(sms);
            sms.setState(StickSms.STATE_NOTIFIED);
            update(sms);
          } catch (Exception ex) {
            LoggingUtils.error(getClass(), "error", ex);
          }
        }
      }
    }
  }
  
  @SuppressWarnings("deprecation")
  public List<StickSms> findAllSMSs() {
    List<StickSmsDs> smss = dao.findAll(StickSmsDs.class);
    return CloneUtils.clone(smss, StickSmsTx.class, StickSms.class);
  }

  public List<StickSms> findAllScheduledSMSs() {
    List<StickSmsDs> results = dao.findList(StickSmsDs.class, "state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, StickSms.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickSmsTx2.class, StickSms.class);
  }
  
  public StickSms update(StickSms entity) {
    StickSmsDs ds = CloneUtils.clone(entity, StickSmsDs.class);
    ds = dao.update(ds);
    return CloneUtils.clone (ds, StickSmsTx2.class);
  }
  
  private void sendSms(StickSms sms) {
    try {
      LoggingUtils.debug(getClass(), "starting twilio rest client - with sms " + sms);
      TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
      String smsBody = sms.getBody();
      String body = "Post Scriptum: ";
      body += smsBody;
      body += " >> By " + sms.getUser().getEmail();
      
      LoggingUtils.debug(getClass(), "GETTING NEW MESSAGE FACTORY");
      MessageFactory messageFactory = client.getAccount().getMessageFactory();
      List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
      messageParams.add(new BasicNameValuePair("From", TWILIO_FROM_NUMBER));
      messageParams.add(new BasicNameValuePair("To", sms.getReceiverNumber()));
      messageParams.add(new BasicNameValuePair("Body", body));
      LoggingUtils.debug(getClass(), "creating message");
      Message message = messageFactory.create(messageParams);      
      LoggingUtils.debug(getClass(), "message created - sid = " + message.getSid());

    } catch (Exception ex) {
      LoggingUtils.error(getClass(), "error", ex);
      logger.error("error", ex);
    }
  }
  
  private void sendSms_OLD_VERSION (StickSms sms) {
    try {
      LoggingUtils.debug(getClass(), "starting twilio rest client - with sms " + sms);
      TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
      int maxLenght = 160 - 25 - sms.getUser().getEmail().length();
      String smsBody = sms.getBody();
      if (smsBody == null) {
        smsBody = "";
      }
      smsBody = smsBody.length() < maxLenght ? smsBody : smsBody.substring(0, maxLenght);
//    String body = "Post Scriptum: ";
      String body = "";
      body += smsBody;
      body += " >> By " + sms.getUser().getEmail();
//    body += " >> This is an automatically generated message: please do not reply.";
      // Build a filter for the SmsList
      Map<String, String> params = new HashMap<String, String>();
      params.put("Body", body);
      params.put("To", sms.getReceiverNumber());
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
  
  @Override
  public void deleteSMS(List<StickSms> entities) {
    if (entities == null)
      return;
    for (StickSms entity : entities) {
      LoggingUtils.debug(getClass(), "deleting " + entity);
      StickSmsDs sms = dao.findById(StickSmsDs.class, entity.getId());
      dao.delete(sms);
    }
  }

  private Date getMidnight() {
    Calendar cal = new GregorianCalendar();
    cal.setTime(new Date());
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
  
  public void updateUserInfos() {
    
    Date NOW = new Date();
    LoggingUtils.debug(getClass(), "STARTING USER INFO UPDATE AT " + NOW);
    
    List<StickSmsDs> smsList = findAllSMSDs();
    LoggingUtils.debug(getClass(), String.format("Fetched %s sms messages", smsList.size()));
    
    Collections.sort(smsList, new Comparator<StickSmsDs>() {
      public int compare(StickSmsDs s1, StickSmsDs s2) {
        if (s1.getCreated() != null && s2.getCreated() != null) {
          return s1.getCreated().compareTo(s2.getCreated());
        }
        return 0;
      }
    });
    
    List<UserInfoDs> userInfoList = findAllUserInfoDs();
    LoggingUtils.debug(getClass(), String.format("Fetched %s user infos", userInfoList.size()));

    // azzero i contatori
    for (UserInfoDs userInfo : userInfoList) {
      userInfo.setCountSmsF0(0);
      userInfo.setCountSmsF1(0);
      userInfo.setCountSmsP1(0);
    }
    
    LoggingUtils.debug(getClass(), String.format("Starting processing %s sms messages", smsList.size()));
    
    int counter = 0;
    
    for (StickSmsDs sms : smsList) {
      
      String userEmail = sms.getUserEmail();
      UserInfoDs userInfo = null;
      for (UserInfoDs item : userInfoList) {
        if (item.getUserEmail().equals(userEmail)) {
          userInfo = item;
          break;
        }
      }
      if (userInfo == null) {
        userInfo = new UserInfoDs();
        userInfo.setUserEmail(sms.getUserEmail());
        userInfoList.add(userInfo);
      }
      
      userInfo.setDevInfoId(sms.getDevInfoIdKey());
      userInfo.setUserId(sms.getUserId());
      
      userInfo.setLastClientType(sms.getClientType());
      userInfo.setLastClientVersion(sms.getClientVersion());
      userInfo.setLastIp(sms.getIp());
      userInfo.setLastLanguage(sms.getLanguage());
      userInfo.setLastReceiverName(sms.getReceiverName());

      if (sms.getCreated() != null) {
        if (userInfo.getLastSms() == null || sms.getCreated().after(userInfo.getLastSms())) {
          userInfo.setLastSms(sms.getCreated());
        }
        if (userInfo.getFirstSms() == null || sms.getCreated().before(userInfo.getFirstSms())) {
          userInfo.setFirstSms(sms.getCreated());
        }
      }
      
      if ("F1".equals(sms.getClientType())) {
        userInfo.setCountSmsF1(userInfo.getCountSmsF1().intValue() + 1);
      } else if ("P1".equals(sms.getClientType())) {
        userInfo.setCountSmsP1(userInfo.getCountSmsP1().intValue() + 1);
      } else {
        userInfo.setCountSmsF0(userInfo.getCountSmsF0().intValue() + 1);
      }
      
      if (counter % 100 == 0) {
        LoggingUtils.debug(getClass(), String.format("Processed %s sms - current user = %s", counter, userInfo));
      }
      
      counter ++;
      
    }
    
    LoggingUtils.debug(getClass(), String.format("Starting updating %s user infos", userInfoList.size()));
    
    counter = 0;

    for (UserInfoDs userInfo : userInfoList) {
      if (userInfo.getKey() == null) {
        userInfo = dao.create(userInfo);
      } else {
        userInfo = dao.update(userInfo);
      }
      if (counter % 100 == 0) {
        LoggingUtils.debug(getClass(), String.format("Updated %s user infos - current user = %s", counter, userInfo));
      }
      counter ++;
    }
    
    LoggingUtils.debug(getClass(), "FINISH USER INFO UPDATE");
    
  }
  
  private List<StickSmsDs> findAllSMSDs() {
    List<StickSmsDs> smss = dao.findAll(StickSmsDs.class);
    return smss;
  }

  private List<UserInfoDs> findAllUserInfoDs() {
    List<UserInfoDs> results = dao.findAll(UserInfoDs.class);
    if (results == null) {
      results = new ArrayList<UserInfoDs>();
    }
    return results;
  }
  
  private UserInfoDs findUserInfoByEmail(String email) {
    UserInfoDs userInfo = dao.findSingle(UserInfoDs.class, "userEmail == userEmailParam", Dao.Utils.buildParameters(new ParameterDefinition[] {
        new ParameterDefinition(String.class, "userEmailParam")
    }), null, email);
    return userInfo;
  }

}
