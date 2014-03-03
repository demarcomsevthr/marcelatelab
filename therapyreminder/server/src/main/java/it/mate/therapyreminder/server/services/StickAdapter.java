package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.model.StickMail;

import java.util.List;

public interface StickAdapter {

  public StickMail create(StickMail entity);
  
  public void checkScheduledMails();
  
  public List<StickMail> findMailsByUser(RemoteUser user);
  
  public List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  public void delete(List<StickMail> entities);
  
}
