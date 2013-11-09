package it.mate.gendtest.services;

import it.mate.gendtest.model.Command;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GendAdapter {
  
  private static Logger logger = Logger.getLogger(GendAdapter.class);

  private static Command pending = new Command();
  
  @PostConstruct
  public void onPostConstruct() {
    logger.info("initialized " + this);
  }

  public Command getRandomCommand(String clientId) {
    int rand = (int)(Math.random() * 1000);
    logger.debug("rand = " + rand);
    int command = 0;
    if (rand > 800) {
      command = Command.WIFI_ENABLE_ACTION;
    } else if (rand > 500) {
      command = Command.WIFI_DISABLE_ACTION;
    }
    return new Command(command);
  }
  
  public Command popPendingCommand(String clientId) {
    Command result = pending;
    pending = new Command();
    return result;
  }
  
  public void setPendingAction(int action) {
    pending = new Command(action);
    logger.debug("pending " + pending);
  }
  
}
