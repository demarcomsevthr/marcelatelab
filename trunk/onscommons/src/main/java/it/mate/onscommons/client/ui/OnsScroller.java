package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsScroller extends HTMLPanel {

  private final static String TAG_NAME = "ons-scroller";
  
  private boolean compiled = false;
  
  public OnsScroller() {
    this(TAG_NAME, "");
  }
  
  public OnsScroller(String html) {
    this(TAG_NAME, html);
  }
  
  protected OnsScroller(String tag, String html) {
    super(tag, html);
    getElement().addClassName(TAG_NAME);
    PhgUtils.ensureId(getElement());
  }

  @Override
  public void add(final Widget widget) {
    super.add(widget, getElement());
    String id = getElement().getId();
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element panelElement) {
        Element childElement = widget.getElement();
        panelElement.appendChild(childElement);
        if (!compiled) {
          compiled = true;
          OnsenUi.compileElement(panelElement);
        }
        OnsenUi.compileElement(childElement);
      }
    });
  }
  
  public void compile() {
    GwtUtils.onAvailable(getElement().getId(), new Delegate<Element>() {
      public void execute(Element panelElement) {
        OnsenUi.compileElement(panelElement);
      }
    });
  }

}
