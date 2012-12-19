package it.mate.econyx.client.factories;

import it.mate.econyx.client.activities.mapper.AdminActivityMapper;
import it.mate.econyx.shared.services.CustomerService;
import it.mate.econyx.shared.services.CustomerServiceAsync;
import it.mate.econyx.shared.services.GeneralService;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.ImageService;
import it.mate.econyx.shared.services.ImageServiceAsync;
import it.mate.econyx.shared.services.OrderService;
import it.mate.econyx.shared.services.OrderServiceAsync;
import it.mate.econyx.shared.services.PortalPageService;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.econyx.shared.services.PortalUserService;
import it.mate.econyx.shared.services.PortalUserServiceAsync;
import it.mate.econyx.shared.services.ProductService;
import it.mate.econyx.shared.services.ProductServiceAsync;
import it.mate.gwtcommons.client.ui.WaitingCursorUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class AppGinModule extends AbstractGinModule {

  @Override
  protected void configure() {

  }

  private static GeneralServiceAsync generalService = null;
  
  private static PortalPageServiceAsync portalPageService = null;
  
  private static OrderServiceAsync orderService = null;
  
  private static ProductServiceAsync productService = null;
  
  private static PortalUserServiceAsync portalUserService = null;
  
  private static ImageServiceAsync imageService = null;
  
  private static CustomerServiceAsync customerService = null;
  
  
  @Provides
  public GeneralServiceAsync getGeneralService() {
    if (generalService == null) {
      generalService = GWT.create(GeneralService.class);
      // 02/12/2012 - TEST it.mate.gwtcommons.rpc.client.RpcServiceInterceptor
//    generalService = applyInterceptors(generalService);
    }
    return generalService;
  }

  @Provides
  public PortalPageServiceAsync getPortalPageService() {
    if (portalPageService == null) {
      portalPageService = GWT.create(PortalPageService.class);
      portalPageService = applyInterceptors(portalPageService);
    }
    return portalPageService;
  }

  @Provides
  public ProductServiceAsync getProductService() {
    if (productService == null) {
      productService = GWT.create(ProductService.class);
      productService = applyInterceptors(productService);
    }
    return productService;
  }
  
  @Provides
  public PortalUserServiceAsync getPortalUserService() {
    if (portalUserService == null) {
      portalUserService = GWT.create(PortalUserService.class);
      portalUserService = applyInterceptors(portalUserService);
    }
    return portalUserService;
  }
  
  @Provides
  public ImageServiceAsync getImageService() {
    if (imageService == null) {
      imageService = GWT.create(ImageService.class);
      imageService = applyInterceptors(imageService);
    }
    return imageService;
  }
  
  @Provides
  public CustomerServiceAsync getCustomerService() {
    if (customerService == null) {
      customerService = GWT.create(CustomerService.class);
      customerService = applyInterceptors(customerService);
    }
    return customerService;
  }
  
  @Provides
  public OrderServiceAsync getOrderService() {
    if (orderService == null) {
      orderService = GWT.create(OrderService.class);
      orderService = applyInterceptors(orderService);
    }
    return orderService;
  }
  
  private <S> S applyInterceptors(final S service) {
    /*
    if (AppClientFactory.isSiteModule && 
        PropertiesHolder.getBoolean("client.portalPageServiceInterceptors", true)) {
      RPCUtils.setInterceptors((ServiceDefTarget)service, new RPCUtils.Interceptors() {
        public void onCreateRequest(RequestBuilder rb) {
          beforeRequest();
        }
        public void onFinishRequest(RequestBuilder rb) {
        }
        public void onResponseReceived(Response response) {
          afterRequest();
        }
        public boolean onError(RequestBuilder rb, Throwable exception) {
          GwtUtils.log(getClass(), "onError", "interceptor");
          afterRequest();
          RootPanel debugPanel = RootPanel.get("debugPanel");
          if (debugPanel != null) {
            debugPanel.clear();
            String msg = "Error occurred ";
            if (rb == null) {
              msg += "on rpc call";
            } else {
              msg+= "on calling " + rb.getUrl();
            }
            msg += " - ";
            if (exception != null) {
              msg += exception.getClass().getName();
              if (exception.getMessage() != null) {
                msg += " - ";
                if (exception.getMessage().length() > 500) {
                  msg += exception.getMessage().substring(0, 500);
                } else {
                  msg += exception.getMessage();
                }
              }
            }
            debugPanel.add(new Label(msg));
            return false;
          }
          return true;
        }
      });
    }
    */
    return service;
  }
  
  private void beforeRequest() {
    WaitingCursorUtil.start();
  }
  
  private void afterRequest() {
    GwtUtils.deferredExecution(1000, new Delegate<Void>() {
      public void execute(Void element) {
        WaitingCursorUtil.stop();
      }
    });
  }
  
  @Provides
  AdminActivityMapper getAdminActivityMapper() {
    return new AdminActivityMapper(AppClientFactory.IMPL);
  }
  
}
