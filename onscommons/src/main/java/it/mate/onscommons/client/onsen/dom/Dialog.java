package it.mate.onscommons.client.onsen.dom;

import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class Dialog extends JavaScriptObject {

  protected Dialog() { }
  
  public final void hideDialog() {
    PhgUtils.log("hiding dialog");
    hideImpl();
  }
  
  protected final native void hideImpl() /*-{
    this.hide();
  }-*/;
  
}
