package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface PortalPageEditView extends BaseView<PortalPageEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (PortalPage page);
    
    public void refresh (PortalPage page);
    
    public void edit(PortalPage page);
    
    public void fetchHtmls (WebContentPage page, Delegate<WebContentPage> delegate);
    
    public void updateHtmlContent(WebContentPage page, HtmlContent content, boolean isHtmlContentModified, final Delegate<WebContentPage> delegate);    
    
  }
  
  public class NotImpl extends UnimplementedView<PortalPageEditView.Presenter> implements PortalPageEditView {

  }
  
}
