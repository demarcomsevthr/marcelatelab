package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface PortalPageListView extends BaseView<PortalPageListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (PortalPage page);
    
    void show (PortalPage page);
    
    void retrieveChildreen (PortalPage parent);
    
    void goToPage (PortalPage page);
    
    void newInstance(String classname, Delegate<PortalPage> delegate);
    
    void delete (PortalPage page);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<PortalPageListView.Presenter> implements PortalPageListView {

  }
  
}
