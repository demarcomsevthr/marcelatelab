package it.mate.econyx.client.portlets;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.util.NavigationUtils;
import it.mate.econyx.client.util.PagesUtils;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.client.view.site.CalendarMonthViewImpl;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;

import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "serial" })
public class CalendarPortlet extends Portlet {

  private CalendarMonthViewImpl view;
  
  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  private CalendarPortlet() {
    initui();
  }

  private void initui() {
    view = new CalendarMonthViewImpl();
    view.setPresenter(new CalendarView.AbstractPresenter() {
      public void goToDate(final Date date) {
        PagesUtils.goToPageByCode(PropertiesHolder.getString("client.CalendarPortlet.calendarDatePageCode", "calendarDatePage"));
        GwtUtils.onAvailable("CalendarDateViewImplHiddenDiv", new Delegate<Element>() {
          public void execute(Element element) {
            NavigationUtils.setSelectedCalendarDate(date);
          }
        });
      }
      public void findByPeriod(Period period) {
        AppClientFactory.IMPL.getGinjector().getCalendarService().findByPeriod(period, new AsyncCallback<List<CalEvent>>() {
          public void onSuccess(List<CalEvent> results) {
            view.setModel(results);
          }
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
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
