package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.CdvUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsList extends HTMLPanel {

  private final static String TAG_NAME = "ons-list";
  
  public OnsList() {
    this(TAG_NAME, "");
  }
  
  public OnsList(String html) {
    this(TAG_NAME, html);
  }
  
  protected OnsList(String tag, String html) {
    super(tag, html);
    getElement().addClassName(TAG_NAME);
    CdvUtils.ensureId(getElement());
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
    String id = getElement().getId();
    Element listElem = DOM.getElementById(id);
    Element itemElem = widget.getElement();
    listElem.appendChild(itemElem);
    OnsenUi.compileElement(listElem);
  }

}
