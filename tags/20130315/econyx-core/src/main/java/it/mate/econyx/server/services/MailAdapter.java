package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.PortalUser;

import javax.mail.MessagingException;

public interface MailAdapter {

  public void sendActivationMail (PortalUser user, String activationUrl) throws MessagingException;
  
  public void sendOrderStateMail(PortalUser user, String mailText, String attachName, byte[] attachBuffer) throws MessagingException;
  
}
