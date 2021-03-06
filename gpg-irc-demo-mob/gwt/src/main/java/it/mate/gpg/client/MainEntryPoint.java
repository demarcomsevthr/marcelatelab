package it.mate.gpg.client;

import it.mate.gpg.client.activities.mapper.MainActivityMapper;
import it.mate.gpg.client.activities.mapper.MainAnimationMapper;
import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gpg.client.places.AppHistoryObserver;
import it.mate.gpg.client.places.MainPlace;
import it.mate.gpg.client.places.MainPlaceHistoryMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.mvp.client.history.MGWTPlaceHistoryHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;

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
  
  private void startApp_SAVE() {

    // SuperDevModeUtil.showDevMode();

    ViewPort viewPort = new MGWTSettings.ViewPort();
    viewPort.setTargetDensity(DENSITY.MEDIUM);
    viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);

    MGWTSettings settings = new MGWTSettings();
    settings.setViewPort(viewPort);
    settings.setIconUrl("logo.png");
    settings.setAddGlosToIcon(true);
    settings.setFullscreen(true);
    settings.setPreventScrolling(true);

    MGWT.applySettings(settings);

    AppClientFactory clientFactory = AppClientFactory.IMPL;

    // Start PlaceHistoryHandler with our PlaceHistoryMapper
    MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);

    if (MGWT.getOsDetection().isTablet()) {

      // StyleInjector.inject(AppBundle.INSTANCE.css().getText());

      createTabletDisplay(clientFactory);
    } else {

      createPhoneDisplay(clientFactory);

    }

    AppHistoryObserver historyObserver = new AppHistoryObserver();

    MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(historyMapper, historyObserver);

    historyHandler.register(clientFactory.getPlaceController(), clientFactory.getBinderyEventBus(), new MainPlace());
    historyHandler.handleCurrentHistory();

  }

  private void createPhoneDisplay(AppClientFactory clientFactory) {
    AnimatableDisplay display = GWT.create(AnimatableDisplay.class);

    MainActivityMapper activityMapper = new MainActivityMapper(clientFactory);

    MainAnimationMapper animationMapper = new MainAnimationMapper();

    AnimatingActivityManager activityManager = new AnimatingActivityManager(activityMapper, animationMapper, clientFactory.getBinderyEventBus());

    activityManager.setDisplay(display);

    RootPanel.get().add(display);
  }

  private void createTabletDisplay(AppClientFactory clientFactory) {

  }

}
