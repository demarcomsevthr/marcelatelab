package it.mate.therapyreminder.server.services;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RestController {
  
  private static Logger logger = Logger.getLogger(RestController.class);
  
  @Autowired private StickAdapter adapter;

  @PostConstruct
  public void onPostConstruct() {
    logger.debug("initialized " + this);
    logger.debug("adapter = " + adapter);
  }

  /*
  @RequestMapping ("/checkMails")
  public void checkMails(HttpServletResponse response) throws Exception {
    logger.debug("here");
    adapter.checkScheduledMails();
    response.getOutputStream().print("Success");
  }
  */
  
}
