package it.mate.ckd.client.utils;

import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;

public class OsDetectionPatch {
  
  private final static int IPAD_LAND_WIDTH = 1024;
  private final static int IPAD_LAND_HEIGHT = 748;
  private final static int IPAD_PORT_WIDTH = 768;
  private final static int IPAD_PORT_HEIGHT = 1004;
  
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
    return false;
  }
  
  public static boolean isTabletPortrait() {
    if (MGWT.getOsDetection().isIOs()) {
      return (getDisplayWidth() >= IPAD_PORT_WIDTH && getDisplayHeight() >= IPAD_PORT_HEIGHT);
    }
    return false;
  }
  
}
