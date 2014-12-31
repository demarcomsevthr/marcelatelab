package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class HasTapHandlerImpl {
  
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
      final Element element = ((Widget)target).getElement();
      CdvUtils.ensureId(element);
      jsEventListener = addEventListenerImpl(element.getId(), new JSOCallback() {
        public void handle(JavaScriptObject jsEvent) {
          Element target = GwtUtils.getJsPropertyJso(jsEvent, "target").cast();
//        CdvUtils.log(">>>>>>>>>>>>>>>>>>> CLICK EVENT LISTENER ON ELEMENT (" + element.getId() + ") " + CdvUtils.elementToString(target));
          
          if (!CdvUtils.isReallyAttached(element.getId())) {
//          CdvUtils.log(">>>>>>>>>>>>>>>>>>> NOT REALY ATTACHED!");
            removeAllHandlers();
            return;
          }
          
          for (TapHandler tapHandler : tapHandlers) {
            tapHandler.onTap(new TapEvent(target, element, 0, 0));
          }
        }
      });
    }
    HandlerRegistration registration = new HandlerRegistration() {
      public void removeHandler() {
        tapHandlers.remove(handler);
        if (tapHandlers.size() == 0) {
          CdvUtils.log("REMOVING EVENT LISTENER ON " + target);
          removeEventListenerImpl(jsEventListener);
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
  
  protected static native JavaScriptObject addEventListenerImpl(String elemId, JSOCallback callback) /*-{
    var jsEventListener = $entry(function(e) {
      if (@it.mate.onscommons.client.ui.HasTapHandlerImpl::isContained(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(e.target, elemId)) {
        callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    });
    $doc.addEventListener("click", jsEventListener, false);    
    return jsEventListener;
  }-*/;
  
  protected static native void removeEventListenerImpl(JavaScriptObject jsEventListener) /*-{
    $doc.removeEventListener("click", jsEventListener);    
  }-*/;

  private static native boolean isContained(final Element elem, String containerId) /*-{
    do {
      if (elem.id == containerId) {
        return true;
      }
    } while(elem = elem.parentElement );
    return false;
  }-*/;
  
}
