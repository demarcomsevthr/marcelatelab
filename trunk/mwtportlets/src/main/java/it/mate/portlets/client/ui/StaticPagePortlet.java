package it.mate.portlets.client.ui;

import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.events.BroadcastListener;
import it.mate.portlets.client.events.PageTemplateRenderRequestEvent;
import it.mate.portlets.client.events.PageTemplateEvent;
import it.mate.portlets.client.layout.Container;
import it.mate.portlets.client.layout.ContainerFactory;
import it.mate.portlets.client.layout.Layout;
import it.mate.portlets.client.layout.HasLayout;

@SuppressWarnings("rawtypes")
public class StaticPagePortlet extends ContainerDelegatePortlet implements HasLayout, BroadcastListener {
  
  private PortalPanel portalPanel = new PortalPanel();
  
  public StaticPagePortlet() {
    super();
    initWidget(portalPanel);
    portalPanel.setStyleName("mwt-PagePortlet");
  }
  
  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  @Override
  protected Container getDelegate() {
    return portalPanel;
  }

  @Override
  public void setLayout(Layout layout) {
    if (layout.getWidth() != null)
      portalPanel.setWidth(layout.getWidth());
  }

  @Override
  public void doLayout() {
    
  }
  
  @Override
  public void onBroadcast(PageTemplateEvent event) {
    if (event instanceof PageTemplateRenderRequestEvent) {
      onPageChange((PageTemplateRenderRequestEvent)event);
    }
  }
  
  protected void onPageChange(PageTemplateRenderRequestEvent event) {
    
  }

  @SuppressWarnings("serial")
  public static class Factory extends ContainerFactory<StaticPagePortlet> {
    @Override
    public StaticPagePortlet createWidget() {
      return new StaticPagePortlet();
    }
  }
  
}
