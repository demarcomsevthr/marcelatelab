package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.List;

public interface ProducerEditView extends BaseView<ProducerEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Produttore producer);
    
    public void refresh (Produttore producer);
    
    public void findOrdersByProducer(Produttore producer, String currentStateCode, Delegate<List<Order>> delegate);
    
    public void editOrder (Order order);
    
    public void updateOrder (Order order, Delegate<Order> delegate);
    
    public void updateOrders (List<Order> orders, Delegate<List<Order>> delegate);
    
    public void findAllOrderStates (Delegate<List<OrderStateConfig>> delegate);
    
    public void findOrderStateConfig (String code, Delegate<OrderStateConfig> delegate);
    
    public void getSaldoByPortalUserId (String portalUserId, Delegate<Double> delegate);
    
    public void findOrdersByIds(List<String> ids, Delegate<List<Order>> delegate);
    
  }
  
  public class NotImpl extends UnimplementedView<ProducerEditView.Presenter> implements ProducerEditView {

  }
  
}
