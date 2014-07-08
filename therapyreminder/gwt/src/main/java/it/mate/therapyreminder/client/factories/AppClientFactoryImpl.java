package it.mate.therapyreminder.client.factories;

import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.plugins.LocalNotificationsPlugin;
import it.mate.phgcommons.client.plugins.NativePropertiesPlugin;
import it.mate.phgcommons.client.ui.theme.DefaultTheme;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.IOSPatches;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.activities.mapper.MainAnimationMapper;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.logic.MainController;
import it.mate.therapyreminder.client.logic.MainDao;
import it.mate.therapyreminder.client.places.AppHistoryObserver;
import it.mate.therapyreminder.client.places.MainPlace;
import it.mate.therapyreminder.client.places.MainPlaceHistoryMapper;
import it.mate.therapyreminder.client.ui.theme.CustomTheme;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.service.RemoteFacadeAsync;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Timer;
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
  
  private Map<String, String> nativeProperties;

  private RemoteFacadeAsync remoteFacade = null;
  
  private boolean alertsEnabled = true;
  
  
  
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

    GwtUtils.setMobileOptimizations(true);
    GwtUtils.setEnableLogInProductionMode(true);
    
    ViewPort viewPort = new MGWTSettings.ViewPort();
    viewPort.setTargetDensity(DENSITY.MEDIUM);
    viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);
    
    // 20/03/2014
    IOSPatches.applyViewPortPatch(viewPort);

    MGWTSettings settings = new MGWTSettings();
    settings.setViewPort(viewPort);
    settings.setIconUrl("logo.png");
    settings.setAddGlosToIcon(true);
    settings.setFullscreen(true);
    settings.setPreventScrolling(true);

    MGWT.applySettings(settings);
    
    // 20/03/2014
    IOSPatches.applyStatusBarStylePatch();
    IOSPatches.applyIOS7HeaderBarPatch();
    
    MGWTStyle.getTheme().getMGWTClientBundle().getHeaderCss().ensureInjected();
    MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();
    MGWTStyle.getTheme().getMGWTClientBundle().getButtonCss().ensureInjected();
