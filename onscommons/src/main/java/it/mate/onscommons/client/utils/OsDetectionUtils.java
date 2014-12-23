package it.mate.onscommons.client.utils;

import com.google.gwt.user.client.Window;

public class OsDetectionUtils {
  
  public final static int IPHONE_WIDTH = 320;
  public final static int IPHONE_HEIGHT = 480;
  public final static int IPHONE_3INCH_HEIGHT = 480;
  public final static int IPHONE_4INCH_HEIGHT = 568;
  public final static int IPAD_LAND_WIDTH = 1024;
  public final static int IPAD_LAND_HEIGHT = 748;
  public final static int IPAD_PORT_WIDTH = 768;
  public final static int IPAD_PORT_HEIGHT = 1004;
  
  public static int IOS_HEADER_PANEL_HEIGHT = 40;
  public static int IOS_MARGIN_TOP = 18;
  
  public final static int APAD_PORT_WIDTH = 526;
  public final static int APAD_PORT_HEIGHT = 951;
  public final static int APAD_LAND_WIDTH = APAD_PORT_HEIGHT;
  public final static int APAD_LAND_HEIGHT = APAD_PORT_WIDTH;
  
  private static int getDisplayHeight() {
    return Window.getClientHeight();
  }
  
  private static int getDisplayWidth() {
    return Window.getClientWidth();
  }
  
  public static boolean isPhone() {
    return !isTablet();
  }
  
  public static boolean isTablet() {
    return (isTabletLandscape() || isTabletPortrait());
  }
  
  public static boolean isIOs() {
    return false;
  }

  public static boolean isAndroid() {
    return false;
  }
  
  public static boolean is3Inch() {
    int dh = getDisplayHeight();
    if (isIOs() && dh == IPHONE_3INCH_HEIGHT) {
      return true;
    }
    return false;
  }

  public static boolean is4Inch() {
    int dh = getDisplayHeight();
    if (isIOs() && dh == IPHONE_4INCH_HEIGHT) {
      return true;
    }
    return false;
  }

  public static boolean isTabletLandscape() {
    return false;
  }
  
  public static boolean isTabletPortrait() {
    return false;
  }
  
  
  public static boolean isDesktop() {
    return Window.Navigator.getUserAgent().toLowerCase().contains("windows nt");
  }
  
}
