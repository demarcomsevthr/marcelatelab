package it.mate.protons.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.mvp.OnsAbstractActivity;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.protons.client.factories.AppClientFactory;
import it.mate.protons.client.logic.MainDao;
import it.mate.protons.client.places.MainPlace;
import it.mate.protons.client.view.ApplicationEditView;
import it.mate.protons.client.view.ApplicationListView;
import it.mate.protons.client.view.HomeView;
import it.mate.protons.client.view.IngredientListView;
import it.mate.protons.client.view.LayoutView;
import it.mate.protons.client.view.MenuView;
import it.mate.protons.client.view.SearchView;
import it.mate.protons.client.view.SettingsView;
import it.mate.protons.client.view.SubSettingsView;
import it.mate.protons.shared.model.Account;
import it.mate.protons.shared.model.Applicazione;
import it.mate.protons.shared.model.impl.ApplicazioneTx;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class MainActivity extends OnsAbstractActivity implements 
  MenuView.Presenter,
  HomeView.Presenter, SettingsView.Presenter, SearchView.Presenter, SubSettingsView.Presenter,
  ApplicationListView.Presenter, ApplicationEditView.Presenter,
  IngredientListView.Presenter,
  LayoutView.Presenter
  {
  
  
  private MainPlace place;
  
  private BaseView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private final static boolean REMOTE_CALLS_DISABLED = true;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    if (place.getToken().equals(MainPlace.HOME)) {
      PhgUtils.setDesktopDebugBorder(OsDetectionUtils.IPHONE_WIDTH, OsDetectionUtils.IPHONE_3INCH_HEIGHT - OsDetectionUtils.IOS_MARGIN_TOP);
    }

    if (OnsenUi.isSlidingMenuLayoutPattern()) {
      OnsenUi.getSlidingMenu().setSwipeable(true);
    }
    
    if (place.getToken().equals(MainPlace.HOME)) {
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          PhgUtils.log("devInfoId is " + devInfoId);
        }
      });
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.MENU)) {
      MenuView view = AppClientFactory.IMPL.getGinjector().getMenuView();
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
    
    if (place.getToken().equals(MainPlace.APPLICATION_LIST)) {
      ApplicationListView view = AppClientFactory.IMPL.getGinjector().getApplicationListView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.APPLICATION_EDIT)) {
      ApplicationEditView view = AppClientFactory.IMPL.getGinjector().getApplicationEditView();
      this.view = view;
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    
    if (place.getToken().equals(MainPlace.INGREDIENT_LIST)) {
      IngredientListView view = AppClientFactory.IMPL.getGinjector().getIngredientListView();
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
    if (place.getToken().equals(MainPlace.APPLICATION_LIST)) {
      dao.findAllApplicazioni(new Delegate<List<Applicazione>>() {
        public void execute(List<Applicazione> results) {
          view.setModel(results, "applications");
        }
      });
    }
    if (place.getToken().equals(MainPlace.APPLICATION_EDIT)) {
      view.setModel(place.getModel(), "application");
    }
    
    if (place.getToken().equals(MainPlace.INGREDIENT_LIST)) {
      view.setModel("init", "ingredients");
    }
    
  }
  
  public void incHomeCounter() {
    homeCounter++;
    view.setModel("Counter "+homeCounter, "counter");
  }
  
  public void incSettingsCounter() {
    settingsCounter++;
    PhgUtils.log("settings counter = " + settingsCounter);
    view.setModel("Counter "+settingsCounter, "counter");
  }
  
  @Override
  public BaseView getView() {
    return view;
  }

  @Override
  public void goToPrevious() {
    if (OnsenUi.isNavigatorLayoutPattern()) {
      OnsenUi.getNavigator().popPage();
    } else {
      if (AppClientFactory.IMPL.getPlaceController() instanceof PlaceControllerWithHistory) {
        PlaceControllerWithHistory placeController = (PlaceControllerWithHistory)AppClientFactory.IMPL.getPlaceController();
        Place prevPlace = placeController.getPreviousPlace();
        if (prevPlace == null) {
          prevPlace = new MainPlace();
        }
        placeController.goToWithEvent(new OnsPlaceChangeEvent(prevPlace).setAnimation(OnsenUi.ANIMATION_NATIVE_POP));
        return;
      }
      AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace());
    }
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

  @Override
  public void goToApplicationListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.APPLICATION_LIST));
  }
  
  @Override
  public void goToApplicationEditView(Applicazione applicazione) {
    if (applicazione == null) {
      applicazione = new ApplicazioneTx();
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.APPLICATION_EDIT, applicazione));
  }
  
  @Override
  public void goToIngredientListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.INGREDIENT_LIST));
  }

  @Override
  public void showMenu() {
    OnsenUi.getSlidingMenu().toggleMenu();
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
    String layout = PhgUtils.getLayoutInfo();
    String devName = PhgUtils.getDeviceName();
    String phgVersion = PhgUtils.getDevicePhonegap();
    String platform = PhgUtils.getDevicePlatform();
    String devUuid = PhgUtils.getDeviceUuid();
    String devVersion = PhgUtils.getDeviceVersion();
    
    if (REMOTE_CALLS_DISABLED) {
      PhgUtils.log("REMOTE CALLS DISABLED!");
    } else {
      AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
          new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
              GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
            }
            public void onSuccess(String devInfoId) {
              if (devInfoId != null) {
                PhgUtils.log("received devInfoId "+ devInfoId +" from remote facade");
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
    return PhgUtils.getLocalStorageItem("devInfoId");
  }

  protected void setDevInfoIdInLocalStorage(String devInfoId) {
    PhgUtils.setLocalStorageItem("devInfoId", devInfoId);
  }
  
  public void saveApplicazione(Applicazione applicazione) {
//  setHeaderWaiting(true);
    dao.saveApplicazione(applicazione, new Delegate<Applicazione>() {
      public void execute(Applicazione savedApplicazione) {
//      setHeaderWaiting(false);
        Window.alert("Saved");
      }
    });
  }
  

}
