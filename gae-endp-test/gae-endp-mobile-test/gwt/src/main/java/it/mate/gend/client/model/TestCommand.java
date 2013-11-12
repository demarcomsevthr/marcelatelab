package it.mate.gend.client.model;

import java.util.Date;

public interface TestCommand {

  public void setExecuted(Date executed);

  public Date getExecuted();

  public void setCreated(Date created);

  public Date getCreated();

  public void setClientId(String clientId);

  public String getClientId();

  public void setAction(int action);

  public int getAction();
  
  public static class Utils {
    public static String toString(TestCommand command) {
      return "TestCommand [action=" + command.getAction() + ", clientId=" + command.getClientId() + ", created=" + command.getCreated() + ", executed=" + command.getExecuted() + "]";
    }
  }

}
