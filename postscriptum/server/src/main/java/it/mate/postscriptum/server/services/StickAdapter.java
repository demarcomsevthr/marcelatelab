package it.mate.postscriptum.server.services;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.List;

public interface StickAdapter {

  public StickMail create(StickMail entity, String devInfoId);
  
  public void checkScheduledMails();
  
  public List<StickMail> findMailsByUser(RemoteUser user);
  
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  public void delete(List<StickMail> entities);
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  
  public void sendSmsTest(String to, String msg);
  
}
