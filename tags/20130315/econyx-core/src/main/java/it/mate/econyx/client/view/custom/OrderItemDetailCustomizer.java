package it.mate.econyx.client.view.custom;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;

public interface OrderItemDetailCustomizer {
  
  public void setDetailPanel(Panel detailPanel);
  
  public List<OrderItemDetail> getDetails();
  
  public void setArticolo(Articolo articolo);
  
  public void setOrderItem(OrderItem orderItem);

}
