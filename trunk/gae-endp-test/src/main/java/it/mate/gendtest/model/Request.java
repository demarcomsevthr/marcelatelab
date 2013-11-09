package it.mate.gendtest.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Request implements Serializable {

  public final static int WIFI_ENABLE_COMMAND = 1;
  
  public final static int WIFI_DISABLE_COMMAND = 1;
  
  int type;
  
  public Request() { }

  public Request(int type) {
    super();
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
  
}
