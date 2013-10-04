package it.mate.ckd.client.factories;

import it.mate.ckd.client.activities.mapper.MainActivityMapper;
import it.mate.ckd.client.activities.mapper.MainAnimationMapper;
import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.places.AppHistoryObserver;
import it.mate.ckd.client.places.MainPlace;
import it.mate.ckd.client.places.MainPlaceHistoryMapper;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
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

    AppClientFactory clientFactory = AppClientFactory.IMPL;

    MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);

    if (MGWT.getOsDetection().isTablet()) {
      Window.alert("Internal error. MGWT Tablet Display Not Supported!");
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
    // 21/02/2013
//  ZIndexPatch.apply();
    
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
  
  
  public void adaptWrapperPanelOnTablet(Panel wrapperPanel, String id, final boolean adaptVerMargin, final Delegate<Element> delegate) {
    wrapperPanel.getElement().setId(id);
    if (OsDetectionUtils.isTablet()) {
      GwtUtils.onAvailable(id, new Delegate<Element>() {
        public void execute(Element wrapperPanelElem) {
          int wrapperPct = AppClientFactory.IMPL.getWrapperPct();
          
          int height = Window.getClientHeight() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setHeight(height, Unit.PX);

          if (adaptVerMargin) {
            int verMargin = ( Window.getClientHeight() - height ) / 2;
            wrapperPanelElem.getStyle().setMarginTop(verMargin, Unit.PX);
            wrapperPanelElem.getStyle().setMarginBottom(verMargin, Unit.PX);
          }
          
          int width = Window.getClientWidth() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setWidth(width, Unit.PX);
          
          int horMargin = ( Window.getClientWidth() - width ) / 2;
          wrapperPanelElem.getStyle().setMarginLeft(horMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginRight(horMargin, Unit.PX);
          
          /*
          List<Element> spacers = JQueryUtils.selectList(".ckdHomeSpacer");
          for (Element spacer : spacers) {
            int spacerHeight = spacer.getClientHeight() / 2;
            spacer.getStyle().setHeight(spacerHeight, Unit.PX);
          }
          */
          
          if (delegate != null) {
            delegate.execute(wrapperPanelElem);
          }
          
        }
      });
    }
  }

  public void initTitle(BaseMgwtView view) {
    if (OsDetectionUtils.isTablet()) {
      view.setTitleHtml(AppConstants.IMPL.tabletAppName());
    } else {
      view.setTitleHtml(AppConstants.IMPL.phoneAppName());
    }
  }
  
  
}
