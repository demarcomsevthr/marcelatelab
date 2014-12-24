package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class OnsBackButton extends Widget {
  
  public OnsBackButton() {
    this(DOM.createElement("ons-back-button"));
  }

  protected OnsBackButton(Element element) {
    if (element.getId() == null || "".equals(element.getId())) {
      element.setId(DOM.createUniqueId());
    }
    element.addClassName("ons-back-button");
    setElement(element);
  }
  
  public void setText(String text) {
    getElement().setInnerText(text);
  }
  
}
