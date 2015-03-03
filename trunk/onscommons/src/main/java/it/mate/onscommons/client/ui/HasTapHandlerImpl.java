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
  private final static String EVENT_NAME = getTapEventName(); 
  
  private static final String getTapEventName() {
    if (OsDetectionUtils.isDesktop()) {
      return "click";
    }
    if (OsDetectionUtils.isAndroid() && PhgUtils.getDeviceVersion().startsWith("4.0")) {
      return "touchend";
    }
    return "touchend";
  }
  
  private List<TapHandler> tapHandlers = new ArrayList<TapHandler>();
  
  private List<HandlerRegistration> tapHandlerRegistrations = new ArrayList<HandlerRegistration>();
  
  private JavaScriptObject jsEventListener = null;
  
  private HasTapHandler target;
  
  public HasTapHandlerImpl(HasTapHandler target) {
    this.target = target;
  }
  
  private boolean isChildOfDialog(Widget parent) {
    if (parent == null) {
      return false;
    }
    if (parent instanceof OnsDialog) {
      return true;
    }
    return isChildOfDialog(parent.getParent());
  }
  
  public HandlerRegistration addTapHandler(final TapHandler handler) {
    
//  setUseDocEventListener(isChildOfDialog(((Widget)target).getParent()));
    
    this.tapHandlers.add(handler);
    
//  applyOldAndroidPatch();
    
    if (jsEventListener == null) {
      
      Element targetElement = ((Widget)target).getElement();
      PhgUtils.ensureId(targetElement);

      // 03/02/2015
      GwtUtils.onAvailable(targetElement.getId(), new Delegate<Element>() {
        public void execute(final Element availableElement) {
          
          JSOCallback callback = new JSOCallback() {
            public void handle(JavaScriptObject jsEvent) {
              Element eventElement = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
              if (!PhgUtils.isReallyAttached(availableElement.getId())) {
                removeAllHandlers();
                return;
              }
              for (TapHandler tapHandler : tapHandlers) {
                tapHandler.onTap(new TapEvent(eventElement, availableElement, 0, 0, (Widget)target));
              }
            }
          };
          
          // 04/02/2015
          
          boolean useDocEventListener = isChildOfDialog(((Widget)target).getParent());
          
          if (useDocEventListener) {
            jsEventListener = addEventListenerDocImpl(availableElement.getId(), EVENT_NAME, callback);
          } else {
            jsEventListener = addEventListenerElemImpl(availableElement, EVENT_NAME, callback);
          }
          /*
          jsEventListener = addEventListenerElemImpl(availableElement, EVENT_NAME, new JSOCallback() {
            public void handle(JavaScriptObject jsEvent) {
              Element eventElement = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
              if (!PhgUtils.isReallyAttached(availableElement.getId())) {
                removeAllHandlers();
                return;
              }
              for (TapHandler tapHandler : tapHandlers) {
                tapHandler.onTap(new TapEvent(eventElement, availableElement, 0, 0));
              }
            }
          });
          */
          
        }
      });

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
  
  protected static native JavaScriptObject addEventListenerDocImpl (String elemId, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("FIRED EVENT ON TARGET " + e.target.tagName + " " + e.target.id);
      if (@it.mate.onscommons.client.event.TouchEventUtils::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener(eventName, jsEventListener, false);    
    return jsEventListener;
  }-*/;
  
  /* 04/02/2015 */
  /*
  var jsPreventEventListener = $entry(function(e) {
    e.preventDefault();
    return false;
  });
  elem.addEventListener("touchstart", jsPreventEventListener, false);    
  elem.addEventListener("touchmove", jsPreventEventListener, false);    
  */
  protected static native JavaScriptObject addEventListenerElemImpl (Element elem, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
//    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("FIRED EVENT ON TARGET " + e.target.tagName + " " + e.target.id);
      if (@it.mate.onscommons.client.event.TouchEventUtils::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elem.id)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    elem.addEventListener(eventName, jsEventListener, false);
    return jsEventListener;
  }-*/;
  
  protected static native void removeEventListenerImpl(String eventName, JavaScriptObject jsEventListener) /*-{
    $doc.removeEventListener(eventName, jsEventListener);    
  }-*/;
  
  private void applyOldAndroidPatch() {
    if (OsDetectionUtils.isAndroid() && PhgUtils.getDeviceVersion().startsWith("4.0")) {
      Boolean patched = (Boolean)GwtUtils.getClientAttribute("HasTapHandlerImpl_OldAndroidPatch");
      if (patched == null) {
        GwtUtils.setClientAttribute("HasTapHandlerImpl_OldAndroidPatch", true);
        preventTouchEventDefaultImpl();
      }
    }
  }

  protected static native void preventTouchEventDefaultImpl () /*-{
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)(">>>>>>>>> PREVENT DEFAULT ON TOUCH EVENTS");
    var jsEventListener = $entry(function(e) {
      e.preventDefault();
      return false;
    });
    $doc.addEventListener("touchstart", jsEventListener, false);    
    $doc.addEventListener("touchmove", jsEventListener, false);    
    $doc.addEventListener("touchend", jsEventListener, false);    
  }-*/;
  
}
