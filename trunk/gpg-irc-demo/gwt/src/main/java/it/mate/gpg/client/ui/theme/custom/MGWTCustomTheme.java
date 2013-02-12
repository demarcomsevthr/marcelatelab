package it.mate.gpg.client.ui.theme.custom;

import com.google.gwt.core.shared.GWT;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.theme.MGWTClientBundle;
import com.googlecode.mgwt.ui.client.theme.MGWTTheme;

public class MGWTCustomTheme implements MGWTTheme {

  private MGWTClientBundle bundle;
  
  public MGWTCustomTheme() {
    if (MGWT.getOsDetection().isIOs()) {
      bundle = GWT.create(MGWTCustomBundleIPhone.class);
    } else {
      bundle = GWT.create(MGWTCustomBundleAndroid.class);
    }
  }

  @Override
  public MGWTClientBundle getMGWTClientBundle() {
    return bundle;
  }
  
}
