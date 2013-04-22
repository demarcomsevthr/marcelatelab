package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.BoxContainerPortlet;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings({ "rawtypes", "serial" })
public class PortalPageSummaryPortlet extends Portlet {

  private PortalPageActivity activity = null;
  
  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  @Override
  protected void onDetach() {
    if (activity != null) {
      activity.onDispose();
    }
    super.onDetach();
  }

  private PortalPageSummaryPortlet() {
    initui();
  }

  private void initui() {
    SimplePanel panel = new SimplePanel();
    PortalPageActivity activity = new PortalPageActivity(new PortalPagePlace(PortalPagePlace.VIEW_SUMMARY), AppClientFactory.IMPL);
    activity.start(panel, AppClientFactory.IMPL.getEventBus());
    initWidget(panel);
    Widget boxContainerPortlet = GwtUtils.getParentWidgetByClassAndStyle(this, BoxContainerPortlet.class.getName(), "mwt-BoxContainer");
    if (boxContainerPortlet != null)
      boxContainerPortlet.setVisible(false);
  }

  public static class Factory extends PortletFactory<PortalPageSummaryPortlet> {
    public PortalPageSummaryPortlet createWidget() {
      return new PortalPageSummaryPortlet();
    }
  }
  
}
