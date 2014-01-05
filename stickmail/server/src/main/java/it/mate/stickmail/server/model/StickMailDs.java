package it.mate.stickmail.server.model;

import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;

public class StickMailDs implements StickMail {
  
  private String body;
  
  private String state;
  
  private Date scheduled;

  @Override
  public String toString() {
    return Utils.asString(this);
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

}
