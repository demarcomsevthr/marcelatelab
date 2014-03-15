package it.mate.postscriptum.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.constants.AppProperties;
import it.mate.postscriptum.client.factories.AppClientFactory;
import it.mate.postscriptum.client.places.MainPlace;
import it.mate.postscriptum.client.view.HomeView;
import it.mate.postscriptum.client.view.MailListView;
import it.mate.postscriptum.client.view.NewMailView;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;

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
  HomeView.Presenter, NewMailView.Presenter, MailListView.Presenter {
  
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
  }
  
  private void ensureDevInfoId() {
    String devInfoId = getDevInfoIdFromLocalStorage();
    if (devInfoId != null)
      return;
    String os = (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other");
    String layout = PhonegapUtils.getLayoutInfo();
    String devName = PhonegapUtils.getDeviceName();
    String phgVersion = PhonegapUtils.getDevicePhonegap();
    String platform = PhonegapUtils.getDevicePlatform();
    String devUuid = PhonegapUtils.getDeviceUuid();
    String devVersion = PhonegapUtils.getDeviceVersion();
    AppClientFactory.IMPL.getStickFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
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
    AppClientFactory.IMPL.getStickFacade().findMailsByUser(remoteUser, new AsyncCallback<List<StickMail>>() {
      public void onSuccess(List<StickMail> results) {
        setHeaderWaiting(false);
        view.setModel(results, MailListView.TAG_MAILS);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void findScheduledMailsByUser(RemoteUser remoteUser) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade().findScheduledMailsByUser(remoteUser, new AsyncCallback<List<StickMail>>() {
      public void onSuccess(List<StickMail> results) {
        setHeaderWaiting(false);
        view.setModel(results, MailListView.TAG_MAILS);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void deleteMails(final RemoteUser remoteUser, List<StickMail> mails) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade().delete(mails, new AsyncCallback<Void>() {
      public void onSuccess(Void result) {
        findScheduledMailsByUser(remoteUser);
      }
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
    });
  }
  
  public void postNewMail(final StickMail stickMail, final Delegate<StickMail> delegate) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade().getServerTime(new AsyncCallback<Date>() {
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
      public void onSuccess(Date serverTime) {
        Date clientTime = new Date();
        long deltaTime = 0;
//      long deltaTime = clientTime.getTime() - serverTime.getTime();
        PhonegapUtils.log("serverTime = " + serverTime);
        PhonegapUtils.log("clientTime = " + clientTime);
        PhonegapUtils.log("delta time = " + deltaTime);
        stickMail.setCreated(new Date(stickMail.getCreated().getTime() - deltaTime));
        stickMail.setScheduled(new Date(stickMail.getScheduled().getTime() - deltaTime));
//      AppClientFactory.IMPL.getStickFacade().create(stickMail, new AsyncCallback<StickMail>() {
        AppClientFactory.IMPL.getStickFacade().createV101(stickMail, getDevInfoIdFromLocalStorage(), new AsyncCallback<StickMail>() {
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
  
  public void sendSmsTest(final RemoteUser remoteUser, String to, String msg) {
    setHeaderWaiting(true);
    AppClientFactory.IMPL.getStickFacade().sendSmsTest(to, msg, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        processFailure(null, caught);
      }
      public void onSuccess(Void result) {
        setHeaderWaiting(false);
      }
    });
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
//    setVisibleChangeUserBtn(true);
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
  
  private void setVisibleChangeUserBtn(boolean visible) {
    if (visible) {
      TouchImage optionsBtn = new TouchImage();
      optionsBtn.addStyleName("ui-changeUserBtn");
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

}
