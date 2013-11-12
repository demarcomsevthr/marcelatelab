package it.mate.gendtest.services;

import it.mate.gendtest.shared.model.Command;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Service
public class GendAdapter {
  
  private static Logger logger = Logger.getLogger(GendAdapter.class);

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
    Command result = getPendingCommand();
    setPendingCommand(new Command());
    return result;
  }
  
  public void setPendingAction(int action) {
    Command pending = new Command(action);
    setPendingCommand(pending);
    logger.debug("pending " + pending);
  }
  
  private Command getPendingCommand() {
    Command result = (Command)MemcacheServiceFactory.getMemcacheService().get("PENDING_COMMAND");
    if (result == null) {
      result = new Command();
    }
    return result;
  }
  
  private void setPendingCommand(Command command) {
    MemcacheServiceFactory.getMemcacheService().put("PENDING_COMMAND", command);
  }
  
}
