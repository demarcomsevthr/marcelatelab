package it.mate.gpg.client.ui.theme.custom;

import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeAndroid;
import com.googlecode.mgwt.ui.client.theme.base.MainCss;

public interface MGWTCustomBundleAndroid extends MGWTClientBundleBaseThemeAndroid {

  @Source({"css/main.css", "css/android/main.css"})
  MainCss getMainCss();
  
}
