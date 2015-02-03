package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class HasTapHandlerImpl {
  
//private final static String EVENT_NAME = OsDetectionUtils.isDesktop() ? "click" : "tap"; 
  private final static String EVENT_NAME = OsDetectionUtils.isDesktop() ? "click" : "touchend"; 
  
  private List<TapHandler> tapHandlers = new ArrayList<TapHandler>();
  
  private List<HandlerRegistration> tapHandlerRegistrations = new ArrayList<HandlerRegistration>();
  
  private JavaScriptObject jsEventListener = null;
  
  private HasTapHandler target;
  
  public HasTapHandlerImpl(HasTapHandler target) {
    this.target = target;
  }

  public HandlerRegistration addTapHandler(final TapHandler handler) {
    this.tapHandlers.add(handler);
    if (jsEventListener == null) {
      
      Element element = ((Widget)target).getElement();
      PhgUtils.ensureId(element);

      // 03/02/2015 (And4.0 compatibility)
      GwtUtils.onAvailable(element.getId(), new Delegate<Element>() {
        public void execute(final Element element) {
          
          jsEventListener = addEventListenerImpl(element.getId(), EVENT_NAME, new JSOCallback() {
            public void handle(JavaScriptObject jsEvent) {
              Element target = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
              if (!PhgUtils.isReallyAttached(element.getId())) {
                removeAllHandlers();
                return;
              }
              for (TapHandler tapHandler : tapHandlers) {
                tapHandler.onTap(new TapEvent(target, element, 0, 0));
              }
            }
          });
          
        }
      });

      /*
      jsEventListener = addEventListenerImpl(element.getId(), EVENT_NAME, new JSOCallback() {
        public void handle(JavaScriptObject jsEvent) {
          Element target = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
          if (!PhgUtils.isReallyAttached(element.getId())) {
            removeAllHandlers();
            return;
          }
          for (TapHandler tapHandler : tapHandlers) {
            tapHandler.onTap(new TapEvent(target, element, 0, 0));
          }
        }
      });
      */
      
    }
    HandlerRegistration registration = new HandlerRegistration() {
      public void removeHandler() {
        tapHandlers.remove(handler);
        if (tapHandlers.size() == 0) {
          removeEventListenerImpl(EVENT_NAME, jsEventListener);
          jsEventListener = null;
        }
      }
    };
    tapHandlerRegistrations.add(registration);
    return registration;
  }
  
  public void removeAllHandlers() {
    for (HandlerRegistration reg : tapHandlerRegistrations) {
      reg.removeHandler();
    }
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
  
  protected static native void removeEventListenerImpl(String eventName, JavaScriptObject jsEventListener) /*-{
    $doc.removeEventListener(eventName, jsEventListener);    
  }-*/;
  
}
