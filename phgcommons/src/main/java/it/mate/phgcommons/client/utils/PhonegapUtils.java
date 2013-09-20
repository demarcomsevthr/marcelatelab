package it.mate.phgcommons.client.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class PhonegapUtils {
  
  public static void openInAppBrowser(String url) {
    openInAppBrowserImpl(url);
  }
  
  private static native void openInAppBrowserImpl(String url) /*-{
    var inAppBrowser = $wnd.open(url, '_blank', 'location=no');
  }-*/;
  
  public static native Navigator getNavigator() /*-{
    return $wnd.navigator;
  }-*/;
  
  public static native String getLocalStorageItem(String name) /*-{
    return $wnd.getLocalStorageItem(name);
  }-*/;
  
  public static native void setLocalStorageItem(String name, String value) /*-{
    $wnd.setLocalStorageItem(name, value);
  }-*/;

  public static class Navigator extends JavaScriptObject {
    protected Navigator() {

    }
    public final native String getLanguage() /*-{
      return this.language;
    }-*/;
  }
  

}
