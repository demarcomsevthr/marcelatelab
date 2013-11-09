package it.mate.gendtest.services;

import it.mate.gendtest.model.Request;

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

  @RequestMapping ("/getPendingRequest/{cliendId}")
  public @ResponseBody Request getPendingRequest(@PathVariable("cliendId") String clientId) {
    logger.debug("reached adapter = " + adapter);
    return adapter.getPendingRequest(clientId);
  }
  
}
