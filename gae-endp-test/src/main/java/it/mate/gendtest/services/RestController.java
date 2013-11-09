package it.mate.gendtest.services;

import it.mate.gendtest.model.Command;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {
  
  private static Logger logger = Logger.getLogger(RestController.class);
  
  @Autowired private GendAdapter adapter;

  @RequestMapping ("/getRandomCommand/{cliendId}")
  public @ResponseBody Command getRandomCommand(@PathVariable("cliendId") String clientId) {
    Command request = adapter.getRandomCommand(clientId);
    logger.debug("returning " + request);
    return request;
  }
  
  @RequestMapping ("/getPendingCommand/{cliendId}")
  public @ResponseBody Command getPendingCommand(@PathVariable("cliendId") String clientId) {
    Command request = adapter.popPendingCommand(clientId);
    logger.debug("returning " + request);
    return request;
  }
  
}
