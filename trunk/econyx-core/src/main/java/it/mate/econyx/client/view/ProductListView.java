package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

import java.util.ArrayList;

public interface ProductListView extends BaseView<ProductListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void edit (Articolo product);
    
    public void show (Articolo product);
    
    public void goToPage(ProductPage page);
    
  }
  
  
  public class ProductPageList extends ArrayList<ProductPage> {
    
  }
  
}
