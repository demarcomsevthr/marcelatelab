package it.mate.protons.client.ui.theme;

import it.mate.phgcommons.client.utils.OsDetectionUtils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.theme.base.MainCss;


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
  
  public static String getHomePanelHeight() {
    return (Window.getClientHeight() - 50) + "px";
  }
  
  public interface CustomMainCss extends MainCss {
    
  }
  
  public interface CustomBundle {
    
    public CustomMainCss css();
    
  }
  
  public interface CustomBundleAndroid extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleAPadLandscape extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android.css", "css/apad.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleAPadPortrait extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/android.css", "css/apad.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIOs extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadLandscape extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios.css", "css/ipad.css", "css/ipad-landscape.css"})
    public CustomMainCss css();
  }
  
  public interface CustomBundleIPadPortrait extends CustomBundle, ClientBundle {
    @Source({"css/main.css", "css/ios.css", "css/ipad.css", "css/ipad-portrait.css"})
    public CustomMainCss css();
  }
  
}
