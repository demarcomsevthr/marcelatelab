package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public abstract class OnsButtonBase extends Widget implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  private String text;
  
  protected OnsButtonBase(String tagName) {
    this(DOM.createElement(tagName), tagName);
  }
  
  protected OnsButtonBase(Element element, String className) {
    if (className != null) {
      element.addClassName(className);
    }
    setElement(element);
    PhgUtils.ensureId(element);
    hasTapHandlerImpl = new HasTapHandlerImpl(this);
  }
  
  public void setText(String text) {
    Element element = GwtUtils.getElementById(getElement().getId());
    if (element == null) {
      String innerHtml = getElement().getInnerHTML();
      innerHtml = innerHtml + text;
      getElement().setInnerHTML(innerHtml);
      this.text = text;
    } else {
      element.setInnerHTML(text);
      this.text = text;
    }
  }
  
  public String getText() {
    return text;
  }

  public HandlerRegistration addTapHandler(final TapHandler handler) {
    return hasTapHandlerImpl.addTapHandler(handler);
  }
  
}
