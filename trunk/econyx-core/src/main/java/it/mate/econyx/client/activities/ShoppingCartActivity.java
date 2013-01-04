package it.mate.econyx.client.activities;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.events.UserOrderChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CustomerPlace;
import it.mate.econyx.client.places.ShoppingCartPlace;
import it.mate.econyx.client.util.EconyxUtils;
import it.mate.econyx.client.util.ClientOrderUtils;
import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.client.view.ShoppingCartView;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShoppingCartActivity extends BaseActivity implements 
    ShoppingCartView.Presenter {

  private ShoppingCartPlace place;
  
  private OrderServiceAsync orderService = AppClientFactory.IMPL.getGinjector().getOrderService();
  
  private HandlerRegistration portalSessionStateChangeRegistration = null;
  
  private HandlerRegistration userOrderChangeRegistration = null;
  
  public ShoppingCartActivity(ShoppingCartPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(ShoppingCartPlace.SUMMARY)) {
      initView(AppClientFactory.IMPL.getGinjector().getShoppingCartSummaryView(), panel);
    }
    if (place.getToken().equals(ShoppingCartPlace.DETAILED_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getShoppingCartDetailedView(), panel);
    }
    registerHandlers(eventBus);
    retrieveModel();
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(ShoppingCartPlace.SUMMARY)) {
      if (place.getModel() == null) {
        checkPortalSessionState(place);
      } else {
        getView().setModel(place.getModel());
      }
    } else if (place.getToken().equals(ShoppingCartPlace.DETAILED_VIEW)) {
      if (place.getModel() == null) {
        checkPortalSessionState(place);
      } else {
        getView().setModel(place.getModel());
      }
      findAllModalitaSpedizione();
    } else {
      getView().setModel(null);
    }
  }
  
  private void registerHandlers(EventBus eventBus) {
    if (portalSessionStateChangeRegistration == null) {
      portalSessionStateChangeRegistration = eventBus.addHandler(PortalSessionStateChangeEvent.TYPE, new PortalSessionStateChangeEvent.Handler() {
        public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "############################");
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "changed portal session state");
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "event = " + event);
          getView().setModel(event.getState());
          checkPortalSessionState(null);
        }
      });
    }
    if (userOrderChangeRegistration == null) {
      userOrderChangeRegistration = eventBus.addHandler(UserOrderChangeEvent.TYPE, new UserOrderChangeEvent.Handler() {
        public void onUserOrderStateChange(UserOrderChangeEvent event) {
          if (place == null) {
            goTo(new ShoppingCartPlace(ShoppingCartPlace.SUMMARY, event.getOrder()));
          } else {
            getView().setModel(event.getOrder());
          }
        }
      });
    }
  }
  
  private void checkPortalSessionState (final ShoppingCartPlace newPlace) {
    EconyxUtils.checkOpenedOrderInSession(orderService, new Delegate<Order>() {
      public void execute(Order order) {
        ShoppingCartPlace actualPlace = newPlace;
        if (actualPlace == null)
          actualPlace = place;
        if (actualPlace == null) {
          actualPlace = new ShoppingCartPlace(ShoppingCartPlace.SUMMARY, order);
          goTo(actualPlace);
        } else {
          getView().setModel(order);
        }
      }
    }, new Delegate<Void>() {
      public void execute(Void v) {
        getView().setModel(null);
      }
    });
  }
  
  @Override
  public void onDispose() {
    if (portalSessionStateChangeRegistration != null)
      portalSessionStateChangeRegistration.removeHandler();
    super.onDispose();
  }
  
  public void fetchItems(Order order, final Delegate<Order> delegate) {
    ClientOrderUtils.fetchItems(order, delegate);
    /*
    orderService.fetchItems(order, new AsyncCallback<Order>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Order order) {
        delegate.execute(order);
      }
    });
    */
  }
  
  public void goToDetailedView() {
    EconyxUtils.goToShoppingCartDetailView();
  }
  
  private void findAllModalitaSpedizione() {
    orderService.findAllModalitaSpedizione(new AsyncCallback<List<ModalitaSpedizione>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(List<ModalitaSpedizione> results) {
        getView().setModel(results, LISTA_MODALITA_SPEDIZIONE);
      }
    });
  }
  
  public void updateOrderItem (OrderItem item) {
    ClientOrderUtils.updateOrderItem(item, new Delegate<OrderItem>() {
      public void execute(OrderItem item) {
        getView().setModel(item, ORDER_ITEM);
      }
    });
    /*
    orderService.updateOrderItem(item, new AsyncCallback<OrderItem>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(OrderItem item) {
        getView().setModel(item, ORDER_ITEM);
      }
    });
    */
    
  }
  
  public void goToUpdateCustomerInformations() {
    goTo(new CustomerPlace(CustomerPlace.UPDATE_CUSTOMER_INFORMATIONS));
  }
  
  public void deleteOrderItem (Order order, OrderItem itemToDelete) {
    for (Iterator<OrderItem> it = order.getItems().iterator(); it.hasNext();) {
      OrderItem item = it.next();
      if (item.getProduct().getCodice().equals(itemToDelete.getProduct().getCodice())) {
        it.remove();

        ClientOrderUtils.updateOrder(order, new Delegate<Order>() {
          public void execute(Order updatedOrder) {
            AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(updatedOrder);
            if (updatedOrder.getItems() == null || updatedOrder.getItems().size() == 0) {
              GwtUtils.messageBox("L'ordine &egrave; stato annullato");
              PortalPageClientUtil.reloadCurrentPage();
            }
            getView().setModel(updatedOrder);
          }
        });
        /*
        orderService.update(order, new AsyncCallback<Order>() {
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
          public void onSuccess(Order updatedOrder) {
            AppClientFactory.IMPL.getPortalSessionState().setUserOrder(updatedOrder);
            if (updatedOrder.getItems() == null || updatedOrder.getItems().size() == 0) {
              GwtUtils.messageBox("L'ordine &egrave; stato annullato");
              PortalPageCacheUtil.reloadCurrentPage();
            }
            getView().setModel(updatedOrder);
          }
        });
        */
        
      }
    }
  }
  
  public void closeOrder(Order order, final Delegate<Void> delegate) {
    ClientOrderUtils.closeOrder(order.getId(), new Delegate<Order>() {
      public void execute(Order result) {
        AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(null);
        delegate.execute(null);
      }
    });
    /*
    orderService.closeOrder(order.getId(), new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        AppClientFactory.IMPL.getPortalSessionState().setUserOrder(null);
        delegate.execute(null);
      }
    });
    */
  }
  
}
