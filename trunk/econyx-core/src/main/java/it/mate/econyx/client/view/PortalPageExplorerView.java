package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

import java.util.List;

public interface PortalPageExplorerView extends BaseView<PortalPageExplorerView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void edit (PortalPage page);
    
    public void show (PortalPage page);
    
    void retrieveChildreen (PortalPage parent);
    
    void goToPage (PortalPage page);
    
  }
  
  public static class TreeModel {
    public PortalPage parent;
    public List<PortalPage> childreen;
  }
  
  
  public class NotImpl extends UnimplementedView<PortalPageExplorerView.Presenter> implements PortalPageExplorerView {

  }
  
  
}
