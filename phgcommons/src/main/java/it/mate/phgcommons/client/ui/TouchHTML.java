package it.mate.phgcommons.client.ui;

import it.mate.phgcommons.client.utils.TouchUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class TouchHTML extends TouchWidget implements HasClickHandlers {
  
  String active = "phg-button-active";

  HandlerRegistration nativePreviewHandlerRegistration;
  
  public TouchHTML() {
    this("");
  }
  
  public TouchHTML(String html) {
    this(SafeHtmlUtils.fromTrustedString(html));
  }
  
  public TouchHTML(SafeHtml html) {
    this(new HTML(html));
  }
  
  protected TouchHTML(HTML html) {
    setElement(html.getElement());
    addStyleName("phg-TouchHTML");

    /******************************************
     * copiato da mgvt.ButtonBase:
     */
    addTouchHandler(new TouchHandler() {

      @Override
      public void onTouchCanceled(TouchCancelEvent event) {
        event.stopPropagation();
        removeStyleName(active);
        if (MGWT.getOsDetection().isDesktop()) {
          DOM.releaseCapture(getElement());
        }
      }

      @Override
      public void onTouchEnd(TouchEndEvent event) {
        event.stopPropagation();
        removeStyleName(active);
        if (MGWT.getOsDetection().isDesktop()) {
          DOM.releaseCapture(getElement());
        }
      }

      @Override
      public void onTouchMove(TouchMoveEvent event) {
        event.preventDefault();
        event.stopPropagation();
      }

      @Override
      public void onTouchStart(TouchStartEvent event) {
        event.stopPropagation();
        addStyleName(active);
        if (MGWT.getOsDetection().isDesktop()) {
          DOM.setCapture(getElement());
        }

      }
    });

    addTapHandler(new TapHandler() {

      @Override
      public void onTap(TapEvent event) {
        removeStyleName(active);

      }
    });
    
    
    /* 
     * 
     * TODO
     * 
     * see com.google.gwt.user.client.ui.PopupPanel.previewNativeEvent(NativePreviewEvent)
     * 
     */
    
    nativePreviewHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
      public void onPreviewNativeEvent(NativePreviewEvent event) {
        previewNativeEvent(event);
      }
    });
    
  }
  
  private void previewNativeEvent(NativePreviewEvent event) {
    // If the event has been canceled or consumed, ignore it
    if (event.isCanceled() || event.isConsumed()) {
      // We need to ensure that we cancel the event even if its been consumed so
      // that popups lower on the stack do not auto hide
      event.cancel();
      return;
    }
    
    // If the event targets the popup or the partner, consume it
    Event nativeEvent = Event.as(event.getNativeEvent());
    
    boolean eventTargetsThis = false;
    EventTarget target = nativeEvent.getEventTarget();
    if (Element.is(target)) {
      eventTargetsThis = getElement().isOrHasChild(Element.as(target));
    }
    
    event.consume();
    
    if (eventTargetsThis) {
      event.cancel();
    }
    
  }
  
  
  public void setHtml(SafeHtml html) {
    Element elem = getElement();
    elem.setInnerSafeHtml(html);
  }
  
  public void setText(String text) {
    setHtml(SafeHtmlUtils.fromTrustedString(text));
  }

  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        TouchUtils.fireClickEventFromTapEvent(TouchHTML.this, event);
      }
    });
  }
  
}
