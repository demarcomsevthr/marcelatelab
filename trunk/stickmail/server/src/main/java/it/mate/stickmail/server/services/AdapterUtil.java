package it.mate.stickmail.server.services;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AdapterUtil {

  private static ApplicationContext context = null;
  
  private static Logger logger = Logger.getLogger(AdapterUtil.class);
  
  public static synchronized void initContext(ServletContext context) {
    AdapterUtil.context = WebApplicationContextUtils.getWebApplicationContext(context);
  }
  
  private static void assertContext() {
    if (context == null) {
      logger.error("error", new IllegalStateException("context not initialized"));
    }
  }
  
  public static StickAdapter getStickAdapter() {
    assertContext();
    return context.getBean(StickAdapter.class);
  }
  
  public static MailAdapter getMailAdapter() {
    assertContext();
    return context.getBean(MailAdapter.class);
  }
  
}
