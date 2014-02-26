package it.mate.postscriptum.server.services;

import it.mate.postscriptum.shared.model.StickMail;

import javax.mail.MessagingException;

public interface MailAdapter {

  public void sendStickMail (StickMail mail) throws MessagingException;
  
}
