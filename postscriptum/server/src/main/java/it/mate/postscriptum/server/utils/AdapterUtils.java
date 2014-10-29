package it.mate.postscriptum.server.utils;

import it.mate.postscriptum.server.services.StickAdapter;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AdapterUtils {

  private static ApplicationContext context = null;
  
  public static synchronized void initContext(ServletContext servletContext) {
    if (context == null)
      context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
  }

  public static synchronized void initContext(ApplicationContext context) {
    if (AdapterUtils.context == null)
      AdapterUtils.context = context;
  }

  public static StickAdapter getStickAdapter() {
    return context.getBean(StickAdapter.class);
  }
  
}
