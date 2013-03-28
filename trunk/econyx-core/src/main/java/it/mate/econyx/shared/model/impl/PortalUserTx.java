package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.PortalUser;

@SuppressWarnings("serial")
public class PortalUserTx implements PortalUser {

  String id;
  
  String screenName;
  
  String emailAddress;
  
  String password;
  
  boolean passwordEncrypted;
  
  Double billingAccount;
  
  Boolean active;
  
  String activationToken;
  
  Boolean testUser;
  
  Boolean adminUser;

  @Override
  public String toString() {
    return "PortalUserTx [id=" + id + ", screenName=" + screenName + ", emailAddress=" + emailAddress + ", password=" + password
        + ", passwordEncrypted=" + passwordEncrypted + ", billingAccount=" + billingAccount + ", active=" + active + ", activationToken="
        + activationToken + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getPasswordEncrypted() {
    return passwordEncrypted;
  }

  public void setPasswordEncrypted(boolean passwordEncrypted) {
    this.passwordEncrypted = passwordEncrypted;
  }

  public Double getBillingAccount() {
    return billingAccount;
  }

  public void setBillingAccount(Double billingAccount) {
    this.billingAccount = billingAccount;
  }

  public boolean isActive() {
    return active;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getActivationToken() {
    return activationToken;
  }

  public void setActivationToken(String activationToken) {
    this.activationToken = activationToken;
  }

  public Boolean getTestUser() {
    return testUser;
  }

  public boolean isTestUser() {
    return testUser != null ? testUser : false;
  }

  public void setTestUser(Boolean testUser) {
    this.testUser = testUser;
  }

  public Boolean getAdminUser() {
    return adminUser;
  }

  public void setAdminUser(Boolean adminUser) {
    this.adminUser = adminUser;
  }
  
  public boolean isAdminUser() {
    return adminUser != null ? adminUser : false;
  }
  
}
