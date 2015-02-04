package it.mate.onscommons.client.event;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;

public class TouchEventUtils {

  private final static String TAP_EVENT_NAME = OsDetectionUtils.isDesktop() ? "click" : "touchend"; 
  
  public static HandlerRegistration addDragStartHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "dragstart", handler);
  }
  
  public static HandlerRegistration addDragOverHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "dragover", handler);
  }
  
  public static HandlerRegistration addDragEndHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "dragend", handler);
  }
  
  public static HandlerRegistration addMouseMoveHandler(Element element, final NativeGestureHandler handler) {
    return addHandler(element, "mousemove", handler);
  }
  
  protected static HandlerRegistration addHandler(Element element, String eventName, final NativeGestureHandler handler) {
    addEventListenerImpl(element.getId(), eventName, new JSOCallback() {
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
      if (@it.mate.onscommons.client.event.TouchEventUtils::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener(eventName, jsEventListener, false);    
    return jsEventListener;
  }-*/;
  
  public static native boolean isContained(Element elem, String containerId) /*-{
    do {
      if (typeof elem == 'undefined' || elem == null) {
//      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("elem is undefined or null");
        break;
      }
//    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("checking elem " + elem.id + " contained in " + containerId);
      if (elem.id == containerId) {
        return true;
      }
    } while(elem = elem.parentElement);
    return false;
  }-*/;
  
  public static JavaScriptObject addOverallEventListener (final Delegate<Element> delegate) {
    return addOverallEventListenerImpl(TAP_EVENT_NAME, new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((Element)jso.cast());
      }
    });
  }
  
  public static void removeEventListener (JavaScriptObject jsEventListener) {
    removeEventListenerImpl(TAP_EVENT_NAME, jsEventListener);
  }
  
  protected static native JavaScriptObject addOverallEventListenerImpl (String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e.target);
    });
    $doc.addEventListener(eventName, jsEventListener, false);    
    return jsEventListener;
  }-*/;
  
  protected static native void removeEventListenerImpl(String eventName, JavaScriptObject jsEventListener) /*-{
    $doc.removeEventListener(eventName, jsEventListener);    
  }-*/;

}
