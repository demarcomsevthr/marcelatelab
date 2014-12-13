package it.mate.protoph.client.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class SortableUtils {

  public static JavaScriptObject makeSortable(Element element) {
    return makeSortable(element, null);
  }
  
  public static JavaScriptObject makeSortable(Element element, String group) {
    return makeSortable(element, group, -1);
  }
  
  public static JavaScriptObject makeSortable(Element element, String group, int animation) {
    return makeSortable(element, group, animation, null);
  }
  
  public static JavaScriptObject makeSortable(Element element, String group, int animation, String handle) {
    return jsMakeSortableImpl(element, group, animation, handle);
  }
  
  private static native JavaScriptObject jsMakeSortableImpl(Element element, String group, int animation, String handle) /*-{
    var options = {};
    if (group != null) {
      options.group = group; 
    }
    if (animation > 0) {
      options['animation'] = animation; 
    }
    if (handle != null) {
      options['handle'] = handle; 
    }
    var opt = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(options);
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('creating sortable with options ' + opt);
    var sortable = new $wnd.Sortable(element, options);
    return sortable;
  }-*/;
  
}
