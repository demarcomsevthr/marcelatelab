package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class OnsNavigator extends ComplexPanel implements HasWidgets {

  public OnsNavigator() {
    this(DOM.createElement("ons-navigator"));
  }
  
  protected OnsNavigator(Element elem) {
    setElement(elem);
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
