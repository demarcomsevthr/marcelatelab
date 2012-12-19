package it.mate.econyx.client.events;

import it.mate.econyx.shared.model.PortalSessionState;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PortalSessionStateChangeEvent extends GwtEvent<PortalSessionStateChangeEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalSessionStateChange(PortalSessionStateChangeEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final PortalSessionState state;

  public PortalSessionStateChangeEvent(PortalSessionState state) {
    this.state = state;
  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  public PortalSessionState getState() {
    return state;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalSessionStateChange(this);
  }

  @Override
  public String toString() {
    return "PortalSessionStateChangeEvent [state=" + state + "]";
  }
  
}
