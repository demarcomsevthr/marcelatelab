package it.mate.protoph.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.protoph.shared.model.Account;
import it.mate.protoph.shared.model.utils.ModelUtils;

import com.google.gwt.core.client.JavaScriptObject;

@SuppressWarnings("serial")
public class AccountTx implements Account, IsMappable {

  private String id;
  
  private String email;
  
  private String name;
  
  private String password;
  
  private String devInfoId;
  
  private String authDomain;
  
  private String federatedIdentity;
  
  private boolean isNullUser;
  
  public static AccountTx NULL_USER = new AccountTx(true);
  
  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("email", email);
    map.put("name", name);
    map.put("password", password);
    map.put("devInfoId", devInfoId);
    map.put("authDomain", authDomain);
    map.put("federatedIdentity", federatedIdentity);
    return map;
  }

  @Override
  public AccountTx fromRpcMap(RpcMap map) {
    this.id = (String)map.get("id");
    this.email = (String)map.get("email");
    this.name = (String)map.get("name");
    this.password = (String)map.get("password");
    this.devInfoId = (String)map.get("devInfoId");
    this.authDomain = (String)map.get("authDomain");
    this.federatedIdentity = (String)map.get("federatedIdentity");
    return this;
  }
  
  @Override
  public String toString() {
    return "AccountTx [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", devInfoId=" + devInfoId + ", authDomain=" + authDomain
        + ", federatedIdentity=" + federatedIdentity + ", isNullUser=" + isNullUser + "]";
  }

  public AccountTx() {

  }
  
  public AccountTx(Account account) {
    if (account instanceof AccountTx) {
      AccountTx that = (AccountTx)account;
      this.id = that.id;
      this.email = that.email;
      this.password = that.password;
      this.devInfoId = that.devInfoId;
      this.authDomain = that.authDomain;
      this.federatedIdentity = that.federatedIdentity;
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj instanceof AccountTx) {
      AccountTx that = (AccountTx)obj;
      if (!ModelUtils.equals(this.id, that.id))
        return false;
      if (!ModelUtils.equals(this.name, that.name))
        return false;
      if (!ModelUtils.equals(this.email, that.email))
        return false;
      if (!ModelUtils.equals(this.devInfoId, that.devInfoId))
        return false;
      if (!ModelUtils.equals(this.password, that.password))
        return false;
      return true;
    }
    return super.equals(obj);
  }

  private AccountTx(boolean isNullUser) {
    this.isNullUser = isNullUser;
  }

  public static Account fromJS(JavaScriptObject jso) {
    Account remoteUser = new AccountTx();
    remoteUser.setId((String)getNativeProperty(jso, "id"));
    remoteUser.setEmail((String)getNativeProperty(jso, "email"));
    remoteUser.setName((String)getNativeProperty(jso, "name"));
    remoteUser.setAuthDomain((String)getNativeProperty(jso, "authDomain"));
    remoteUser.setFederatedIdentity((String)getNativeProperty(jso, "federatedIdentity"));
    remoteUser.setPassword((String)getNativeProperty(jso, "password"));
    remoteUser.setDevInfoId((String)getNativeProperty(jso, "devInfoId"));
    return remoteUser;
  }
  
  public JavaScriptObject asJS() {
    JavaScriptObject jso = JavaScriptObject.createObject();
    setNativeProperty(jso, "id", this.getId());
    setNativeProperty(jso, "email", this.getEmail());
    setNativeProperty(jso, "name", this.getName());
    setNativeProperty(jso, "authDomain", this.getAuthDomain());
    setNativeProperty(jso, "federatedIdentity", this.getFederatedIdentity());
    setNativeProperty(jso, "password", this.getPassword());
    setNativeProperty(jso, "devInfoId", this.getDevInfoId());
    return jso;
  }
  
  protected native static Object getNativeProperty(JavaScriptObject obj, String name) /*-{
    if (obj[name]===undefined) {
      return null;
    }
    return obj[name];
  }-*/;
  
  protected native static void setNativeProperty(JavaScriptObject obj, String name, String value) /*-{
    obj[name] = value;
  }-*/;
  
  public String getId() {
    return id;
  }

  public void setId(String userId) {
    this.id = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String nickname) {
    this.name = nickname;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDevInfoId() {
    return devInfoId;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId;
  }
  
}
