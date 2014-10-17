package it.mate.postscriptum.shared.model;

public interface StickSms2 extends StickSms {

  public String getClientType();
  
  public void setClientType(String clientType);
  
  public String getLanguage();

  public void setLanguage(String language);
  
  public String getReceiverName();

  public void setReceiverName(String receiverName);
  
  public String getClientVersion();

  public void setClientVersion(String clientVersion);
  
  public String getIp();

  public void setIp(String ip);
  
}
