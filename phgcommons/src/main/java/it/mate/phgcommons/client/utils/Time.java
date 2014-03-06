package it.mate.phgcommons.client.utils;

import java.util.Date;

public class Time {
  private int hours;
  private int minutes;
  public Time() { 
    this(new Date());
  }
  public Time(int hours, int minutes) {
    this.hours = hours;
    this.minutes = minutes;
  }
  public Time(Date date) { 
    this(date.getHours(), date.getMinutes());
  }
  public int getHours() {
    return hours;
  }
  public Time setHours(int hours) {
    this.hours = hours;
    return this;
  }
  public int getMinutes() {
    return minutes;
  }
  public Time setMinutes(int minutes) {
    this.minutes = minutes;
    return this;
  }
  public Date asDate() {
    return new Date(1971, 1, 1, getHours(), getMinutes());
  }
  @Override
  public String toString() {
    return "Time [" + hours + ":" + minutes + "]";
  }
  public static Time fromDate(Date date) {
    return new Time(date);
  }
  public Date setToDate(Date date) {
    return new Date(date.getYear(), date.getMonth(), date.getDate(), hours, minutes);
  }
  public boolean isValidTime() {
    return (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59);
  }
  public Time incHours(int amount) {
    hours = (hours + amount) % 24;
    return this;
  }
  public Time incMinutes(int amount) {
    minutes = (minutes + amount) % 60;
    return this;
  }
}
