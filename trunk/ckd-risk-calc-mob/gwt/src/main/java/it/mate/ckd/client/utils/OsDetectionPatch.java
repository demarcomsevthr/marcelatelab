package it.mate.ckd.client.utils;

import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;

public class OsDetectionPatch {
  
  private final static int IPAD_LAND_WIDTH = 1024;
  private final static int IPAD_LAND_HEIGHT = 748;
  private final static int IPAD_PORT_WIDTH = 768;
  private final static int IPAD_PORT_HEIGHT = 1004;
  
  private final static int APAD_LAND_WIDTH = 1280;
  private final static int APAD_LAND_HEIGHT = 800;
  private final static int APAD_PORT_WIDTH = 800;
  private final static int APAD_PORT_HEIGHT = 1280;
  
  private static int getDisplayHeight() {
    return Window.getClientHeight();
  }
  
  private static int getDisplayWidth() {
    return Window.getClientWidth();
  }
  
  public static boolean isTablet() {
    return (isTabletLandscape() || isTabletPortrait());
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
  
}
