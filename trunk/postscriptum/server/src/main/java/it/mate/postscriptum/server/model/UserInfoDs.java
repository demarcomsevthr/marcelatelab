package it.mate.postscriptum.server.model;

import it.mate.commons.server.model.HasKey;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class UserInfoDs implements Serializable, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  private String userId;
  
  @Persistent
  private String userEmail;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  private Key devInfoId;
  
  @Persistent
  private String lastLanguage;

  @Persistent
  private String lastClientType;

  @Persistent
  private String lastReceiverName;
  
  @Persistent
  private String lastClientVersion;
  
  @Persistent
  private String lastIp;
  
  @Persistent
  private Date firstSms;

  @Persistent
  private Date lastSms;

  @Persistent
  private Date lastEmail;

  @Persistent
  private Integer countSmsF0;

  @Persistent
  private Integer countSmsF1;

  @Persistent
  private Integer countSmsP1;
  
  @Override
  public String toString() {
    return "UserInfoDs [id=" + id + ", userId=" + userId + ", userEmail=" + userEmail + ", devInfoId=" + devInfoId + ", lastLanguage=" + lastLanguage
        + ", lastClientType=" + lastClientType + ", lastReceiverName=" + lastReceiverName + ", lastClientVersion=" + lastClientVersion + ", lastIp=" + lastIp
        + ", lastSms=" + lastSms + ", lastEmail=" + lastEmail + ", countSmsF0=" + countSmsF0 + ", countSmsF1=" + countSmsF1 + ", countSmsP1=" + countSmsP1
        + "]";
  }

  public Key getKey() {
    return id;
  }

  public Key getId() {
    return id;
  }

  public void setId(Key id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Key getDevInfoId() {
    return devInfoId;
  }

  public void setDevInfoId(Key devInfoId) {
    this.devInfoId = devInfoId;
  }

  public String getLastLanguage() {
    return lastLanguage;
  }

  public void setLastLanguage(String lastLanguage) {
    this.lastLanguage = lastLanguage;
  }

  public String getLastClientType() {
    return lastClientType;
  }

  public void setLastClientType(String lastClientType) {
    this.lastClientType = lastClientType;
  }

  public String getLastReceiverName() {
    return lastReceiverName;
  }

  public void setLastReceiverName(String lastReceiverName) {
    this.lastReceiverName = lastReceiverName;
  }

  public String getLastClientVersion() {
    return lastClientVersion;
  }

  public void setLastClientVersion(String lastClientVersion) {
    this.lastClientVersion = lastClientVersion;
  }

  public String getLastIp() {
    return lastIp;
  }

  public void setLastIp(String lastIp) {
    this.lastIp = lastIp;
  }

  public Date getLastSms() {
    return lastSms;
  }

  public void setLastSms(Date lastSms) {
    this.lastSms = lastSms;
  }

  public Date getLastEmail() {
    return lastEmail;
  }

  public void setLastEmail(Date lastEmail) {
    this.lastEmail = lastEmail;
  }

  public Integer getCountSmsF0() {
    return countSmsF0 != null ? countSmsF0 : 0;
  }

  public void setCountSmsF0(Integer countSmsF0) {
    this.countSmsF0 = countSmsF0;
  }

  public Integer getCountSmsF1() {
    return countSmsF1 != null ? countSmsF1 : 0;
  }

  public void setCountSmsF1(Integer countSmsF1) {
    this.countSmsF1 = countSmsF1;
  }

  public Integer getCountSmsP1() {
    return countSmsP1 != null ? countSmsP1 : 0;
  }

  public void setCountSmsP1(Integer countSmsP1) {
    this.countSmsP1 = countSmsP1;
  }

  public Date getFirstSms() {
    return firstSms;
  }

  public void setFirstSms(Date firstSms) {
    this.firstSms = firstSms;
  }
  
}
