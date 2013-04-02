package it.mate.ckd.client.ui.theme.custom;

import it.mate.ckd.client.utils.OsDetectionPatch;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.googlecode.mgwt.ui.client.MGWT;


public class CustomTheme {

  public static class Instance {
    private static CustomBundle instance = null;
    public static CustomBundle get() {
      if (instance == null) {
        if (MGWT.getOsDetection().isIOs()) {
          if (OsDetectionPatch.isTabletLandscape()) {
            instance = GWT.create(CustomBundleIPadLandscape.class);
          } else if (OsDetectionPatch.isTabletPortrait()) {
            instance = GWT.create(CustomBundleIPadPortrait.class);
          } else {
            instance = GWT.create(CustomBundleIOs.class);
          }
        } else {
          instance = GWT.create(CustomBundleAndroid.class);
        }
      }
      return instance;
    }
  }
  
  public interface CustomBundle {
    
    public CustomMainCss css();
    
    @Source("resources/bgr_iphone_a.jpg")
    DataResource bgrImage();
    
    @Source("resources/creatinine.png")
    ImageResource headerImage();
    
    @Source("resources/flag_en.png")
    ImageResource flagEnImage();
    
    @Source("resources/flag_it.png")
    ImageResource flagItImage();
    
    @Source("resources/check.png")
    ImageResource checkImage();
    
    @Source("resources/ico-minus.png")
    ImageResource minusImage();
    
    @Source("resources/ico-plus.png")
    ImageResource plusImage();
    
  }
  
  public interface CustomBundleAndroid extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android/main.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIOs extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/main.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadLandscape extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/main.css", "css/ipad/landscape/main.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadPortrait extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/main.css", "css/ipad/portrait/main.css"})
    public CustomMainCss css();
  }
  
}
