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

public class HasTapHandlerImpl_TEST {
  
//private final static String TAP_EVENT_NAME = OsDetectionUtils.isDesktop() ? "click" : "tap"; 
  private final static String TAP_EVENT_NAME = getTapEventName();
  
  private static final String getTapEventName() {
    if (OsDetectionUtils.isDesktop()) {
      return "click";
    }
    if (OsDetectionUtils.isAndroid() && PhgUtils.getDeviceVersion().startsWith("4.0")) {
      return "tap";
    }
    return "touchend";
  }
  
  private List<TapHandler> tapHandlers = new ArrayList<TapHandler>();
  
  private List<HandlerRegistration> tapHandlerRegistrations = new ArrayList<HandlerRegistration>();
  
  private JavaScriptObject jsEventListener = null;
  
  private HasTapHandler target;
  
  private Element targetElement;
  
  public HasTapHandlerImpl_TEST(HasTapHandler target) {
    this.target = target;
  }

  public HandlerRegistration addTapHandler(final TapHandler handler) {
    this.tapHandlers.add(handler);
    
    if (jsEventListener == null) {
      
      targetElement = ((Widget)target).getElement();
      PhgUtils.ensureId(targetElement);
      
      // 03/02/2015 (And4.0 compatibility)
      GwtUtils.deferredExecution(1000, new Delegate<Void>() {
        public void execute(Void element) {
          
          // 03/02/2015 (And4.0 compatibility)
          GwtUtils.onAvailable(targetElement.getId(), new Delegate<Element>() {
            public void execute(Element availableElementXX) {
              
              targetElement = availableElementXX;
              
              /** PER DEBUG 
              targetElement.setInnerText(targetElement.getInnerText() + " (" + targetElement.getId() + ")");
              **/

              PhgUtils.log(">>>> ADDING EVENT LISTENER ON " + targetElement.getTagName() + " " + targetElement.getId() + " " + targetElement.getInnerHTML());
              
              jsEventListener = addEventListenerDocImpl(targetElement.getId(), TAP_EVENT_NAME, new JSOCallback() {
//            jsEventListener = addEventListenerElemImpl(targetElement, TAP_EVENT_NAME, new JSOCallback() {
//            jsEventListener = addEventListenerOldImpl(targetElement.getId(), new JSOCallback() {
                public void handle(JavaScriptObject jsEvent) {
                  
                  PhgUtils.log(">>>> RISED TAP HANDLER");
                  
                  Element eventTargetElem = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
                  if (!PhgUtils.isReallyAttached(eventTargetElem.getId())) {
                    removeAllHandlers();
                    return;
                  }
                  for (TapHandler tapHandler : tapHandlers) {
                    tapHandler.onTap(new TapEvent(target, targetElement, 0, 0));
                  }
                }
              });
              
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
          removeEventListenerImpl(TAP_EVENT_NAME, jsEventListener);
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
  
  /*
  str = @it.mate.phgcommons.client.utils.JSONUtils::stringifyAvoidCircularRef(Lcom/google/gwt/core/client/JavaScriptObject;)(e.currentTarget);
  @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("RISED EVENT CURRENT TARGET " + str);
  str = @it.mate.phgcommons.client.utils.JSONUtils::stringifyAvoidCircularRef(Lcom/google/gwt/core/client/JavaScriptObject;)(e.target);
  @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("RISED EVENT TARGET " + str);
  */
  protected static native JavaScriptObject addEventListenerDocImpl (String elemId, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
//    e = event || e;
//    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("RISED EVENT > CURRENT TARGET " + e.currentTarget.tagName + " " + e.currentTarget.id + " " + e.currentTarget.innerHTML);
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("RISED EVENT > TARGET " + e.target.tagName + " " + e.target.id + " " + e.target.innerHTML);
      if (@it.mate.onscommons.client.event.TouchEventUtils::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener(eventName, jsEventListener, false);
//  $doc.addEventListener(eventName, jsEventListener, true);  // TEST And4.0
    return jsEventListener;
  }-*/;
  
  protected static native JavaScriptObject addEventListenerElemImpl (Element elem, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("RISED EVENT > TARGET " + e.target.tagName + " " + e.target.id + " " + e.target.innerHTML);
      if (@it.mate.onscommons.client.event.TouchEventUtils::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elem.id)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    elem.addEventListener(eventName, jsEventListener);    
    return jsEventListener;
  }-*/;

  protected static native void removeEventListenerImpl(String eventName, JavaScriptObject jsEventListener) /*-{
    $doc.removeEventListener(eventName, jsEventListener);    
  }-*/;
  

  /*
  protected static native JavaScriptObject addEventListenerOldImpl(String elemId, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      if (@it.mate.onscommons.client.ui.HasTapHandlerImpl::isContainedOld(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener("click", jsEventListener, false);    
    return jsEventListener;
  }-*/;

  private static native boolean isContainedOld(final Element elem, String containerId) /*-{
    do {
      if (elem.id == containerId) {
        return true;
      }
    } while(elem = elem.parentElement );
    return false;
  }-*/;
  
  
}
