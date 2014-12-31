package it.mate.onscommons.client.ui;

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
    String text = DOM.getElementById(id).getPropertyString("value");
    return text;
  }

}
