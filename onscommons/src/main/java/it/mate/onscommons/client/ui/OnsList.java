package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.dom.client.Element;
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
    PhgUtils.ensureId(getElement());
  }

  @Override
  public void add(final Widget widget) {
    super.add(widget, getElement());
    String id = getElement().getId();
    
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element listElem) {
        Element itemElem = widget.getElement();
        listElem.appendChild(itemElem);
        OnsenUi.compileElement(listElem);
        OnsenUi.compileElement(itemElem);
      }
    });
    
    /*
    Element listElem = DOM.getElementById(id);
    Element itemElem = widget.getElement();
    listElem.appendChild(itemElem);
    OnsenUi.compileElement(listElem);
    */
  }

}
