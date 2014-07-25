package it.mate.postscriptum.server.model;

import it.mate.commons.server.model.HasKey;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class StickMailDs implements StickMail, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  private Text body;
  
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
  private String subject;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  private Key devInfoId;

  @Override
  public String toString() {
    return "StickMailDs [id=" + id + ", body=" + body + ", state=" + state + ", created=" + created + ", scheduled=" + scheduled + ", userId=" + userId
        + ", userEmail=" + userEmail + "]";
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
    return body != null ? body.getValue() : null;
  }

  public void setBody(String body) {
    this.body = body != null ? new Text(body) : null;
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

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDevInfoId() {
    return devInfoId != null ? KeyFactory.keyToString(devInfoId) : null;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId != null ? KeyFactory.stringToKey(devInfoId) : null;
  }
  
}
