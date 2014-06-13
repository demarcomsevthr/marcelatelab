package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.server.model.AccountDs;
import it.mate.therapyreminder.server.model.SomministrazioneDs;

import javax.mail.MessagingException;


public interface MailAdapter {

  /*
  public void sendStickMail (StickMail mail) throws MessagingException;
  */
  
  public void sendNotificationMail(SomministrazioneDs somministrazione, AccountDs account) throws MessagingException;
  
}
