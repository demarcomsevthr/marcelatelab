package it.mate.econyx.client.activities;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.util.ClientOrderUtils;
import it.mate.econyx.client.util.NavigationUtils;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.client.view.OrderItemEditView;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.client.view.OrderView;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class OrderActivity extends BaseActivity implements 
    OrderListView.Presenter, OrderEditView.Presenter, OrderView.Presenter, 
    OrderItemEditView.Presenter {

  private OrderPlace place;
  
  private OrderServiceAsync orderService = AppClientFactory.IMPL.getGinjector().getOrderService();
  
  private HandlerRegistration portalSessionStateChangeRegistration = null;
  
  public OrderActivity(OrderPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(OrderPlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getOrderEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(OrderPlace.LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getOrderListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(OrderPlace.VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getOrderView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(OrderPlace.EDIT_ITEM)) {
      initView(AppClientFactory.IMPL.getGinjector().getOrderItemEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(OrderPlace.LIST)) {
      if (AppClientFactory.isAdminModule) {

        // 27/11/2012
        OrderListView orderListView = (OrderListView)getView();
        orderListView.setOrderStateFilterChangeDelegate(new Delegate<String>() {
          public void execute(String currentStateCode) {
            GwtUtils.showWait();
            orderService.findOrdersByState(currentStateCode, new AsyncCallback<List<Order>>() {
              public void onFailure(Throwable caught) {
                GwtUtils.hideWait();
                Window.alert(caught.getMessage());
              }
              public void onSuccess(List<Order> orders) {
                GwtUtils.hideWait();
                getView().setModel(orders);
              }
            });
          }
        });

      } else {
        orderService.findOrdersByCustomer(AppClientFactory.IMPL.getPortalSessionState().getCustomer(), new AsyncCallback<List<Order>>() {
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
          public void onSuccess(List<Order> results) {
            if (results == null || results.size() == 0) {
              Window.alert("Non ci sono ordini pregressi");
            } else {
              getView().setModel(results, ORDER_LIST_BY_CUSTOMER);
            }
          }
        });
      }
    } else if (place.getToken().equals(OrderPlace.EDIT)) {
      getView().setModel(place.getModel());
    } else if (place.getToken().equals(OrderPlace.EDIT_ITEM)) {
      getView().setModel(place.getModel());
    } else if (place.getToken().equals(OrderPlace.VIEW)) {
      getView().setModel(place.getModel());
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
          getView().setModel(event.getState(), null);
          checkPortalSessionState();
        }
      });
    }
  }
  
  private void checkPortalSessionState () {
    NavigationUtils.checkOpenedOrderInSession(orderService, new Delegate<Order>() {
      public void execute(Order order) {
        goTo(new OrderPlace(OrderPlace.VIEW, order));
      }
    }, null);
  }
  
  @Override
  public void onDispose() {
    if (portalSessionStateChangeRegistration != null)
      portalSessionStateChangeRegistration.removeHandler();
    super.onDispose();
  }
  
  public void fetchItems(Order order, final Delegate<Order> delegate) {
    ClientOrderUtils.fetchItems(order, delegate);
  }
  
  @Override
  public void edit(Order order) {
    if (order.getItems() == null || order.getItems().size() == 0) {
      fetchItems(order, new Delegate<Order>() {
        public void execute(Order order) {
          goTo(new OrderPlace(OrderPlace.EDIT, order).setHistoryName(order.getCode()).setHistoryAppend());
        }
      });
    } else {
      goTo(new OrderPlace(OrderPlace.EDIT, order).setHistoryName(order.getCode()).setHistoryAppend());
    }
  }
  
  @Override
  public void editItem(OrderItem orderItem) {
    goTo(new OrderPlace(OrderPlace.EDIT_ITEM, orderItem));
  }

  @Override
  public void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento) {
    ClientOrderUtils.closeOrder(id, modalitaSpedizione, modalitaPagamento, new Delegate<Order>() {
      public void execute(Order element) {
        Window.alert("Ordine chiuso");
      }
    });
  }

  @Override
  public void update(Order order) {
    ClientOrderUtils.updateOrder(order, new Delegate<Order>() {
      public void execute(Order order) {
        goTo(new OrderPlace(OrderPlace.EDIT, order));
      }
    });
  }
  
  @Override
  public void findAllOrderStates(Delegate<List<OrderStateConfig>> delegate) {
    ClientOrderUtils.findAllOrderStates(delegate);
  }

  @Override
  public void updateOrderItem(OrderItem item) {
    orderService.updateOrderItem(item, new AsyncCallback<OrderItem>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(OrderItem item) {
        goTo(new OrderPlace(OrderPlace.EDIT, item.getOrder()));
      }
    });
  }
  
  @Override
  public void updateImportoTotale(Order order, Double importoTotale) {
    orderService.updateImportoTotale(order, importoTotale, new AsyncCallback<Order>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Order result) {
        goTo(new OrderPlace(OrderPlace.EDIT, result));
      }
    });
  }
  
}
