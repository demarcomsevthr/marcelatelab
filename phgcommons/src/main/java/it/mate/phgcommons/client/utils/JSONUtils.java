package it.mate.phgcommons.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;

public class JSONUtils {
  
  public static void ensureStringify() {
    if (isAbsentStringify()) {
      String source = GWT.getModuleBaseURL()+"js/json2.js";
      ScriptElement scriptElem = Document.get().createScriptElement();
      scriptElem.setSrc(source);
      scriptElem.setType("text/javascript");
      Element head = Document.get().getElementsByTagName("head").getItem(0);
      head.appendChild(scriptElem);
    }
  }
  
  private static native boolean isAbsentStringify () /*-{
    return (typeof JSON == "undefined");
  }-*/;

  public static native String stringify(JavaScriptObject jso) /*-{
    return JSON.stringify(jso);
  }-*/;
  
}
