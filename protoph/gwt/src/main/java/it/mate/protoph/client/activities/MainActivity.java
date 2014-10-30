package it.mate.protoph.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.plugins.FileSystemPlugin;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.protoph.client.constants.AppProperties;
import it.mate.protoph.client.factories.AppClientFactory;
import it.mate.protoph.client.logic.MainDao;
import it.mate.protoph.client.places.MainPlace;
import it.mate.protoph.client.view.AboutView;
import it.mate.protoph.client.view.ApplicationApplyView;
import it.mate.protoph.client.view.ApplicationEditView;
import it.mate.protoph.client.view.ApplicationListView;
import it.mate.protoph.client.view.HomeView;
import it.mate.protoph.client.view.IngredientListView;
import it.mate.protoph.client.view.SettingsView;
import it.mate.protoph.shared.model.Account;
import it.mate.protoph.shared.model.Applicazione;
import it.mate.protoph.shared.model.PrincipioAttivo;
import it.mate.protoph.shared.model.impl.ApplicazioneTx;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter,
  ApplicationListView.Presenter,
  ApplicationEditView.Presenter,
  IngredientListView.Presenter,
  ApplicationApplyView.Presenter,
  SettingsView.Presenter,
  AboutView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private final static boolean REMOTE_CALLS_DISABLED = true;
  
  private final static String dataPath = "www/main/data";
  
  private final static String workPath = "protoph/workArea";
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    if (place.getToken().equals(MainPlace.HOME)) {
      PhgUtils.setDesktopDebugBorder(OsDetectionUtils.IPHONE_WIDTH, OsDetectionUtils.IPHONE_3INCH_HEIGHT - OsDetectionUtils.IOS_MARGIN_TOP);
    }

    if (place.getToken().equals(MainPlace.HOME)) {
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          PhgUtils.log("devInfoId is " + devInfoId);
        }
      });
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      this.view = view;
      initBaseMgwtView(true);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          AppClientFactory.IMPL.getPhoneGap().exitApp();
        }
      });
    }
    if (place.getToken().equals(MainPlace.APPLICATION_LIST)) {
      ApplicationListView view = AppClientFactory.IMPL.getGinjector().getApplicationListView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.APPLICATION_EDIT)) {
      ApplicationEditView view = AppClientFactory.IMPL.getGinjector().getApplicationEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.APPLICATION_APPLY)) {
      ApplicationApplyView view = AppClientFactory.IMPL.getGinjector().getApplicationApplyView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.INGREDIENT_LIST)) {
      IngredientListView view = AppClientFactory.IMPL.getGinjector().getIngredientListView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      SettingsView view = AppClientFactory.IMPL.getGinjector().getSettingsView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToPrevious();
        }
      });
    }
    retrieveModel();
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(MainPlace.APPLICATION_LIST)) {
      dao.findAllApplicazioni(new Delegate<List<Applicazione>>() {
        public void execute(List<Applicazione> results) {
          view.setModel(results, ApplicationListView.TAG_APPLICAZIONI);
        }
      });
    }
    if (place.getToken().equals(MainPlace.APPLICATION_EDIT)) {
      if (place.getModel() != null) {
        view.setModel(place.getModel(), ApplicationEditView.TAG_APPLICAZIONE);
      } else if (getEditingApplicazione() != null) {
        view.setModel(getEditingApplicazione(), ApplicationEditView.TAG_APPLICAZIONE);
      }
    }
    if (place.getToken().equals(MainPlace.APPLICATION_APPLY)) {
      view.setModel(place.getModel(), ApplicationApplyView.TAG_APPLICAZIONE);
    }
    if (place.getToken().equals(MainPlace.INGREDIENT_LIST)) {
      dao.findAllPrincipiAttivi(new Delegate<List<PrincipioAttivo>>() {
        public void execute(List<PrincipioAttivo> results) {
          Applicazione editingApplicazione = getEditingApplicazione();
          for (Iterator<PrincipioAttivo> it = results.iterator(); it.hasNext();) {
            PrincipioAttivo result = it.next();
            for (PrincipioAttivo principioSelezionato : editingApplicazione.getPrincipiAttivi()) {
              if (principioSelezionato.getId().equals(result.getId())) {
                it.remove();
              }
            }
          }
          view.setModel(results, IngredientListView.TAG_INGREDIENTS);
        }
      });
    }
  }
  
  private void processFailure(String message, Throwable caught) {
    setHeaderWaiting(false);
    String popupTitle = "Alert";
    String popupMsg = "Failure";
    String logMsg = null;
    if (message != null) {
      popupMsg = message;
    } else if (caught != null) {
      caught.printStackTrace();
      logMsg = caught.getClass().getName()+" - "+caught.getMessage();
      if (caught instanceof InvocationException) {
//      popupMsg = "Maybe data connection is not active";
        popupMsg = null;
      } else {
        if (caught.getMessage() != null) {
          popupMsg = caught.getMessage();
        } else {
          popupMsg = caught.getClass().getName();
        }
      }
      popupTitle = "Error";
    }
    if (logMsg != null)
      PhgUtils.log(logMsg);
    if (popupMsg != null) {
      PhgDialogUtils.showMessageDialog(popupMsg, popupTitle, PhgDialogUtils.BUTTONS_OK);
    }
  }
  
  private void setHeaderWaiting(boolean flag) {
    if (flag) {
      view.getTitle().addStyleName("ui-header-panel-center-waiting");
    } else {
      view.getTitle().removeStyleName("ui-header-panel-center-waiting");
    }
    setVisibleHomeBtn(!flag);
  }
  
  private void initBaseMgwtView(boolean home) {
    if (OsDetectionUtils.isTablet()) {
      view.setTitleHtml(AppProperties.IMPL.tabletAppName());
    } else {
      view.setTitleHtml(AppProperties.IMPL.phoneAppName());
    }
    view.getTitle().addStyleName("ui-header-panel-center");
    if (home) {
      view.getHeaderPanel().addStyleName("ui-header-panel-home");
    } else {
      view.getHeaderPanel().addStyleName("ui-header-panel-no-home");
    }
    if (!home) {
      setVisibleHomeBtn(true);
    }
  }
  
  private void setVisibleOptionsBtn(boolean visible) {
    if (visible) {
      TouchImage optionsBtn = new TouchImage();
      optionsBtn.addStyleName("ui-optionsBtn");
      optionsBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          
       }
      });
      view.getHeaderPanel().setRightWidget(optionsBtn);
    } else {
      view.getHeaderPanel().setRightWidget(new Label());
    }
  }
  
  private void setVisibleHomeBtn(boolean visible) {
    if (visible) {
      TouchImage homeBtn = new TouchImage();
      homeBtn.addStyleName("ui-homeBtn");
      homeBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          goToHome();
        }
      });
      view.getHeaderPanel().setRightWidget(homeBtn);
    } else {
      view.getHeaderPanel().setRightWidget(new Label());
    }
  }
  
  @SuppressWarnings("unchecked")
  private void setBackButtonDelegate(final Delegate<Void> goToBackDelegate) {
    final Delegate<Void> wrappedGoToBackDelegate = new Delegate<Void>() {
      public void execute(Void element) {
        if (view instanceof HasClosingViewHandler) {
          HasClosingViewHandler closingView = (HasClosingViewHandler)view;
          closingView.onClosingView(new HasClosingViewHandler.ClosingHandler() {
            public void doClose() {
              goToBackDelegate.execute(null);
            }
          });
        } else {
          goToBackDelegate.execute(null);
        }
      }
    };
    AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
      public void execute(String element) {
        wrappedGoToBackDelegate.execute(null);
      }
    });
    view.initHeaderBackButton(SafeHtmlUtils.fromTrustedString("<img src='main/images/home-back.png'/>"), new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        wrappedGoToBackDelegate.execute(null);
      }
    });
  }
  
  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    if (AppClientFactory.IMPL.getPlaceController() instanceof PlaceControllerWithHistory) {
      PlaceControllerWithHistory placeController = (PlaceControllerWithHistory)AppClientFactory.IMPL.getPlaceController();
      placeController.goBack(new MainPlace());
      return;
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace());
  }

  @Override
  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  @Override
  public void goToSettingsView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SETTINGS));
  }

  @Override
  public void goToApplicationListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.APPLICATION_LIST));
  }

  @Override
  public void goToIngredientListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.INGREDIENT_LIST));
  }

  @Override
  public void goToApplicationEditView(Applicazione applicazione) {
    if (applicazione == null) {
      applicazione = new ApplicazioneTx();
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.APPLICATION_EDIT, applicazione));
  }
  public static void setEnableDoneBtnAddon(boolean value) {
    PhgUtils.setLocalStorageItem("thrDoneBtnAddon", ""+value);
    PhgUtils.reloadApp();
  }
  
  public static boolean isEnabledDoneBtnAddon() {
    String value = PhgUtils.getLocalStorageItem("thrDoneBtnAddon");
    if (value != null) {
      return "true".equals(value);
    } else {
      return true;
    }
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
    
    String os = (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other");
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

  public void clearALL() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        
        JsArrayString localStorageKeys = PhgUtils.getLocalStorageKeys();
        for (int it = 0; it < localStorageKeys.length(); it++) {
          String key = localStorageKeys.get(it);
          PhgUtils.removeLocalStorageItem(key);
        }
        
        GwtUtils.deferredExecution(2000, new Delegate<Void>() {
          public void execute(Void element) {
            PhgUtils.setAppLocalLanguageAndReload(PhgUtils.getLocaleLanguageFromLocaleInfo());
          }
        });
      }
    });
  }

  protected String getDevInfoIdFromLocalStorage() {
    return PhgUtils.getLocalStorageItem("devInfoId");
  }

  protected void setDevInfoIdInLocalStorage(String devInfoId) {
    PhgUtils.setLocalStorageItem("devInfoId", devInfoId);
  }

  @Override
  public void setEditingApplicazione(Applicazione applicazione) {
    GwtUtils.setClientAttribute("editingApplicazione", applicazione);
  }

  @Override
  public Applicazione getEditingApplicazione() {
    return (Applicazione)GwtUtils.getClientAttribute("editingApplicazione");
  }

  @Override
  public void saveApplicazione(Applicazione applicazione) {
    setHeaderWaiting(true);
    dao.saveApplicazione(applicazione, new Delegate<Applicazione>() {
      public void execute(Applicazione savedApplicazione) {
        setHeaderWaiting(false);
        PhgDialogUtils.showMessageDialog("Application saved", "Info", PhgDialogUtils.BUTTONS_OK, new Delegate<Integer>() {
          public void execute(Integer btnIndex) {
            goToPrevious();
          }
        });
      }
    });
  }
  
  
  private int testNumber = 0;

  @Override
  public void doTestFile() {
    
    if (FileSystemPlugin.isInstalled()) {
      
      if ((testNumber % 2) == 0) {
        FileSystemPlugin.copyApplicationFileToTmpDir("www/data/test.txt", "protoph/workArea", new Delegate<String>() {
          public void execute(String result) {
            PhgUtils.log("COPIED FILE " + result);
          }
        });
      } else {
        FileSystemPlugin.deleteTmpDir("protoph/workArea", new Delegate<String>() {
          public void execute(String result) {
            PhgUtils.log("DELETED DIR " + result);
          }
        });
      }
      testNumber ++;
      
    } else {
      PhgUtils.log("FileSystem Plugin NOT INSTALLED!");
    }

  }
  
  public void deleteWorkDir(final Delegate<String> delegate) {
    if (FileSystemPlugin.isInstalled()) {
      FileSystemPlugin.deleteTmpDir(workPath, new Delegate<String>() {
        public void execute(String result) {
          PhgUtils.log("DELETED DIR " + result);
          delegate.execute(result);
        }
      });
    } else {
      PhgUtils.log("FileSystem Plugin NOT INSTALLED!");
      delegate.execute(null);
    }
  }
  
  public void copyDataFile(String fileName, final Delegate<String> delegate) {
    if (FileSystemPlugin.isInstalled()) {
      FileSystemPlugin.copyApplicationFileToTmpDir(dataPath + "/" + fileName, workPath, new Delegate<String>() {
        public void execute(String result) {
          PhgUtils.log("COPIED FILE " + result);
          delegate.execute(result);
        }
      });
    } else {
      PhgUtils.log("FileSystem Plugin NOT INSTALLED!");
      delegate.execute(null);
    }
  }

  public void doApply(Applicazione applicazione) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.APPLICATION_APPLY, applicazione));
  }

}
