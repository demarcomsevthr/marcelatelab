package it.mate.ckd.client;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

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
    
    PhgUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    PhgUtils.logEnvironment();
    
    PhgUtils.log("AppProperties.extendedVersion = "+AppProperties.IMPL.extendedVersion());
    PhgUtils.log("AppConstants.versionCredits = "+AppConstants.IMPL.versionCredits());
    
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable ex) {
        Window.alert("uncaught: " + ex.getClass().getName() + " - " + ex.getMessage());
        log.log(Level.SEVERE, "uncaught exception", ex);
        ex.printStackTrace();
      }
    });
    
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
