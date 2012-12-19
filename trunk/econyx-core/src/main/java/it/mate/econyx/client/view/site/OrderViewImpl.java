package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.OrderView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class OrderViewImpl extends AbstractBaseView<OrderView.Presenter> implements OrderView {

  public interface ViewUiBinder extends UiBinder<Widget, OrderViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable orderTable;
  
  public OrderViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    
    if (model instanceof Order) {
      Order order = (Order)model;

      if (order.getItems() != null) {
        for (int it = 0; it < order.getItems().size(); it++) {
          OrderItem item = order.getItems().get(it);
          
          orderTable.setText(it, 0, item.getProduct().getName());
          
          orderTable.setText(it, 1, GwtUtils.formatCurrency(item.getQuantity()));
          
        }
      }

    }
    
  }
  
}
