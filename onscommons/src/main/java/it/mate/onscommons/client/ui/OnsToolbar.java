package it.mate.onscommons.client.ui;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsToolbar extends HTMLPanel {

  private final static String TAG_NAME = "ons-toolbar";
  
  public OnsToolbar() {
    this(TAG_NAME, "");
  }
  
  public OnsToolbar(String html) {
    this(TAG_NAME, html);
  }
  
  protected OnsToolbar(String tag, String html) {
    super(tag, html);
    getElement().addClassName("ons-toolbar");
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

}
