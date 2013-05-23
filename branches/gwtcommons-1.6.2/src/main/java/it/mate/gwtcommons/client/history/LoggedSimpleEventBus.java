package it.mate.gwtcommons.client.history;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceChangeRequestEvent;
import com.google.web.bindery.event.shared.Event;

public class LoggedSimpleEventBus extends SimpleEventBus {
  
  @Override
  public void fireEvent(Event<?> event) {
    logEvent(event, "fireEvent");
    super.fireEvent(event);
  }
  
  /*
  @Override
  public void fireEvent(GwtEvent<?> event) {
    logEvent(event, "fireEvent");
    super.fireEvent(event);
  }
  */
  
  private void logEvent(Event<?> event, String methodName) {
    String msg = "event = " + event.getClass().getName();
    if (event instanceof PlaceChangeEvent) {
      PlaceChangeEvent pce = (PlaceChangeEvent)event;
      msg += " newPlace = " + pce.getNewPlace();
    }
    if (event instanceof PlaceChangeRequestEvent) {
      PlaceChangeRequestEvent pce = (PlaceChangeRequestEvent)event;
      msg += " newPlace = " + pce.getNewPlace();
    }
    GwtUtils.log(getClass(), hashCode(), methodName, msg);
  }
  

}
