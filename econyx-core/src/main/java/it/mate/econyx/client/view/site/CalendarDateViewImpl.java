package it.mate.econyx.client.view.site;

import it.mate.econyx.client.events.CalendarDateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.CalendarView;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CalendarDateViewImpl extends AbstractBaseView<CalendarView.Presenter> implements CalendarView {

  public interface ViewUiBinder extends UiBinder<Widget, CalendarDateViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label dateLbl;

  public CalendarDateViewImpl() {
    super();
    initUI();
    GwtUtils.log("INITIALIZING " + this);
  }
  
  @Override
  public String toString() {
    return getClass()+"@"+hashCode();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    
    AppClientFactory.IMPL.getEventBus().addHandler(CalendarDateChangeEvent.TYPE, new CalendarDateChangeEvent.Handler() {
      public void onCalendarDateChange(CalendarDateChangeEvent event) {
        setSelectedCalendarDate(event.getDate());
      }
    });
    
  }

  public void setModel(Object model, String tag) {

  }
  
  private void setSelectedCalendarDate(Date date) {
    dateLbl.setText(GwtUtils.dateToString(date));
  }
  
}
