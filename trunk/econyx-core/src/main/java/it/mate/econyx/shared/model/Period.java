package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Period implements Serializable {
  private Date start;
  private Date end;
  public Period() { }
  public Period(Date start, Date end) {
    this.start = start;
    this.end = end;
  }
  public Date getStart() {
    return start;
  }
  public Date getEnd() {
    return end;
  }
}
