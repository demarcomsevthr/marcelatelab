package it.mate.econyx.client.view;

import it.mate.econyx.client.view.PortalUserEditView.Presenter;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface PortalUserEditView extends BaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {

    public void update (PortalUser user);
    
    public void sendActivationMail(PortalUser user);
    
    public void editOrder(Order order);
    
    public void updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword);
    
  }

  public class NotImpl extends UnimplementedView<PortalUserEditView.Presenter> implements PortalUserEditView {

  }
  
}
