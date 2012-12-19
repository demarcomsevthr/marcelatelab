package it.mate.portlets.server.services.impl;

import it.mate.portlets.client.ui.BoxLayoutPanel;
import it.mate.portlets.server.services.PortalServiceAdapter;
import it.mate.portlets.server.util.WebClassUtils;
import it.mate.portlets.shared.model.PageTemplate;
import it.mate.portlets.shared.services.PortalService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortalServiceImpl extends RemoteServiceServlet implements PortalService {

  private static Logger logger = Logger.getLogger(PortalServiceImpl.class);
  
  private PortalServiceAdapter adapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
//  adapter = new DebugPortalServiceAdapter();
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    adapter = context.getBean(PortalServiceAdapter.class);
    logger.debug("initialized " + this);
    logger.debug("adapter = " + adapter);
    
    WebClassUtils.addWidgetFactoryClass(BoxLayoutPanel.Factory.class);
  }
  
  
  @Override
  public PageTemplate getPage(String historyToken) {
//  Page page = new Page();
//  page.setWidgetFactory(getWidgetFactory(historyToken));
    PageTemplate page = adapter.getPage(historyToken);
    page.setName(historyToken);
    return page;
  }
  
}
