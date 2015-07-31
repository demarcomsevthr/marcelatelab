package it.mate.postscriptum.server.services;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.service.AdapterException;

import java.util.List;

public interface StickAdapter {

  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  
  public void sendSmsTest(String to, String msg);
  
  public void checkScheduledMails();
  
  public void checkScheduledSMSs();
  
  /////////////////////
  /////////////////////
  /////////////////////
  
  public StickMail create(StickMail entity, String devInfoId);
  public List<StickMail> findMailsByUser(RemoteUser user);
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  public void delete(List<StickMail> entities);
  
  public StickMail2 createV2(StickMail2 entity);
  public List<StickMail2> findMailsByUserV2(RemoteUser user);
  public List<StickMail2> findScheduledMailsByUserV2(RemoteUser user);
  
  
  /////////////////////
  /////////////////////
  /////////////////////
  
  public StickSms createSMS(StickSms entity) throws AdapterException;
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user);
  public void deleteSMS(List<StickSms> entities);
  
  public StickSms2 createOrUpdateSMSV2(StickSms2 entity) throws AdapterException;
  public List<StickSms2> findScheduledSMSsByUserV2(RemoteUser user);

  
  public void updateUserInfos();
  
  public void purgeNotifiedSMSs();
  
}
