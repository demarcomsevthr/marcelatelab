package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.MailRecipient;
import it.mate.econyx.server.services.MailAdapter;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.util.PropertyConstants;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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

  public void sendOrderStateMail(PortalUser user, String mailText, String attachName, byte[] attachBuffer) throws MessagingException {
    StringBuffer text = new StringBuffer(mailText);
    doSendMail(new MailRecipient(user.getEmailAddress()), "Conferma ordine", text, attachName, attachBuffer);
  }

  public void sendActivationMail(PortalUser user, String activationUrl) throws MessagingException {
    StringBuffer text = new StringBuffer();
    text.append(String.format("Buongiorno %s,\n", user.getScreenName()));
    text.append(String.format("Ti sei registrato sul sito %s.\n\n", PropertiesHolder.get("azienda.siteUrl")));
    text.append(String.format("Da oggi potrai collegarti per effettuare acquisti online.\n"));
    text.append(String.format("Per farlo devi confermare la tua registrazione accedendo a questo link:\n\n"));
    text.append(String.format(String.format("<a href=\"%1$s\">%1$s</a>\n\n", activationUrl)));
    doSendMail(new MailRecipient(user.getEmailAddress()), "Conferma registrazione", text, null, null);
  }

  protected void doSendMail (MailRecipient recipient, String subject, StringBuffer text, String attachName, byte[] attachBuffer) throws MessagingException {

    JavaMailSender javaMailSender = (JavaMailSender) mailSender;
    MimeMessage msg = javaMailSender.createMimeMessage();
    Multipart mp = new MimeMultipart(); 
    
    String escapedContent = escapeMailText(text.toString());
    
    MimeBodyPart htmlPart = new MimeBodyPart(); 
    htmlPart.setContent(escapedContent, "text/html"); 
    
    msg.setFrom(new InternetAddress(mailTemplate.getFrom())); 
    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail())); 
    msg.setSubject(subject); 
    
    logger.debug("@@@@@@@@ sending mail to " + recipient.getEmail());
    logger.debug("@@@@@@@@ subject = " + subject);
    logger.debug("@@@@@@@@ content = \n\n" + escapedContent + "\n");
    
    MimeBodyPart attachment = null; 
    if (attachName != null && attachBuffer != null) {
      attachment = new MimeBodyPart(); 
      DataSource src = new ByteArrayDataSource(attachBuffer, "application/pdf"); 
      attachment.setFileName(attachName); 
      attachment.setDataHandler(new DataHandler(src)); 
    }

    mp.addBodyPart(htmlPart); 
    
    if (attachment != null) {
      mp.addBodyPart(attachment); 
    }

    msg.setContent(mp); 
    msg.saveChanges();//I think this is necessary, 

    javaMailSender.send(msg);
    
  }

  @SuppressWarnings("unused")
  protected void doSendMail0 (MailRecipient recipient, String subject, StringBuffer text, String attachName, byte[] attachBuffer) throws MessagingException {
    JavaMailSender javaMailSender = (JavaMailSender) mailSender;
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    // MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
    // "UTF-8");
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
    helper.setSubject(subject);
    helper.setFrom(mailTemplate.getFrom());
    addFixedMailFooter(text);
    // helper.setText("", escapeMailText(text.toString()));
    helper.setText(text.toString());
    helper.setTo(recipient.getEmail());
    if (attachName != null && attachBuffer != null) {
      helper.addAttachment(attachName, new ByteArrayResource(attachBuffer));
      logger.debug(String.format("sending email to %s with attachment %s with len %s bytes", recipient.getEmail(), attachName,
          attachBuffer.length));
    } else {
      logger.debug(String.format("sending email to %s", recipient.getEmail()));
    }
    javaMailSender.send(mimeMessage);
  }

  private void addFixedMailFooter(StringBuffer text) {
    String organizationEmailAddress = (String) PropertiesHolder.get(PropertyConstants.MAIL_TEMPLATE_ORGANIZATION_EMAIL_ADDRESS);
    text.append("\n\n\n\n");
    text.append("ATTENZIONE: questo messaggio &egrave; stato generato automaticamente.\n");
    text.append("Non rispondere a questa e-mail; per eventuali comunicazioni accedi a " + organizationEmailAddress
        + " e usa i canali preposti.\n");
    text.append("Grazie della collaborazione\n");
  }

  private String escapeMailText(String text) {
    text = text.replace("\n", "<br>");
    return text;
  }

}
