package it.mate.gendtest.shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Command implements Serializable {

  public final static int NULL_ACTION = 0;
  
  public final static int WIFI_ENABLE_ACTION = 1;
  
  public final static int WIFI_DISABLE_ACTION = 2;
  
  public final static int SHUTDOWN_PC_ACTION = 3;
  
  private int action;
  
  public Command() { 
    this(NULL_ACTION); 
  }

  public Command(int action) {
    super();
    this.action = action;
  }
  
  @Override
  public String toString() {
    return "Command [action=" + action + "]";
  }

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
  }
  
}
