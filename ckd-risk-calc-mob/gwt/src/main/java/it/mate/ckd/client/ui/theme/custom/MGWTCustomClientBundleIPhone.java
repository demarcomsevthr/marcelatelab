package it.mate.ckd.client.ui.theme.custom;

import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeIPhone;

public interface MGWTCustomClientBundleIPhone extends MGWTCustomClientBundle, MGWTClientBundleBaseThemeIPhone {

  @Source({"css/main.css", "css/iphone/main.css"})
  CustomMainCss getMainCss();
  
}
