package it.mate.therapyreminder.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.places.MainPlace;
import it.mate.therapyreminder.client.service.SomministrazioniService;
import it.mate.therapyreminder.client.view.CalendarEventTestView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;
import it.mate.therapyreminder.shared.model.impl.UdMTx;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, TherapyEditView.Presenter, TherapyListView.Presenter,
  DosageEditView.Presenter, 
  CalendarEventTestView.Presenter, SettingsView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  private AppSqlDao dao = AppClientFactory.IMPL.getGinjector().getAppSqlDao();
  
  private final static String ALL_UDM_KEY = "AllUdM";

  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
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
      SettingsView view = AppClientFactory.IMPL.getGinjector().getSettingsView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToHome();
        }
      });
    }
    if (place.getToken().equals(MainPlace.THERAPY_LIST)) {
      TherapyListView view = AppClientFactory.IMPL.getGinjector().getTherapyListView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToHome();
        }
      });
    }
    if (place.getToken().equals(MainPlace.THERAPY_EDIT)) {
      TherapyEditView view = AppClientFactory.IMPL.getGinjector().getTherapyEditView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      setBackButtonDelegate(new Delegate<Void>() {
        public void execute(Void element) {
          goToTherapyListView();
        }
      });
    }
    if (place.getToken().equals(MainPlace.DOSAGE_EDIT)) {
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
      }
    }
    if (place.getToken().equals(MainPlace.DOSAGE_EDIT)) {
      if (place.getModel() != null) {
        view.setModel(place.getModel(), DosageEditView.TAG_DOSAGGIO);
      }
    }
  }
  
  @SuppressWarnings("unused")
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
          AppClientFactory.IMPL.authenticate();
        }
      });
      view.getHeaderPanel().setRightWidget(optionsBtn);
    } else {
      view.getHeaderPanel().setRightWidget(new Label());
    }
  }
  
  @SuppressWarnings("unchecked")
  private void setBackButtonDelegate(final Delegate<Void> delegate) {
    AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
      public void execute(String element) {
        delegate.execute(null);
      }
    });
    view.initHeaderBackButton(SafeHtmlUtils.fromTrustedString("<img src='main/images/home-back.png'/>"), new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        delegate.execute(null);
      }
    });
  }
  
  @Override
  public void setRemoteUserDelegate(final Delegate<RemoteUser> delegate) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.setRemoteUserDelegate(new Delegate<RemoteUser>() {
      public void execute(RemoteUser remoteUser) {
        setHeaderWaiting(false);
        delegate.execute(remoteUser);
      }
    });
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
  
  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
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
      prescrizione = new PrescrizioneTx();
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.THERAPY_EDIT, prescrizione));
  }

  public void goToDosageEditView(Dosaggio dosaggio) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.DOSAGE_EDIT, dosaggio));
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
                SomministrazioniService.getInstance().sviluppaSomministrazioni(prescrizione);
                fEndDelegate.execute(prescrizione);
              } catch (ServiceException ex) {
                processFailure(null, ex);
              }
            }
          };
          if (oldPrescrizione.isPersistent()) {
            SomministrazioniService.getInstance().cancellaSomministrazioni(oldPrescrizione, new Delegate<Void>() {
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
  public void deletePrescrizioni(List<Prescrizione> prescrizioni) {
    dao.deletePrescrizioni(prescrizioni, new Delegate<Void>() {
      public void execute(Void element) {
        goToTherapyListView();
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
  
  public void adaptUmDescription(Double qta, final String currentUdmCode, final Delegate<UdM> delegate) {
//  LogUtil.log("adaptUmDescription: qta = "+qta+", currentUdmCode = "+currentUdmCode);
    final boolean singular = qta != null && qta == 1d;
    findAllUdM(new Delegate<List<UdM>>() {
      public void execute(List<UdM> udms) {
        if (udms == null)
          return;
        for (int it = 0; it < udms.size(); it++) {
          UdM udm = udms.get(it);
          if (currentUdmCode == null || udm.getCodice().equals(currentUdmCode)) {
            String[] tokens = udm.getDescrizione().split("/");
            String desc = tokens[0] + (singular ? tokens[1] : tokens[2]);
            UdM udmToView = new UdMTx();
            udmToView.setCodice(udm.getCodice());
            udmToView.setDescrizione(desc);
            delegate.execute(udmToView);
          }
          
//        umCombo.addItem(udm.getCodice(), desc, it == 0 ? true : false);
        }
      }
    });
  }
  
  public void dropDB() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        GwtUtils.deferredExecution(2000, new Delegate<Void>() {
          public void execute(Void element) {
            AppClientFactory.IMPL.getPhoneGap().exitApp();
          }
        });
      }
    });
  }
  
}
