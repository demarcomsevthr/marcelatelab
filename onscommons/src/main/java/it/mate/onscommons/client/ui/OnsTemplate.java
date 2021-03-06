package it.mate.onscommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class OnsTemplate extends ComplexPanel implements HasWidgets, AcceptsOneWidget {

  private String token;
  
  private boolean compiled = false;
  
  public OnsTemplate() {
    this("");
  }

  public OnsTemplate(String token) {
    this(DOM.createElement("ons-template"), token);
    this.token = token;
  }
  
  public String getToken() {
    return token;
  }
  
  public String getId() {
    return token;
  }
  
  public void setId(String id) {
    this.token = id;
  }
  
  protected OnsTemplate(Element elem, String id) {
    elem.setId(id);
    setElement(elem);
//  addStyleName("ons-Template");
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

  @Override
  public void setWidget(IsWidget w) {
    add(w);
  }
  
  public boolean isCompiled() {
    return compiled;
  }
  
  public void setCompiled(boolean compiled) {
    this.compiled = compiled;
  }

}
