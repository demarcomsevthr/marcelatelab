package it.mate.commons.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  
  public static String dateToString(Date date) {
    return dateToString(date, "dd/MM/yyyy");
  }

  public static String dateToString(Date date, String pattern) {
    SimpleDateFormat fmt = new SimpleDateFormat(pattern);
    return fmt.format(date);
  }

}
