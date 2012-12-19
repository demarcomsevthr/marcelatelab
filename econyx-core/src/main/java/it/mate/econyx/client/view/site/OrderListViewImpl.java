package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.shared.model.Order;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class OrderListViewImpl extends AbstractBaseView<OrderListView.Presenter> implements OrderListView {

  public interface ViewUiBinder extends UiBinder<Widget, OrderListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable orderTable;
  
  public OrderListViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    
    if (Presenter.ORDER_LIST_BY_CUSTOMER.equals(tag) && model != null) {
      
      List<Order> orders = (List<Order>)model;

      int row = 0;
      
      Collections.sort(orders, new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
          return o2.getCreated().compareTo(o1.getCreated());
        }
      });
      
      for (Order order : orders) {
        orderTable.setText(row, 0, order.getCode());
        orderTable.setText(row, 1, GwtUtils.dateToString(order.getCreated()));
        orderTable.setText(row, 2, order.getProducer().getNome());
        orderTable.setText(row, 3, order.getCurrentState().getConfig().getDescription());
        orderTable.setWidget(row, 4, new Anchor("stampa", "/re/pdf/order/" + order.getId()));
        row++;
      }
      
      
    }

  }
  
  @Override
  public void setOrderStateFilterChangeDelegate(Delegate<String> orderStateFilterChangeDelegate) {
    
  }
  
  @Override
  public void addButton(Button button) {
    
  }
  
}
