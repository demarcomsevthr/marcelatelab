package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.LoginActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import com.google.gwt.user.client.ui.SimplePanel;

public class LoginPortlet extends Portlet {

  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  public LoginPortlet() {
    initui();
  }

  private void initui() {
    SimplePanel panel = new SimplePanel();
    LoginActivity activity = new LoginActivity(new PortalUserPlace(PortalUserPlace.LOGIN), AppClientFactory.IMPL);
    activity.start(panel, AppClientFactory.IMPL.getEventBus());
    initWidget(panel);
  }
  
  public static class Factory extends PortletFactory<LoginPortlet> {

    public LoginPortlet createWidget() {
      return new LoginPortlet();
    }
    
  }

}
