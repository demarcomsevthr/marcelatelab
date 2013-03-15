package it.mate.econyx.client.view;

import it.mate.econyx.client.view.CustomerProfileView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

import com.google.gwt.user.client.ui.AcceptsOneWidget;


public interface CustomerProfileView extends BaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    void goToUpdateCustomerInformations();
    
    void goToListOrderView();
    
    void goToShoppingCartView();
    
    void createOrderListView(AcceptsOneWidget panel);
    
  }
  
  public class NotImpl extends UnimplementedView<CustomerProfileView.Presenter> implements CustomerProfileView {

  }
  
}
