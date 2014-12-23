package it.mate.onscommons.client.ui;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsPage extends HTMLPanel {

  private final static String TAG_NAME = "ons-page";
  
  public OnsPage() {
    super(TAG_NAME, "");
  }
  
  public OnsPage(String html) {
    super(TAG_NAME, html);
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
