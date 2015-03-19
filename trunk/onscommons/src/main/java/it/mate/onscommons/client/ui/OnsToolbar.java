package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
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
    createWaitingIcon();
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }
  
  private void createWaitingIcon() {
    OnsenUi.onAttachedElement(this, new Delegate<Element>() {
      public void execute(Element toolbarElement) {
        Element waitingDiv = DOM.createDiv();
        waitingDiv.setClassName("right");
        Element waitingBtn = DOM.createElement("ons-toolbar-button");
        waitingBtn.setClassName("ons-toolbar-button-waiting");
        waitingBtn.setAttribute("disabled", "");
        waitingBtn.setAttribute("var", "_onsToolbarButtonWaiting");
        waitingBtn.getStyle().setOpacity(0);
        Element waitingIco = DOM.createElement("ons-icon");
        waitingIco.setAttribute("icon", "fa-cog");
        waitingIco.setAttribute("spin", "true");
        waitingBtn.appendChild(waitingIco);
        waitingDiv.appendChild(waitingBtn);
        toolbarElement.appendChild(waitingDiv);
      }
    });
  }
  
  public static void setWaitingButtonVisible(boolean visible) {
    setWaitingButtonVisibleImpl(visible ? "1" : "0");
  }
  
  private static native void setWaitingButtonVisibleImpl (String opacity) /*-{
    $wnd._onsToolbarButtonWaiting._element[0].style.opacity = opacity;
  }-*/;

}
