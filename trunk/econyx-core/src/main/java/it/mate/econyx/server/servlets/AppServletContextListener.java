package it.mate.econyx.server.servlets;

import it.mate.commons.server.utils.CacheUtils;
import it.mate.commons.server.utils.KeyUtils;
import it.mate.econyx.server.util.CacheConstants;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent event) {
    String instanceId = KeyUtils.getRandomUUID();
    CacheUtils.instPut(CacheConstants.INSTANCE_ID, instanceId);
    CacheUtils.instPut(CacheConstants.INSTANCE_STARTUP, new Date());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    
  }

}
