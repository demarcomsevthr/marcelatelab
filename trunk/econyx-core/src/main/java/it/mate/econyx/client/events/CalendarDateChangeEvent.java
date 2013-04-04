package it.mate.econyx.client.events;

import java.util.Date;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CalendarDateChangeEvent extends GwtEvent<CalendarDateChangeEvent.Handler> {

  public interface Handler extends EventHandler {
    void onCalendarDateChange(CalendarDateChangeEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final Date date;

  public CalendarDateChangeEvent(Date date) {
    this.date = date;
  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  public Date getDate() {
    return date;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onCalendarDateChange(this);
  }

  @Override
  public String toString() {
    return "CalendarDateChangeEvent [date=" + date + "]";
  }
  
}
