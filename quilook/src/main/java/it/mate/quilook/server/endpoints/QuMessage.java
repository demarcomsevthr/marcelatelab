package it.mate.quilook.server.endpoints;

public class QuMessage {
  
  private String message;
  
  public QuMessage() {

  }

  public QuMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
