package it.mate.protons.client.factories;

import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.mvp.MvpUtils;
import it.mate.onscommons.client.mvp.OnsActivityMapper;
import it.mate.onscommons.client.mvp.OnsNavigationDisplay;
import it.mate.onscommons.client.place.PlaceControllerWithHistory;
import it.mate.onscommons.client.ui.theme.DefaultTheme;
import it.mate.onscommons.client.utils.OnsUtils;
import it.mate.onscommons.client.utils.OsDetectionUtils;
import it.mate.onscommons.client.utils.callbacks.VoidCallback;
import it.mate.protons.client.activities.mapper.MainActivityMapper;
import it.mate.protons.client.places.MainPlace;
import it.mate.protons.client.places.MainPlaceHistoryMapper;
import it.mate.protons.shared.service.RemoteFacadeAsync;

import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
//import com.google.gwt.event.shared.EventBus;

public class AppClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> implements AppClientFactory {

  private PlaceHistoryMapper placeHistoryMapper;
  
  private PlaceController placeController;
  
  private PhoneGap phoneGap;
  
  private static int TABLET_IOS_WRAPPER_PCT = 70;
  
  private static int TABLET_AND_WRAPPER_PCT = 80;
  
  private static int HEADER_PANEL_HEIGHT = 40;
  
  private Map<String, String> nativeProperties;

  private RemoteFacadeAsync remoteFacade = null;
  
  
  
  @Override
  public void initModule(final Panel modulePanel) {
    this.phoneGap = GWT.create(PhoneGap.class);
    phoneGap.addHandler(new PhoneGapAvailableHandler() {
      @Override
      public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
        OnsUtils.log("PHONEGAP AVAILABLE EVENT");
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

    GwtUtils.setMobileOptimizations(true);
    GwtUtils.setEnableLogInProductionMode(true);
    
    /*
    ViewPort viewPort = new MGWTSettings.ViewPort();
    viewPort.setTargetDensity(DENSITY.MEDIUM);
    viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);
    */

    /*
    MGWTSettings settings = new MGWTSettings();
    settings.setViewPort(viewPort);
    settings.setIconUrl("logo.png");
    settings.setAddGlosToIcon(true);
    settings.setFullscreen(true);
    settings.setPreventScrolling(true);
    MGWT.applySettings(settings);
    */

    /*
    MGWTStyle.getTheme().getMGWTClientBundle().getHeaderCss().ensureInjected();
    MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();
    MGWTStyle.getTheme().getMGWTClientBundle().getButtonCss().ensureInjected();
    MGWTStyle.getTheme().getMGWTClientBundle().getInputCss().ensureInjected();
    */
    
    DefaultTheme.Impl.get().css().ensureInjected();
    
//  CustomTheme.Instance.get().css().ensureInjected();

    // 07/07/2014: bug fix 
    if (OnsUtils.getAppLocalLanguageImpl() == null) {
      if (Window.Location.getHref().contains("index.html")) {
        if ("it".equals(OnsUtils.getLocaleLanguageFromLocaleInfo())) {
//        OnsUtils.setAppLocalLanguageAndReload("it");
          return;
        }
      }
    }
    
    OnsUtils.commonInitializations();

    if (OsDetectionUtils.isIOs()) {
      OnsUtils.log("setting resize handler");
      OnsUtils.addResizeHandler(new VoidCallback() {
        public void handle() {
          OnsUtils.reloadApp();
        }
      });
    }

    createDisplay();
  
  }

  private void createDisplay() {
    MainActivityMapper activityMapper = new MainActivityMapper(this);
    initMvp(null, activityMapper);
  }
  
  @Override
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    OnsActivityMapper onsMapper = (OnsActivityMapper)activityMapper;
    OnsNavigationDisplay display = new OnsNavigationDisplay(onsMapper);
    MainPlace defaultPlace = new MainPlace();
    MvpUtils.initOnsMvp(this, display, onsMapper, defaultPlace);
    
    /** COSI FUNZIONA LA PRIMA VOLTA:
    OnsenUi.initializeOnsen(new OnsenReadyHandler() {
      public void onReady() {
        OnsUtils.log("ONSEN READY");
      }
    });
    OnsenUi.pushPage(defaultPlace.getToken());
    **/
    
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
  public com.google.gwt.event.shared.EventBus getEventBus() {
    throw new RuntimeException("Cannot use com.google.gwt.event.shared.EventBus in mgwt app, use instead injector.getBinderyEventBus");
  }
  
  public com.google.web.bindery.event.shared.EventBus getBinderyEventBus() {
    return getGinjector().getBinderyEventBus();
  }
  
  @Override
  public PlaceController getPlaceController() {
    if (placeController == null)
      placeController = new PlaceControllerWithHistory(getGinjector().getBinderyEventBus(), new PlaceController.DefaultDelegate());
    return placeController;
  }
  
  @Override
  public PhoneGap getPhoneGap() {
    return phoneGap;
  }

  @Override
  public String getNativeProperty(String name, String defValue) {
    String value = null;
    if (value == null) {
      value = OnsUtils.getWindowSetting(name);
    }
    if (value == null && nativeProperties != null) {
      value = nativeProperties.get(name);
    }
    if (value == null) {
      value = defValue;
    }
    return value;
  }
  
  @Override
  public boolean getNativeProperty(String name, boolean defValue) {
    String value = getNativeProperty(name, null);
    if (value == null)
      return defValue;
    return Boolean.parseBoolean(value);
  }
  
  @Override
  public RemoteFacadeAsync getRemoteFacade() {
    if (remoteFacade == null) {
      remoteFacade = getGinjector().getRemoteFacade();
    }
    return remoteFacade;
  }

}
