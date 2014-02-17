package it.mate.phgcommons.client.utils;

import com.google.gwt.user.client.Element;
import com.googlecode.mgwt.ui.client.MGWT;

public class WebkitCssUtil {

  private static boolean has3d = false /* _has3d() */;

  public static void translatePct(Element el, int x, int y) {
    translate(el, x, y, "%");
  }

  public static void translatePx(Element el, int x, int y) {
    translate(el, x, y, "px");
  }

  private static void translate(Element el, int x, int y, String unit) {
    String cssText = null;
    if (has3d && !MGWT.getOsDetection().isDesktop()) {
      cssText = "translate3d(" + x+unit + ", " + y+unit + ", 0"+unit+")";
    } else {
      cssText = "translate( " + x+unit + ", " + y+unit + " )";
    }
    _translate(el, cssText);
  }

  private static native void _translate(Element el, String css)/*-{
    el.style.webkitTransform = css;
  }-*/;
  
  public static native void resetTransform(Element el) /*-{
    el.style.webkitTransform = "";
  }-*/;

  private static native boolean _has3d()/*-{
    return ('WebKitCSSMatrix' in $wnd && 'm11' in new WebKitCSSMatrix())
  }-*/;

}
