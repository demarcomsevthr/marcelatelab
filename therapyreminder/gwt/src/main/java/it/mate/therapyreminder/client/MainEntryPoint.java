package it.mate.therapyreminder.client;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.factories.AppClientFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;

public class MainEntryPoint implements EntryPoint {

  private Logger log = Logger.getLogger(getClass().getName());

  @Override
  public void onModuleLoad() {
    
    String traceActive = PhonegapUtils.getLocalStorageProperty(AppClientFactory.KEY_TRACE_ACTIVE);
    if ("true".equals(traceActive)) {
      PhonegapUtils.log("***********    TRACE ENABLED   *************");
      PhonegapUtils.startTrace();
    }
    
    PhonegapUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    PhonegapUtils.logEnvironment();
    
    PhonegapUtils.log("AppProperties.extendedVersion = "+AppProperties.IMPL.extendedVersion());
    PhonegapUtils.log("AppConstants.versionCredits = "+AppProperties.IMPL.versionCredits());
    
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable ex) {
        log.log(Level.SEVERE, "uncaught exception", ex);
        ex.printStackTrace();
        if (!PhonegapUtils.isSuspendUncaughtExceptionAlerts()) {
          Window.alert("uncaught: " + ex.getClass().getName() + " - " + ex.getMessage());
        }
      }
    });
    
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
