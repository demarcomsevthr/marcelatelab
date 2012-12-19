package it.mate.econyx.client.view;

import it.mate.econyx.client.view.OrderListView.Presenter;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.List;

import com.google.gwt.user.client.ui.Button;

public interface OrderListView extends BaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    
    public static final String ORDER_LIST_BY_CUSTOMER = "listOrderByCustomer";
    
    public void fetchItems(Order order, Delegate<Order> delegate);
    
    public void edit (Order order);
    
    public void findAllOrderStates (Delegate<List<OrderStateConfig>> delegate);
    
  }
  
  public void setOrderStateFilterChangeDelegate(Delegate<String> orderStateFilterChangeDelegate);  
  
  public void addButton (Button button);
  
}
