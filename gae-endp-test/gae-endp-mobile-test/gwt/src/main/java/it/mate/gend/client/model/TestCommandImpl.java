package it.mate.gend.client.model;

import java.util.Date;

public class TestCommandImpl implements TestCommand {

  private int action;
  
  private String clientId;
  
  private Date created;
  
  private Date executed;

  @Override
  public String toString() {
    return "TestCommandImpl [action=" + action + ", clientId=" + clientId + ", created=" + created + ", executed=" + executed + "]";
  }

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getExecuted() {
    return executed;
  }

  public void setExecuted(Date executed) {
    this.executed = executed;
  }
  
}
