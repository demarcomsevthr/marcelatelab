package it.mate.econyx.client.view;

import it.mate.econyx.client.view.CustomerProfileView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface CustomerProfileView extends BaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    void goToUpdateCustomerInformations();
    
    void goToListOrderView();
    
    public void goToShoppingCartView();
    
  }
  
  public class NotImpl extends UnimplementedView<CustomerProfileView.Presenter> implements CustomerProfileView {

  }
  
}
