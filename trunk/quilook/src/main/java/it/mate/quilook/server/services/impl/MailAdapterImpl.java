package it.mate.quilook.server.services.impl;

import it.mate.econyx.server.services.MailAdapter;
import it.mate.econyx.shared.model.PortalUser;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailAdapterImpl implements MailAdapter {

  private static final Logger logger = Logger.getLogger(MailAdapterImpl.class);

  private JavaMailSender mailSender;

  private SimpleMailMessage mailTemplate;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setMailTemplate(SimpleMailMessage mailTemplate) {
    this.mailTemplate = mailTemplate;
  }

  @Override
  public void sendActivationMail(PortalUser user, String activationUrl) throws MessagingException {
    
  }

  @Override
  public void sendOrderStateMail(PortalUser user, String mailText, String attachName, byte[] attachBuffer) throws MessagingException {
    
  }
  
  
  


}
