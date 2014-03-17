package it.mate.postscriptum.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface StickSMS extends Serializable {
  
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
  
  public String getReceiverNumber();
  
  public void setReceiverNumber(String receiverNumber);
  
  public String getDevInfoId();
  
  public void setDevInfoId(String devInfoId);
  
  public static class Utils {
    
    public static String asString(StickSMS sms) {
      return "StickSMS [receiver=" + sms.getReceiverNumber() + ", state=" + sms.getState() + ", scheduled=" + sms.getScheduled() + "]";
    }
    
    public static boolean isScheduled(StickSMS sms) {
      return STATE_SCHEDULED.equals(sms.getState());
    }
    
  }

}
