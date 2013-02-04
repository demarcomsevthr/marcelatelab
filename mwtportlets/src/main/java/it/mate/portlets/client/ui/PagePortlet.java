package it.mate.portlets.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.events.BroadcastListener;
import it.mate.portlets.client.events.BroadcastManager;
import it.mate.portlets.client.events.PageTemplateChangeCompleteEvent;
import it.mate.portlets.client.events.PageTemplateEvent;
import it.mate.portlets.client.events.PageTemplateRenderRequestEvent;
import it.mate.portlets.client.layout.Container;
import it.mate.portlets.client.layout.ContainerFactory;
import it.mate.portlets.client.layout.HasLayout;
import it.mate.portlets.client.layout.Layout;
import it.mate.portlets.client.layout.LayoutUtil;
import it.mate.portlets.client.util.StyleUtil;
import it.mate.portlets.shared.util.StringUtils;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

@SuppressWarnings("rawtypes")
public class PagePortlet extends ContainerDelegatePortlet implements HasLayout, BroadcastListener {
  
  private PortalPanel portalPanel = new PortalPanel();
  
  private VerticalPanel contentPanel = new VerticalPanel() {
    public void setStyleName(String style) {
      super.setStyleName(style);
    };
    public void setStyleName(String style, boolean add) {
      super.setStyleName(style, add);
    };
  };
  
  public PagePortlet() {
    super();
    contentPanel.setWidth("100%");
    contentPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    contentPanel.add(portalPanel);
    initWidget(contentPanel);
    portalPanel.setStyleName("mwt-PagePortlet");
//  initWidget(portalPanel);
    BroadcastManager.get().addBroadcastListener(this);
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
    if (layout.getWidth() != null) {
      portalPanel.setWidth(layout.getWidth());
      if (layout.getStylename() != null && !"".equals(layout.getStylename())) {
        contentPanel.setStyleName(layout.getStylename());
      }
      if (StringUtils.isSet(layout.getStyle())) {
        StyleUtil.applyStyle(contentPanel, layout.getStyle());
      }
    }
    
    /*
    GwtUtils.createTimer(100, new Delegate<Void>() {
      public void execute(Void element) {
        if (portalPanel.getOffsetHeight() < 930) {
          portalPanel.setHeight("930px");
        } else {
          portalPanel.getElement().getStyle().clearHeight();
        }
      }
    });
    */
    
  }

  @Override
  public void doLayout() {
    
  }
  
  @Override
  public void onBroadcast(PageTemplateEvent event) {
    if (event instanceof PageTemplateRenderRequestEvent) {
      onPageTemplateRenderRequestEvent((PageTemplateRenderRequestEvent)event);
    }
  }
  
  protected void onPageTemplateRenderRequestEvent(PageTemplateRenderRequestEvent event) {
    portalPanel.clear();
    portalPanel.add(LayoutUtil.createWidget(event.getWidgetFactory()));
    BroadcastManager.get().broadcast(new PageTemplateChangeCompleteEvent(event.getName(), event.getWidgetFactory()));
  }

  @SuppressWarnings("serial")
  public static class Factory extends ContainerFactory<PagePortlet> {
    
    private String initialPageName;
    
    public void setInitialPageName(String initialPageName) {
      this.initialPageName = initialPageName;
    }
    
    public String getInitialPageName() {
      return initialPageName;
    }
    
    @Override
    public PagePortlet createWidget() {
      return new PagePortlet();
    }
  }
  
}
