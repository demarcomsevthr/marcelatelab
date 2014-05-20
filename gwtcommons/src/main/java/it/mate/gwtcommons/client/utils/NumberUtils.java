package it.mate.gwtcommons.client.utils;

public class NumberUtils {
  
  public static boolean isInteger(Double value) {
    if (value == null)
      return false;
    if (Math.floor(value) == value)
      return true;
    return false;
  }

}
