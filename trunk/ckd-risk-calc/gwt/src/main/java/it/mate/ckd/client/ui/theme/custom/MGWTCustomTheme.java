package it.mate.ckd.client.ui.theme.custom;

import com.google.gwt.core.shared.GWT;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.theme.MGWTTheme;

public class MGWTCustomTheme implements MGWTTheme {
  
  private static MGWTCustomTheme instance;

  private MGWTCustomClientBundle bundle;
  
  private MGWTCustomTheme() {
    if (MGWT.getOsDetection().isIOs()) {
      bundle = GWT.create(MGWTCustomClientBundleIPhone.class);
    } else {
      bundle = GWT.create(MGWTCustomClientBundleAndroid.class);
    }
  }

  @Override
  public MGWTCustomClientBundle getMGWTClientBundle() {
    return bundle;
  }
  
  public static MGWTCustomTheme getInstance() {
    if (instance == null) {
      instance = new MGWTCustomTheme();
    }
    return instance;
  }
  
}
