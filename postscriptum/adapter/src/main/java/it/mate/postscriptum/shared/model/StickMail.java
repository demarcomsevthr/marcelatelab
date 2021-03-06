package it.mate.postscriptum.shared.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ATTENZIONE - 22/07/2014
 * 
 * NON MODIFICARE QUESTA INTERFACCIA
 * 
 * DEVE RIMANERE CONGELATA
 * 
 * UTILIZZARE StickMail2
 *
 */

/*
@Deprecated
*/
public interface StickMail extends Serializable {
  
  public static final String STATE_NEW = "NW";

  public static final String STATE_SCHEDULED = "SC";

  public static final String STATE_SENDING = "SN";

  public static final String STATE_NOTIFIED = "NO";

  public void setId(String id);

  public String getId();

  public void setState(String state);

  public String getState();

  public void setScheduled(Date scheduled);

  public Date getScheduled();

  public void setBody(String body);

  public String getBody();
  
  public void setCreated(Date created);

  public Date getCreated();

  public void setUser(RemoteUser user);

  public RemoteUser getUser();
  
  public String getSubject();

  public void setSubject(String subject);

  public static class Utils {
    
    public static String asString(StickMail mail) {
      return "StickMail [body=" + mail.getBody() + ", state=" + mail.getState() + ", scheduled=" + mail.getScheduled() + "]";
    }
    
    public static boolean isScheduled(StickMail mail) {
      return STATE_SCHEDULED.equals(mail.getState());
    }
    
  }

}
