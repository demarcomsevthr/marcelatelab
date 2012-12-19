package it.mate.econyx.client.portlets;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.PageBreadcrumb;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

public class PortalPageBreadcrumbPortlet extends Portlet {

  private static PortalPageBreadcrumbPortlet instance;
  
  private PortalPageBreadcrumbPortlet() {
    initUI();
  }
  
  private void initUI() {
    PageBreadcrumb breadcrumb = new PageBreadcrumb();
    initWidget(breadcrumb);
    breadcrumb.setClientFactory(AppClientFactory.IMPL);
  }
  
  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  public static class Factory extends PortletFactory<PortalPageBreadcrumbPortlet> {

    @Override
    public PortalPageBreadcrumbPortlet createWidget() {
      if (PortalPageBreadcrumbPortlet.instance == null)
        PortalPageBreadcrumbPortlet.instance = new PortalPageBreadcrumbPortlet();
      return instance;
    }
    
  }
  
}
