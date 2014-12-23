package it.mate.protons.client;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.utils.OnsUtils;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.factories.AppClientFactory;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;

public class MainEntryPoint implements EntryPoint {

  @Override
  public void onModuleLoad() {
    
    String traceActive = OnsUtils.getLocalStorageItem(AppClientFactory.KEY_TRACE_ACTIVE);
    if ("true".equals(traceActive)) {
      OnsUtils.log("***********    TRACE ENABLED   *************");
      OnsUtils.startTrace();
    }
    
    OnsUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    OnsUtils.logEnvironment();
    
    OnsUtils.log("AppProperties.extendedVersion = "+AppProperties.IMPL.extendedVersion());
    OnsUtils.log("AppConstants.versionNumber = "+AppProperties.IMPL.versionNumber());

    OnsUtils.setDefaultExceptionHandler(Logger.getLogger(getClass().getName()));
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
