package it.mate.phgcommons.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Time {
  private int hours;
  
  private int minutes;
  
  private static boolean use12HFormat = false;
  
  private static DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm");
  
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
  
  public Time(Time that) {
    this.hours = that.hours;
    this.minutes = that.minutes;
  }
  
  public int getHours() {
    return hours;
  }
  
  public int getHours12() {
    return hours % 12;
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
  
  public String asString() {
    return fmt.format(asDate());
  }
  
  @Override
  public String toString() {
    return "Time [" + hours + ":" + minutes + "]";
  }
  
  public static Time fromDate(Date date) {
    return new Time(date);
  }
  
  public static Time fromString(String text) {
    Date temp = fmt.parse(text);
    return new Time(temp);
  }
  
  public Date setInDate(Date date) {
    date.setHours(hours);
    date.setMinutes(minutes);
    date.setSeconds(0);
    return date;
  }

  public boolean isValidTime() {
    return (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59);
  }
  
  public Time incHours(int amount) {
    hours = (hours + amount) % 24;
    return this;
  }
  
  public Time incMinutes(int amount) {
    int hoursAmount = (minutes + amount) / 60;
    minutes = (minutes + amount) % 60;
    if (hoursAmount > 0) {
      incHours(hoursAmount);
    }
    return this;
  }
  
  public boolean isAM() {
    return hours < 12;
  }
  
  public boolean isPM() {
    return hours >= 12;
  }
  
  public static boolean is12HFormat() {
    return use12HFormat;
  }
  
  public static void set12HFormat(boolean use12hFormat) {
    use12HFormat = use12hFormat;
    fmt = use12hFormat ? DateTimeFormat.getFormat("h:mm a") : DateTimeFormat.getFormat("HH:mm");
  }
  
  public static DateTimeFormat getCurrentFormat() {
    return fmt;
  }
  
}
