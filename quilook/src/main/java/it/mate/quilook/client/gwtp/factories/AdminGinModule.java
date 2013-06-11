package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.client.view.BlogDiscussionView;
import it.mate.econyx.client.view.BlogView;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.client.view.CustomerProfileView;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.client.view.DocumentFolderListView;
import it.mate.econyx.client.view.DocumentFolderView;
import it.mate.econyx.client.view.DocumentView;
import it.mate.econyx.client.view.ImageEditView;
import it.mate.econyx.client.view.ImageListView;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.client.view.OrderItemEditView;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.client.view.OrderView;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.client.view.PortalPageSummaryView;
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
import it.mate.econyx.client.view.admin.ArticleEditViewImpl;
import it.mate.econyx.client.view.admin.ArticleFolderEditViewImpl;
import it.mate.econyx.client.view.admin.ArticleFolderListViewImpl;
import it.mate.econyx.client.view.admin.CalEventEditViewImpl;
import it.mate.econyx.client.view.admin.CalEventListViewImpl;
import it.mate.econyx.client.view.admin.DocumentEditViewImpl;
import it.mate.econyx.client.view.admin.DocumentFolderEditViewImpl;
import it.mate.econyx.client.view.admin.DocumentFolderListViewImpl;
import it.mate.econyx.client.view.admin.ImageEditViewImpl;
import it.mate.econyx.client.view.admin.ImageListViewImpl;
import it.mate.econyx.client.view.admin.OrderEditViewImpl;
import it.mate.econyx.client.view.admin.OrderItemEditViewImpl;
import it.mate.econyx.client.view.admin.OrderListViewImpl;
import it.mate.econyx.client.view.admin.PortalPageEditViewImpl;
import it.mate.econyx.client.view.admin.PortalPageListViewImpl;
import it.mate.econyx.client.view.admin.PortalUserEditViewImpl;
import it.mate.econyx.client.view.admin.PortalUserListViewImpl;
import it.mate.econyx.client.view.admin.ProducerEditViewImpl;
import it.mate.econyx.client.view.admin.ProducerListViewImpl;
import it.mate.econyx.client.view.admin.ProductEditViewImpl;
import it.mate.econyx.client.view.admin.ProductListViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class AdminGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    
    bind(OrderListView.class).to(OrderListViewImpl.class);
    bind(OrderEditView.class).to(OrderEditViewImpl.class);
    bind(OrderView.class).to(OrderView.NotImpl.class);
    bind(OrderItemEditView.class).to(OrderItemEditViewImpl.class);
    
    bind(ProductListView.class).to(ProductListViewImpl.class);
    bind(ProductEditView.class).to(ProductEditViewImpl.class);
    bind(ProductView.class).to(ProductView.NotImpl.class);
    bind(ProductOrderDetailView.class).to(ProductOrderDetailView.NotImpl.class);
    
    bind(ProducerListView.class).to(ProducerListViewImpl.class);
    bind(ProducerEditView.class).to(ProducerEditViewImpl.class);
    
    bind(PortalPageEditView.class).to(PortalPageEditViewImpl.class);
    bind(PortalPageListView.class).to(PortalPageListViewImpl.class);
    bind(PortalPageView.class).to(PortalPageView.NotImpl.class);
    bind(PortalPageExplorerView.class).to(PortalPageExplorerView.NotImpl.class);
    bind(PortalPageSummaryView.class).to(PortalPageSummaryView.NotImpl.class);
    
    bind(PortalUserView.class).to(PortalUserView.NotImpl.class);
    bind(PortalUserListView.class).to(PortalUserListViewImpl.class);
    bind(PortalUserEditView.class).to(PortalUserEditViewImpl.class);
    
    bind(CustomerEditView.class).to(CustomerEditView.NotImpl.class);
    bind(CustomerProfileView.class).to(CustomerProfileView.NotImpl.class);
    
    bind(ImageListView.class).to(ImageListViewImpl.class);
    bind(ImageEditView.class).to(ImageEditViewImpl.class);
    
    bind(ArticleFolderView.class).to(ArticleFolderView.NotImpl.class);
    bind(ArticleFolderListView.class).to(ArticleFolderListViewImpl.class);
    bind(ArticleFolderEditView.class).to(ArticleFolderEditViewImpl.class);
    bind(ArticleView.class).to(ArticleView.NotImpl.class);
    bind(ArticleEditView.class).to(ArticleEditViewImpl.class);
    
    bind(BlogView.class).to(BlogView.NotImpl.class);
    bind(BlogDiscussionView.class).to(BlogDiscussionView.NotImpl.class);

    bind(DocumentFolderView.class).to(DocumentFolderView.NotImpl.class);
    bind(DocumentFolderListView.class).to(DocumentFolderListViewImpl.class);
    bind(DocumentFolderEditView.class).to(DocumentFolderEditViewImpl.class);
    bind(DocumentView.class).to(DocumentView.NotImpl.class);
    bind(DocumentEditView.class).to(DocumentEditViewImpl.class);

    bind(CalEventListView.class).to(CalEventListViewImpl.class);
    bind(CalEventEditView.class).to(CalEventEditViewImpl.class);
    
    bind(TestView.class).to(TestView.NotImpl.class);
    
  }
  
}
