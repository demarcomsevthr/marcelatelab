package it.mate.econyx.client.events;

import it.mate.econyx.shared.model.Order;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserOrderChangeEvent extends GwtEvent<UserOrderChangeEvent.Handler> {

  public interface Handler extends EventHandler {
    void onUserOrderStateChange(UserOrderChangeEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final Order order;

  public UserOrderChangeEvent(Order order) {
    this.order = order;
  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  public Order getOrder() {
    return order;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onUserOrderStateChange(this);
  }

  @Override
  public String toString() {
    return "UserOrderChangeEvent [order=" + order + "]";
  }
  
}
