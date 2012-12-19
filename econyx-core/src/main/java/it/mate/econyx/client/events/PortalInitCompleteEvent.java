package it.mate.econyx.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PortalInitCompleteEvent extends GwtEvent<PortalInitCompleteEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalInitComplete(PortalInitCompleteEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();
  
  public PortalInitCompleteEvent() {

  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalInitComplete(this);
  }
  
}
