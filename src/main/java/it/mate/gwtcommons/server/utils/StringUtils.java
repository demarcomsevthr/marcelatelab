package it.mate.gwtcommons.server.utils;

public class StringUtils {

  public static String substringSlice (String text, String substring) {
    int index = text.indexOf(substring);
    if (index > -1) {
      index += substring.length();
    } else {
      return null;
    }
    return text.substring(index);
  }
  
}
