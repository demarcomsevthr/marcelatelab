package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class OnsToolbar extends ComplexPanel implements HasWidgets {

  public OnsToolbar() {
    this(createElement(), null);
  }
  
  public OnsToolbar(String html) {
    this(createElement(), html);
  }
  
  public OnsToolbar(SafeHtml html) {
    this(createElement(), html.asString());
  }
  
  private static Element createElement() {
    return DOM.createElement("ons-toolbar");
  }
  
  protected OnsToolbar(Element elem, String html) {
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
