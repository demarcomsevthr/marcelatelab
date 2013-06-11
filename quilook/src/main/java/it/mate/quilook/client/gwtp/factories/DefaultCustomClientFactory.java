package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.client.events.PortalInitCompleteEvent;
import it.mate.econyx.client.events.PortalInitEvent;
import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizer;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizerDefaultImpl;
import it.mate.econyx.client.view.custom.OrderItemEditViewCustomizer;
import it.mate.econyx.client.view.custom.OrderItemEditViewCustomizerDefaultImpl;
import it.mate.econyx.client.view.custom.PortalPageExplorerViewCustomizer;
import it.mate.econyx.client.view.custom.PortalPageExplorerViewCustomizerDefaultImpl;
import it.mate.econyx.client.view.custom.PortalPageMenuViewCustomizerImpl;
import it.mate.econyx.client.view.custom.ProductEditViewCustomizer;
import it.mate.econyx.client.view.custom.ProductEditViewCustomizerDefaultImpl;
import it.mate.gwtcommons.client.factories.AbstractCustomClientFactory;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

@SuppressWarnings("serial")
public class DefaultCustomClientFactory implements AbstractCustomClientFactory {

  protected transient static Panel portalPanel;
  
  @Override
  public void initEventBus(EventBus eventBus) {
    
    eventBus.addHandler(PortalInitEvent.TYPE, new PortalInitEvent.Handler() {
      public void onPortalInit(PortalInitEvent event) {
        DefaultCustomClientFactory.portalPanel = event.getPortalPanel();
      }
    });
    
    eventBus.addHandler(PortalInitCompleteEvent.TYPE, new PortalInitCompleteEvent.Handler() {
      public void onPortalInitComplete(PortalInitCompleteEvent event) {
        Panel loadingPanel = RootPanel.get("loadingPanel");
        if (loadingPanel != null) {
          loadingPanel.setVisible(false);
        }
        if (DefaultCustomClientFactory.portalPanel != null) {
          DefaultCustomClientFactory.portalPanel.setVisible(true);
        }
      }
    });
    
    
  }
  
  public String getCustomName() {
    return "default";
  }
  
  @Override
  public CssResource getCustomCss() {
    return null;
  }
  
  public PortalPageMenuViewCustomizerImpl getPortalPageMenuViewCustomizer() {
    return new PortalPageMenuViewCustomizerImpl();
  }
  
  public PortalPageExplorerViewCustomizer getPortalPageExplorerViewCustomizer() {
    return new PortalPageExplorerViewCustomizerDefaultImpl();
  }
  
  public OrderItemDetailCustomizer getOrderItemDetailCustomizer() {
    return new OrderItemDetailCustomizerDefaultImpl();
  }

  public OrderItemEditViewCustomizer getOrderItemEditViewCustomizer() {
    return new OrderItemEditViewCustomizerDefaultImpl();
  }
  
  public ProductEditViewCustomizer getProductEditViewCustomizer() {
    return new ProductEditViewCustomizerDefaultImpl();
  }
  
  public AdminTabPanel.Section<PortalUserEditView.Presenter> getCustomPortalUserEditSection() {
    return null;
  }
  
  public AbstractBaseView getCustomProfileView1 () {
    return null;
  }
  
  public Button getPortalUserEditListCustomButton() {
    return null;
  }
  
}
