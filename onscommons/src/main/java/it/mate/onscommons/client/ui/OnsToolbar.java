package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsToolbar extends HTMLPanel {

  private final static String TAG_NAME = "ons-toolbar";
  
  private static boolean waitingButtonVisible = false;
  
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

        
        /*
        Element waitingIco = DOM.createElement("ons-icon");
        waitingIco.addClassName("ons-toolbar-button-waiting-icon");
        waitingIco.setAttribute("icon", "fa-cog");
        waitingIco.setAttribute("spin", "true");
        */
        
        /*
        Element waitingIco = DOM.createElement("i");
        waitingIco.addClassName("fa fa-circle-o-notch fa-spin ons-toolbar-button-waiting-icon");
//      waitingIco.addClassName("fa fa-cog fa-spin ons-toolbar-button-waiting-icon");
         */
        
        
        Element waitingIco = DOM.createElement("ons-icon");
        waitingIco.addClassName("ons-toolbar-button-waiting-icon");
//      waitingIco.setAttribute("icon", "ion-refresh");
        waitingIco.setAttribute("icon", "fa-circle-o-notch");
        waitingIco.setAttribute("spin", "true");
        
        
        waitingBtn.appendChild(waitingIco);
        waitingDiv.appendChild(waitingBtn);
        toolbarElement.appendChild(waitingDiv);
      }
    });
  }
  
  public static void setWaitingButtonVisible(boolean visible) {
    waitingButtonVisible = visible;
    setWaitingButtonVisibleImpl(visible ? "1" : "0");
  }
  
  public static boolean isWaitingButtonVisible() {
    return waitingButtonVisible;
  }
  
  private static native void setWaitingButtonVisibleImpl (String opacity) /*-{
    if (typeof $wnd._onsToolbarButtonWaiting != 'undefined') {
      $wnd._onsToolbarButtonWaiting._element[0].style.opacity = opacity;
    }
  }-*/;

}
