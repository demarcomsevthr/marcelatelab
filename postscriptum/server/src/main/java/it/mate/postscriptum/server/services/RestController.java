package it.mate.postscriptum.server.services;

import it.mate.commons.server.utils.LoggingUtils;
import it.mate.postscriptum.server.tasks.UpdateUserInfosTask;
import it.mate.postscriptum.server.utils.AdapterUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestController implements ApplicationContextAware {
  
  private static Logger logger = Logger.getLogger(RestController.class);
  
  @Autowired private StickAdapter adapter;

  @PostConstruct
  public void onPostConstruct() {
    logger.debug("initialized " + this);
    logger.debug("adapter = " + adapter);
  }
  
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    LoggingUtils.debug(getClass(), "initializing AdapterUtils");
    AdapterUtils.initContext(context);
  }

  @RequestMapping ("/checkMessages")
  public void checkMessages(HttpServletResponse response) throws Exception {
    adapter.checkScheduledMails();
    adapter.checkScheduledSMSs();
    response.getOutputStream().print("Finish");
  }
  
  /*
   * COMMENTATA IL 31/07/2015 PER VIA DELLA CRISI SUL SERVER (100% read ops alle 9,40!)
   * 
  @RequestMapping ("/updateUserInfos")
  public void updateUserInfos(HttpServletResponse response) throws Exception {
    UpdateUserInfosTask.enqueue();
    response.getOutputStream().print("Task enqueued");
  }
   */
  
  /*
   * TODO: DA PROVARE
   */
  @RequestMapping ("/purgeMessages")
  public void purgeMessages(HttpServletResponse response) throws Exception {
    adapter.purgeNotifiedSMSs();
    response.getOutputStream().print("Finish");
  }
  
}
