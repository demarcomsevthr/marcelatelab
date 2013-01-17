package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CustomerPlace;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.util.EconyxUtils;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.client.view.CustomerProfileView;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.services.CustomerServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CustomerActivity extends BaseActivity implements 
      CustomerEditView.Presenter,
      CustomerProfileView.Presenter {

  private CustomerPlace place;
  
  private CustomerServiceAsync customerService = AppClientFactory.IMPL.getGinjector().getCustomerService();
  
  public CustomerActivity(CustomerPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(CustomerPlace.REGISTRATION)) {
      initView(AppClientFactory.IMPL.getGinjector().getCustomerEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CustomerPlace.PROFILE_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getCustomerProfileView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CustomerPlace.UPDATE_CUSTOMER_INFORMATIONS)) {
      initView(AppClientFactory.IMPL.getGinjector().getCustomerEditView(), panel);
      retrieveModel();
    }
  }
  
  @Override
  public void onDispose() {
    super.onDispose();
  }
  
  private void registerHandlers(EventBus eventBus) {

  }
  
  private void retrieveModel() {
    if (place.getToken().equals(CustomerPlace.REGISTRATION)) {
      getView().setModel(place.getModel());
    } else if (place.getToken().equals(CustomerPlace.UPDATE_CUSTOMER_INFORMATIONS)) {
      Customer customer = null;
      if (place.getModel() != null) {
        customer = (Customer)place.getModel();
      } else {
        if (AppClientFactory.IMPL.getPortalSessionState().getCustomer() != null) {
          customer = AppClientFactory.IMPL.getPortalSessionState().getCustomer();
        }
      }
      if (customer != null) {
        if (customer.getPortalUser() == null && AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() != null) {
          customer.setPortalUser(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
        }
        getView().setModel(customer, UPDATE_CUSTOMER_INFORMATIONS);
      }
    } else {
      getView().setModel(null);
    }
  }

  @Override
  public void registerNewCustomer(Customer cliente) {
    customerService.register(cliente, new AsyncCallback<Customer>() {
      public void onSuccess(Customer cliente) {
        getView().setModel(cliente, REGISTRATION_SUCCESS);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }

  @Override
  public void goToUpdateCustomerInformations() {
    goTo(new CustomerPlace(CustomerPlace.UPDATE_CUSTOMER_INFORMATIONS));
  }
  
  @Override
  public void goToListOrderView() {
    goTo(new OrderPlace(OrderPlace.LIST));
  }
  
  public void createOrderListView(AcceptsOneWidget panel) {
    OrderActivity orderActivity = new OrderActivity(new OrderPlace(OrderPlace.LIST), AppClientFactory.IMPL);
    orderActivity.start(panel, AppClientFactory.IMPL.getEventBus());
  }
  
  @Override
  public void updateCustomer(Customer cliente) {
    customerService.update(cliente, new AsyncCallback<Customer>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Customer result) {
        AppClientFactory.IMPL.getPortalSessionState().setCustomer(result);
        Window.alert("Profilo aggiornato");
        goTo(new CustomerPlace(CustomerPlace.PROFILE_VIEW));
      }
    });
  }

  @Override
  public void goToShoppingCartView() {
    EconyxUtils.goToShoppingCartDetailView();
  }
  
}
