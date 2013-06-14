package it.mate.econyx.server.servlets;

import it.mate.commons.server.utils.CacheUtils;
import it.mate.commons.server.utils.KeyUtils;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.CacheConstants;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class AppServletContextListener implements ServletContextListener {

  private static Logger logger = Logger.getLogger(AppServletContextListener.class);
  
  @Override
  public void contextInitialized(ServletContextEvent event) {
    String instanceId = KeyUtils.getRandomUUID();
    CacheUtils.instPut(CacheConstants.INSTANCE_ID, instanceId);
    CacheUtils.instPut(CacheConstants.INSTANCE_STARTUP, new Date());
    // 14/06/2013
    logger.debug("INITIALIZING AdaptersUtil");
    AdaptersUtil.initContext(event.getServletContext());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    
  }

}
