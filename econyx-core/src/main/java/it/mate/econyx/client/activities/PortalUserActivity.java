package it.mate.econyx.client.activities;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CustomerPlace;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.econyx.client.util.NavigationUtils;
import it.mate.econyx.client.util.TemplatesUtils;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.services.CustomerServiceAsync;
import it.mate.econyx.shared.services.PortalUserServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PortalUserActivity extends BaseActivity implements PortalUserView.Presenter,
    PortalUserListView.Presenter, PortalUserEditView.Presenter {

  private PortalUserPlace place;
  
  private PortalUserServiceAsync portalUserService = AppClientFactory.IMPL.getGinjector().getPortalUserService();
  
  private CustomerServiceAsync customerService = AppClientFactory.IMPL.getGinjector().getCustomerService();
  
  private HandlerRegistration portalSessionStateChangeRegistration = null;
  
  public PortalUserActivity(PortalUserPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(PortalUserPlace.VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalUserView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalUserPlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalUserEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalUserPlace.LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalUserListView(), panel);
      retrieveModel();
    }
  }
  
  @Override
  public void onDispose() {
    super.onDispose();
  }
  
  private void registerHandlers(EventBus eventBus) {
    if (portalSessionStateChangeRegistration == null) {
      portalSessionStateChangeRegistration = eventBus.addHandler(PortalSessionStateChangeEvent.TYPE, new PortalSessionStateChangeEvent.Handler() {
        public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
          PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
          if (portalSessionState != null && portalSessionState.getLoggedUser() != null) {
            getView().setModel(portalSessionState.getLoggedUser());
          } else {
            getView().setModel(null);
          }
        }
      });
    }
      
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(PortalUserPlace.VIEW)) {
      if (place.getModel() != null) {
        getView().setModel(place.getModel());
      } else {
        getView().setModel(retrieveLoggedUser());
      }
    } else if (place.getToken().equals(PortalUserPlace.EDIT)) {
      getView().setModel(place.getModel());
    } else if (place.getToken().equals(PortalUserPlace.LIST)) {

      portalUserService.findAll(new AsyncCallback<List<PortalUser>>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(List<PortalUser> users) {
          getView().setModel(users);
        }
      });

    } else {
      getView().setModel(null, null);
    }
  }
  
  private PortalUser retrieveLoggedUser() {
    return AppClientFactory.IMPL.getPortalSessionState() != null ? AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() : null;
  }
  
  public void login(PortalUser portalUser, boolean keepConnection) {
    portalUserService.login(portalUser, keepConnection, new AsyncCallback<PortalUser>() {
      public void onSuccess(PortalUser loggedUser) {
        getView().setModel(loggedUser);
        AppClientFactory.IMPL.getPortalSessionState().setLoggedUser(loggedUser);
        customerService.findByPortalUser(loggedUser, new AsyncCallback<Customer>() {
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
          public void onSuccess(Customer cliente) {
            AppClientFactory.IMPL.getPortalSessionState().setCustomer(cliente);
          }
        });
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }
  
  @Override
  public void logout() {
    portalUserService.logout(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser(), new AsyncCallback<Void>() {
      public void onSuccess(Void res) { }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
    AppClientFactory.IMPL.getPortalSessionState().setLoggedUser(null);
    AppClientFactory.IMPL.getPortalSessionState().setCustomer(null);
  }

  @Override
  public void edit(PortalUser user) {
    goTo(new PortalUserPlace(PortalUserPlace.EDIT, user).setHistoryName(user.getScreenName()).setHistoryAppend());
  }

  @Override
  public void update(PortalUser user) {
    portalUserService.update(user, new AsyncCallback<PortalUser>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalUser user) {
//      refresh(user);
        edit(user);
      }
    });
  }
  
  private void refresh(PortalUser user) {
    portalUserService.findById(user.getId(), new AsyncCallback<PortalUser>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalUser user) {
        edit(user);
      }
    });
  }
  
  public void editOrder(Order order) {
    goTo(new OrderPlace(OrderPlace.EDIT, order).setHistoryName(order.getCode()).setHistoryAppend());
  }

  @Override
  public void goToRegistrationView() {
    TemplatesUtils.changeCurrentTemplate(PropertiesHolder.getString("client.PortalUserActivity.registrationTemplateName", "registrationTemplate"), new Delegate<Void>() {
      public void execute(Void element) {
        goTo(new CustomerPlace(CustomerPlace.REGISTRATION));
      }
    });
  }
  
  @Override
  public void goToProfileView() {
    TemplatesUtils.changeCurrentTemplate(PropertiesHolder.getString("client.PortalUserActivity.profileTemplateName", "registrationTemplate"), new Delegate<Void>() {
      public void execute(Void element) {
        goTo(new CustomerPlace(CustomerPlace.PROFILE_VIEW).setHistoryName("Profilo"));
      }
    });
  }
  
  public void sendActivationMail(PortalUser user) {
    portalUserService.updateUserNotActive(user, new AsyncCallback<PortalUser>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(final PortalUser user) {
        portalUserService.sendActivationMail(user, new AsyncCallback<Void>() {
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
          public void onSuccess(Void result) {
            GwtUtils.log(getClass(), "sendRegistrationMail", "inviata mail a " + user);
          }
        });
      }
    });
  }

  @Override
  public void getGoogleLoginURL(final Delegate<String> delegate) {
    portalUserService.getGoogleLoginURL(getRedirectUrl(), new AsyncCallback<String>() {
      public void onSuccess(String result) {
        delegate.execute(result);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }
  
  @Override
  public void getGoogleLogoutURL(final Delegate<String> delegate) {
    portalUserService.getGoogleLogoutURL(getRedirectUrl(), new AsyncCallback<String>() {
      public void onSuccess(String result) {
        delegate.execute(result);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }

  private String getRedirectUrl() {
    return NavigationUtils.getCompleteUrl("site.html");
  }
  
  @Override
  public void updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword) {
    portalUserService.updatePassword(portalUser, passwordAttuale, nuovaPassword, confermaPassword, new AsyncCallback<PortalUser>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalUser result) {
        Window.alert("Password modificata");
        edit(result);
      }
    });
  }

  @Override
  public void createCustomer(Customer customer, final Delegate<Customer> delegate) {
    customerService.create(customer, new AsyncCallback<Customer>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Customer results) {
        delegate.execute(results);
      }
    });
  }

  @Override
  public void activateUser(String id) {
    portalUserService.activateUserById(id, new AsyncCallback<PortalUser>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalUser result) {
        Window.alert("Utente attivato");
      }
    });
  }
  
}
