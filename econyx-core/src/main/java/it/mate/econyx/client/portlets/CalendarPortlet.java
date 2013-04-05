package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.CalendarActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CalendarPlace;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import com.google.gwt.user.client.ui.SimplePanel;

@SuppressWarnings({ "rawtypes", "serial" })
public class CalendarPortlet extends Portlet {

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  private CalendarPortlet() {
    initui();
  }

  private void initui() {
    SimplePanel panel = new SimplePanel();
    CalendarActivity activity = new CalendarActivity(new CalendarPlace(CalendarPlace.CAL_MONTH_VIEW), AppClientFactory.IMPL);
    activity.start(panel, AppClientFactory.IMPL.getEventBus());
    initWidget(panel);
  }

  public static class Factory extends PortletFactory<CalendarPortlet> {
    public CalendarPortlet createWidget() {
      return new CalendarPortlet();
    }
  }
  
}
