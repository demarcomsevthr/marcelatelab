package it.mate.postscriptum.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.constants.AppProperties;
import it.mate.postscriptum.client.factories.AppClientFactory;
import it.mate.postscriptum.client.places.MainPlace;
import it.mate.postscriptum.client.view.HomeView;
import it.mate.postscriptum.client.view.MailListView;
import it.mate.postscriptum.client.view.NewMailView;
import it.mate.postscriptum.client.view.NewSmsView;
import it.mate.postscriptum.client.view.SMSListView;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.model.impl.StickMailTx2;
import it.mate.postscriptum.shared.model.impl.StickSmsTx2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, NewMailView.Presenter, MailListView.Presenter, NewSmsView.Presenter, SMSListView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    ensureDevInfoId();
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
    if (place.getToken().equals(MainPlace.NEW_MAIL)) {
      NewMailView view = AppClientFactory.IMPL.getGinjector().getNewMailView();
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
    if (place.getToken().equals(MainPlace.MAIL_LIST)) {
      MailListView view = AppClientFactory.IMPL.getGinjector().getMailListView();
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
    if (place.getToken().equals(MainPlace.NEW_SMS)) {
      NewSmsView view = AppClientFactory.IMPL.getGinjector().getNewSmsView();
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
    if (place.getToken().equals(MainPlace.SMS_LIST)) {
      SMSListView view = AppClientFactory.IMPL.getGinjector().getSMSListView();
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
    if (place.getToken().equals(MainPlace.EDIT_SMS)) {
      NewSmsView view = AppClientFactory.IMPL.getGinjector().getNewSmsView();
      this.view = view;
      initBaseMgwtView(false);
      view.setPresenter(this);
      view.setModel(place.getModel(), "sms");
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToHome();
        }
      });
    }
  }
  
  private void ensureDevInfoId() {
    String devInfoId = getDevInfoIdFromLocalStorage();
    if (devInfoId != null)
      return;
    String os = (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other");
    String layout = PhgUtils.getLayoutInfo();
    String devName = PhgUtils.getDeviceName();
    String phgVersion = PhgUtils.getDevicePhonegap();
    String platform = PhgUtils.getDevicePlatform();
    String devUuid = PhgUtils.getDeviceUuid();
    String devVersion = PhgUtils.getDeviceVersion();
    
    AppClientFactory.IMPL.getStickFacade2().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
        new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            //nothing
          }
          public void onSuccess(String devInfoId) {
            if (devInfoId != null) {
              setDevInfoIdInLocalStorage(devInfoId);
            }
          }
      });
    
  }
  
  public void findMailsByUser(RemoteUser remoteUser) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade2().findMailsByUser(remoteUser, new AsyncCallback<List<RpcMap>>() {
      public void onSuccess(List<RpcMap> results) {
        setHeaderWaiting(false);
        List<StickMail> models = new ArrayList<StickMail>();
        if (results != null) {
          for (RpcMap res : results) {
            models.add(StickMailTx2.fromRpcMap2(res));
          }
        }
        view.setModel(models, MailListView.TAG_MAILS);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void findScheduledMailsByUser(RemoteUser remoteUser) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade2().findScheduledMailsByUser(remoteUser, new AsyncCallback<List<RpcMap>>() {
      public void onSuccess(List<RpcMap> results) {
        setHeaderWaiting(false);
        List<StickMail> models = new ArrayList<StickMail>();
        if (results != null) {
          for (RpcMap res : results) {
            models.add(StickMailTx2.fromRpcMap2(res));
          }
        }
        view.setModel(models, MailListView.TAG_MAILS);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void deleteMails(final RemoteUser remoteUser, List<StickMail> mails) {
    setHeaderWaiting(true);
    List<RpcMap> rpcs = new ArrayList<RpcMap>();
    if (mails != null) {
      for (StickMail mail : mails) {
        rpcs.add(StickMailTx2.toRpcMap(mail));
      }
    }
    AppClientFactory.IMPL.getStickFacade2().delete(rpcs, new AsyncCallback<Void>() {
      public void onSuccess(Void result) {
        findScheduledMailsByUser(remoteUser);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void findScheduledSMSsByUser(RemoteUser remoteUser) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade2().findScheduledSMSsByUser(remoteUser, new AsyncCallback<List<RpcMap>>() {
      public void onSuccess(List<RpcMap> results) {
        setHeaderWaiting(false);
        List<StickSms> models = new ArrayList<StickSms>();
        if (results != null) {
          for (RpcMap rpc : results) {
            models.add(StickSmsTx2.fromRpcMap2(rpc));
          }
          view.setModel(models, SMSListView.TAG_SMSS);
        }
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void deleteSMSs(final RemoteUser remoteUser, List<StickSms> smss) {
    setHeaderWaiting(true);
    List<RpcMap> rpcs = new ArrayList<RpcMap>();
    for (StickSms sms : smss) {
      rpcs.add(StickSmsTx2.toRpcMap(sms));
    }
    AppClientFactory.IMPL.getStickFacade2().deleteSMS(rpcs, new AsyncCallback<Void>() {
      public void onSuccess(Void result) {
        findScheduledSMSsByUser(remoteUser);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void postNewMail(final StickMail stickMail, final Delegate<StickMail> delegate) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade2().getServerTime(new AsyncCallback<Date>() {
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
      public void onSuccess(Date serverTime) {
        Date clientTime = new Date();
        long deltaTime = 0;
        PhgUtils.log("serverTime = " + serverTime);
        PhgUtils.log("clientTime = " + clientTime);
        PhgUtils.log("delta time = " + deltaTime);
        stickMail.setCreated(new Date(stickMail.getCreated().getTime() - deltaTime));
        stickMail.setScheduled(new Date(stickMail.getScheduled().getTime() - deltaTime));
        if (stickMail instanceof StickMailTx2) {
          ((StickMailTx2)stickMail).setDevInfoId(getDevInfoIdFromLocalStorage());
        }
        RpcMap rpc = StickMailTx2.toRpcMap(stickMail);
        AppClientFactory.IMPL.getStickFacade2().create(rpc, new AsyncCallback<RpcMap>() {
          public void onSuccess(RpcMap result) {
            setHeaderWaiting(false);
            delegate.execute(StickMailTx2.fromRpcMap2(result));
          }
          public void onFailure(Throwable caught) {
            processFailure(null, caught);
          }
        });
      }
    });
  }
  
  public void postNewSMS(final StickSms2 stickSMS, final Delegate<StickSms> delegate) {
    setHeaderWaiting(true);
    stickSMS.setDevInfoId(getDevInfoIdFromLocalStorage());

    PhgUtils.getLocaleLanguageFromDevice(new Delegate<String>() {
      public void execute(String language) {
        
        stickSMS.setLanguage(language);
        
        RpcMap rpc = StickSmsTx2.toRpcMap(stickSMS);
        AppClientFactory.IMPL.getStickFacade2().createSMS(rpc, new AsyncCallback<RpcMap>() {
          public void onSuccess(RpcMap result) {
            setHeaderWaiting(false);
            delegate.execute(StickSmsTx2.fromRpcMap2(result));
          }
          public void onFailure(Throwable caught) {
            processFailure(null, caught);
          }
        });
        
      }
    });
    
  }
  
  public void sendSmsTest(final RemoteUser remoteUser, String to, String msg) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade2().sendSmsTest(to, msg, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
      public void onSuccess(Void result) {
        setHeaderWaiting(false);
      }
    });
  }
  
  public void editSms(StickSms2 sms) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.EDIT_SMS, sms));
  }
  
  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  public void goToNewMail() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.NEW_MAIL));
  }

  public void goToMailList() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.MAIL_LIST));
  }

  public void goToNewSms() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.NEW_SMS));
  }

  public void goToSMSList() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SMS_LIST));
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }
  
  private void processFailure(String message, Throwable caught) {
    setHeaderWaiting(false);
    if (caught != null) {
      PhgUtils.log(caught.getClass().getName()+" - "+caught.getMessage());
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
          PhgUtils.log("optons tapped");
          AppClientFactory.IMPL.authenticate();
        }
      });
      view.getHeaderPanel().setRightWidget(optionsBtn);
    } else {
      view.getHeaderPanel().setRightWidget(new Label());
    }
  }
  
  private void setVisibleChangeUserBtn(boolean visible) {
    if (visible) {
      TouchImage optionsBtn = new TouchImage();
      optionsBtn.addStyleName("ui-changeUserBtn");
      optionsBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          PhgUtils.log("optons tapped");
          AppClientFactory.IMPL.authenticate();
        }
      });
      view.getHeaderPanel().setRightWidget(optionsBtn);
    } else {
      view.getHeaderPanel().setRightWidget(new Label());
    }
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
  
  private native void setDevInfoIdInLocalStorage(String devInfoId) /*-{
    localStorage.devInfoId = devInfoId;
  }-*/;
  
  private native String getDevInfoIdFromLocalStorage() /*-{
    return localStorage.devInfoId;
  }-*/;

  public native final String getLastReceiverNumber() /*-{
    return localStorage.lastReceiverNumber;
  }-*/;

  public native final void setLastReceiverNumber(String receiverNumber) /*-{
    localStorage.lastReceiverNumber = receiverNumber;
  }-*/;
  
  public native final String getLastReceiverName() /*-{
    return localStorage.lastReceiverName;
  }-*/;

  public native final void setLastReceiverName(String receiverName) /*-{
    localStorage.lastReceiverName = receiverName;
  }-*/;

}
