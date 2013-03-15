package it.mate.econyx.server.tasks;

import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.util.AdaptersUtil;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class OrderStateChangeDeferredTask implements DeferredTask {

  private String orderId;
  
  private String orderStateCode;
  
  public OrderStateChangeDeferredTask(String orderId, String orderStateCode) {
    super();
    this.orderId = orderId;
    this.orderStateCode = orderStateCode;
  }

  @Override
  public void run() {
    OrderAdapter orderAdapter = AdaptersUtil.getOrderAdapter();
    orderAdapter.onOrderStateChange(orderId, orderStateCode);
  }
  
}
