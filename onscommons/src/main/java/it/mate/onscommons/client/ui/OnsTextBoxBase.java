package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.CdvUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.text.shared.testing.PassthroughParser;
import com.google.gwt.text.shared.testing.PassthroughRenderer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueBoxBase;

public abstract class OnsTextBoxBase extends /* Widget */ ValueBoxBase<String>{
  
  protected OnsTextBoxBase(String type) {
    this(DOM.createInputText(), type, "ons-textbox");
  }

  protected OnsTextBoxBase(Element element, String type, String className) {
    super(element, PassthroughRenderer.instance(), PassthroughParser.instance());
    CdvUtils.ensureId(element);
    element.setAttribute("type", type);
    if (className != null) {
      element.addClassName(className);
    }
//  setElement(element);
  }
  
  public void setPlaceholder(String placeholder) {
    getElement().setAttribute("placeholder", placeholder);
  }
  
  @Override
  public String getText() {
    String id = getElement().getId();
    String text = null;
    Element elem = DOM.getElementById(id);
    if (elem != null) {
      text = elem.getPropertyString("value");
    }
    if (text == null) {
      text = "";
    }
    return text;
  }
  
  @Override
  public void setText(final String text) {
    String id = getElement().getId();
//  Element elem = DOM.getElementById(id);
    Element elem = getElementByIdImpl(id);
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element elem) {
        elem.setPropertyString("value", text);
        OnsenUi.compileElement(elem);
      }
    });
    /*
    if (elem != null) {
      elem.setPropertyString("value", text);
      OnsenUi.compileElement(elem);
    }
    */
  }

  protected static native Element getElementByIdImpl(String elementId) /*-{
    return $doc.getElementById(elementId);
  }-*/;
  
}