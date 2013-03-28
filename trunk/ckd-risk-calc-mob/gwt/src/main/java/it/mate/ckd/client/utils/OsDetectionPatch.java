package it.mate.ckd.client.utils;

import com.google.gwt.user.client.Window;

public class OsDetectionPatch {
  
  public static boolean isTablet() {
    int ch = Window.getClientHeight();
//  return (ch == 768 || ch == 1024);
    return (ch > 480);
  }

}
