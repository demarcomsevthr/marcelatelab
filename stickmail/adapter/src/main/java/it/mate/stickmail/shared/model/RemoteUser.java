package it.mate.stickmail.shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RemoteUser implements Serializable {

  private String userId;
  
  private String email;
  
  private String nickname;
  
  private String authDomain;
  
  private String federatedIdentity;
  
  private boolean isNullUser;
  
  public static RemoteUser NULL_USER = new RemoteUser(true);
  
  public RemoteUser() {

  }

  private RemoteUser(boolean isNullUser) {
    this.isNullUser = isNullUser;
  }

  @Override
  public String toString() {
    return "RemoteUser [userId=" + userId + ", email=" + email + ", nickname=" + nickname + ", authDomain=" + authDomain + ", federatedIdentity="
        + federatedIdentity + "]";
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAuthDomain() {
    return authDomain;
  }

  public void setAuthDomain(String authDomain) {
    this.authDomain = authDomain;
  }

  public String getFederatedIdentity() {
    return federatedIdentity;
  }

  public void setFederatedIdentity(String federatedIdentity) {
    this.federatedIdentity = federatedIdentity;
  }
  
  public boolean isNullUser() {
    return isNullUser;
  }
  
}
