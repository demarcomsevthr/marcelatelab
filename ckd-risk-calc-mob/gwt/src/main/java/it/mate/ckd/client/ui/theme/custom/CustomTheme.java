package it.mate.ckd.client.ui.theme.custom;

import it.mate.phgcommons.client.utils.OsDetectionUtils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;


public class CustomTheme {

  public static class Instance {
    private static CustomBundle instance = null;
    public static CustomBundle get() {
      return get(false);
    }
    public static CustomBundle get(boolean forceReset) {
      if (instance == null || forceReset) {
        if (MGWT.getOsDetection().isIOs()) {
          if (OsDetectionUtils.isTabletLandscape()) {
            instance = GWT.create(CustomBundleIPadLandscape.class);
          } else if (OsDetectionUtils.isTabletPortrait()) {
            instance = GWT.create(CustomBundleIPadPortrait.class);
          } else {
            instance = GWT.create(CustomBundleIOs.class);
          }
        } else {
          if (OsDetectionUtils.isTabletLandscape()) {
            instance = GWT.create(CustomBundleAPadLandscape.class);
          } else if (OsDetectionUtils.isTabletPortrait()) {
            instance = GWT.create(CustomBundleAPadPortrait.class);
          } else {
            instance = GWT.create(CustomBundleAndroid.class);
          }
        }
      }
      return instance;
    }
  }
  
  
  public static String getWindowHeight() {
    return Window.getClientHeight() + "px";
  }
  
  public static String getWindowWidth() {
    return Window.getClientWidth() + "px";
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
    
    @Source("resources/workflow36b.png")
    ImageResource workflowImage();
    
  }
  
  public interface CustomBundleAndroid extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android/phone.css", "css/android/android.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleAPadLandscape extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android/tablet.css", "css/android/android.css", "css/android/tablet-landscape.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleAPadPortrait extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android/tablet.css", "css/android/android.css", "css/android/tablet-portrait.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIOs extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/phone.css", "css/ios/ios.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadLandscape extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/tablet.css", "css/ios/ios.css", "css/ios/tablet-landscape.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadPortrait extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios/tablet.css", "css/ios/ios.css", "css/ios/tablet-portrait.css"})
    public CustomMainCss css();
  }
  
  public interface CssBuild1001 {
    
  }
  
}
