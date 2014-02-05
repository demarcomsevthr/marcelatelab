package it.mate.commons.server.utils;

public class LoggingUtils {

  public static java.util.logging.Logger getJavaLogging(Class<?> loggerClass) {
    return java.util.logging.Logger.getLogger(loggerClass.getName());
  }
  
}
