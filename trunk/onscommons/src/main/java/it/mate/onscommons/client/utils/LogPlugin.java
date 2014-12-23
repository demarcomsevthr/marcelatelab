package it.mate.onscommons.client.utils;


public class LogPlugin extends AbstractPluginWrapper {

  public static void debug(String msg) {
    execVoid("LogPlugin", "debug", msg, null);
  }
  
  public static void info(String msg) {
    execVoid("LogPlugin", "info", msg, null);
  }
  
}
