package it.mate.gpg.client.ui.theme.custom;

import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeAndroid;

public interface MGWTCustomClientBundleAndroid extends MGWTCustomClientBundle, MGWTClientBundleBaseThemeAndroid {

  @Source({"css/main.css", "css/android/main.css"})
  CustomMainCss getMainCss();
  
}
