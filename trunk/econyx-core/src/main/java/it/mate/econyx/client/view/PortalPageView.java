package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.ArticlePage;
import it.mate.econyx.shared.model.DocumentFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface PortalPageView extends BaseView<PortalPageView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void fetchHtmls (WebContentPage page, Delegate<WebContentPage> delegate);
    
    void goToPage (PortalPage page);
    
    public void initProductListView (AcceptsOneWidget panel, ProductFolderPage productFolderPage, boolean useProductPageList, String productListHeader);
    
    public void initProductView (AcceptsOneWidget panel, ProductPage productPage);
    
    public void initChildPortalPageView(AcceptsOneWidget panel, PortalPage childPage, PortalPage parentPage);
    
    public void initArticleFolderPageView (AcceptsOneWidget panel, ArticleFolderPage page);
    
    public void initArticlePageView (AcceptsOneWidget panel, ArticlePage page);
    
    public void initDocumentFolderPageView (AcceptsOneWidget panel, DocumentFolderPage page);
    
  }
  
  
  public class NotImpl extends UnimplementedView<PortalPageView.Presenter> implements PortalPageView {

  }
  
  
}
