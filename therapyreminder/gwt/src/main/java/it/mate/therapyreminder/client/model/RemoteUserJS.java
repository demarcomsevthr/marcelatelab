package it.mate.therapyreminder.client.model;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.therapyreminder.shared.model.RemoteUser;

import com.google.gwt.core.client.JavaScriptObject;

public class RemoteUserJS extends JavaScriptObject {

  protected RemoteUserJS() { }
  
  public final RemoteUser asRemoteUser() {
    RemoteUser remoteUser = new RemoteUser();
    remoteUser.setEmail(getEmail());
    remoteUser.setNickname(getNickname());
    remoteUser.setUserId(getUserId());
    remoteUser.setAuthDomain(getAuthDomain());
    remoteUser.setFederatedIdentity(getFederatedIdentity());
    return remoteUser;
  }
  
  public static RemoteUserJS cast(RemoteUser remoteUser) {
    RemoteUserJS user = JavaScriptObject.createObject().cast();
    user.setUserId(remoteUser.getUserId());
    user.setEmail(remoteUser.getEmail());
    user.setNickname(remoteUser.getNickname());
    user.setAuthDomain(remoteUser.getAuthDomain());
    user.setFederatedIdentity(remoteUser.getFederatedIdentity());
    return user;
  }
  
  public final String getUserId() {
    return (String)GwtUtils.getPropertyImpl(this, "userId");
  }
  
  protected final void setUserId(String value) {
    GwtUtils.setPropertyImpl(this, "userId", value);
  }
  
  public final String getEmail() {
    return (String)GwtUtils.getPropertyImpl(this, "email");
  }
  
  protected final void setEmail(String value) {
    GwtUtils.setPropertyImpl(this, "email", value);
  }
  
  public final String getNickname() {
    return (String)GwtUtils.getPropertyImpl(this, "nickname");
  }
  
  protected final void setNickname(String value) {
    GwtUtils.setPropertyImpl(this, "nickname", value);
  }
  
  public final String getAuthDomain() {
    return (String)GwtUtils.getPropertyImpl(this, "authDomain");
  }
  
  protected final void setAuthDomain(String value) {
    GwtUtils.setPropertyImpl(this, "authDomain", value);
  }
  
  public final String getFederatedIdentity() {
    return (String)GwtUtils.getPropertyImpl(this, "federatedIdentity");
  }
  
  protected final void setFederatedIdentity(String value) {
    GwtUtils.setPropertyImpl(this, "federatedIdentity", value);
  }
  
  public final boolean isNullUser() {
    return GwtUtils.getPropertyBoolImpl(this, "isNullUser");
  }
  
  public final String toStringCustom() {
    return "RemoteUserJS [userId=" + getUserId() + ", email=" + getEmail() + ", nickname=" + getNickname() + ", authDomain="
        + getAuthDomain() + ", federatedIdentity=" + getFederatedIdentity() + "]";
  }

}
