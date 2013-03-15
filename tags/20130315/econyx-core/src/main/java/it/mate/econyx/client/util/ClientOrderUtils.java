package it.mate.econyx.client.util;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientOrderUtils {
  
  private static OrderServiceAsync orderServiceInstance = null;
  
  private static final String CACHE_ENABLED_KEY = "client.UserOrderClientUtils.cacheEnabled";
  
  public static void fetchItems(Order order, final Delegate<Order> delegate) {
    if (isCacheEnabled() && isUserOrderInCache(order)) {
      delegate.execute(getUserOrderInCache());
    } else {
      getOrderService().fetchItems(order, new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Order order) {
          delegate.execute(order);
        }
      });
    }
  }
  
  public static void closeOrder(String id, final ModalitaSpedizione modalitaSpedizione, final ModalitaPagamento modalitaPagamento, final Delegate<Order> delegate) {
    if (isCacheEnabled() && isUserOrderInCache(id)) {
      getOrderService().update(getUserOrderInCache(), new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(final Order order) {
          getOrderService().closeOrder(order.getId(), modalitaSpedizione, modalitaPagamento, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
              Window.alert(caught.getMessage());
            }
            public void onSuccess(Void result) {
              AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(null);
              delegate.execute(order);
            }
          });
        }
      });
    } else {
      getOrderService().closeOrder(id, modalitaSpedizione, modalitaPagamento, new AsyncCallback<Void>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Void result) {
          AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(null);
          delegate.execute(null);
        }
      });
    }
  }
  
  public static void updateOrder(Order order, final Delegate<Order> delegate) {
    if (isCacheEnabled() && isUserOrderInCache(order)) {
      setUserOrderInCache(order);
      delegate.execute(order);
    } else {
      getOrderService().update(order, new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Order order) {
          delegate.execute(order);
        }
      });
    }
  }
  
  public static void orderProduct(Articolo articolo, Customer customer, Double quantity, List<OrderItemDetail> details, final Delegate<Order> delegate) {
    GwtUtils.showWaitPanel();
    if (isCacheEnabled() && getUserOrderInCache() != null) {
      getOrderService().orderProduct(getUserOrderInCache(), getUserOrderIdInCache(), articolo, customer, quantity, details, new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          GwtUtils.hideWaitPanel(true);
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Order order) {
          GwtUtils.hideWaitPanel(true);
          delegate.execute(order);
        }
      });
    } else {
      getOrderService().orderProduct(null, getUserOrderIdInCache(), articolo, customer, quantity, details, new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          GwtUtils.hideWaitPanel(true);
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Order order) {
          GwtUtils.hideWaitPanel(true);
          delegate.execute(order);
        }
      });
    }
  }
  
  public static void updateOrderItem (OrderItem item, final Delegate<OrderItem> delegate) {
    if (isCacheEnabled() && getUserOrderInCache() != null) {
      List<OrderItem> items = getUserOrderInCache().getItems();
      for (int it = 0; it < items.size(); it++) {
        OrderItem cachedItem = items.get(it);
        if (cachedItem.getProduct().getCodice().equals(item.getProduct().getCodice())) {
          items.set(it, item);
          break;
        }
      }
      delegate.execute(item);
    } else {
      getOrderService().updateOrderItem(item, new AsyncCallback<OrderItem>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(OrderItem item) {
          delegate.execute(item);
        }
      });
    }
  }
  
  
  public static void updateOrderInCache() {
    if (isCacheEnabled() && getUserOrderInCache() != null) {
      getOrderService().update(getUserOrderInCache(), new AsyncCallback<Order>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Order order) {
          GwtUtils.log(ClientOrderUtils.class, "updateOrderInCache", "order updated");
        }
      });
    }
  }
  
  private static boolean isCacheEnabled() {
    return AppClientFactory.isSiteModule && PropertiesHolder.getBoolean(CACHE_ENABLED_KEY, false);
  }
  
  private static OrderServiceAsync getOrderService() {
    if (orderServiceInstance == null) {
      orderServiceInstance = AppClientFactory.IMPL.getGinjector().getOrderService();
    }
    return orderServiceInstance;
  }
  
  private static Order getUserOrderInCache() {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    if (portalSessionState != null) {
      return portalSessionState.getOpenOrder();
    }
    return null;
  }
  
  private static String getUserOrderIdInCache() {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    if (portalSessionState != null) {
      return portalSessionState.getOpenOrderId();
    }
    return null;
  }
  
  private static void setUserOrderInCache(Order order) {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    if (portalSessionState != null) {
      portalSessionState.setOpenOrder(order);
    }
  }
  
  private static boolean isUserOrderInCache(Order order) {
    return isUserOrderInCache(order.getId());
  }
  
  private static boolean isUserOrderInCache(String id) {
    Order cachedOrder = getUserOrderInCache();
    if (cachedOrder != null) {
      if (cachedOrder.getId() != null && cachedOrder.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
  
  public static void findAllOrderStates(final Delegate<List<OrderStateConfig>> delegate) {
    getOrderService().findAllOrderStates(new AsyncCallback<List<OrderStateConfig>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(List<OrderStateConfig> results) {
        delegate.execute(results);
      }
    });
  }

}
