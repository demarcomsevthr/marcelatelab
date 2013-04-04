package it.mate.econyx.client.factories;

import it.mate.econyx.client.activities.mapper.AdminActivityMapper;
import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.client.view.CustomerProfileView;
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
import it.mate.econyx.client.view.admin.GeneralConfigView;
import it.mate.econyx.client.view.site.CalendarDateViewImpl;
import it.mate.econyx.client.view.site.PortalPageMenuViewImpl;
import it.mate.econyx.client.view.site.ShoppingCartDetailedViewImpl;
import it.mate.econyx.client.view.site.ShoppingCartSummaryViewImpl;
import it.mate.econyx.shared.services.ArticleServiceAsync;
import it.mate.econyx.shared.services.CalEventServiceAsync;
import it.mate.econyx.shared.services.CustomerServiceAsync;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.ImageServiceAsync;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.econyx.shared.services.PortalUserServiceAsync;
import it.mate.econyx.shared.services.ProductServiceAsync;
import it.mate.gwtcommons.client.factories.CommonGinjector;

public interface AppGinjector extends CommonGinjector {
  
  public GeneralServiceAsync getGeneralService();
  public PortalPageServiceAsync getPortalPageService();
  public OrderServiceAsync getOrderService();
  public ProductServiceAsync getProductService();
  public PortalUserServiceAsync getPortalUserService();
  public ImageServiceAsync getImageService();
  public CustomerServiceAsync getCustomerService();
  
  public ArticleServiceAsync getArticleService();
  public CalEventServiceAsync getCalEventService();

  public OrderView getOrderView();
  public OrderEditView getOrderEditView();
  public OrderListView getOrderListView();
  public OrderItemEditView getOrderItemEditView();
  
  public ShoppingCartSummaryViewImpl getShoppingCartSummaryView();
  public ShoppingCartDetailedViewImpl getShoppingCartDetailedView();
  
  public ImageListView getImageListView();
  public ImageEditView getImageEditView();
  
  public ProductView getProductView();
  public ProductEditView getProductEditView();
  public ProductListView getProductListView();
  public ProductOrderDetailView getProductOrderDetailView();
  
  public PortalPageMenuViewImpl getPortalPageListMenuView();
  public PortalPageEditView getPortalPageEditView();
  public PortalPageListView getPortalPageListView();
  public PortalPageView getPortalPageView();
  public PortalPageExplorerView getPortalPageExplorerView();
  
  public PortalUserView getPortalUserView();
  public PortalUserListView getPortalUserListView();
  public PortalUserEditView getPortalUserEditView();
  
  public CustomerEditView getCustomerEditView();
  public CustomerProfileView getCustomerProfileView();
  
  public ProducerListView getProducerListView();
  public ProducerEditView getProducerEditView();
  
  public GeneralConfigView getGeneralConfigView();
  
  public AdminActivityMapper getAdminActivityMapper();
  
  public ArticleFolderView getArticleFolderView();
  public ArticleFolderListView getArticleFolderListView();
  public ArticleFolderEditView getArticleFolderEditView();
  public ArticleView getArticleView();
  public ArticleEditView getArticleEditView();

  public CalEventListView getCalEventListView();
  public CalEventEditView getCalEventEditView();
  public CalendarDateViewImpl getCalendarDateView();
  
  public TestView getTestView();
  
}
