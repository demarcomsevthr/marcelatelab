package it.mate.econyx.client.events;

import it.mate.econyx.shared.model.PortalPage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PortalPageChangedEvent extends GwtEvent<PortalPageChangedEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalPageChanged(PortalPageChangedEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final PortalPage page;
  
  public PortalPageChangedEvent(PortalPage page) {
    super();
    this.page = page;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  public PortalPage getPage() {
    return page;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalPageChanged(this);
  }
  
}
