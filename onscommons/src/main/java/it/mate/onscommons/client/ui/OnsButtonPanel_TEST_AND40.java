package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;


public class OnsButtonPanel_TEST_AND40 extends /* HTMLPanel */ ComplexPanel implements HasTapHandler, HasHTML {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  public OnsButtonPanel_TEST_AND40() {
    this("");
  }

  public OnsButtonPanel_TEST_AND40(String html) {
//  super("ons-button", "");
    Element element = DOM.createElement("ons-button");
    setElement(element);
    getElement().setInnerHTML(html);
    getElement().addClassName("ons-button");
    OnsenUi.ensureId(getElement());
    hasTapHandlerImpl = new HasTapHandlerImpl(this);
  }
  
  public OnsButtonPanel_TEST_AND40(SafeHtml safeHtml) {
    this(safeHtml.asString());
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

  @Override
  public HandlerRegistration addTapHandler(TapHandler handler) {
    return hasTapHandlerImpl.addTapHandler(handler);
  }

  @Override
  public String getText() {
    return getElement().getInnerText();
  }

  @Override
  public void setText(String text) {
    getElement().setInnerText(text);
  }

  @Override
  public String getHTML() {
    return getElement().getInnerHTML();
  }

  @Override
  public void setHTML(String html) {
    getElement().setInnerHTML(html);
  }
  
}
