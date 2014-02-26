package it.mate.postscriptum.shared.model.impl;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.Date;

@SuppressWarnings("serial")
public class StickMailTx implements StickMail {

  private String id;
  
  private String state;
  
  private Date created;
  
  private Date scheduled;
  
  private String body;
  
  private String subject;
  
  private RemoteUser user;

  @Override
  public String toString() {
    return "StickMailTx [id=" + id + ", state=" + state + ", created=" + created + ", scheduled=" + scheduled + ", body=" + body + ", user=" + user + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Date getScheduled() {
    return scheduled;
  }

  public void setScheduled(Date scheduled) {
    this.scheduled = scheduled;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public RemoteUser getUser() {
    return user;
  }

  public void setUser(RemoteUser user) {
    this.user = user;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
  
}
