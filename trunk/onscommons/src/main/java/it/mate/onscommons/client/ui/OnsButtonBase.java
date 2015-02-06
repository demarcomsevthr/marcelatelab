package it.mate.onscommons.client.ui;

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
    String innerHtml = getElement().getInnerHTML();
    innerHtml = innerHtml + text;
    getElement().setInnerHTML(innerHtml);
//  getElement().setInnerText(text);
    this.text = text;
  }
  
  public String getText() {
    return text;
  }

  public HandlerRegistration addTapHandler(final TapHandler handler) {
    return hasTapHandlerImpl.addTapHandler(handler);
  }
  
}
