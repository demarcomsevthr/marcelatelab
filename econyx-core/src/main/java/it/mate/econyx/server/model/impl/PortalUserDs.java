package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=PortalUserTx.class)

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class PortalUserDs implements PortalUser, HasKey {

  @PrimaryKey
  /* 27/11/2012
  @Persistent
  */
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String screenName;
  
  @Persistent
  String emailAddress;
  
  @Persistent
  String password;
  
  @Persistent
  Double billingAccount;
  
  transient boolean passwordEncrypted = true;
  
  @Persistent
  Boolean active;

  @Persistent
  String activationToken;

  public Key getKey() {
    return id;
  }
  
  public void setKey(Key key) {
    this.id = key;
  }
  
  public String getId() {
    return getKey() != null ? KeyFactory.keyToString(getKey()) : null;
  }

  public void setId(String id) {
    setKey( id != null ? KeyFactory.stringToKey(id) : null );
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

  @Override
  public boolean getPasswordEncrypted() {
    return passwordEncrypted;
  }

  @Override
  public void setPasswordEncrypted(boolean passwordEncrypted) {
    this.passwordEncrypted = passwordEncrypted;
  }

  public Double getBillingAccount() {
    return billingAccount;
  }

  public void setBillingAccount(Double billingAccount) {
    this.billingAccount = billingAccount;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
  
  public boolean isActive() {
    return active != null ? active : false;
  }

  public String getActivationToken() {
    return activationToken;
  }

  public void setActivationToken(String activationToken) {
    this.activationToken = activationToken;
  }
  
}
