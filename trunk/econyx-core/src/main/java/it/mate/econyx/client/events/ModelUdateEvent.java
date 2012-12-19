package it.mate.econyx.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ModelUdateEvent extends GwtEvent<ModelUdateEvent.Handler> {

  public interface Handler extends EventHandler {
    void onModelUpdate(ModelUdateEvent event);
  }
  
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final Object model;

  public ModelUdateEvent(Object model) {
    this.model = model;
  }
  
  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }
  
  public Object getModel() {
    return model;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onModelUpdate(this);
  }
  
}
