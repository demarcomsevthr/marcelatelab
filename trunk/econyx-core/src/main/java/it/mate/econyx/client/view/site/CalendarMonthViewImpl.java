package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.CalendarWidget;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class CalendarMonthViewImpl extends AbstractBaseView<CalendarView.Presenter> implements CalendarView {

  public interface ViewUiBinder extends UiBinder<Widget, CalendarMonthViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CalendarWidget<CalEvent> calendar;
  
  private List<CalEvent> events = new ArrayList<CalEvent>();
  
  public CalendarMonthViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }

  private void initProvidedElements() {
    calendar = new CalendarWidget<CalEvent>();
  }
  
  /*
   * serve metterli qui perchè deve essere valorizzato il presenter
   */
  @Override
  protected void onLoad() {
    super.onLoad();
    calendar.setCellClickDelegate(new Delegate<CalEvent>() {
      public void execute(CalEvent event) {
        List<CalEvent> selectedEvents = new ArrayList<CalEvent>();
        for (CalEvent calEvent : events) {
          if (calEvent.getDate().equals(event.getDate())) {
            selectedEvents.add(calEvent);
          }
        }
        getPresenter().goToDate(event.getDate(), selectedEvents);
      }
    });
    calendar.setChangedMonthDelegate(new Delegate<Period>() {
      public void execute(Period period) {
        /*
        List<CalEvent> tests = new ArrayList<CalEvent>();
        tests.add(new CalEventTx(GwtUtils.stringToDate("20130415"), ""));
        tests.add(new CalEventTx(GwtUtils.stringToDate("20130418"), ""));
        setModel(tests);
        */
        getPresenter().findByPeriod(period);
      }
    });
  }

  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      this.events = (List<CalEvent>)model;
      setCalendarModel(events);
    }
  }

  private void setCalendarModel(List<CalEvent> events) {
    List<CalendarWidget.Model<CalEvent>> calModel = new ArrayList<CalendarWidget.Model<CalEvent>>();
    for (CalEvent event : events) {
      calModel.add(
        new CalendarWidget.Model<CalEvent>()
        .setDate(event.getDate())
        .setText(event.getTitle())
        .setData(event)
      );
    }
    calendar.setModel(calModel);
  }
  
}
