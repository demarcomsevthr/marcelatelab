package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class Options extends JavaScriptObject {
  protected Options() { }
  public static Options create() {
    return JavaScriptObject.createObject().cast();
  }
  public final void setHoge(String hoge) {
    GwtUtils.setJsPropertyString(this, "hoge", hoge);
  }
}
