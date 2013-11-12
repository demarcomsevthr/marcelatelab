package it.mate.gendtest.endpoints;

import it.mate.gendtest.services.AdapterUtil;
import it.mate.gendtest.shared.model.Command;
import it.mate.gendtest.shared.model.TestCommand;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

/**
 * 
 *  NOTA BENE
 *  
 *  PER TEST IN LOCALE:
 *  
 *  http://127.0.0.1:8080/_ah/api/commandsAPI/v1/get/1
 *  (non funziona l'api explorer in locale)
 *  
 * 
 *  SU APPSPOT:
 *  RICORDARSI DI ANDARE SEMPRE IN https !!!
 *
 */



@Api (name="commandsAPI", version="v1", description="Commands API - build 1")
public class CommandsEndpoint {

  private static Logger logger = Logger.getLogger(CommandsEndpoint.class);
  
  @PostConstruct
  public void onPostConstruct() {
    logger.debug("initialized " + this);
  }
  
  @ApiMethod (name="get", httpMethod=HttpMethod.GET)
  public Command getCommand(@Named("id") Integer id) {
    return AdapterUtil.getAdapter().popPendingCommand("");
  }

  @ApiMethod (name="addAction", httpMethod=HttpMethod.PUT)
  public VoidResult addAction(@Named("action") Integer action) {
    AdapterUtil.getAdapter().setPendingAction(action);
    return VoidResult.VOID;
  }

  
  // TODO: DA TESTARE
  
  @ApiMethod (name="addActionJson", httpMethod=HttpMethod.PUT)
  public VoidResult addActionJson(@Named("json") String json) {
//  AdapterUtil.getAdapter().setPendingAction(action);
    
    MyAutoBeanFactory factory = AutoBeanFactorySource.create(MyAutoBeanFactory.class);
    TestCommand command = AutoBeanCodex.decode(factory, TestCommand.class, json).as();
    logger.debug("received command = " + TestCommand.Utils.toString(command));
    
    return VoidResult.VOID;
  }

}
