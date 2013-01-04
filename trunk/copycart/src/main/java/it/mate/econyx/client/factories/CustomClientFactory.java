package it.mate.econyx.client.factories;

import it.mate.econyx.client.css.CustomClientBundle;
import it.mate.econyx.client.events.PortalInitEvent;
import it.mate.econyx.client.events.PortalPageExplorerRetrieveEvent;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizer;
import it.mate.econyx.client.view.custom.OrderItemEditViewCustomizer;
import it.mate.econyx.client.view.custom.PortalPageExplorerViewCustomizer;
import it.mate.econyx.client.view.custom.ProductEditViewCustomizer;
import it.mate.econyx.client.view.site.custom.OrderItemDetailCustomizerImpl;
import it.mate.econyx.client.view.site.custom.OrderItemEditViewCustomizerImpl;
import it.mate.econyx.client.view.site.custom.PortalPageExplorerViewCustomizerImpl;
import it.mate.econyx.client.view.site.custom.PortalPageMenuViewCustomizerImpl;
import it.mate.econyx.client.view.site.custom.ProductEditViewCustomizerImpl;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


@SuppressWarnings("serial")
public class CustomClientFactory extends DefaultCustomClientFactory {
  
  @Override
  public void initEventBus(EventBus eventBus) {
    
    super.initEventBus(eventBus);

    eventBus.addHandler(PortalInitEvent.TYPE, new PortalInitEvent.Handler() {
      public void onPortalInit(PortalInitEvent event) {
//      CustomClientFactory.portalPanel = event.getPortalPanel();
        Panel centralLoadingPanel = RootPanel.get("centralLoadingPanel");
        if (centralLoadingPanel != null) {
          VerticalPanel p1 = new VerticalPanel();
          p1.setWidth("100%");
          p1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
          HorizontalPanel p2 = new HorizontalPanel();
          p2.add(new Image("/images/commons/loading.gif"));
          Label label = new Label("Attendere.....");
          GwtUtils.setStyleAttribute(label, "paddingTop", "8px");
          GwtUtils.setStyleAttribute(label, "paddingLeft", "20px");
          GwtUtils.setStyleAttribute(label, "fontSize", "18px");
          GwtUtils.setStyleAttribute(label, "fontWeight", "bold");
          GwtUtils.setStyleAttribute(label, "textDecoration", "blink");
          p2.add(label);
          p1.add(p2);
          centralLoadingPanel.add(p1);
        }
      }
    });
    
    eventBus.addHandler(PortalPageExplorerRetrieveEvent.TYPE, new PortalPageExplorerRetrieveEvent.Handler() {
      public void onPortalPageExplorerRetrieve(PortalPageExplorerRetrieveEvent event) {
        if (AppClientFactory.isSiteModule) {
          GwtUtils.setStyleAttribute(RootPanel.get(), "background", "url('/images/wallp/alter_04alt_turq.jpg')");
        }
      }
    });
    
  }
  
  public String getCustomName() {
    return "copycart";
  }
  
  @Override
  public CssResource getCustomCss() {
    return CustomClientBundle.INSTANCE.custom();
  }
  
  @Override
  public PortalPageMenuViewCustomizerImpl getPortalPageMenuViewCustomizer() {
    return new PortalPageMenuViewCustomizerImpl();
  }

  @Override
  public PortalPageExplorerViewCustomizer getPortalPageExplorerViewCustomizer() {
    return new PortalPageExplorerViewCustomizerImpl();
  }
  
  @Override
  public OrderItemDetailCustomizer getOrderItemDetailCustomizer() {
    return new OrderItemDetailCustomizerImpl();
  }

  @Override
  public OrderItemEditViewCustomizer getOrderItemEditViewCustomizer() {
    return new OrderItemEditViewCustomizerImpl();
  }
  
  @Override
  public ProductEditViewCustomizer getProductEditViewCustomizer() {
    return new ProductEditViewCustomizerImpl();
  }

}
