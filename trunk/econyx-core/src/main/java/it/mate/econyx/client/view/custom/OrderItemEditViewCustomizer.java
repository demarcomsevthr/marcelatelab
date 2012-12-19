package it.mate.econyx.client.view.custom;

import it.mate.econyx.shared.model.OrderItem;

import com.google.gwt.user.client.ui.Panel;

public interface OrderItemEditViewCustomizer {
  
  public void initDetailPanel(Panel detailPanel, OrderItem orderItem);
  
}
