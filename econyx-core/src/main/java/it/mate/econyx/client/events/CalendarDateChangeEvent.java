package it.mate.econyx.client.events;

import it.mate.econyx.shared.model.CalEvent;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CalendarDateChangeEvent extends GwtEvent<CalendarDateChangeEvent.Handler> {

  public interface Handler extends EventHandler {
    void onCalendarDateChange(CalendarDateChangeEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final Date date;
  
  private final List<CalEvent> calEvents;

  public CalendarDateChangeEvent(Date date, List<CalEvent> calEvents) {
    this.date = date;
    this.calEvents = calEvents;
  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  public Date getDate() {
    return date;
  }
  
  public List<CalEvent> getCalEvents() {
    return calEvents;
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
