package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;


public class OnsDateBox extends OnsTextBox {
  
  public OnsDateBox() {
    super("date");
    addStyleName("text-input");
  }
  
  public Date getValueAsDate() {
    String text = getText();
    Date result = GwtUtils.stringToDate(text, "yyyy-MM-dd");
    return result;
  }

}
