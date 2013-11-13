package it.mate.stickmail.client.ui;

import it.mate.gwtcommons.client.utils.JQuery;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.TextBox;

public class JQDateBox extends TextBox {
  
  private Map<String, String> dataOptionsMap = new HashMap<String, String>();

  public JQDateBox() {
    super();
    getElement().addClassName("gwt-JQDateBox ui-input-text ui-body-d ui-corner-all ui-icon-datebox");
    getElement().setAttribute("data-role", "datebox");
    initDataOptions();
    applyElementDataOptions();
    doDateboxImpl(JQuery.withElement(getElement()));
  }
  
  protected void initDataOptions() {
    setDataOption("use-new-style", "true");
    setDataOption("mode", "calbox");
  }
  
  protected void setDataOption(String name, String value) {
    dataOptionsMap.put(name, value);
  }
  
  private void applyElementDataOptions() {
    for (String optName : dataOptionsMap.keySet()) {
      String optValue = dataOptionsMap.get(optName);
      getElement().setAttribute("data-datebox-" + optName, optValue);
    }
  }
  
  private native JQuery doDateboxImpl(JQuery jq) /*-{
    return jq.datebox();
  }-*/;
  
}
