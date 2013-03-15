package it.mate.econyx.server.util;

import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.InitAdapter;
import it.mate.econyx.server.services.MailAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.portlets.server.services.PortalServiceAdapter;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AdaptersUtil {
  
  private static ApplicationContext context = null;
  
  public static synchronized void initContext(ServletContext servletContext) {
    if (context == null)
      context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
  }
  
  public static PortalDataExporter getPortalDataMarshaller() {
    return context.getBean(PortalDataExporter.class);
  }
  
  public static InitAdapter getInitAdapter() {
    return context.getBean(InitAdapter.class);
  }
  
  public static CustomerAdapter getCustomerAdapter() {
    return context.getBean(CustomerAdapter.class);
  }
  
  public static GeneralAdapter getGeneralAdapter() {
    return context.getBean(GeneralAdapter.class);
  }
  
  public static ImageAdapter getImageAdapter() {
    return context.getBean(ImageAdapter.class);
  }
  
  public static MailAdapter getMailAdapter() {
    return context.getBean(MailAdapter.class);
  }
  
  public static OrderAdapter getOrderAdapter() {
    return context.getBean(OrderAdapter.class);
  }

  public static PortalPageAdapter getPortalPageAdapter() {
    return context.getBean(PortalPageAdapter.class);
  }

  public static PortalUserAdapter getPortalUserAdapter() {
    return context.getBean(PortalUserAdapter.class);
  }

  public static ProductAdapter getProductAdapter() {
    return context.getBean(ProductAdapter.class);
  }
  
  public static PortalServiceAdapter getPortalServiceAdapter() {
    return context.getBean(PortalServiceAdapter.class);
  }

}
