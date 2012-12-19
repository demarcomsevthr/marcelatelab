package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import com.google.gwt.user.client.ui.SimplePanel;

public class StaticPortalPagePortlet extends Portlet {
  
  private static StaticPortalPagePortlet instance;
  
  private SimplePanel panel;
  
  public StaticPortalPagePortlet(String portalPageName) {
    initUI();
    initActivity(portalPageName);
  }
  
  private void initUI() {
    this.panel = new SimplePanel();
    initWidget(panel);
  }
  
  private void initActivity(String portalPageName) {
    PortalPageActivity activity = new PortalPageActivity(new PortalPagePlace(PortalPagePlace.VIEW_BY_CODE, portalPageName), AppClientFactory.IMPL);
    activity.start(panel, AppClientFactory.IMPL.getEventBus());
  }

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  public static class Factory extends PortletFactory<StaticPortalPagePortlet> {
    
    private String portalPageName;

    @Override
    public StaticPortalPagePortlet createWidget() {
      if (StaticPortalPagePortlet.instance == null)
        StaticPortalPagePortlet.instance = new StaticPortalPagePortlet(portalPageName);
      return StaticPortalPagePortlet.instance;
    }
    
    public void setPortalPageName(String portalPageName) {
      this.portalPageName = portalPageName;
    }
    
  }
 
  
}
