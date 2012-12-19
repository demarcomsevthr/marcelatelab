package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface PortalUserView extends BaseView<PortalUserView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void login(PortalUser portalUser, boolean keepConnection);
    
    void logout();
    
    void goToRegistrationView();
    
    void goToProfileView();
    
    void getGoogleLoginURL(Delegate<String> delegate);
    
    void getGoogleLogoutURL(Delegate<String> delegate);
    
  }
  
  
  public class NotImpl extends UnimplementedView<PortalUserView.Presenter> implements PortalUserView {

  }
  
  
}
