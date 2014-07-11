package it.mate.therapyreminder.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.IterationUtil;
import it.mate.phgcommons.client.utils.IterationUtil.FinishDelegate;
import it.mate.phgcommons.client.utils.IterationUtil.ItemDelegate;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.logic.CheckSomministrazioniScaduteTask;
import it.mate.therapyreminder.client.logic.MainController;
import it.mate.therapyreminder.client.logic.MainDao;
import it.mate.therapyreminder.client.logic.SviluppaSomministrazioniTask;
import it.mate.therapyreminder.client.logic.SynchSomministrazioniTask;
import it.mate.therapyreminder.client.places.MainPlace;
import it.mate.therapyreminder.client.view.AboutView;
import it.mate.therapyreminder.client.view.AccountEditView;
import it.mate.therapyreminder.client.view.ContactEditView;
import it.mate.therapyreminder.client.view.ContactListView;
import it.mate.therapyreminder.client.view.ContactMenuView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.ReminderEditView;
import it.mate.therapyreminder.client.view.ReminderListView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TestView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.ContattoTx;
import it.mate.therapyreminder.shared.model.impl.DosaggioEditModel;
import it.mate.therapyreminder.shared.model.impl.UdMTx;

import java.util.Date;
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
  HomeView.Presenter, TherapyEditView.Presenter, TherapyListView.Presenter,
  DosageEditView.Presenter, TestView.Presenter, SettingsView.Presenter,
  ReminderListView.Presenter, ReminderEditView.Presenter,
  AccountEditView.Presenter, AboutView.Presenter,
  ContactMenuView.Presenter, ContactListView.Presenter,
  ContactEditView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getPrescrizioniDao();
  
  private final static String ALL_UDM_KEY = "AllUdM";

  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    //TODO: REVISIONE BACKGROUND TASKS
    if (AppClientFactory.USE_BACKGROUND_TASKS) {
      SviluppaSomministrazioniTask.getInstance();
      CheckSomministrazioniScaduteTask.getInstance();
      SynchSomministrazioniTask.getInstance();
    }
    
    if (place.getToken().equals(MainPlace.HOME)) {
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          PhgUtils.log("devInfoId is " + devInfoId);
        }
      });
      setBackgroundAlertsEnabled(true);
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
//    TestView view = AppClientFactory.IMPL.getGinjector().getTestView();
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
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      setBackgroundAlertsEnabled(false);
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
    if (place.getToken().equals(MainPlace.THERAPY_LIST)) {
      setBackgroundAlertsEnabled(false);
      TherapyListView view = AppClientFactory.IMPL.getGinjector().getTherapyListView();
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
    if (place.getToken().equals(MainPlace.THERAPY_EDIT)) {
      setBackgroundAlertsEnabled(false);
      TherapyEditView view = AppClientFactory.IMPL.getGinjector().getTherapyEditView();
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
    if (place.getToken().equals(MainPlace.DOSAGE_EDIT)) {
      setBackgroundAlertsEnabled(false);
      DosageEditView view = AppClientFactory.IMPL.getGinjector().getDosageEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          DosaggioEditModel dosaggio = (DosaggioEditModel)place.getModel();
          goToTherapyEditView(dosaggio.getPrescrizione());
        }
      });
    }
    if (place.getToken().equals(MainPlace.REMINDER_LIST)) {
      setBackgroundAlertsEnabled(false);
      ReminderListView view = AppClientFactory.IMPL.getGinjector().getReminderListView();
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
    if (place.getToken().equals(MainPlace.REMINDER_EDIT)) {
      setBackgroundAlertsEnabled(false);
      ReminderEditView view = AppClientFactory.IMPL.getGinjector().getReminderEditView();
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
    if (place.getToken().equals(MainPlace.ACCOUNT_EDIT)) {
      setBackgroundAlertsEnabled(false);
      AccountEditView view = AppClientFactory.IMPL.getGinjector().getAccountEditView();
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
    if (place.getToken().equals(MainPlace.ABOUT)) {
      setBackgroundAlertsEnabled(false);
      AboutView view = AppClientFactory.IMPL.getGinjector().getAboutView();
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
    if (place.getToken().equals(MainPlace.CONTACT_MENU)) {
      setBackgroundAlertsEnabled(false);
      ContactMenuView view = AppClientFactory.IMPL.getGinjector().getContactMenuView();
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
    if (place.getToken().equals(MainPlace.CONTACT_DOCTOR_LIST) || place.getToken().equals(MainPlace.CONTACT_TUTOR_LIST)) {
      setBackgroundAlertsEnabled(false);
      ContactListView view = AppClientFactory.IMPL.getGinjector().getContactListView();
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
    if (place.getToken().equals(MainPlace.CONTACT_DOCTOR_EDIT) || place.getToken().equals(MainPlace.CONTACT_TUTOR_EDIT)) {
      setBackgroundAlertsEnabled(false);
      ContactEditView view = AppClientFactory.IMPL.getGinjector().getContactEditView();
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
    if (place.getToken().equals(MainPlace.THERAPY_LIST)) {
      PhgUtils.log("retrieving therapy list from db");
      dao.findAllPrescrizioni(new Delegate<List<Prescrizione>>() {
        public void execute(List<Prescrizione> results) {
          view.setModel(results, TherapyListView.TAG_PRESCRIZIONI);
        }
      });
    }
    if (place.getToken().equals(MainPlace.THERAPY_EDIT)) {
      if (place.getModel() != null) {
        view.setModel(place.getModel(), TherapyEditView.TAG_PRESCRIZIONE);
      } else {
        goToHome();
      }
    }
    if (place.getToken().equals(MainPlace.DOSAGE_EDIT)) {
      if (place.getModel() != null) {
        view.setModel(place.getModel(), DosageEditView.TAG_DOSAGGIO);
      } else {
        goToHome();
      }
    }
    if (place.getToken().equals(MainPlace.REMINDER_LIST)) {
      setHeaderWaiting(true);
      
      MainController.getInstance().findSomministrazioniNonEseguiteInMemoria(new Delegate<List<Somministrazione>>() {
        public void execute(List<Somministrazione> results) {
          setHeaderWaiting(false);
          if (results != null && results.size() > 0) {
            view.setModel(results, ReminderListView.TAG_SOMMINISTRAZIONI);
          }
        }
      });
      
      /*
      dao.findSomministrazioniNonEseguite(new Delegate<List<Somministrazione>>() {
        public void execute(List<Somministrazione> results) {
          setHeaderWaiting(false);
          view.setModel(results, ReminderListView.TAG_SOMMINISTRAZIONI);
        }
      });
      */
    }
    if (place.getToken().equals(MainPlace.REMINDER_EDIT)) {
      if (place.getModel() != null) {
        view.setModel(place.getModel(), ReminderEditView.TAG_SOMMINISTRAZIONE);
      } else {
        goToHome();
      }
    }
    if (place.getToken().equals(MainPlace.ACCOUNT_EDIT)) {
      Account account = null;
      if (place.getModel() != null) {
        account = (Account)place.getModel();
      } else {
        account = new AccountTx();
      }
      view.setModel(account, AccountEditView.TAG_ACCOUNT);
    }
    if (place.getToken().equals(MainPlace.CONTACT_DOCTOR_LIST)) {
      view.setModel(Contatto.TIPO_MEDICO, ContactListView.TAG_TIPO_CONTATTO);
      dao.findContattiByTipo(Contatto.TIPO_MEDICO, new Delegate<List<Contatto>>() {
        public void execute(List<Contatto> results) {
          view.setModel(results, ContactListView.TAG_CONTATTI);
        }
      });
    }
    if (place.getToken().equals(MainPlace.CONTACT_TUTOR_LIST)) {
      view.setModel(Contatto.TIPO_TUTOR, ContactListView.TAG_TIPO_CONTATTO);
      dao.findContattiByTipo(Contatto.TIPO_TUTOR, new Delegate<List<Contatto>>() {
        public void execute(List<Contatto> results) {
          view.setModel(results, ContactListView.TAG_CONTATTI);
        }
      });
    }
    if (place.getToken().equals(MainPlace.CONTACT_DOCTOR_EDIT) || place.getToken().equals(MainPlace.CONTACT_TUTOR_EDIT)) {
      view.setModel(place.getModel(), ContactEditView.TAG_CONTACT);
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
        popupMsg = "Maybe data connection is not active";
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
    PhgDialogUtils.showMessageDialog(popupMsg, popupTitle, PhgDialogUtils.BUTTONS_OK);
  }
  
  private void setHeaderWaiting(boolean flag) {
    if (flag) {
      view.getTitle().addStyleName("ui-HeaderPanel-center-waiting");
    } else {
      view.getTitle().removeStyleName("ui-HeaderPanel-center-waiting");
    }
    setVisibleHomeBtn(!flag);
  }
  
  private void initBaseMgwtView(boolean home) {
    if (OsDetectionUtils.isTablet()) {
      view.setTitleHtml(AppProperties.IMPL.tabletAppName());
    } else {
      view.setTitleHtml(AppProperties.IMPL.phoneAppName());
    }
    view.getTitle().addStyleName("ui-HeaderPanel-center");
    if (home) {
      view.getHeaderPanel().addStyleName("ui-HeaderPanel-home");
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
          PhgUtils.log("optons tapped");
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

  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  public void goToTherapyListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_LIST));
  }

  public void goToSettingsView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SETTINGS));
  }

  public void goToTherapyEditView(Prescrizione prescrizione) {
    if (prescrizione == null) {
      MainController.getInstance().createPrescrizioneWithDefaults(new Delegate<Prescrizione>() {
        public void execute(Prescrizione prescrizione) {
          AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_EDIT, prescrizione));
        }
      });
    } else {
      AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_EDIT, prescrizione));
    }
  }

  public void goToDosageEditView(DosaggioEditModel model) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.DOSAGE_EDIT, model));
  }

  public void goToReminderListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.REMINDER_LIST));
  }

  public void goToReminderEditView(Somministrazione somministrazione) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazione));
  }

  public void goToAccountEditView(Account account) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ACCOUNT_EDIT, account));
  }

  public void goToAbout() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ABOUT));
  }

  public void goToContactMenu() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CONTACT_MENU));
  }

  public void goToContactDoctorListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CONTACT_DOCTOR_LIST));
  }

  public void goToContactTutorListView() {
    
    if (isOnlineMode()) {
      AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CONTACT_TUTOR_LIST));
    } else {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_goToContactTutorListView_msg1(), "Alert", 
          PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer btn) {
          if (btn == 1) {
            goToSettingsView();
          }
        }
      });
    }
    
  }

  @Override
  public void goToContactEditView(Contatto contatto) {
    String token = Contatto.TIPO_MEDICO.equals(contatto.getTipo()) ? MainPlace.CONTACT_DOCTOR_EDIT : MainPlace.CONTACT_TUTOR_EDIT;
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(token, contatto));
  }

  @Override
  public void savePrescrizione(Prescrizione newPrescrizione, final Prescrizione oldPrescrizione, final Delegate<Prescrizione> completionDelegate) {
    setHeaderWaiting(true);
    PhgUtils.log("INIZIO SALVATAGGIO PRESCRIZIONE");
    final Delegate<Prescrizione> innerCompletionDelegate = new Delegate<Prescrizione>() {
      public void execute(Prescrizione prescrizione) {
        setHeaderWaiting(false);
        PhgUtils.log("FINE SALVATAGGIO PRESCRIZIONE");
        if (completionDelegate != null) {
          completionDelegate.execute(prescrizione);
        } else {
          goToTherapyListView();
        }
      }
    };
    dao.savePrescrizione(newPrescrizione, new Delegate<Prescrizione>() {
      public void execute(final Prescrizione newPrescrizioneSalvata) {
        if (oldPrescrizione.hasEqualSomministrazioneOf(newPrescrizioneSalvata)) {
          innerCompletionDelegate.execute(newPrescrizioneSalvata);
        } else {
          final Delegate<Prescrizione> doSviluppoSomministrazioniDelegate = new Delegate<Prescrizione>() {
            public void execute(Prescrizione prescrizione) {
              try {
                MainController.getInstance().sviluppaSomministrazioni(prescrizione, new Delegate<Prescrizione>() {
                  public void execute(Prescrizione prescrizione) {
                    innerCompletionDelegate.execute(prescrizione);
                  }
                });
              } catch (ServiceException ex) {
                processFailure(null, ex);
              }
            }
          };
          if (oldPrescrizione.isPersistent()) {
            MainController.getInstance().cancellaSomministrazioni(oldPrescrizione, new Delegate<Void>() {
              public void execute(Void element) {
                doSviluppoSomministrazioniDelegate.execute(newPrescrizioneSalvata);
              }
            });
          } else {
            doSviluppoSomministrazioniDelegate.execute(newPrescrizioneSalvata);
          }
        }
      }
    });
  }
  
  @Override
  public void deletePrescrizioni(final List<Prescrizione> prescrizioni) {
    new IterationUtil<Prescrizione>(prescrizioni, new ItemDelegate<Prescrizione>() {
      public void handleItem(Prescrizione prescrizione, final IterationUtil<Prescrizione> iteration) {
        MainController.getInstance().cancellaSomministrazioni(prescrizione, new Delegate<Void>() {
          public void execute(Void element) {
            iteration.next();
          }
        });
      }
    }, new FinishDelegate() {
      public void doFinish() {
        dao.deletePrescrizioni(prescrizioni, new Delegate<Void>() {
          public void execute(Void element) {
            PhgUtils.log("going to therapy list view");
            goToTherapyListView();
          }
        });
      }
    });
  }
  
  @Override
  public void updateSomministrazione(Somministrazione somministrazione) {
    MainController.getInstance().updateSomministrazione(somministrazione, new Delegate<Somministrazione>() {
      public void execute(Somministrazione result) {
        goToPrevious();
      }
    }, new Delegate<Somministrazione>() {
      public void execute(Somministrazione result) {
        // farmaco da riordinare
        view.setModel(result, ReminderEditView.TAG_FARMACO_DA_RIORDINARE);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void findAllUdM(final Delegate<List<UdM>> delegate) {
    List<UdM> results = (List<UdM>)GwtUtils.getClientAttribute(ALL_UDM_KEY);
    if (results == null) {
      dao.findAllUdM(new Delegate<List<UdM>>() {
        public void execute(List<UdM> results) {
          GwtUtils.setClientAttribute(ALL_UDM_KEY, results);
          delegate.execute(results);
        }
      });
    } else {
      delegate.execute(results);
    }
  }
  
  private String getLanguageDescription(String multiLanguageText, String currentLocaleName) {
    String result = null;
    String[] languages = multiLanguageText.split(",");
    for (String lang : languages) {
      String[] langTokens = lang.split("=");
      if (langTokens[0].equals(currentLocaleName)) {
        result = langTokens[1];
      }
    }
    return result;
  }
  
  public void getUdmDescription(Double qta, final String udmCode, final Delegate<UdM> delegate) {
//  final String currentLocaleName = LocaleInfo.getCurrentLocale().getLocaleName();
    final String currentLocaleName = PhgUtils.getAppLocalLanguage();
    final boolean singular = qta != null && qta == 1d;
    findAllUdM(new Delegate<List<UdM>>() {
      public void execute(List<UdM> udms) {
        if (udms == null)
          return;
        for (int it = 0; it < udms.size(); it++) {
          UdM udm = udms.get(it);
          if (udmCode == null || udm.getCodice().equals(udmCode)) {
            String udmDescription = getLanguageDescription(udm.getDescrizione(), currentLocaleName);
            if (udmDescription == null)
              udmDescription = getLanguageDescription(udm.getDescrizione(), "default");
            String[] numerTokens = udmDescription.split("/");
            String desc = numerTokens[0];
            if (numerTokens.length == 3) {
              desc += (singular ? numerTokens[1] : numerTokens[2]);
            }
            UdM udmToView = new UdMTx();
            udmToView.setCodice(udm.getCodice());
            udmToView.setDescrizione(desc);
            delegate.execute(udmToView);
          }
        }
      }
    });
  }
  
  public void completeReset() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        
        JsArrayString localStorageKeys = PhgUtils.getLocalStorageKeys();
        for (int it = 0; it < localStorageKeys.length(); it++) {
          String key = localStorageKeys.get(it);
          PhgUtils.log("removing localStorage item " + key);
          PhgUtils.removeLocalStorageItem(key);
        }
        
        GwtUtils.deferredExecution(2000, new Delegate<Void>() {
          public void execute(Void element) {
            PhgUtils.setAppLocalLanguageAndReload(PhgUtils.getCurrentLanguage());
          }
        });
      }
    });
  }
  
  
  public void findAllTutors(final Delegate<List<Contatto>> delegate) {
    dao.findContattiByTipo(Contatto.TIPO_TUTOR, delegate);
  }
  
  //TODO: REVISIONE BACKGROUND TASKS
  private void setBackgroundAlertsEnabled(final boolean enabled) {
    MainController.getInstance().checkConnectionIfOnlineMode(new Delegate<Boolean>() {
      public void execute(Boolean connessioneDatiAttiva) {
        if (AppClientFactory.USE_BACKGROUND_TASKS) {
          SviluppaSomministrazioniTask.getInstance().setEnabled(enabled);
          CheckSomministrazioniScaduteTask.getInstance().setEnabled(enabled);
          SynchSomministrazioniTask.getInstance().setEnabled(enabled);
        } else {
          if (connessioneDatiAttiva) {
            AppClientFactory.IMPL.setBackgroundAlertsEnabled(enabled);
          } else {
            AppClientFactory.IMPL.setBackgroundAlertsEnabled(false);
          }
        }
      }
    });
  }
  
  public void setOnlineMode(boolean onlineMode) {
    MainController.getInstance().setOnlineMode(onlineMode);
  }
  
  public boolean isOnlineMode() {
    return MainController.getInstance().isOnlineMode();
  }
  
  public void setUseCalendar(boolean value) {
    MainController.getInstance().setUseCalendar(value);
  }
  
  public boolean isUseCalendar() {
    return MainController.getInstance().isUseCalendar();
  }
  
  private void ensureDevInfoId() {
    String devInfoId = MainController.getInstance().getDevInfoIdFromLocalStorage();
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
    PhgUtils.log("calling sendDevInfo on remote facade...");
    AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
      new AsyncCallback<String>() {
        public void onFailure(Throwable caught) {
          if (isOnlineMode()) {
            PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_dataConnectionOff_msg());
          }
          GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
        }
        public void onSuccess(String devInfoId) {
          if (devInfoId != null) {
            PhgUtils.log("received devInfoId "+ devInfoId +" from remote facade");
            MainController.getInstance().setDevInfoIdInLocalStorage(devInfoId);
            GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
          }
        }
    });
  }

  /*
  private String getDevInfoIdFromLocalStorage() {
    return MainController.getInstance().getDevInfoIdFromLocalStorage();
  }
  */

  public void getDevInfoId(final Delegate<String> delegate) {
    final ObjectWrapper<Boolean> delegateFired = new ObjectWrapper<Boolean>(false);
    GwtUtils.createTimerDelegate(500, true, new Delegate<Timer>() {
      public void execute(Timer timer) {
        ensureDevInfoId();
        String devInfoId = MainController.getInstance().getDevInfoIdFromLocalStorage();
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
  
  /**
   * chiamato dalla ui per mostrare il remote user
   */
  public void getAccount(final Delegate<Account> delegate) {
    delegate.execute(MainController.getInstance().getAccountFromLocalStorage());
  }
  
  @Override
  public void saveContatto(final Contatto contatto, final Delegate<Contatto> successDelegate) {
    
    getAccount(new Delegate<Account>() {
      public void execute(final Account account) {
        boolean procedi = true;
        if (account != null) {
          if (account.getName().trim().equals(contatto.getNome().trim())) {
            PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveContatto_msg1());
            procedi = false;
          }
        }
        if (procedi) {
          dao.findAllContatti(new Delegate<List<Contatto>>() {
            public void execute(List<Contatto> contatti) {
              boolean procedi = true;
              if (contatti != null) {
                for (Contatto contattoInDb : contatti) {
                  if (contattoInDb.getId().equals(contatto.getId())) {
                    continue;
                  }
                  if (contattoInDb.getNome().trim().equalsIgnoreCase(contatto.getNome().trim())) {
                    PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveContatto_msg2());
                    procedi = false;
                    break;
                  }
                  if (contattoInDb.getEmail().trim().equalsIgnoreCase(contatto.getEmail().trim())) {
                    PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveContatto_msg3());
                    procedi = false;
                    break;
                  }
                  if (contattoInDb.getTelefono() != null && contattoInDb.getTelefono().trim().equalsIgnoreCase(contatto.getTelefono().trim())) {
                    PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveContatto_msg4());
                    procedi = false;
                    break;
                  }
                }
              }
              if (procedi) {
                dao.saveContatto(contatto, new Delegate<Contatto>() {
                  public void execute(Contatto savedContatto) {
                    if (account != null && Contatto.TIPO_TUTOR.equals(savedContatto.getTipo())) {
                      RpcMap contattoMap = ((ContattoTx)savedContatto).toRpcMap();
                      RpcMap accountMap = ((AccountTx)account).toRpcMap();
                      AppClientFactory.IMPL.getRemoteFacade().updateDatiContatto(contattoMap, accountMap, new AsyncCallback<Void>() {
                        public void onSuccess(Void result) {
                          PhgUtils.log("tutor data remotely saved");
                        }
                        public void onFailure(Throwable caught) {
                          processFailure(null, caught);
                        }
                      });
                    }
                    if (successDelegate != null) {
                      successDelegate.execute(savedContatto);
                    } else {
                      goToPrevious();
                    }
                  }
                });
              }
            }
          });
        }
      }
    });
  }

  @Override
  public void saveAccount(final Account account, final Delegate<Account> successDelegate) {
    setHeaderWaiting(true);
    if (account.getId() == null) {
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          account.setDevInfoId(devInfoId);
          PhgUtils.log("creating account " + account);
          AppClientFactory.IMPL.getRemoteFacade().createAccount(((AccountTx)account).toRpcMap(), new AsyncCallback<RpcMap>() {
            public void onFailure(Throwable caught) {
              setHeaderWaiting(false);
              PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_dataConnectionOff_msg());
            }
            public void onSuccess(final RpcMap resultAccountMap) {
              final AccountTx resultAccount = new AccountTx().fromRpcMap(resultAccountMap);
              setHeaderWaiting(false);
              PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveAccount_msg1(), "Info", PhgDialogUtils.BUTTONS_OK, new Delegate<Integer>() {
                public void execute(Integer element) {
                  MainController.getInstance().setAccountInLocalStorage(resultAccount);
                  successDelegate.execute(resultAccount);
                }
              });
            }
          });
        }
      });
    } else {
      PhgUtils.log("updating account " + account);
      AppClientFactory.IMPL.getRemoteFacade().updateAccount(((AccountTx)account).toRpcMap(), new AsyncCallback<RpcMap>() {
        public void onFailure(Throwable caught) {
          setHeaderWaiting(false);
          PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_dataConnectionOff_msg());
        }
        public void onSuccess(RpcMap resultAccount) {
          setHeaderWaiting(false);
          PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_saveAccount_msg2());
          MainController.getInstance().setAccountInLocalStorage(account);
          successDelegate.execute(account);
        }
      });
    }
  }

  @Override
  public void deleteContatto(final Contatto contatto) {
    dao.findPrescrizioniAttiveByContatto(new Date(), contatto, new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioni) {
        if (prescrizioni != null && prescrizioni.size() > 0) {
          PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainActivity_deleteContatto_msg1());
          goToPrevious();
          return;
        } else {
          dao.deleteContatto(contatto, new Delegate<Void>() {
            public void execute(Void element) {
              goToPrevious();
            }
          });
        }
      }
    });
  }
  
}
