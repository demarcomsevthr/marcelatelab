package it.mate.abaco.client.factories;

import it.mate.abaco.client.activities.mapper.MainActivityMapper;
import it.mate.abaco.client.activities.mapper.MainAnimationMapper;
import it.mate.abaco.client.places.AppHistoryObserver;
import it.mate.abaco.client.places.MainPlace;
import it.mate.abaco.client.places.MainPlaceHistoryMapper;
import it.mate.abaco.client.utils.ZIndexPatch;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.mvp.client.history.MGWTPlaceHistoryHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;

public class AppClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> implements AppClientFactory {

  private PlaceHistoryMapper placeHistoryMapper;
  
  private PlaceController placeController;
  
  private PhoneGap phoneGap;
  
  @Override
  protected AppGinjector createGinjector() {
    return GWT.create(AppGinjector.class);
  }
  
  @Override
  public PlaceHistoryMapper getPlaceHistoryMapper() {
    if (placeHistoryMapper == null)
      placeHistoryMapper = GWT.create(MainPlaceHistoryMapper.class);
    return placeHistoryMapper;
  }
  
  @Override
  public EventBus getEventBus() {
    throw new RuntimeException("Cannot use com.google.gwt.event.shared.EventBus in mgwt app, use instead injector.getBinderyEventBus");
  }
  
  public com.google.web.bindery.event.shared.EventBus getBinderyEventBus() {
    return getGinjector().getBinderyEventBus();
  }
  
  @Override
  public PlaceController getPlaceController() {
    if (placeController == null)
      placeController = new PlaceController(getGinjector().getBinderyEventBus(), new PlaceController.DefaultDelegate());
    return placeController;
  }
  
  @Override
  public void initModule(final Panel modulePanel) {
    
    this.phoneGap = GWT.create(PhoneGap.class);

    phoneGap.addHandler(new PhoneGapAvailableHandler() {
      @Override
      public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
        initDisplay(modulePanel);
      }
    });

    phoneGap.addHandler(new PhoneGapTimeoutHandler() {
      @Override
      public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
        Window.alert("cannot load phonegap");
      }
    });

    phoneGap.initializePhoneGap();
  }
  
  private void initDisplay(Panel modulePanel) {
    
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
    
    ZIndexPatch.apply();
    
  }

  private void createTabletDisplay(AppClientFactory clientFactory) {

  }

  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    super.initMvp(panel, getPlaceHistoryMapper(), activityMapper);
  }
  
}
