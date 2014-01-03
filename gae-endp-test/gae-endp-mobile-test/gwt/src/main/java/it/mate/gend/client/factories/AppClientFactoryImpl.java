package it.mate.gend.client.factories;

import it.mate.gend.client.activities.mapper.MainActivityMapper;
import it.mate.gend.client.activities.mapper.MainAnimationMapper;
import it.mate.gend.client.api.CommandsProxy;
import it.mate.gend.client.api.GreetingsProxy;
import it.mate.gend.client.constants.AppProperties;
import it.mate.gend.client.places.AppHistoryObserver;
import it.mate.gend.client.places.MainPlace;
import it.mate.gend.client.places.MainPlaceHistoryMapper;
import it.mate.gend.client.ui.theme.CustomTheme;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
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
import com.googlecode.mgwt.ui.client.MGWTStyle;

public class AppClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> implements AppClientFactory {

  private PlaceHistoryMapper placeHistoryMapper;
  
  private PlaceController placeController;
  
  private PhoneGap phoneGap;
  
  private static int TABLET_IOS_WRAPPER_PCT = 70;
  
  private static int TABLET_AND_WRAPPER_PCT = 80;
  
  private static int HEADER_PANEL_HEIGHT = 40;
  
  private CommandsProxy commandsProxy;

  private GreetingsProxy greetingsProxy;

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
    
    MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();
    
    CustomTheme.Instance.get().css().ensureInjected();
    
    PhonegapUtils.addOrientationChangeHandler(new Delegate<Void>() {
      public void execute(Void void_) {
        PhonegapUtils.log("CKD - changing orientation handler");
        PhonegapUtils.logEnvironment();
        CustomTheme.Instance.get(true).css().ensureInjected();
      }
    });

    AppClientFactory clientFactory = AppClientFactory.IMPL;

    MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);

    if (MGWT.getOsDetection().isTablet()) {
      Window.alert("Internal error. MGWT Tablet Display Not Supported!");
    } else {
      createPhoneDisplay(clientFactory);
    }

    AppHistoryObserver historyObserver = new AppHistoryObserver();

    final MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(historyMapper, historyObserver);

    historyHandler.register(clientFactory.getPlaceController(), clientFactory.getBinderyEventBus(), new MainPlace());

    /*
    PhonegapUtils.log("initializing commands proxy...");
    commandsProxy = new CommandsProxy(new Delegate<Void>() {
      public void execute(Void element) {
        PhonegapUtils.log("commands proxy initialized");
        historyHandler.handleCurrentHistory();
      }
    });
    */
    
    historyHandler.handleCurrentHistory();

  }

  public GreetingsProxy getGreetingsProxy() {
    return greetingsProxy;
  }
  
  public CommandsProxy getCommandsProxy() {
    return commandsProxy;
  }
  
  private void createPhoneDisplay(AppClientFactory clientFactory) {
    AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
    MainActivityMapper activityMapper = new MainActivityMapper(clientFactory);
    MainAnimationMapper animationMapper = new MainAnimationMapper();
    AnimatingActivityManager activityManager = new AnimatingActivityManager(activityMapper, animationMapper, clientFactory.getBinderyEventBus());
    activityManager.setDisplay(display);
    RootPanel.get().add(display);
    AndroidBackButtonHandler.start();
  }

  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    super.initMvp(panel, getPlaceHistoryMapper(), activityMapper);
  }
  
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
  public PhoneGap getPhoneGap() {
    return phoneGap;
  }

  @Override
  public int getWrapperPct() {
    int wrapperPct = MGWT.getOsDetection().isIOs() ? TABLET_IOS_WRAPPER_PCT : TABLET_AND_WRAPPER_PCT;
    return wrapperPct;
  }
  
  public void adaptWrapperPanel(Panel wrapperPanel, String id, final boolean adaptVerMargin, final int headerPanelHeight, final Delegate<Element> delegate) {
    wrapperPanel.getElement().setId(id);
    if (OsDetectionUtils.isTablet()) {
      GwtUtils.onAvailable(id, new Delegate<Element>() {
        public void execute(Element wrapperPanelElem) {
          
          int height = getTabletWrapperHeight();
          
          GwtUtils.log("applying wrapperPanel height = " + height);
          
          wrapperPanelElem.getStyle().setHeight(height, Unit.PX);

          if (adaptVerMargin) {
            int verMargin = ( Window.getClientHeight() - height ) / 2 - headerPanelHeight;
            wrapperPanelElem.getStyle().setMarginTop(verMargin, Unit.PX);
            wrapperPanelElem.getStyle().setMarginBottom(verMargin, Unit.PX);
          }
          
          int width = getTabletWrapperWidth();
          wrapperPanelElem.getStyle().setWidth(width, Unit.PX);
          
          int horMargin = ( Window.getClientWidth() - width ) / 2;
          wrapperPanelElem.getStyle().setMarginLeft(horMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginRight(horMargin, Unit.PX);
          
          if (delegate != null) {
            delegate.execute(wrapperPanelElem);
          }
          
        }
      });
      
    } else {
      
      //09/10/2013
      // Patch per IPhone per far funzionare le animazioni jquery
      wrapperPanel.setHeight((Window.getClientHeight() - HEADER_PANEL_HEIGHT ) + "px");
      wrapperPanel.setWidth(Window.getClientWidth() + "px");
      
    }
  }

  public int getTabletWrapperHeight() {
    int wrapperPct = AppClientFactory.IMPL.getWrapperPct();
    int height = Window.getClientHeight() * wrapperPct / 100;
    return height;
  }

  public int getTabletWrapperWidth() {
    int wrapperPct = AppClientFactory.IMPL.getWrapperPct();
    int width = Window.getClientWidth() * wrapperPct / 100;
    return width;
  }

  @SuppressWarnings("rawtypes")
  public void initTitle(BaseMgwtView view) {
    if (OsDetectionUtils.isTablet()) {
      view.setTitleHtml(AppProperties.IMPL.tabletAppName());
    } else {
      view.setTitleHtml(AppProperties.IMPL.phoneAppName());
    }
  }
  
}
