package it.mate.phgcommons.client.utils;

import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;

public class OsDetectionUtils {
  
  public final static int IPHONE_WIDTH = 320;
  public final static int IPHONE_HEIGHT = 480;
  public final static int IPAD_LAND_WIDTH = 1024;
  public final static int IPAD_LAND_HEIGHT = 748;
  public final static int IPAD_PORT_WIDTH = 768;
  public final static int IPAD_PORT_HEIGHT = 1004;
  
  public final static int APAD_LAND_WIDTH = 1280;
  public final static int APAD_LAND_HEIGHT = 800;
  public final static int APAD_PORT_WIDTH = 800;
  public final static int APAD_PORT_HEIGHT = 1280;
  
  private static int getDisplayHeight() {
    return Window.getClientHeight();
  }
  
  private static int getDisplayWidth() {
    return Window.getClientWidth();
  }
  
  public static boolean isTablet() {
    return (isTabletLandscape() || isTabletPortrait());
  }
  
  public static boolean isIOs() {
    return MGWT.getOsDetection().isIOs();
  }

  public static boolean isAndroid() {
    return MGWT.getOsDetection().isAndroid();
  }

  /*
   * NOTA BENE
   * 
   * Utilizzo le condizioni >= per poter debuggare in desktop
   * 
   */
  
  public static boolean isTabletLandscape() {
    if (MGWT.getOsDetection().isIOs()) {
      return (getDisplayWidth() >= IPAD_LAND_WIDTH && getDisplayHeight() >= IPAD_LAND_HEIGHT);
    }
    if (MGWT.getOsDetection().isAndroid()) {
      return (getDisplayWidth() >= APAD_LAND_WIDTH && getDisplayHeight() >= APAD_LAND_HEIGHT);
    }
    return false;
  }
  
  public static boolean isTabletPortrait() {
    if (MGWT.getOsDetection().isIOs()) {
      return (getDisplayWidth() >= IPAD_PORT_WIDTH && getDisplayHeight() >= IPAD_PORT_HEIGHT);
    }
    if (MGWT.getOsDetection().isAndroid()) {
      return (getDisplayWidth() >= APAD_PORT_WIDTH && getDisplayHeight() >= APAD_PORT_HEIGHT);
    }
    return false;
  }
  
  
  public static boolean isDesktop() {
    return Window.Navigator.getUserAgent().toLowerCase().contains("windows nt");
  }
  
}