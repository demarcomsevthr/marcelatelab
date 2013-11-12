package it.mate.stickmail.shared.model;

import java.util.Date;

public interface StickMail {
  
  public static final String STATE_SCHEDULED = "SC";

  public static final String STATE_SENDING = "SN";

  public static final String STATE_NOTIFIED = "NO";

  public void setState(String state);

  public String getState();

  public void setScheduled(Date scheduled);

  public Date getScheduled();

  public void setBody(String body);

  public String getBody();
  
  public static class Utils {
    
    public static String asString(StickMail mail) {
      return "StickMail [body=" + mail.getBody() + ", state=" + mail.getState() + ", scheduled=" + mail.getScheduled() + "]";
    }
    
  }

}
