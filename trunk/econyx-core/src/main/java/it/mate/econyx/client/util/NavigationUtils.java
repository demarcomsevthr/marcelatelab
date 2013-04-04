package it.mate.econyx.client.util;

import it.mate.econyx.client.events.CalendarDateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ShoppingCartPlace;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NavigationUtils {
  
  public static final String SECURE_ADMIN_PAGE_URL = "secureAdmin.html";
  
  public static final String NOT_SECURE_ADMIN_PAGE_URL = "admin.html";
  
  public static void goToShoppingCartDetailView () {
    TemplatesUtils.changeCurrentTemplate(PropertiesHolder.getString("client.EconyxUtils.shoppingCartTemplateName", "shoppingCartTemplate"), new Delegate<Void>() {
      public void execute(Void element) {
        AppClientFactory.IMPL.getPlaceController().goTo(new ShoppingCartPlace(ShoppingCartPlace.DETAILED_VIEW));
      }
    });
  }
  
  public static void goToPlace(Place place) {
    AppClientFactory.IMPL.getPlaceController().goTo(place);
  }
  
  public static void checkOpenedOrderInSession (OrderServiceAsync orderService, final Delegate<Order> openOrderPresentDelegate,
      final Delegate<Void> openOrderAbsentDelegate) {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    if (portalSessionState != null && portalSessionState.getLoggedUser() != null && portalSessionState.getCustomer() != null) {
      if (portalSessionState.getOpenOrderId() != null) {
        if (portalSessionState.getOpenOrder() != null) {
          openOrderPresentDelegate.execute(portalSessionState.getOpenOrder());
        } else {
          orderService.findById(portalSessionState.getOpenOrderId(), new AsyncCallback<Order>() {
            public void onFailure(Throwable caught) {
              Window.alert(caught.getMessage());
            }
            public void onSuccess(Order order) {
              AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(order);
              openOrderPresentDelegate.execute(order);
            }
          });
        }
      } else {
        if (!portalSessionState.isOpenOrderCheckDone()) {
          portalSessionState.setOpenOrderCheckDone(true);
          orderService.findOpenOrderByCustomer(AppClientFactory.IMPL.getPortalSessionState().getCustomer(), new AsyncCallback<Order>() {
            public void onFailure(Throwable caught) {
              if (openOrderAbsentDelegate != null)
                openOrderAbsentDelegate.execute(null);
              Window.alert(caught.getMessage());
            }
            public void onSuccess(Order order) {
              if (order != null) {
                AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(order);
                openOrderPresentDelegate.execute(order);
              } else {
                if (openOrderAbsentDelegate != null)
                  openOrderAbsentDelegate.execute(null);
              }
            }
          });
        }
      }
    } else {
      if (openOrderAbsentDelegate != null)
        openOrderAbsentDelegate.execute(null);
    }
  }
  
  public static String getCompleteUrl(String pageUrl) {
    String resultUrl = GWT.getHostPageBaseURL() + pageUrl;
    String gwtCodesvr = Window.Location.getParameter("gwt.codesvr");
    if (gwtCodesvr != null) {
      resultUrl += "?gwt.codesvr="+gwtCodesvr;
    }
    return resultUrl;
  }
  
  public static void setSelectedCalendarDate(Date date) {
    AppClientFactory.IMPL.getEventBus().fireEvent(new CalendarDateChangeEvent(date));
  }
  
}
