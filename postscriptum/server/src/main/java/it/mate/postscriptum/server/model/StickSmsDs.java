package it.mate.postscriptum.server.model;

import it.mate.commons.server.model.HasKey;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickSms2;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class StickSmsDs implements StickSms2, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  private String body;
  
  @Persistent
  private String state;
  
  @Persistent
  private Date created;
  
  @Persistent
  private Date scheduled;

  @Persistent
  private String userId;
  
  @Persistent
  private String userEmail;
  
  @Persistent
  private String receiverNumber;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  private Key devInfoId;
  
  @Persistent
  private String language;

  @Persistent
  private String clientType;

  @Persistent
  private String receiverName;
  
  @Persistent
  private String clientVersion;
  
  @Persistent
  private String ip;
  
  @Override
  public String toString() {
    return "StickSmsDs [id=" + id + ", body=" + body + ", state=" + state + ", created=" + created + ", scheduled=" + scheduled + ", userId=" + userId
        + ", userEmail=" + userEmail + ", receiverNumber=" + receiverNumber + ", devInfoId=" + devInfoId + ", language=" + language + ", clientType="
        + clientType + ", receiverName=" + receiverName + ", clientVersion=" + clientVersion + ", ip=" + ip + "]";
  }

  public Key getKey() {
    return id;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Date getScheduled() {
    return scheduled;
  }

  public void setScheduled(Date scheduled) {
    this.scheduled = scheduled;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public RemoteUser getUser() {
    RemoteUser user = new RemoteUser();
    user.setEmail(userEmail);
    user.setUserId(userId);
    return user;
  }

  public void setUser(RemoteUser user) {
    if (user == null) {
      this.userId = null;
      this.userEmail = null;
    } else {
      this.userId = user.getUserId();
      this.userEmail = user.getEmail();
    }
  }

  public String getReceiverNumber() {
    return receiverNumber;
  }

  public void setReceiverNumber(String receiverNumber) {
    this.receiverNumber = receiverNumber;
  }

  public String getDevInfoId() {
    return devInfoId != null ? KeyFactory.keyToString(devInfoId) : null;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId != null ? KeyFactory.stringToKey(devInfoId) : null;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getClientVersion() {
    return clientVersion;
  }

  public void setClientVersion(String clientVersion) {
    this.clientVersion = clientVersion;
  }
  
  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
  
}
