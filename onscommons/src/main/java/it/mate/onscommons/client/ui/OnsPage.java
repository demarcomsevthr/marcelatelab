package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class OnsPage extends HTMLPanel implements AcceptsOneWidget {

  private final static String TAG_NAME = "ons-page";
  
  private static OnsPage lastCreatedPage;
  
  public OnsPage() {
    super(TAG_NAME, "");
    OnsenUi.ensureId(getElement());
    lastCreatedPage = this;
  }
  
  public OnsPage(String html) {
    super(TAG_NAME, html);
    OnsenUi.ensureId(getElement());
    lastCreatedPage = this;
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
  
  public static OnsPage getLastCreatedPage() {
    return lastCreatedPage;
  }

}
