package it.mate.protoph.server.services;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestController {
  
  private static Logger logger = Logger.getLogger(RestController.class);
  
  @Autowired private RemoteAdapter adapter;

  @PostConstruct
  public void onPostConstruct() {
    logger.debug("initialized " + this);
    logger.debug("adapter = " + adapter);
  }

  @RequestMapping ("/scheduledChecks")
  public void scheduledChecks(HttpServletResponse response) throws Exception {
    logger.debug("adapter scheduled checks start");
    adapter.scheduledChecks();
    response.getOutputStream().print("Adapter scheduled checks started");
  }
  
}
