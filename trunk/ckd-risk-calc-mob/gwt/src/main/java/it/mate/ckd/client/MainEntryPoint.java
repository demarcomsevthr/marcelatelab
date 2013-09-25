package it.mate.ckd.client;

import it.mate.ckd.client.activities.mapper.MainActivityMapper;
import it.mate.ckd.client.activities.mapper.MainAnimationMapper;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.places.AppHistoryObserver;
import it.mate.ckd.client.places.MainPlace;
import it.mate.ckd.client.places.MainPlaceHistoryMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.OsDetectionPatch;

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
    GwtUtils.log("***********    STARTING NEW APP INSTANCE   ***********");
    GwtUtils.logEnvironment(getClass(), "onModuleLoad");
    
    GwtUtils.log("os detection = " + (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other"));
    String layoutInfo = "Width " + Window.getClientWidth();
    layoutInfo += " Height " + Window.getClientHeight();
    if (OsDetectionPatch.isTabletLandscape()) {
      layoutInfo += " isTabletLandscape";
    } else if (OsDetectionPatch.isTabletPortrait()) {
      layoutInfo += " isTabletPortrait";
    } else {
      layoutInfo += " isPhone";
    }
    GwtUtils.log("Layout = " + layoutInfo);
    GwtUtils.log("(see it.mate.phgcommons.client.utils.OsDetectionPatch for details)");
    
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
