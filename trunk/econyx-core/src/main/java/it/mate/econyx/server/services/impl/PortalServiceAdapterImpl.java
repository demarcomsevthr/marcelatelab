package it.mate.econyx.server.services.impl;

import it.mate.gwtcommons.server.utils.SpringUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.WidgetFactoryProvider;
import it.mate.portlets.server.services.PortalServiceAdapter;
import it.mate.portlets.server.util.WebClassUtils;
import it.mate.portlets.shared.model.PageTemplate;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


/**
 * 
 *      PER GLI ERRORI DI CLASS LOADING vedi /econyx-core/src/main/java/it/mate/econyx/client/portlets/ATTENZIONE-README.txt
 *
 * 
 *
 */


@Component
public class PortalServiceAdapterImpl implements PortalServiceAdapter {

  private static Logger logger = Logger.getLogger(PortalServiceAdapterImpl.class);
  
  private static final Class<?> INITIAL_PORTLET_CLASS = it.mate.econyx.client.portlets.PortalPageBodyPortlet.Factory.class;
  
  @Override
  public PageTemplate getPage(String historyToken) {
    PageTemplate result = new PageTemplate();
    result.setWidgetFactory(getPageFromSpringContext(historyToken));
    return result;
  }

  @PostConstruct
  public void onPostConstruct() {
    try {
//    List<Class<? extends WidgetFactoryProvider>> portletClasses = SpringUtils.scanPackageClasses(INITIAL_PORTLET_CLASS.getPackage().getName(), WidgetFactoryProvider.class);
      List<Class<? extends WidgetFactory>> portletClasses = SpringUtils.scanPackageClasses(INITIAL_PORTLET_CLASS.getPackage().getName(), WidgetFactory.class);
      for (Class<?> portletClass : portletClasses) {
        WebClassUtils.addWidgetFactoryClass(portletClass);
      }
      /*
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.PortalPageBodyPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.PortalPageBreadcrumbPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.PortalPageExplorerPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.PortalPageMenuPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.PortalUserPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(it.mate.econyx.client.portlets.ShoppingCartPortlet.Factory.class);
      */
      logger.debug("widgetFactoryClasses = " + WebClassUtils.getWidgetFactoryClasses());
    } catch (Exception ex) {
      logger.error("error", ex);
    }
    logger.debug("initialized " + this);
  }

  private WidgetFactory getPageFromSpringContext(String historyToken) {
    try {
      
      /*
      WebClassUtils.addWidgetFactoryClass(BodyPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(FolderExplorerPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(FolderMenuPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(PortalPageBodyPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(PortalPageExplorerPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(PortalPageMenuPortlet.Factory.class);
      WebClassUtils.addWidgetFactoryClass(PortalPageBreadcrumbPortlet.Factory.class);
      */
      
      logger.debug("getting template " + historyToken);
      
      String content = FileUtils.readFileToString(new ClassPathResource("META-INF/pages/pages.xml").getFile());
      return SpringUtils.getStringApplicationContext(content).getBean(historyToken, WidgetFactory.class);
      
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
  
}
