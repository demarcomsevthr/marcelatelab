package it.mate.econyx.client.events;

import it.mate.econyx.shared.model.PortalPage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PortalPageChangingEvent extends GwtEvent<PortalPageChangingEvent.Handler> {

  public interface Handler extends EventHandler {
    void onPortalPageChanging(PortalPageChangingEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final PortalPage page;
  
  private boolean forceReloadPage;

  public PortalPageChangingEvent(PortalPage newPage) {
    this(newPage, false);
  }
  
  public PortalPageChangingEvent(PortalPage page, boolean forceReloadPage) {
    super();
    this.page = page;
    this.forceReloadPage = forceReloadPage;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  public PortalPage getPage() {
    return page;
  }
  
  public boolean forceReloadPage() {
    return forceReloadPage;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onPortalPageChanging(this);
  }
  
}
