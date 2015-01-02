package it.mate.protons.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.onscommons.client.mvp.OnsAbstractActivity;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.OsDetectionUtils;
import it.mate.protons.client.factories.AppClientFactory;
import it.mate.protons.client.places.MainPlace;
import it.mate.protons.client.view.HomeView;
import it.mate.protons.client.view.SearchView;
import it.mate.protons.client.view.SettingsView;
import it.mate.protons.client.view.SubSettingsView;
import it.mate.protons.shared.model.Account;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class MainActivity extends OnsAbstractActivity implements 
  HomeView.Presenter, SettingsView.Presenter, SearchView.Presenter, SubSettingsView.Presenter
  {
  
  private MainPlace place;
  
  private BaseView view;
  
//private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private final static boolean REMOTE_CALLS_DISABLED = true;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    if (place.getToken().equals(MainPlace.HOME)) {
      CdvUtils.setDesktopDebugBorder(OsDetectionUtils.IPHONE_WIDTH, OsDetectionUtils.IPHONE_3INCH_HEIGHT - OsDetectionUtils.IOS_MARGIN_TOP);
    }

    if (place.getToken().equals(MainPlace.HOME)) {
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          CdvUtils.log("devInfoId is " + devInfoId);
        }
      });
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      SettingsView view = AppClientFactory.IMPL.getGinjector().getSettingsView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.SUB_SETTINGS)) {
      SubSettingsView view = AppClientFactory.IMPL.getGinjector().getSubSettingsView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.SEARCH)) {
      SearchView view = AppClientFactory.IMPL.getGinjector().getSearchView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    retrieveModel();
    
  }
  
  private static int settingsCounter = 0;
  private static int homeCounter = 0;
  
  private void retrieveModel() {
    if (place.getToken().equals(MainPlace.HOME)) {
      incHomeCounter();
    }
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      incSettingsCounter();
    }
  }
  
  public void incHomeCounter() {
    homeCounter++;
    view.setModel("Counter "+homeCounter, "counter");
  }
  
  public void incSettingsCounter() {
    settingsCounter++;
    CdvUtils.log("settings counter = " + settingsCounter);
    view.setModel("Counter "+settingsCounter, "counter");
  }
  
  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
//  OnsenUi.popPage();
    OnsenUi.getNavigator().popPage();
  }

  @Override
  public void goToHomeView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  @Override
  public void goToSettingsView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SETTINGS));
  }

  @Override
  public void goToSubSettingsView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SUB_SETTINGS));
  }

  @Override
  public void goToSearchView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SEARCH));
  }


  private void ensureDevInfoId() {
    String devInfoId = getDevInfoIdFromLocalStorage();
    if (devInfoId != null)
      return;
    
    final String duringGenerateDevInfoSemaphore = "duringGenerateDevInfo";
    
    String duringGenerateDevInfo = (String)GwtUtils.getClientAttribute(duringGenerateDevInfoSemaphore);
    if (duringGenerateDevInfo != null)
      return;
    
    GwtUtils.setClientAttribute(duringGenerateDevInfoSemaphore, "true");
    
//  String os = (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other");
    String os = "unknown";
    String layout = CdvUtils.getLayoutInfo();
    String devName = CdvUtils.getDeviceName();
    String phgVersion = CdvUtils.getDevicePhonegap();
    String platform = CdvUtils.getDevicePlatform();
    String devUuid = CdvUtils.getDeviceUuid();
    String devVersion = CdvUtils.getDeviceVersion();
    
    if (REMOTE_CALLS_DISABLED) {
      CdvUtils.log("REMOTE CALLS DISABLED!");
    } else {
      AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
          new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
              GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
            }
            public void onSuccess(String devInfoId) {
              if (devInfoId != null) {
                CdvUtils.log("received devInfoId "+ devInfoId +" from remote facade");
                setDevInfoIdInLocalStorage(devInfoId);
                GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
              }
            }
        });
    }
    
  }

  public void getDevInfoId(final Delegate<String> delegate) {
    final ObjectWrapper<Boolean> delegateFired = new ObjectWrapper<Boolean>(false);
    GwtUtils.createTimerDelegate(500, true, new Delegate<Timer>() {
      public void execute(Timer timer) {
        ensureDevInfoId();
        String devInfoId = getDevInfoIdFromLocalStorage();
        if (devInfoId != null) {
          timer.cancel();
          if (!delegateFired.get()) {
            delegateFired.set(true);
            delegate.execute(devInfoId);
          }
        }
      }
    });
  }
  
  public void getAccount(final Delegate<Account> delegate) {
//  delegate.execute(MainController.getInstance().getAccountFromLocalStorage());
  }

  protected String getDevInfoIdFromLocalStorage() {
    return CdvUtils.getLocalStorageItem("devInfoId");
  }

  protected void setDevInfoIdInLocalStorage(String devInfoId) {
    CdvUtils.setLocalStorageItem("devInfoId", devInfoId);
  }

}
