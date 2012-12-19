package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Articolo;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface ProductEditView extends BaseView<ProductEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Articolo product);
    
    public void refresh (Articolo product);
    
    public void fetchHtmls (Articolo product, Delegate<Articolo> delegate);
    
    public void updateHtmlContent(Articolo product, HtmlContent content, boolean isHtmlContentModified, final Delegate<Articolo> delegate);
    
  }
  
}
