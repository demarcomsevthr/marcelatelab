package it.mate.protons.client;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.factories.AppClientFactory;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;

public class MainEntryPoint implements EntryPoint {

  @Override
  public void onModuleLoad() {
    
    String traceActive = CdvUtils.getLocalStorageItem(AppClientFactory.KEY_TRACE_ACTIVE);
    if ("true".equals(traceActive)) {
      CdvUtils.log("***********    TRACE ENABLED   *************");
      CdvUtils.startTrace();
    }
    
    CdvUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    CdvUtils.logEnvironment();
    
    CdvUtils.log("AppProperties.extendedVersion = "+AppProperties.IMPL.extendedVersion());
    CdvUtils.log("AppConstants.versionNumber = "+AppProperties.IMPL.versionNumber());

    CdvUtils.setDefaultExceptionHandler(Logger.getLogger(getClass().getName()));
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
