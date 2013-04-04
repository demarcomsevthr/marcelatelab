package it.mate.econyx.client.portlets;

import it.mate.econyx.client.util.NavigationUtils;
import it.mate.econyx.client.util.PagesUtils;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.client.view.site.CalendarMonthViewImpl;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import java.util.Date;

import com.google.gwt.dom.client.Element;

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
    CalendarMonthViewImpl view = new CalendarMonthViewImpl();
    view.setPresenter(new CalendarView.AbstractPresenter() {
      public void goToDate(final Date date) {
        PagesUtils.goToPageByCode(PropertiesHolder.getString("client.CalendarPortlet.calendarDatePageCode", "calendarDatePage"));
        GwtUtils.onAvailable("CalendarDateViewImplHiddenDiv", new Delegate<Element>() {
          public void execute(Element element) {
            NavigationUtils.setSelectedCalendarDate(date);
          }
        });
      }
    });
    initWidget(view);
  }

  public static class Factory extends PortletFactory<CalendarPortlet> {
    public CalendarPortlet createWidget() {
      return new CalendarPortlet();
    }
  }
  
}
