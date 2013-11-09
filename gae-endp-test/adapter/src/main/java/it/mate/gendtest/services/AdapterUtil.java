package it.mate.gendtest.services;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class AdapterUtil {

  private static ApplicationContext context = null;
  
  private static Logger logger = Logger.getLogger(AdapterUtil.class);
  
  public static synchronized void initContext(ApplicationContext context) {
    AdapterUtil.context = context;
  }
  
  
  public static GendAdapter getAdapter() {
    if (context == null) {
      logger.error("error", new IllegalStateException("context not initialized"));
    }
    return context.getBean(GendAdapter.class);
  }
  
}
