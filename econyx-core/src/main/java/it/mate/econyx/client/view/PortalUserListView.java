package it.mate.econyx.client.view;

import it.mate.econyx.client.view.PortalUserListView.Presenter;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;


public interface PortalUserListView extends BaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public void edit (PortalUser user);
    
    public void sendActivationMail(PortalUser user);
    
    public void createCustomer(Customer customer, Delegate<Customer> delegate);
    
  }

  public class NotImpl extends UnimplementedView<PortalUserListView.Presenter> implements PortalUserListView {

  }
  
}
