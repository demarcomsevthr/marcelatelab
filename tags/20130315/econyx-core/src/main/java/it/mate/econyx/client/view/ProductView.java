package it.mate.econyx.client.view;

import it.mate.econyx.client.model.ArticoloDaOrdinare;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.List;


public interface ProductView extends BaseView<ProductView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public void fetchHtmls (Articolo product, Delegate<Articolo> delegate);
    
    public void orderProduct(Articolo product, Double quantity, List<OrderItemDetail> details);
    
    public void goToProductOrderDetailView(ArticoloDaOrdinare articolo);
    
  }
  
  public class NotImpl extends UnimplementedView<ProductView.Presenter> implements ProductView {

  }
  
  
}
