package it.mate.quilook.server.services.impl;

import it.mate.quilook.server.services.MailAdapter;

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


}
