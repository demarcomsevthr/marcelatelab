package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface OrderEditView extends BaseView<OrderEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento);
    
    public void editItem(OrderItem orderItem);
    
    public void update(Order order);
    
    public void updateOrderItem (OrderItem item);
    
    public void updateImportoTotale(Order order, Double importoTotale);
    
  }
  
  public class NotImpl extends UnimplementedView<OrderEditView.Presenter> implements OrderEditView {

  }
  
}
