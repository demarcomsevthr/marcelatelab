package it.mate.protoph.server.services;

import javax.mail.MessagingException;


public interface MailAdapter {

  public void sendMailNotification(String messageBody, String emailAddr) throws MessagingException;
  
}
