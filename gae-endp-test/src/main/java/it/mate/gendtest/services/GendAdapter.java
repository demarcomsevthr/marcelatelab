package it.mate.gendtest.services;

import it.mate.gendtest.model.Request;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GendAdapter {
  
  private static Logger logger = Logger.getLogger(GendAdapter.class);

  @PostConstruct
  public void onPostConstruct() {
    logger.info("initialized " + this);
  }

  public Request getPendingRequest(String clientId) {
    return new Request(Request.WIFI_ENABLE_COMMAND);
  }
  
  
}