//  MGWTStyle.getTheme().getMGWTClientBundle().getInputCss().ensureInjected();
    
    DefaultTheme.Impl.get().css().ensureInjected();
    
    PhgUtils.log("LOCAL NOTIFICATION PLUGIN INSTALLED = " + LocalNotificationsPlugin.isInstalled());
    
    CustomTheme.Instance.get().css().ensureInjected();

    // 07/07/2014: bug fix 
    if (PhgUtils.getAppLocalLanguageImpl() == null) {
      if (Window.Location.getHref().contains("index.html")) {
        if ("it".equals(PhgUtils.getCurrentLanguage())) {
          PhgUtils.setAppLocalLanguageAndReload("it");
          return;
        }
      }
    }
    
    PhgUtils.commonInitializations();
    
    PhgUtils.addOrientationChangeHandler(new Delegate<Void>() {
      public void execute(Void void_) {
        PhgUtils.logEnvironment();
        CustomTheme.Instance.get(true).css().ensureInjected();
      }
    });

    MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);

    if (MGWT.getOsDetection().isTablet()) {
      Window.alert("Internal error. MGWT Tablet Display Not Supported!");
    } else {
      createPhoneDisplay();
    }
  
    AppHistoryObserver historyObserver = new AppHistoryObserver();

    MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(historyMapper, historyObserver);

    startTimersAndHistory(new FindSomministrazioniScaduteDelegate(historyHandler));
    
  }
  
  private void initHistoryHandler(final MGWTPlaceHistoryHandler historyHandler, MainPlace defaultPlace) {
    historyHandler.register(this.getPlaceController(), this.getBinderyEventBus(), defaultPlace);
    NativePropertiesPlugin.getProperties(new Delegate<Map<String,String>>() {
      public void execute(Map<String, String> properties) {
        for (String name : properties.keySet()) {
          PhgUtils.log("natProp " + name + "=" + properties.get(name));
        }
        AppClientFactoryImpl.this.nativeProperties = properties;
        historyHandler.handleCurrentHistory();
      }
    });
  }
  
  private void createPhoneDisplay() {
    AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
    MainActivityMapper activityMapper = new MainActivityMapper(this);
    MainAnimationMapper animationMapper = new MainAnimationMapper();
    AnimatingActivityManager activityManager = new AnimatingActivityManager(activityMapper, animationMapper, this.getBinderyEventBus());
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
      placeController = new PlaceControllerWithHistory(getGinjector().getBinderyEventBus(), new PlaceController.DefaultDelegate());
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
  
  @Override
  public String getNativeProperty(String name, String defValue) {
    String value = null;
    if (value == null) {
      value = PhgUtils.getWindowSetting(name);
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
  
  @Override
  public MainDao getPrescrizioniDao() {
    return ginjector.getPrescrizioniDao();
  }
  
  // 15/05/2014
  private void startTimersAndHistory(final FindSomministrazioniScaduteDelegate findSomministrazioneScadutaDelegate) {
    
    // setting dao error delegate
    getPrescrizioniDao().setErrorDelegate(new Delegate<String>() {
      public void execute(String errorMessage) {
        PhgUtils.log(errorMessage);
        PhgDialogUtils.showMessageDialog(errorMessage);
      }
    });
    
    // purge orphan notifications
    LocalNotificationsPlugin.getScheduledIds(new Delegate<List<String>>() {
      public void execute(List<String> ids) {
        MainController.getInstance().purgeNotificationIds(ids);
      }
    });
    
    if (!OsDetectionUtils.isDesktop()) {
      LocalNotificationsPlugin.setOnCancel(new LocalNotificationsPlugin.JSOEventCallback() {
        public void handleEvent(String id, String state, String json) {
          PhgUtils.log("RECEIVED CANCEL NOTIFICATION EVENT: id="+id);
        }
      });
    }
    
    // starting timers
    final ObjectWrapper<Timer> timer = new ObjectWrapper<Timer>();
    timer.set(GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        if (getPrescrizioniDao().isReady()) {
          findSomministrazioneScadutaDelegate.execute(null);
          timer.get().cancel();
          GwtUtils.createTimer(5000, new Delegate<Void>() {
            public void execute(Void element) {
              findSomministrazioneScadutaDelegate.execute(null);
            }
          });
          GwtUtils.createTimer(20000, true, new Delegate<Void>() {
            public void execute(Void element) {
//            PhonegapLog.log("sviluppo somministrazioni in background...");
              MainController.getInstance().sviluppaSomministrazioniInBackground();
            }
          });
        }
      }
    }));
  }
  
  public void setBackgroundAlertsEnabled(boolean enabled) {
    this.alertsEnabled = enabled;
  }
  
  protected class FindSomministrazioniScaduteDelegate implements Delegate<Void> {
    
    private boolean firstRun = true;
    
    private MGWTPlaceHistoryHandler historyHandler;
    
    private MainPlace defaultPlace = new MainPlace();
    
    protected FindSomministrazioniScaduteDelegate(MGWTPlaceHistoryHandler historyHandler) {
      this.historyHandler = historyHandler;
    }

    @Override
    public void execute(Void element) {
      if (!alertsEnabled)
        return;
      MainController.getInstance().findSomministrazioniScadute(new Delegate<List<Somministrazione>>() {
        public void execute(final List<Somministrazione> somministrazioni) {
          if (firstRun) {
            if (somministrazioni != null && somministrazioni.size() > 0) {
              
              if (somministrazioni.size() == 1) {
                defaultPlace = new MainPlace(MainPlace.REMINDER_EDIT, somministrazioni.get(0));
              } else {
                
                defaultPlace = new MainPlace(MainPlace.REMINDER_LIST);
                GwtUtils.deferredExecution(500, new Delegate<Void>() {
                  public void execute(Void element) {
                    PhgDialogUtils.showMessageDialog("Il sistema ha trovato diverse somministrazioni scadute. Vuoi annullarle tutte in blocco?", "Alert", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
                      public void execute(Integer btn) {
                        if (btn == 1) {
                          MainController.getInstance().annullaSomministrazioni(somministrazioni, new Delegate<Void>() {
                            public void execute(Void element) {
                              PhgDialogUtils.showMessageDialog("Somministrazioni annullate");
                              getPlaceController().goTo(new MainPlace());
                            }
                          });
                        } else {
                          getPlaceController().goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazioni.get(0)));
                        }
                      }
                    });
                  }
                });
                
              }
              
            }
            initHistoryHandler(historyHandler, defaultPlace);
          } else {
            if (somministrazioni != null && somministrazioni.size() > 0) {
              getPlaceController().goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazioni.get(0)));
            }
          }
          firstRun = false;
        }
      });
    }
  }

}
