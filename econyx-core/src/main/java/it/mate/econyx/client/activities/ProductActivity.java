package it.mate.econyx.client.activities;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.events.UserOrderChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.model.ArticoloDaOrdinare;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.places.ProductPlace;
import it.mate.econyx.client.ui.PageBreadcrumb;
import it.mate.econyx.client.util.ClientOrderUtils;
import it.mate.econyx.client.util.EconyxUtils;
import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.client.util.ProductCacheUtil;
import it.mate.econyx.client.view.ProducerEditView;
import it.mate.econyx.client.view.ProducerListView;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.client.view.ProductView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.econyx.shared.services.ProductServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProductActivity extends BaseActivity implements 
    ProductListView.Presenter,
    ProductEditView.Presenter,
    ProductView.Presenter,
    ProducerListView.Presenter,
    ProducerEditView.Presenter {

  private ProductPlace place;
  
  private ProductServiceAsync productService = AppClientFactory.IMPL.getGinjector().getProductService();
  
  private OrderServiceAsync orderService = AppClientFactory.IMPL.getGinjector().getOrderService();
  
  private HandlerRegistration portalSessionStateChangeRegistration = null;
  
  private static final String PREVIOUS_PRODUCT_LIST_VIEW_PLACE_WORKAROUND = "PREVIOUS_PRODUCT_LIST_VIEW_PLACE_WORKAROUND";

  
  public ProductActivity(ProductPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(ProductPlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getProductEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ProductPlace.LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getProductListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ProductPlace.VIEW)) {
      
      // 31/12/2012
      if (AppClientFactory.isSiteModule && !(place.getModel() instanceof ArticoloDaOrdinare) && PropertiesHolder.getBoolean("client.productActivity.orderProduct.detailsMandatory", false)) {
        goTo(new ProductPlace(ProductPlace.ORDER_DETAIL, place.getModel()));
      } else {
        initView(AppClientFactory.IMPL.getGinjector().getProductView(), panel);
        retrieveModel();
      }
      
    }
    if (place.getToken().equals(ProductPlace.ORDER_DETAIL)) {
      initView(AppClientFactory.IMPL.getGinjector().getProductOrderDetailView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ProductPlace.PRODUCER_LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getProducerListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ProductPlace.PRODUCER_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getProducerEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    
    if (place.getToken().equals(ProductPlace.LIST)) {
      if (AppClientFactory.isAdminModule) {
        productService.findAll(new AsyncCallback<List<Articolo>>() {
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
          public void onSuccess(List<Articolo> products) {
            getView().setModel(products);
          }
        });
      } else {
        getView().setModel(place.getModel());
        checkPortalSessionState();
        GwtUtils.setClientAttribute(PREVIOUS_PRODUCT_LIST_VIEW_PLACE_WORKAROUND, place);
      }
      
    } else if (place.getToken().equals(ProductPlace.EDIT)) {
      getView().setModel(place.getModel());
      
    } else if (place.getToken().equals(ProductPlace.VIEW)) {
      // 31/12/2012
      if (place.getModel() instanceof ArticoloDaOrdinare) {
        Articolo product = ((ArticoloDaOrdinare)place.getModel()).getArticolo();
        fetchHtmls(product, new Delegate<Articolo>() {
          public void execute(Articolo product) {
            ((ArticoloDaOrdinare)place.getModel()).setArticolo(product);
            getView().setModel(place.getModel());
          }
        });
      } else if (place.getModel() instanceof Articolo) {
        Articolo product = (Articolo)place.getModel();
        fetchHtmls(product, new Delegate<Articolo>() {
          public void execute(Articolo product) {
            getView().setModel(product);
          }
        });
      } else {
        getView().setModel(place.getModel());
      }
      
    } else if (place.getToken().equals(ProductPlace.ORDER_DETAIL)) {
      getView().setModel(place.getModel());
      
    } else if (place.getToken().equals(ProductPlace.PRODUCER_LIST)) {
      productService.findAllProducers(new AsyncCallback<List<Produttore>>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(List<Produttore> results) {
          getView().setModel(results);
        }
      });
      
    } else if (place.getToken().equals(ProductPlace.PRODUCER_EDIT)) {
      getView().setModel(place.getModel());
      
    } else {
      getView().setModel(null);
    }
  }
  
  @Override
  public void onDispose() {
    GwtUtils.log(getClass(), "dispose", "disposing " + this);
    if (portalSessionStateChangeRegistration != null)
      portalSessionStateChangeRegistration.removeHandler();
    super.onDispose();
  }
  
  private void registerHandlers(EventBus eventBus) {
    if (portalSessionStateChangeRegistration == null) {
      portalSessionStateChangeRegistration = eventBus.addHandler(PortalSessionStateChangeEvent.TYPE, new PortalSessionStateChangeEvent.Handler() {
        public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "############################");
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "changed portal session state");
          GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "event = " + event);
          getView().setModel(event.getState());
          checkPortalSessionState();
        }
      });
    }
  }
  
  private void checkPortalSessionState () {
    EconyxUtils.checkOpenedOrderInSession(orderService, new Delegate<Order>() {
      public void execute(Order order) {
        getView().setModel(order);
      }
    }, null);
  }

  public void edit(Articolo product) {
    goTo(new ProductPlace(ProductPlace.EDIT, product));
  }
  
  public void show(Articolo product) {
    goTo(new ProductPlace(ProductPlace.VIEW, product));
  }
  
  public void refresh(Articolo product) {
    productService.findById(product.getId(), new AsyncCallback<Articolo>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Articolo product) {
        goTo(new ProductPlace(ProductPlace.EDIT, product));
      }
    });
  }

  public void update(Articolo product) {
    if (product.getId() == null) {
      productService.create(product, new AsyncCallback<Articolo>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Articolo product) {
          System.out.println("created product id " + product.getId());
          refresh(product);
        }
      });
    } else {
      productService.update(product, new AsyncCallback<Articolo>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Articolo product) {
          System.out.println("updated product id " + product.getId());
          refresh(product);
        }
      });
    }
  }
  
  public void updateHtmlContent(Articolo product, HtmlContent content, boolean isHtmlContentModified, final Delegate<Articolo> delegate) {
    if (isHtmlContentModified && product.getId() != null) {
      productService.updateHtmlContent(product.getId(), content, new AsyncCallback<Articolo>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Articolo result) {
          delegate.execute(result);
        }
      });
    } else {
      delegate.execute(product);
    }
  }

  @Override
  public void fetchHtmls(Articolo product, final Delegate<Articolo> delegate) {
    ProductCacheUtil.fetchHtmls(product, delegate);
  }
  
  @Override
  public void goToProductOrderDetailView(ArticoloDaOrdinare articolo) {
    goTo(new ProductPlace(ProductPlace.ORDER_DETAIL, articolo));
  }

  public void orderProduct(Articolo articolo, Double quantity, List<OrderItemDetail> details) {
    
    // 23/11/2012
    if (details == null && PropertiesHolder.getBoolean("client.productActivity.orderProduct.detailsMandatory", false)) {
      if (articolo instanceof ArticoloDaOrdinare) {
        goToProductOrderDetailView((ArticoloDaOrdinare)articolo);
      } else {
        Window.alert("Errore interno 01 in productActivity.orderProduct (contattare gli amministratori)");
      }
      return;
    }

    // 31/12/2012
    if (quantity == null) {
      if (checkLoggedCustomerUser()) {
        ArticoloDaOrdinare articoloDaOrdinare = new ArticoloDaOrdinare();
        articoloDaOrdinare.setArticolo(articolo);
        articoloDaOrdinare.setDetails(details);
        goTo(new ProductPlace(ProductPlace.VIEW, articoloDaOrdinare));
      } else {
        Window.alert("Devi registrarti o inserire il tuo account per procedere");
      }
      return;
    }
    
    if (checkLoggedCustomerUser()) {
      Articolo wrappedArticolo = articolo;
      if (articolo instanceof ArticoloDaOrdinare) {
        wrappedArticolo = ((ArticoloDaOrdinare)articolo).getArticolo();
      }
      PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
      ClientOrderUtils.orderProduct(wrappedArticolo, portalSessionState.getCustomer(), quantity, details, new Delegate<Order>() {
        public void execute(Order order) {
          AppClientFactory.IMPL.getPortalSessionState().setOpenOrder(order);
          AppClientFactory.IMPL.getEventBus().fireEvent(new UserOrderChangeEvent(order));
          if (PageBreadcrumb.getPreviousPage() != null) {
            PortalPageClientUtil.goToPage(PageBreadcrumb.getPreviousPage().getId());
          } else {
            PortalPageClientUtil.goToPage(AppClientFactory.IMPL.getPortalSessionState().getCurrentPageId());
          }
        }
      });
    } else {
      Window.alert("Devi registrarti o inserire il tuo account per procedere");
    }
  }
  
  private boolean checkLoggedCustomerUser() {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    return (portalSessionState != null && portalSessionState.getLoggedUser() != null && portalSessionState.getCustomer() != null);
  }
  
  @Override
  public void goToPage(ProductPage page) {
    PortalPageClientUtil.goToPage(page);
  }

  @Override
  public void edit(Produttore producer) {
    goTo(new ProductPlace(ProductPlace.PRODUCER_EDIT, producer).setHistoryName(producer.getNome()).setHistoryAppend());
  }

  @Override
  public void delete(Produttore producer) {
    
  }

  @Override
  public void update(Produttore producer) {
    
  }

  @Override
  public void refresh(Produttore producer) {
    
  }

  @Override
  public void findOrdersByProducer(Produttore produttore, String currentStateCode, final Delegate<List<Order>> delegate) {
    orderService.findOrdersByProducer(produttore, currentStateCode, new AsyncCallback<List<Order>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(List<Order> results) {
        delegate.execute(results);
      }
    });
  }

  @Override
  public void editOrder(Order order) {
    goTo(new OrderPlace(OrderPlace.EDIT, order).setHistoryName(order.getCode()).setHistoryAppend());
  }

  @Override
  public void updateOrder(Order order, final Delegate<Order> delegate) {
    orderService.update(order, new AsyncCallback<Order>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Order result) {
        delegate.execute(result);
      }
    });
  }
  
  @Override
  public void updateOrders(List<Order> orders, final Delegate<List<Order>> delegate) {
    orderService.updateOrders(orders, new AsyncCallback<List<Order>>() {
      public void onFailure(Throwable caught) {
        GwtUtils.hideWaitPanel(true);
        Window.alert(caught.getMessage());
      }
      public void onSuccess(List<Order> result) {
        delegate.execute(result);
      }
    });
  }

  @Override
  public void findAllOrderStates(Delegate<List<OrderStateConfig>> delegate) {
    ClientOrderUtils.findAllOrderStates(delegate);
  }

}
