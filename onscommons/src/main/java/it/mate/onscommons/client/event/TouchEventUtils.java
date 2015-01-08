package it.mate.onscommons.client.event;

import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;

public class TouchEventUtils {

  public static HandlerRegistration addDragStartHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "dragstart", handler);
  }
  
  public static HandlerRegistration addDragEndHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "dragend", handler);
  }
  
  protected static HandlerRegistration addHandler(Element element, String eventName, final NativeGestureHandler handler) {
    addEventListenerImpl(element.getId(), "dragstart", new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        handler.on(new NativeGestureEvent((NativeGesture)jso.cast()));
      }
    });
    HandlerRegistration registration = new HandlerRegistration() {
      public void removeHandler() {
        
      }
    };
    return registration;
  }
  
  protected static native JavaScriptObject addEventListenerImpl (String elemId, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      if (@it.mate.onscommons.client.onsen.OnsenUi::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener(eventName, jsEventListener, false);    
    return jsEventListener;
  }-*/;
  
}
