package it.mate.econyx.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Panel;

public class PortalInitEvent extends GwtEvent<PortalInitEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalInit(PortalInitEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();
  
  private Panel portalPanel;

  public PortalInitEvent(Panel portalPanel) {
    this.portalPanel = portalPanel;
  }
  
  public Panel getPortalPanel() {
    return portalPanel;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalInit(this);
  }
  
}
