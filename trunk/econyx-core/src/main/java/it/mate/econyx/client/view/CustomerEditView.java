package it.mate.econyx.client.view;

import it.mate.econyx.client.view.CustomerEditView.Presenter;
import it.mate.econyx.shared.model.Customer;
import it.mate.gwtcommons.client.mvp.AbstractVoidPresenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface CustomerEditView extends BaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public static final String REGISTRATION_SUCCESS = "regSuccess";
    
    public static final String REVIEW_CUSTOMER_INFORMATIONS = "reviewCustomerData";
    
    public static final String UPDATE_CUSTOMER_INFORMATIONS = "updateCustomerData";
    
    public void registerNewCustomer(Customer cliente);

    public void updateCustomer(Customer cliente);

  }
  
  public abstract class VoidPresenter extends AbstractVoidPresenter implements Presenter {

  }
  
  public class NotImpl extends UnimplementedView<CustomerEditView.Presenter> implements CustomerEditView {

  }
  

}
