package it.mate.ckd.client.utils;

import com.google.gwt.user.client.Window;

public class OsDetectionPatch {
  
  public static boolean isTablet() {
    int ch = Window.getClientHeight();
    return (ch > 480);
  }
  
  public static boolean isTabletLandscape() {
    int ch = Window.getClientHeight();
    return (ch >= 768 && ch < 1004);
  }
  
  public static boolean isTabletPortrait() {
    int ch = Window.getClientHeight();
    return (ch >= 1004);
  }
  
}
