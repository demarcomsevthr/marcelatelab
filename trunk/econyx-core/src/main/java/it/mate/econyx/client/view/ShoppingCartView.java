package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface ShoppingCartView extends BaseView<ShoppingCartView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public static final String LISTA_MODALITA_SPEDIZIONE = "listaModalitaSpedizione";
    
    public static final String LISTA_MODALITA_PAGAMENTO = "listaModalitaPagamento";
    
    public static final String ORDER_ITEM = "orderItem";

    public void goToDetailedView();
    
    public void updateOrderItem (OrderItem item);
    
    public void deleteOrderItem (Order order, OrderItem itemToDelete);
    
    public void closeOrder(Order order, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento, final Delegate<Void> delegate);
    
    public void goToUpdateCustomerInformations();
    
  }
  
  public class NotImpl extends UnimplementedView<ShoppingCartView.Presenter> implements ShoppingCartView {

  }
  
  
}
