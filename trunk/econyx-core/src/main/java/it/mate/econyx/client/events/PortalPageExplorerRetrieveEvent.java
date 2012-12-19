package it.mate.econyx.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PortalPageExplorerRetrieveEvent extends GwtEvent<PortalPageExplorerRetrieveEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalPageExplorerRetrieve(PortalPageExplorerRetrieveEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  public PortalPageExplorerRetrieveEvent() {
    super();
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalPageExplorerRetrieve(this);
  }
  
}
