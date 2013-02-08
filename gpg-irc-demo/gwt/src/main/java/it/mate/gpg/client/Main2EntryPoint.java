package it.mate.gpg.client;

import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.client.PhoneGap;

public class Main2EntryPoint implements EntryPoint {

  private Logger log = Logger.getLogger(getClass().getName());

  @Override
  public void onModuleLoad() {
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable e) {
        Window.alert("uncaught: " + e.getClass().getName() +" - "+ e.getMessage());
        log.log(Level.SEVERE, "uncaught exception", e);
        e.printStackTrace();
      }
    });
    startModule(null);
  }

  private void startModule(PhoneGap phoneGap) {
    SimplePanel modulePanel = new SimplePanel();
    RootPanel.get().add(modulePanel);
    AppClientFactory.IMPL.initModule(modulePanel);
  }

}
