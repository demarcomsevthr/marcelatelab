package it.mate.onscommons.client.ui;

import com.google.gwt.user.client.DOM;


public class OnsButton extends OnsButtonBase {
  
  public OnsButton() {
    super("ons-button");
  }
  
  public OnsButton(String classname) {
    super(DOM.createElement("ons-button"), classname);
  }
  
  public void setIcon(String icon) {
    String iconHtml = "<ons-icon icon='"+icon+"'/>";
    String innerHtml = getElement().getInnerHTML();
    innerHtml = iconHtml + innerHtml;
    getElement().setInnerHTML(innerHtml);
  }

}
