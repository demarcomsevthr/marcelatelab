package it.mate.gpg.client.ui.theme.custom;

import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.googlecode.mgwt.ui.client.theme.MGWTClientBundle;

public interface MGWTCustomClientBundle extends MGWTClientBundle {

  @Source("resources/bgr_iphone_a.jpg")
  DataResource bgrImage();
  
  @Source("resources/creatinine.png")
  ImageResource headerImage();
  
  @Source("resources/flag_en.png")
  ImageResource flagEnImage();
  
  @Source("resources/flag_it.png")
  ImageResource flagItImage();
  
}
