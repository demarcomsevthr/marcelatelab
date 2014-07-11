package it.mate.postscriptum.server.services;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.impl.StickMailTx2;
import it.mate.postscriptum.shared.service.AdapterException;

import java.util.List;

public interface StickAdapter {

  public StickMail create(StickMail entity, String devInfoId);
  
  public void checkScheduledMails();
  
  public List<StickMail> findMailsByUser(RemoteUser user);
  
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  public void delete(List<StickMail> entities);
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  
  public void sendSmsTest(String to, String msg);
  
  /////////////////////
  
  public StickSms createSMS(StickSms entity) throws AdapterException;
  
  public void checkScheduledSMSs();
  
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user);

  public void deleteSMS(List<StickSms> entities);
  
  /////////////////////
  
  public StickMailTx2 createV2(StickMailTx2 entity);
  
}
