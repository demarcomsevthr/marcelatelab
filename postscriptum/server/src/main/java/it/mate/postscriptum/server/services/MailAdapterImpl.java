package it.mate.postscriptum.server.services;

import it.mate.postscriptum.server.model.MailRecipient;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;

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
  public void sendStickMail(StickMail mail) throws MessagingException {
    StringBuffer text = new StringBuffer(mail.getBody());
    addFixedMailFooter(text);
    String recipientEmail = mail.getUser().getEmail();
    if (mail instanceof StickMail2) {
      StickMail2 mail2 = (StickMail2)mail;
      if (mail2.getReceiverEmail() != null && mail2.getReceiverEmail().trim().length() > 0) {
        recipientEmail = mail2.getReceiverEmail();
      }
    }
    doSendMail(new MailRecipient(recipientEmail), "Post Scriptum Reminder: " + mail.getSubject(), text, null, null);
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

  private void addFixedMailFooter(StringBuffer text) {
    text.append("\n\n\n\n\n\n\n\n\n\n");
    text.append("<hr/>");
    text.append("<span style='font-size:10px;font-style:italic;'>This email was sent to you by the Post Scriptum Service. This is an automatically generated message: please do not reply. If you want to see all your scheduled reminders use the Post Scriptum App. Thanks.</span>");
  }

  private String escapeMailText(String text) {
    text = text.replace("\n", "<br>");
    return text;
  }

}
