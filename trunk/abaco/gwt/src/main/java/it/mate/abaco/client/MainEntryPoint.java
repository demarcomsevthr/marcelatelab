package it.mate.abaco.client;

import it.mate.abaco.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;

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
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable ex) {
        Window.alert("uncaught: " + ex.getClass().getName() + " - " + ex.getMessage());
        log.log(Level.SEVERE, "uncaught exception", ex);
        ex.printStackTrace();
      }
    });

    /*
    GwtUtils.deferredExecution(1000, new Delegate<Void>() {
      public void execute(Void element) {
        startApp();
      }
    });
    */
    
    startApp();
    
  }

  private void startApp() {
    AppClientFactory.IMPL.initModule(null);
  }
  
}
