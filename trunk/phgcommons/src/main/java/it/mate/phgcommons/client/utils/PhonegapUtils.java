package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;

public class PhonegapUtils {
  
  public static void logEnvironment() {
    GwtUtils.log("os detection = " + (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other"));
    String layoutInfo = "Width " + Window.getClientWidth();
    layoutInfo += " Height " + Window.getClientHeight();
    if (OsDetectionUtils.isTabletLandscape()) {
      layoutInfo += " isTabletLandscape";
    } else if (OsDetectionUtils.isTabletPortrait()) {
      layoutInfo += " isTabletPortrait";
    } else {
      layoutInfo += " isPhone";
    }
    GwtUtils.log("Current layout = " + layoutInfo);
    if (OsDetectionUtils.isIOs()) {
      GwtUtils.log("IOs phone layout is " + OsDetectionUtils.IPHONE_WIDTH + " x " + OsDetectionUtils.IPHONE_HEIGHT);
      GwtUtils.log("IOs landscape layout is " + OsDetectionUtils.IPAD_LAND_WIDTH + " x " + OsDetectionUtils.IPAD_LAND_HEIGHT);
      GwtUtils.log("IOs portrait  layout is " + OsDetectionUtils.IPAD_PORT_WIDTH + " x " + OsDetectionUtils.IPAD_PORT_HEIGHT);
    } else {
      GwtUtils.log("Android landscape layout is " + OsDetectionUtils.APAD_LAND_WIDTH + " x " + OsDetectionUtils.APAD_LAND_HEIGHT);
      GwtUtils.log("Android portrait  layout is " + OsDetectionUtils.APAD_PORT_WIDTH + " x " + OsDetectionUtils.APAD_PORT_HEIGHT);
    }
    GwtUtils.log("(see "+ OsDetectionUtils.class.getName() +" for details)");
  }
  
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
