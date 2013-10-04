package it.mate.gwtcommons.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;

public class JQueryUtils {
  
  public static JsArray<Element> select(String selector) {
    JsArray<Element> elements = selectImpl(selector);
    if (elements != null && elements.length() == 0) {
      elements = null;
    }
    return elements;
  }
  
  public static Element selectElement(String selector) {
    return selectFirst(selector);
  }
  
  public static List<Element> selectList(String selector) {
    List<Element> result = new ArrayList<Element>();
    JsArray<Element> array = select(selector);
    for (int it = 0; it < array.length(); it++) {
      result.add(array.get(it));
    }
    return result;
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
  
  
  
  /***************************************************************************/
  
  public static JQueryObject castElement(Element element) {
    return JQueryObject.cast(element);
  }
  
  public static StyleProperties createStyleProperties() {
    return StyleProperties.create();
  }
  
  public static JQueryObject css(JQueryObject obj, StyleProperties properties) {
    return cssImpl(obj, properties);
  }
  
  public static JQueryObject animate(JQueryObject obj, StyleProperties properties, int duration) {
    return animateImpl(obj, properties, duration);
  }
  
  public static JQueryObject slideToggle(JQueryObject obj, int duration) {
    return slideToggleImpl(obj, duration);
  }
  
  public static JQueryObject slideDown(JQueryObject obj, int duration) {
    return slideDownImpl(obj, duration);
  }
  
  public static JQueryObject fadeIn(JQueryObject obj, int duration) {
    return fadeInImpl(obj, duration);
  }
  
  public static JQueryObject fadeOut(JQueryObject obj, int duration) {
    return fadeOutImpl(obj, duration);
  }
  
  public static JQueryObject hide(JQueryObject obj, int duration) {
    return hideImpl(obj, duration);
  }
  
  
  /***************************************************************************/
  
  public static class JQueryObject extends JavaScriptObject {
    protected JQueryObject() { }
    private static JQueryObject cast(Element element) {
      return elementToJQueryImpl(element).cast();
    }
  }
  
  public static class StyleProperties extends Style {
    protected StyleProperties() { }
    private static StyleProperties create() {
      return JavaScriptObject.createObject().cast();
    }
  }
  
  /***************************************************************************/
  
  private static native JsArray<Element> selectImpl(String selector) /*-{
    return $wnd.$(selector);
  }-*/;

  private static native JQueryObject elementToJQueryImpl(Element element) /*-{
    return $wnd.jQuery(element);
  }-*/;

  private static native JQueryObject cssImpl(JQueryObject jQueryObject, StyleProperties properties) /*-{
    return jQueryObject.css(properties);
  }-*/;

  private static native JQueryObject animateImpl(JQueryObject jQueryObject, StyleProperties properties, int duration) /*-{
    return jQueryObject.animate(properties, duration);
  }-*/;

  private static native JQueryObject slideToggleImpl(JQueryObject jQueryObject, int duration) /*-{
    return jQueryObject.slideToggle(duration);
  }-*/;

  private static native JQueryObject slideDownImpl(JQueryObject jQueryObject, int duration) /*-{
    return jQueryObject.slideDown(duration);
  }-*/;

  private static native JQueryObject fadeInImpl(JQueryObject jQueryObject, int duration) /*-{
    return jQueryObject.fadeIn(duration);
  }-*/;

  private static native JQueryObject fadeOutImpl(JQueryObject jQueryObject, int duration) /*-{
    return jQueryObject.fadeOut(duration);
  }-*/;

  private static native JQueryObject hideImpl(JQueryObject jQueryObject, int duration) /*-{
    return jQueryObject.hide(duration);
  }-*/;

}
