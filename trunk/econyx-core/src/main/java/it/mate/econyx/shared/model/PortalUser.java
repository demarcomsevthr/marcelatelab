package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface PortalUser extends Serializable {

  public String getId();

  public void setId(String id);
  
  public String getScreenName();

  public void setScreenName(String screenname);
  
  public String getEmailAddress();

  public void setEmailAddress(String emailAddress);

  public String getPassword();

  public void setPassword(String password);
  
  public boolean getPasswordEncrypted();

  public void setPasswordEncrypted(boolean passwordEncrypted);
  
  public Double getBillingAccount();
  
  public void setBillingAccount(Double value);
  
  public boolean isActive();

  public String getActivationToken();

  public void setActivationToken(String activationToken);
  
  
}
