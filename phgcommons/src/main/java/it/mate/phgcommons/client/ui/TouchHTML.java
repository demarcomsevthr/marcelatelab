package it.mate.phgcommons.client.ui;

import it.mate.phgcommons.client.utils.TouchUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
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
     */
    
    
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
