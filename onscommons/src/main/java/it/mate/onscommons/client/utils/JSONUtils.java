package it.mate.onscommons.client.utils;

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
  
  public static String stringify(JavaScriptObject jso) {
    ensureStringify();
    return stringifyImpl(jso);
  }
  
  public static JavaScriptObject parse(String json) {
    ensureStringify();
    return parseImpl(json);
  }

  protected static native String stringifyImpl(JavaScriptObject jso) /*-{
    return JSON.stringify(jso);
  }-*/;
  
  protected static native JavaScriptObject parseImpl(String json) /*-{
    return JSON.parse(json);
  }-*/;

}
