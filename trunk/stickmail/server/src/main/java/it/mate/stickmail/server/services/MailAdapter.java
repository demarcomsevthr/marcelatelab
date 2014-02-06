package it.mate.stickmail.server.services;

import it.mate.stickmail.shared.model.StickMail;

import javax.mail.MessagingException;

public interface MailAdapter {

  public void sendStickMail (StickMail mail) throws MessagingException;
  
}
