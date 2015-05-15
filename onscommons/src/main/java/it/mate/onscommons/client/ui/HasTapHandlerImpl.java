package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
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
  
  private HasTapHandler targetWidget;
  
  private Element targetElement;
  
  private static boolean allHandlersDisabled = false;
  
  private static boolean useDocEventListener = false;
  
  public HasTapHandlerImpl(HasTapHandler target) {
    this.targetWidget = target;
    this.targetElement = ((Widget)targetWidget).getElement();
  }
  
  public HasTapHandlerImpl(Element target) {
    this.targetElement = target;
  }
  
  private boolean isTargetWidgetChildOfDialog() {
    if (targetWidget == null) {
      return false;
    } else {
      return isChildOfDialog(((Widget)targetWidget).getParent());
    }
  }
  
  private boolean isChildOfDialog(Widget targetWidget) {
    if (targetWidget == null) {
      return false;
    }
    if (targetWidget instanceof OnsDialog) {
      return true;
    }
    return isChildOfDialog(targetWidget.getParent());
  }
  
  public static void setUseDocEventListener(boolean useDocEventListener) {
    HasTapHandlerImpl.useDocEventListener = useDocEventListener;
  }
  
  private String getTargetElementLog() {
//  Element targetElement = ((Widget)targetWidget).getElement();
    return targetElement.getNodeName().toLowerCase() + " " + targetElement.getId();
  }
  
  public HandlerRegistration addTapHandler(final TapHandler handler) {
    
//  Element targetElement = ((Widget)targetWidget).getElement();
    OnsenUi.ensureId(targetElement);
    
    this.tapHandlers.add(handler);
    
    if (jsEventListener == null) {
      
      // 03/02/2015
      GwtUtils.onAvailable(targetElement.getId(), new Delegate<Element>() {
        public void execute(/* final */ Element availableElement) {
          
          // 04/02/2015
          boolean isInDialog = isTargetWidgetChildOfDialog();
          
          if (isInDialog || useDocEventListener) {
            jsEventListener = addEventListenerDocImpl(availableElement.getId(), EVENT_NAME, onTapCallback(availableElement));
          } else {
            jsEventListener = addEventListenerElemImpl(availableElement, EVENT_NAME, onTapCallback(availableElement));
          }
          
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
  
  private JSOCallback onTapCallback(final Element availableElement) {
    return new JSOCallback() {
      public void handle(JavaScriptObject jsEvent) {
        Element eventElement = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
        if (!PhgUtils.isReallyAttached(availableElement.getId())) {
          removeAllHandlers();
          return;
        }
        if (allHandlersDisabled) {
          PhgUtils.log("ALL HANDLERS DISABLED");
          return;
        }
        for (TapHandler tapHandler : tapHandlers) {
          tapHandler.onTap(new TapEvent(eventElement, availableElement, 0, 0, (targetWidget != null ? (Widget)targetWidget : null)));
        }
      }
    };
  }
  
  public void removeAllHandlers() {
    for (HandlerRegistration reg : tapHandlerRegistrations) {
      reg.removeHandler();
    }
  }
  
  protected static native JavaScriptObject addEventListenerDocImpl (String elemId, String eventName, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
//    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("FIRED EVENT ON TARGET " + e.target.tagName + " " + e.target.id);
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
  
  public static void setAllHandlersDisabled(boolean allHandlersDisabled) {
    if (allHandlersDisabled) {
      PhgUtils.log("SETTING ALL TAP HANDLERS DISABLED");
    } else {
      PhgUtils.log("SETTING ALL TAP HANDLERS ENABLED");
    }
    HasTapHandlerImpl.allHandlersDisabled = allHandlersDisabled;
  }
  
}
