package it.mate.gpg.client.ui.theme.custom;

import com.google.gwt.resources.client.DataResource;
import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeIPhone;

public interface MGWTCustomClientBundleIPhone extends MGWTCustomClientBundle, MGWTClientBundleBaseThemeIPhone {

  @Source({"css/main.css", "css/iphone/main.css"})
  CustomMainCss getMainCss();
  
  @Source("resources/bgr_iphone_a.jpg")
  DataResource bgrImage();
  
}
