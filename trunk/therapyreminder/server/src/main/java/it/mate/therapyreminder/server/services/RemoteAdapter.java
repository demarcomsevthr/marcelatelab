package it.mate.therapyreminder.server.services;


public interface RemoteAdapter {
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);  

  public void refresh();
  
  /* 

  public StickMail create(StickMail entity);
  
  public void checkScheduledMails();
  
  public List<StickMail> findMailsByUser(RemoteUser user);
  
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  public void delete(List<StickMail> entities);
  
  */
  
}
