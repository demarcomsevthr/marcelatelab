package it.mate.econyx.client.factories;

import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.client.view.BlogView;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.client.view.CustomerProfileView;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.client.view.DocumentFolderListView;
import it.mate.econyx.client.view.DocumentFolderView;
import it.mate.econyx.client.view.ImageEditView;
import it.mate.econyx.client.view.ImageListView;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.client.view.OrderItemEditView;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.client.view.OrderView;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.client.view.ProducerEditView;
import it.mate.econyx.client.view.ProducerListView;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.client.view.ProductOrderDetailView;
import it.mate.econyx.client.view.ProductView;
import it.mate.econyx.client.view.TestView;
import it.mate.econyx.client.view.site.ArticleFolderViewImpl;
import it.mate.econyx.client.view.site.ArticleViewImpl;
import it.mate.econyx.client.view.site.BlogViewImpl;
import it.mate.econyx.client.view.site.CustomerEditViewImpl;
import it.mate.econyx.client.view.site.CustomerProfileViewImpl;
import it.mate.econyx.client.view.site.DocumentFolderViewImpl;
import it.mate.econyx.client.view.site.OrderListViewImpl;
import it.mate.econyx.client.view.site.OrderViewImpl;
import it.mate.econyx.client.view.site.PortalPageExplorerViewImpl;
import it.mate.econyx.client.view.site.PortalPageViewImpl;
import it.mate.econyx.client.view.site.PortalUserViewImpl;
import it.mate.econyx.client.view.site.ProductEditViewImpl;
import it.mate.econyx.client.view.site.ProductListViewImpl;
import it.mate.econyx.client.view.site.ProductOrderDetailViewImpl;
import it.mate.econyx.client.view.site.ProductViewImpl;
import it.mate.econyx.client.view.site.TestViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class SiteGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    
    bind(OrderListView.class).to(OrderListViewImpl.class);
    bind(OrderEditView.class).to(OrderEditView.NotImpl.class);
    bind(OrderView.class).to(OrderViewImpl.class);
    bind(OrderItemEditView.class).to(OrderItemEditView.NotImpl.class);
    
    bind(ProductListView.class).to(ProductListViewImpl.class);
    bind(ProductEditView.class).to(ProductEditViewImpl.class);
    bind(ProductView.class).to(ProductViewImpl.class);
    bind(ProductOrderDetailView.class).to(ProductOrderDetailViewImpl.class);
    
    bind(ProducerListView.class).to(ProducerListView.NotImpl.class);
    bind(ProducerEditView.class).to(ProducerEditView.NotImpl.class);
    
    bind(PortalPageEditView.class).to(PortalPageEditView.NotImpl.class);
    bind(PortalPageListView.class).to(PortalPageListView.NotImpl.class);
    bind(PortalPageView.class).to(PortalPageViewImpl.class);
    bind(PortalPageExplorerView.class).to(PortalPageExplorerViewImpl.class);
    
    bind(PortalUserView.class).to(PortalUserViewImpl.class);
    bind(PortalUserListView.class).to(PortalUserListView.NotImpl.class);
    bind(PortalUserEditView.class).to(PortalUserEditView.NotImpl.class);
    
    bind(CustomerEditView.class).to(CustomerEditViewImpl.class);
    bind(CustomerProfileView.class).to(CustomerProfileViewImpl.class);
    
    bind(ImageListView.class).to(ImageListView.NotImpl.class);
    bind(ImageEditView.class).to(ImageEditView.NotImpl.class);
    
    bind(ArticleFolderView.class).to(ArticleFolderViewImpl.class);
    bind(ArticleFolderListView.class).to(ArticleFolderListView.NotImpl.class);
    bind(ArticleFolderEditView.class).to(ArticleFolderEditView.NotImpl.class);
    bind(ArticleView.class).to(ArticleViewImpl.class);
    bind(ArticleEditView.class).to(ArticleEditView.NotImpl.class);
    
    bind(BlogView.class).to(BlogViewImpl.class);
    
    bind(DocumentFolderView.class).to(DocumentFolderViewImpl.class);
    bind(DocumentFolderListView.class).to(DocumentFolderListView.NotImpl.class);
    bind(DocumentFolderEditView.class).to(DocumentFolderEditView.NotImpl.class);
    bind(DocumentEditView.class).to(DocumentEditView.NotImpl.class);
    
    bind(CalEventListView.class).to(CalEventListView.NotImpl.class);
    bind(CalEventEditView.class).to(CalEventEditView.NotImpl.class);
    
    bind(TestView.class).to(TestViewImpl.class);
    
  }

}
