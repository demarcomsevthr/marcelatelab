package it.mate.quilook.client.gwtp.portlets;

import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.PortletFactory;
import it.mate.portlets.client.ui.gwtp.BaseGwtpPortlet;

import com.google.gwt.user.client.ui.SimplePanel;

@SuppressWarnings({"rawtypes", "serial"})
public class PortalPageBodyGwtpPortlet extends BaseGwtpPortlet {

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  @Override
  protected void initGwtp(SimplePanel gwtpPanel) {
//  AppClientFactory.IMPL.initMvp(mvpPanel, new PortalPageBodyActivityMapper(AppClientFactory.IMPL));
  }

  public static class Factory extends PortletFactory<PortalPageBodyGwtpPortlet> {

    @Override
    public PortalPageBodyGwtpPortlet createWidget() {
      return new PortalPageBodyGwtpPortlet();
    }
    
  }
  
}
