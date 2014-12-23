package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class OnsPage extends ComplexPanel implements HasWidgets {

  public OnsPage() {
    this(DOM.createElement("ons-page"));
  }
  
  protected OnsPage(Element elem) {
//  elem.setId(id);
    setElement(elem);
//  addStyleName("ons-Template");
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
