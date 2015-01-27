package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class OnsPage extends HTMLPanel implements AcceptsOneWidget {

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
    if (widget.getElement().getNodeName().toLowerCase().startsWith("ons")) {
      OnsenUi.compileElement(widget.getElement());
    }
  }

  @Override
  public void setWidget(IsWidget w) {
    add(w);
  }

}
