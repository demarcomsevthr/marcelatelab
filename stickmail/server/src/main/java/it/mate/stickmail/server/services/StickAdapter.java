package it.mate.stickmail.server.services;

import it.mate.stickmail.shared.model.StickMail;

public interface StickAdapter {

  public StickMail create(StickMail entity);
  
  public void checkScheduledMails();
  
}
