package it.mate.protons.client;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.factories.AppClientFactory;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;

public class MainEntryPoint implements EntryPoint {

//private Logger log = Logger.getLogger(getClass().getName());

  @Override
  public void onModuleLoad() {
    
    String traceActive = PhgUtils.getLocalStorageItem(AppClientFactory.KEY_TRACE_ACTIVE);
    if ("true".equals(traceActive)) {
      PhgUtils.log("***********    TRACE ENABLED   *************");
      PhgUtils.startTrace();
    }
    
    PhgUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    PhgUtils.logEnvironment();
    
    PhgUtils.log("AppProperties.extendedVersion = "+AppProperties.IMPL.extendedVersion());
    PhgUtils.log("AppConstants.versionNumber = "+AppProperties.IMPL.versionNumber());

    PhgUtils.setDefaultExceptionHandler(Logger.getLogger(getClass().getName()));
    /*
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable ex) {
        log.log(Level.SEVERE, "uncaught exception", ex);
        ex.printStackTrace();
        if (!PhgUtils.isSuspendUncaughtExceptionAlerts()) {
          Window.alert("uncaught: " + ex.getClass().getName() + " - " + ex.getMessage());
        }
      }
    });
    */
    
    
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
