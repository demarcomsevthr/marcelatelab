package it.mate.therapyreminder.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.IterationUtil;
import it.mate.phgcommons.client.utils.IterationUtil.FinishDelegate;
import it.mate.phgcommons.client.utils.IterationUtil.ItemDelegate;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.logic.PrescrizioniCtrl;
import it.mate.therapyreminder.client.logic.PrescrizioniDao;
import it.mate.therapyreminder.client.places.MainPlace;
import it.mate.therapyreminder.client.view.AccountEditView;
import it.mate.therapyreminder.client.view.CalendarEventTestView;
import it.mate.therapyreminder.client.view.ContactEditView;
import it.mate.therapyreminder.client.view.ContactListView;
import it.mate.therapyreminder.client.view.ContactMenuView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.LegalNotesView;
import it.mate.therapyreminder.client.view.ReminderEditView;
import it.mate.therapyreminder.client.view.ReminderListView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.UdMTx;

import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
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
  DosageEditView.Presenter, 
  CalendarEventTestView.Presenter, SettingsView.Presenter,
  ReminderListView.Presenter, ReminderEditView.Presenter,
  AccountEditView.Presenter, LegalNotesView.Presenter,
  ContactMenuView.Presenter, ContactListView.Presenter,
  ContactEditView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  private PrescrizioniDao dao = AppClientFactory.IMPL.getGinjector().getPrescrizioniDao();
  
  private final static String ALL_UDM_KEY = "AllUdM";

  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
      
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          PhonegapUtils.log("received devInfoId in activity is " + devInfoId);
        }
      });
      
      AppClientFactory.IMPL.setDisableAlertSomministrazione(false);
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
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      SettingsView view = AppClientFactory.IMPL.getGinjector().getSettingsView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
//        goToHome();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.THERAPY_LIST)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      TherapyListView view = AppClientFactory.IMPL.getGinjector().getTherapyListView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
//        goToHome();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.THERAPY_EDIT)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      TherapyEditView view = AppClientFactory.IMPL.getGinjector().getTherapyEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
//        goToTherapyListView();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.DOSAGE_EDIT)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      DosageEditView view = AppClientFactory.IMPL.getGinjector().getDosageEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          Dosaggio dosaggio = (Dosaggio)place.getModel();
          goToTherapyEditView(dosaggio.getPrescrizione());
        }
      });
    }
    if (place.getToken().equals(MainPlace.REMINDER_LIST)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      ReminderListView view = AppClientFactory.IMPL.getGinjector().getReminderListView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
//        goToHome();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.REMINDER_EDIT)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      ReminderEditView view = AppClientFactory.IMPL.getGinjector().getReminderEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          //goToHome();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.ACCOUNT_EDIT)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
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
    if (place.getToken().equals(MainPlace.LEGAL_NOTES)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      LegalNotesView view = AppClientFactory.IMPL.getGinjector().getLegalNotesView();
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
    if (place.getToken().equals(MainPlace.TEST)) {
      CalendarEventTestView view = AppClientFactory.IMPL.getGinjector().getCalendarEventTestView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToHome();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CONTACT_MENU)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
      ContactMenuView view = AppClientFactory.IMPL.getGinjector().getContactMenuView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
//        goToHome();
          goToPrevious();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CONTACT_DOCTOR_LIST) || place.getToken().equals(MainPlace.CONTACT_TUTOR_LIST)) {
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
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
      AppClientFactory.IMPL.setDisableAlertSomministrazione(true);
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
      dao.findSomministrazioniNonEseguite(new Delegate<List<Somministrazione>>() {
        public void execute(List<Somministrazione> results) {
          view.setModel(results, ReminderListView.TAG_SOMMINISTRAZIONI);
        }
      });
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
    if (caught != null) {
      PhonegapUtils.log(caught.getClass().getName()+" - "+caught.getMessage());
      if (caught instanceof InvocationException) {
        PhgDialogUtils.showMessageDialog("Maybe data connection is not active", "Error", PhgDialogUtils.BUTTONS_OK);
      } else {
        PhgDialogUtils.showMessageDialog(caught.getMessage(), "Error", PhgDialogUtils.BUTTONS_OK);
      }
    }
    if (message != null) {
      PhgDialogUtils.showMessageDialog(message, "Alert", PhgDialogUtils.BUTTONS_OK);
    }
  }
  
  private void setHeaderWaiting(boolean flag) {
    if (flag) {
      view.getTitle().addStyleName("ui-HeaderPanel-center-waiting");
    } else {
      view.getTitle().removeStyleName("ui-HeaderPanel-center-waiting");
    }
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
      setVisibleOptionsBtn(true);
    }
  }
  
  private void setVisibleOptionsBtn(boolean visible) {
    if (visible) {
      TouchImage optionsBtn = new TouchImage();
      optionsBtn.addStyleName("ui-optionsBtn");
      optionsBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          PhonegapUtils.log("optons tapped");
        }
      });
      view.getHeaderPanel().setRightWidget(optionsBtn);
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
      /*
      Place lastPlace = placeController.getPreviousPlace();
      if (lastPlace != null) {
        placeController.goTo(lastPlace);
        return;
      }
      */
      placeController.goBack();
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
      PrescrizioniCtrl.getInstance().createPrescrizioneWithDefaults(new Delegate<Prescrizione>() {
        public void execute(Prescrizione prescrizione) {
          AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_EDIT, prescrizione));
        }
      });
    } else {
      AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_EDIT, prescrizione));
    }
  }

  public void goToDosageEditView(Dosaggio dosaggio) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.DOSAGE_EDIT, dosaggio));
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

  public void goToLegalNotes() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.LEGAL_NOTES));
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
      String msg = "You can use the tutor feature only in online mode. Do you want to activate it?";
      PhgDialogUtils.showMessageDialog(msg, "Alert", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
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

  //TODO
  @Override
  public void deleteContatto(Contatto contatto) {
    
  }

  @Override
  public void savePrescrizione(Prescrizione newPrescrizione, final Prescrizione oldPrescrizione, Delegate<Prescrizione> endDelegate) {
    final Delegate<Prescrizione> fEndDelegate = endDelegate != null ? endDelegate : new Delegate<Prescrizione>() {
      public void execute(Prescrizione element) {
        goToTherapyListView();
      }
    };
    dao.savePrescrizione(newPrescrizione, new Delegate<Prescrizione>() {
      public void execute(final Prescrizione newPrescrizioneSalvata) {
        if (oldPrescrizione.hasEqualSomministrazioneOf(newPrescrizioneSalvata)) {
          fEndDelegate.execute(newPrescrizioneSalvata);
        } else {
          final Delegate<Prescrizione> sviluppoDelegate = new Delegate<Prescrizione>() {
            public void execute(Prescrizione prescrizione) {
              try {
                PrescrizioniCtrl.getInstance().sviluppaSomministrazioni(prescrizione);
                fEndDelegate.execute(prescrizione);
              } catch (ServiceException ex) {
                processFailure(null, ex);
              }
            }
          };
          if (oldPrescrizione.isPersistent()) {
            PrescrizioniCtrl.getInstance().cancellaSomministrazioni(oldPrescrizione, new Delegate<Void>() {
              public void execute(Void element) {
                sviluppoDelegate.execute(newPrescrizioneSalvata);
              }
            });
          } else {
            sviluppoDelegate.execute(newPrescrizioneSalvata);
          }
        }
      }
    });
  }
  
  @Override
  public void deletePrescrizioni(final List<Prescrizione> prescrizioni) {
    new IterationUtil<Prescrizione>(prescrizioni, new ItemDelegate<Prescrizione>() {
      public void handleItem(Prescrizione prescrizione, final IterationUtil<Prescrizione> iteration) {
        PrescrizioniCtrl.getInstance().cancellaSomministrazioni(prescrizione, new Delegate<Void>() {
          public void execute(Void element) {
            iteration.next();
          }
        });
      }
    }, new FinishDelegate() {
      public void doFinish() {
        dao.deletePrescrizioni(prescrizioni, new Delegate<Void>() {
          public void execute(Void element) {
            goToTherapyListView();
          }
        });
      }
    });
  }
  
  @Override
  public void updateSomministrazione(Somministrazione somministrazione) {
    PrescrizioniCtrl.getInstance().updateSomministrazione(somministrazione, new Delegate<Somministrazione>() {
      public void execute(Somministrazione result) {
        // somministrazione aggiornata
//      goToHome();
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
    final String currentLocaleName = LocaleInfo.getCurrentLocale().getLocaleName();
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
            String desc = numerTokens[0] + (singular ? numerTokens[1] : numerTokens[2]);
            UdM udmToView = new UdMTx();
            udmToView.setCodice(udm.getCodice());
            udmToView.setDescrizione(desc);
            delegate.execute(udmToView);
          }
        }
      }
    });
  }
  
  public void dropDB() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        GwtUtils.deferredExecution(2000, new Delegate<Void>() {
          public void execute(Void element) {
            if (OsDetectionUtils.isAndroid()) {
              AppClientFactory.IMPL.getPhoneGap().exitApp();
            } else {
              goToHome();
            }
          }
        });
      }
    });
  }
  
  
  
  
  
  //TODO: 05/06/2014 - ONLINE MODE
  
  public void setOnlineMode(boolean onlineMode) {
    PhonegapUtils.setLocalStorageProperty("onlineMode", ""+onlineMode);
  }
  
  public boolean isOnlineMode() {
    String value = PhonegapUtils.getLocalStorageProperty("onlineMode");
    return ("true".equalsIgnoreCase(value));
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
    String layout = PhonegapUtils.getLayoutInfo();
    String devName = PhonegapUtils.getDeviceName();
    String phgVersion = PhonegapUtils.getDevicePhonegap();
    String platform = PhonegapUtils.getDevicePlatform();
    String devUuid = PhonegapUtils.getDeviceUuid();
    String devVersion = PhonegapUtils.getDeviceVersion();
    PhonegapUtils.log("calling sendDevInfo on remote facade...");
    AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
      new AsyncCallback<String>() {
        public void onFailure(Throwable caught) {
          if (isOnlineMode()) {
            PhgDialogUtils.showMessageDialog("Maybe data connection is off");
          }
          GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
        }
        public void onSuccess(String devInfoId) {
          if (devInfoId != null) {
            PhonegapUtils.log("received devInfoId "+ devInfoId +" from remote facade");
            setDevInfoIdInLocalStorage(devInfoId);
            GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
          }
        }
    });
  }
  
  private void setDevInfoIdInLocalStorage(String devInfoId) {
    PhonegapUtils.setLocalStorageProperty("devInfoId", devInfoId);
  }

  private String getDevInfoIdFromLocalStorage() {
    return PhonegapUtils.getLocalStorageProperty("devInfoId");
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
  
  /**
   * chiamato dalla ui per mostrare il remote user
   */
  public void getAccount(final Delegate<Account> delegate) {
    String accountJson = getAccountFromLocalStorage();
    if (accountJson != null && accountJson.contains("{")) {
      Account account = AccountTx.fromJS(JSONUtils.parse(accountJson));
      delegate.execute(account);
    } else {
      delegate.execute(null);
    }
  }
  
  @Override
  public void saveContatto(final Contatto contatto, final Delegate<Contatto> successDelegate) {
    dao.saveContatto(contatto, new Delegate<Contatto>() {
      public void execute(Contatto savedModel) {
        if (successDelegate != null) {
          successDelegate.execute(savedModel);
        } else {
          goToPrevious();
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
          PhonegapUtils.log("creating account " + account);
          AppClientFactory.IMPL.getRemoteFacade().createAccount(account, new AsyncCallback<Account>() {
            public void onFailure(Throwable caught) {
              setHeaderWaiting(false);
              PhgDialogUtils.showMessageDialog("Maybe data connection is off");
            }
            public void onSuccess(final Account account) {
              setHeaderWaiting(false);
              PhgDialogUtils.showMessageDialog("Account created", "Info", PhgDialogUtils.BUTTONS_OK, new Delegate<Integer>() {
                public void execute(Integer element) {
                  AccountTx tx = (AccountTx)account;
                  setAccountInLocalStorage(JSONUtils.stringify(tx.asJS()));
                  successDelegate.execute(account);
                }
              });
            }
          });
        }
      });
    } else {
      PhonegapUtils.log("updating account " + account);
      AppClientFactory.IMPL.getRemoteFacade().updateAccount(account, new AsyncCallback<Account>() {
        public void onFailure(Throwable caught) {
          setHeaderWaiting(false);
          PhgDialogUtils.showMessageDialog("Maybe data connection is off");
        }
        public void onSuccess(Account account) {
          setHeaderWaiting(false);
          PhgDialogUtils.showMessageDialog("Account saved");
          AccountTx tx = (AccountTx)account;
          setAccountInLocalStorage(JSONUtils.stringify(tx.asJS()));
          successDelegate.execute(account);
        }
      });
    }
  }
  
  private void setAccountInLocalStorage(String accountJson) {
    PhonegapUtils.setLocalStorageProperty("account", accountJson);
  }
  
  private String getAccountFromLocalStorage() {
    return PhonegapUtils.getLocalStorageProperty("account");
  }
  
  /*
  public void postNewMail(final StickMail stickMail, final Delegate<StickMail> delegate) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade().getServerTime(new AsyncCallback<Date>() {
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
      public void onSuccess(Date serverTime) {
        stickMail.setCreated(stickMail.getCreated());
        stickMail.setScheduled(stickMail.getScheduled());
        AppClientFactory.IMPL.getStickFacade().create(stickMail, new AsyncCallback<StickMail>() {
          public void onSuccess(StickMail result) {
            setHeaderWaiting(false);
            delegate.execute(result);
          }
          public void onFailure(Throwable caught) {
            processFailure(null, caught);
          }
        });
      }
    });
  }
  */
  
}
