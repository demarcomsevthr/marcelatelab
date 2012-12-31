package it.mate.gwtcommons.client.utils;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;

public class JQueryUtils {
  
  public static native JsArray<Element> select(String selector) /*-{
    return $wnd.$(selector);
  }-*/;

}
