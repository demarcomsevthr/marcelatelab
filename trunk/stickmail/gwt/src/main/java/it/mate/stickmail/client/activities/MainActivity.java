package it.mate.stickmail.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.factories.AppClientFactory;
import it.mate.stickmail.client.places.MainPlace;
import it.mate.stickmail.client.view.HomeView;
import it.mate.stickmail.client.view.MailListView;
import it.mate.stickmail.client.view.NewMailView;
import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, NewMailView.Presenter, MailListView.Presenter {
  
  private MainPlace place;
  
  private BaseMgwtView view;
  
  private RemoteUser remoteUser;
  
  private Delegate<RemoteUser> remoteUserDelegate;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    ensureRemoteUser();
    if (place.getToken().equals(MainPlace.HOME)) {
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      this.view = view;
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
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToHome();
        }
      });
      retrieveModel();
    }
  }
  
  private void ensureRemoteUser() {
    if (remoteUser == null) {
      AppClientFactory.IMPL.initEndpointProxy(null, new Delegate<Boolean>() {
        public void execute(Boolean isSignedIn) {
          if (isSignedIn) {
            AppClientFactory.IMPL.getRemoteUser(new Delegate<RemoteUser>() {
              public void execute(RemoteUser remoteUser) {
                MainActivity.this.remoteUser = remoteUser;
                if (remoteUserDelegate != null) {
                  remoteUserDelegate.execute(remoteUser);
                }
              }
            });
          }
        }
      });
    }
  }
  
  private void waitForRemoteUser(Delegate<RemoteUser> delegate) {
    remoteUserDelegate = delegate;
    if (remoteUser != null) {
      delegate.execute(remoteUser);
    }
  }
  
  private void retrieveModel() {
    if (view instanceof MailListView) {
      waitForRemoteUser(new Delegate<RemoteUser>() {
        public void execute(RemoteUser element) {
          AppClientFactory.IMPL.getStickFacade().findMailsByUser(remoteUser, new AsyncCallback<List<StickMail>>() {
            public void onSuccess(List<StickMail> results) {
              view.setModel(results, MailListView.TAG_MAILS);
            }
            public void onFailure(Throwable caught) {
              processFailure(null, caught);
            }
          });
        }
      });
    }
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
      PhgDialogUtils.showMessageDialog(caught.getMessage(), "Errore", PhgDialogUtils.BUTTONS_OK);
    }
    if (message != null) {
      PhgDialogUtils.showMessageDialog(message, "Attenzione", PhgDialogUtils.BUTTONS_OK);
    }
  }
  
  private void setHeaderWaiting(boolean flag) {
    if (flag) {
      view.getTitle().addStyleName("app-HeaderPanel-center-waiting");
    } else {
      view.getTitle().removeStyleName("app-HeaderPanel-center-waiting");
    }
  }
  
}
