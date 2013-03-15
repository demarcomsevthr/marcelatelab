package it.mate.econyx.client.view.custom;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Panel;

public class OrderItemDetailCustomizerDefaultImpl implements OrderItemDetailCustomizer {

  public void setDetailPanel(Panel detailPanel) {
    
  }
  
  public List<OrderItemDetail> getDetails() {
    List<OrderItemDetail> details = new ArrayList<OrderItemDetail>();
    return details;
  }

  public void setArticolo(Articolo articolo) {
    
  }

  @Override
  public void setOrderItem(OrderItem orderItem) {
    
  }
  

}
