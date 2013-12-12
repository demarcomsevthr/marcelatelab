package it.mate.phgcommons.client.ui;

import it.mate.phgcommons.client.utils.TouchUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Anchor;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class TouchAnchor extends TouchWidget implements HasClickHandlers {
  
  public TouchAnchor() {
    this("");
  }
  
  public TouchAnchor(String html) {
    this(SafeHtmlUtils.fromTrustedString(html));
  }
  
  public TouchAnchor(SafeHtml html) {
    this(new Anchor(html));
  }
  
  protected TouchAnchor(Anchor anchor) {
    setElement(anchor.getElement());
    addStyleName("phg-TouchAnchor");

    addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        TouchUtils.executePatch20131211();
      }
    });

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
        TouchUtils.fireClickEventFromTapEvent(TouchAnchor.this, event);
      }
    });
  }

}
