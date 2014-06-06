package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.Account;


public interface RemoteAdapter {
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp);  

  public void refresh();
  
  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
  /* 

  public StickMail create(StickMail entity);
  
  public void checkScheduledMails();
  
  public List<StickMail> findMailsByUser(RemoteUser user);
  
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  public void delete(List<StickMail> entities);
  
  */
  
}
