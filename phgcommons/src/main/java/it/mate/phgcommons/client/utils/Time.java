package it.mate.phgcommons.client.utils;

import java.util.Date;

public class Time {
  private int hour;
  private int minute;
  public Time() { }
  public Time(int hour, int minute) {
    this.hour = hour;
    this.minute = minute;
  }
  public Time(Date date) { 
    this(date.getHours(), date.getMinutes());
  }
  public int getHour() {
    return hour;
  }
  public void setHour(int hour) {
    this.hour = hour;
  }
  public int getMinute() {
    return minute;
  }
  public void setMinute(int minute) {
    this.minute = minute;
  }
  public Date asDate() {
    return new Date(1971, 1, 1, getHour(), getMinute());
  }
  @Override
  public String toString() {
    return "Time [" + hour + ":" + minute + "]";
  }
  public static Time fromDate(Date date) {
    return new Time(date);
  }
}
