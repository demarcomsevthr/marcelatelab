package it.mate.econyx.server.services.impl;

import it.mate.commons.server.utils.SpringUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.server.services.PortalServiceAdapter;
import it.mate.portlets.server.util.WebClassUtils;
import it.mate.portlets.shared.model.PageTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@SuppressWarnings("rawtypes")
public class PortalServiceAdapterImpl implements PortalServiceAdapter {

  private static Logger logger = Logger.getLogger(PortalServiceAdapterImpl.class);
  
  private static final Class<?> INITIAL_PORTLET_CLASS = it.mate.econyx.client.portlets.PortalPageBodyPortlet.Factory.class;
  
  private static Map<Object, Object> instanceCache;
  
  private final static boolean USE_INSTANCE_CACHE = PropertiesHolder.getBoolean("it.mate.econyx.server.services.impl.PortalServiceAdapterImpl.useInstanceCache", true);
  
  private final static String CONFIG_PATH = PropertiesHolder.getString("it.mate.econyx.server.services.impl.PortalServiceAdapterImpl.pageTemplatesConfigPath", "META-INF/pages/pages.xml");
  
  @Override
  public PageTemplate getPage(String historyToken) {
    PageTemplate result = new PageTemplate();
    result.setWidgetFactory(getPageFromSpringContext(historyToken));
    return result;
  }

  @PostConstruct
  public void onPostConstruct() {
    try {
      List<Class<? extends WidgetFactory>> portletClasses = SpringUtils.scanPackageClasses(INITIAL_PORTLET_CLASS.getPackage().getName(), WidgetFactory.class);
      for (Class<?> portletClass : portletClasses) {
        WebClassUtils.addWidgetFactoryClass(portletClass);
      }
      logger.debug("widgetFactoryClasses = " + WebClassUtils.getWidgetFactoryClasses());
    } catch (Exception ex) {
      logger.error("error", ex);
    }
    logger.debug("initialized " + this);
  }

  private WidgetFactory getPageFromSpringContext(String historyToken) {
    try {
      logger.debug("getting template " + historyToken);
      WidgetFactory cachedWidgetFactory = (WidgetFactory)getInstCache().get(historyToken);
      if (cachedWidgetFactory != null) {
        return cachedWidgetFactory;
      }
      String content = FileUtils.readFileToString(new ClassPathResource(CONFIG_PATH).getFile());
      WidgetFactory widgetFactory = SpringUtils.getStringApplicationContext(content).getBean(historyToken, WidgetFactory.class);
      getInstCache().put(historyToken, widgetFactory);
      return widgetFactory;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
  
  private synchronized static Map<Object, Object> getInstCache() {
    if (USE_INSTANCE_CACHE) {
      if (instanceCache == null) {
        instanceCache = new HashMap<Object, Object>();
      }
      return instanceCache;
    } else {
      return new HashMap<Object, Object>();
    }
  }
  
  public void clearCache() {
    PortalServiceAdapterImpl.instanceCache = null;
  }
  
}
