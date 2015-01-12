package it.mate.onscommons.client.ui;


public class OnsButton extends OnsButtonBase {
  
  public OnsButton() {
    super("ons-button");
  }
  
  public void setIcon(String icon) {
    String iconHtml = "<ons-icon icon='"+icon+"'/>";
    String innerHtml = getElement().getInnerHTML();
    innerHtml = iconHtml + innerHtml;
    getElement().setInnerHTML(innerHtml);
  }

}
