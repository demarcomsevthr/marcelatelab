package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.StickMail;

import javax.mail.MessagingException;

public interface MailAdapter {

  public void sendStickMail (StickMail mail) throws MessagingException;
  
}
