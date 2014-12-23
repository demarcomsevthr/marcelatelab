package it.mate.onscommons.client.ui;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsToolbar extends HTMLPanel {

  private final static String TAG_NAME = "ons-toolbar";
  
  public OnsToolbar() {
    super(TAG_NAME, "");
  }
  
  public OnsToolbar(String html) {
    super(TAG_NAME, html);
  }
  
  public OnsToolbar(SafeHtml html) {
    super(TAG_NAME, html.asString());
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
