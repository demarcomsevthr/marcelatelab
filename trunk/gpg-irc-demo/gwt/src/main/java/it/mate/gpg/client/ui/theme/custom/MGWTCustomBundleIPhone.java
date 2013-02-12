package it.mate.gpg.client.ui.theme.custom;

import com.google.gwt.resources.client.DataResource;
import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeIPhone;
import com.googlecode.mgwt.ui.client.theme.base.MainCss;

public interface MGWTCustomBundleIPhone extends MGWTClientBundleBaseThemeIPhone {

  @Source("resources/bgr_iphone_a.jpg")
  DataResource bgrImage();
  
  @Source({"css/main.css", "css/iphone/main.css"})
  MainCss getMainCss();
  
}
