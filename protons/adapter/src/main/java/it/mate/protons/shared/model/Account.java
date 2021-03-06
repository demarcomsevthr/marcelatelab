package it.mate.protons.shared.model;

import java.io.Serializable;

public interface Account extends Serializable {

  public void setFederatedIdentity(String federatedIdentity);

  public String getFederatedIdentity();

  public void setAuthDomain(String authDomain);

  public String getAuthDomain();

  public void setName(String nickname);

  public String getName();

  public void setEmail(String email);

  public String getEmail();

  public void setId(String userId);

  public String getId();

  public void setDevInfoId(String devInfoId);

  public String getDevInfoId();

  public void setPassword(String password);

  public String getPassword();

}
