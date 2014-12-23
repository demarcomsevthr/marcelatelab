package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class OnsToolbar_BUONO extends ComplexPanel implements HasWidgets {

  public OnsToolbar_BUONO() {
    this(createElement(), null);
  }
  
  public OnsToolbar_BUONO(String html) {
    this(createElement(), html);
  }
  
  public OnsToolbar_BUONO(SafeHtml html) {
    this(createElement(), html.asString());
  }
  
  private static Element createElement() {
    return DOM.createElement("ons-toolbar");
  }
  
  protected OnsToolbar_BUONO(Element elem, String html) {
//  elem.setId(id);
    if (html != null) {
      elem.setInnerHTML(html);
    }
    setElement(elem);
//  addStyleName("ons-Template");
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
