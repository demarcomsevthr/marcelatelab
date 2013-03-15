package it.mate.gwtcommons.client.utils;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;

public class JQueryUtils {
  
  public static JsArray<Element> select(String selector) {
    JsArray<Element> elements = selectImpl(selector);
    if (elements != null && elements.length() == 0) {
      elements = null;
    }
    return elements;
  }
  
  @SuppressWarnings("rawtypes")
  public static Element selectFirst(String selector) {
    Object results = selectImpl(selector);
    if (results != null && results instanceof JsArray) {
      JsArray jsArray = (JsArray)results;
      if (jsArray.length() > 0) {
        Element firstElement = (Element)jsArray.get(0);
        return firstElement;
      }
    }
    return null;
  }
  
  private static native JsArray<Element> selectImpl(String selector) /*-{
    return $wnd.$(selector);
  }-*/;

}
