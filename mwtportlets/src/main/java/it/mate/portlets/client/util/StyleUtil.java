package it.mate.portlets.client.util;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.user.client.ui.Widget;

public class StyleUtil {
  
  public static void applyStyle(Widget w, String style) {
    String[] properties = style.split(";");
    for (String property : properties) {
      String[] tokens = property.split(":");
      GwtUtils.setStyleAttribute(w, tokens[0], tokens[1]);
    }
  }

}
