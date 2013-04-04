package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.CalendarWidget;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.impl.CalEventTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

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
  
  public CalendarMonthViewImpl() {
    super();
    initUI();
    
    List<CalEvent> tests = new ArrayList<CalEvent>();
    tests.add(new CalEventTx(GwtUtils.stringToDate("20130415"), ""));
    tests.add(new CalEventTx(GwtUtils.stringToDate("20130418"), ""));
    fillCalendar(tests);
    
  }
  
  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }

  private void initProvidedElements() {
    calendar = new CalendarWidget<CalEvent>();
  }
  
  public void setModel(Object model, String tag) {

  }

  private void fillCalendar(List<CalEvent> events) {
    List<CalendarWidget.Model<CalEvent>> calModel = new ArrayList<CalendarWidget.Model<CalEvent>>();
    for (CalEvent event : events) {
      calModel.add(
        new CalendarWidget.Model<CalEvent>()
        .setDate(event.getDate())
        .setText(event.getTitle())
        .setData(event)
      );
    }
    calendar.setCellClickCallback(new Delegate<CalEvent>() {
      public void execute(CalEvent event) {
        GwtUtils.log("click on date " + event.getDate());
        getPresenter().goToDate(event.getDate());
      }
    });
    calendar.setModel(calModel);
  }
  
}
