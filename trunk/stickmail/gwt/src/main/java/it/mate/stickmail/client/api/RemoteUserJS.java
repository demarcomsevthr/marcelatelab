package it.mate.stickmail.client.api;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class RemoteUserJS extends JavaScriptObject {

  protected RemoteUserJS() { }
  
  public final String getUserId() {
    return (String)GwtUtils.getPropertyImpl(this, "userId");
  }
  
  public final String getEmail() {
    return (String)GwtUtils.getPropertyImpl(this, "email");
  }
  
  public final String getNickname() {
    return (String)GwtUtils.getPropertyImpl(this, "nickname");
  }
  
  public final String getAuthDomain() {
    return (String)GwtUtils.getPropertyImpl(this, "authDomain");
  }
  
  public final String getFederatedIdentity() {
    return (String)GwtUtils.getPropertyImpl(this, "federatedIdentity");
  }
  
  public final boolean isNullUser() {
    return GwtUtils.getPropertyBoolImpl(this, "isNullUser");
  }
  
  public final String toStringCustom() {
    return "RemoteUserJS [userId=" + getUserId() + ", email=" + getEmail() + ", nickname=" + getNickname() + ", authDomain="
        + getAuthDomain() + ", federatedIdentity=" + getFederatedIdentity() + "]";
  }

}
