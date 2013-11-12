package it.mate.stickmail.shared.model;

import java.util.Date;



public interface ServerTime {
  
  public Long getMillis();

  public void setMillis(Long millis);
  
  public class Impl implements ServerTime {
    private Long millis;
    public Impl() { }
    public Impl(Date date) {
      this.millis = date.getTime();
    }
    public Long getMillis() {
      return millis;
    }
    public void setMillis(Long millis) {
      this.millis = millis;
    }
  }

}
