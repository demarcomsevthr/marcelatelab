package it.mate.gwtcommons.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;


/*********************************************************
 * 
 * SEE ALSO
 * 
 * http://api.jquery.com/
 * 
 *
 */


@SuppressWarnings("rawtypes")
public class JQuery extends JavaScriptObject {

  public static JQuery select (String selector) {
    return selectImpl(selector).cast();
  }
  
  public static Element selectFirstElement (String selector) {
    return select(selector).firstElement();
  }
  
  public static List<Element> selectElements (String selector) {
    return select(selector).toElements();
  }
  
  public static JQuery withElement(Element element) {
    return jQueryImpl(element).cast();
  }
  
  public static StyleProperties createStyleProperties() {
    return StyleProperties.create();
  }
  
  public static StyleProperties castToStyleProperties(Style style) {
    return style.cast();
  }
  
  public static Options createOptions() {
    return Options.create();
  }
  
  /* --------------------------------------------------------------------------------------------------- */
  
  public final Element toElement() {
    if (getLengthImpl() == 1) {
      return getImpl().cast();
    } else {
      return null;
    }
  }
  
  public final Element firstElement() {
    return toElements().get(0);
  }
  
  public final List<Element> toElements() {
    List<Element> results = new ArrayList<Element>();
    JsArray jsa = this.cast();
    for (int it = 0; it < jsa.length(); it++) {
      results.add((Element)jsa.get(it));
    }
    return results;
  }
  
  public final JQuery css(StyleProperties properties) {
    return cssImpl(properties);
  }
  
  public final JQuery animate(StyleProperties properties, int duration) {
    return animateImpl(properties, duration);
  }
  
  public final JQuery animate(StyleProperties properties, int duration, final Delegate<Void> delegate) {
    return animateImpl(properties, duration, new JQueryCallback() {
      public void execute() {
        delegate.execute(null);
      }
    });
  }
  
  public final JQuery animate(StyleProperties properties, Options options) {
    GwtUtils.log(options.toMyString());
    return animateImpl(properties, options);
  }
  
  public final JQuery slideToggle(int duration) {
    return slideToggleImpl(duration);
  }
  
  public final JQuery slideDown(int duration) {
    return slideDownImpl(duration);
  }
  
  public final JQuery fadeIn(int duration) {
    return fadeInImpl(duration);
  }
  
  public final JQuery fadeIn(Options options) {
    return fadeInImpl(options);
  }
  
  public final JQuery fadeOut(int duration) {
    return fadeOutImpl(duration);
  }
  
  public final JQuery hide(int duration) {
    return hideImpl(duration);
  }
  
  public final JQuery dequeue(String queueName) {
    return dequeueImpl(queueName);
  }
  
  
  
  /* --------------------------------------------------------------------------------------------------- */
  
  public static class StyleProperties extends Style {
    protected StyleProperties() { }
    private static StyleProperties create() {
      return JavaScriptObject.createObject().cast();
    }
  }
  
  public static class Options extends JavaScriptObject {
    protected Options() { }
    private static Options create() {
      return JavaScriptObject.createObject().cast();
    }
    public final Options setDuration(String duration) {
      GwtUtils.setPropertyImpl(this, "duration", duration);
      return this;
    }
    public final Options setEasing(String easing) {
      GwtUtils.setPropertyImpl(this, "easing", easing);
      return this;
    }
    public final Options setQueue(boolean queue) {
      GwtUtils.setPropertyImpl(this, "queue", queue);
      return this;
    }
    public final String toMyString() {
      return "Options [duration = "+GwtUtils.getPropertyImpl(this, "duration")+", queue = "+GwtUtils.getPropertyBoolImpl(this, "queue")+"]";
    }
  }
  
  /**
   * 
   * NOTA
   * 
   * NON USO direttamente Delegate perche' mi da problemi la signature con il generic
   *
   */
  
  private static interface JQueryCallback {
    public void execute();
  }
  
  /* --------------------------------------------------------------------------------------------------- */
  
  protected JQuery() {

  }
  
  private static native JavaScriptObject selectImpl(String selector) /*-{
    return $wnd.$(selector);
  }-*/;

  private static native JavaScriptObject jQueryImpl(Element element) /*-{
    return $wnd.jQuery(element);
  }-*/;

  private native JavaScriptObject getImpl() /*-{
    return this.get();
  }-*/;

  private native int getLengthImpl() /*-{
    return this.length;
  }-*/;

  private native JQuery cssImpl(StyleProperties properties) /*-{
    return this.css(properties);
  }-*/;
  
  private native JQuery animateImpl(StyleProperties properties, int duration) /*-{
    return this.animate(properties, duration);
  }-*/;
  
  private native JQuery animateImpl(StyleProperties properties, int duration, JQueryCallback callback) /*-{
    var jsCallback = $entry(function() {
      callback.@it.mate.gwtcommons.client.utils.JQuery.JQueryCallback::execute()();
    });
    return this.animate(properties, duration, "swing", jsCallback);
  }-*/;

  private native JQuery animateImpl(StyleProperties properties, Options options) /*-{
    return this.animate(properties, options);
  }-*/;

  private native JQuery slideToggleImpl(int duration) /*-{
    return this.slideToggle(duration);
  }-*/;
  
  private native JQuery slideDownImpl(int duration) /*-{
    return this.slideDown(duration);
  }-*/;
  
  private native JQuery fadeInImpl(int duration) /*-{
    return this.fadeIn(duration);
  }-*/;
  
  private native JQuery fadeInImpl(Options options) /*-{
    return this.fadeIn(options);
  }-*/;

  private native JQuery fadeOutImpl(int duration) /*-{
    return this.fadeOut(duration);
  }-*/;
  
  private native JQuery hideImpl(int duration) /*-{
    return this.hide(duration);
  }-*/;
  
  private native JQuery dequeueImpl(String queueName) /*-{
    return this.dequeue(queueName);
  }-*/;

}
