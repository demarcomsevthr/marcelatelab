package it.mate.onscommons.client.ui.theme;

import it.mate.phgcommons.client.utils.OsDetectionUtils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;

public class DefaultTheme {

  public static class Impl {
    private static ThemeBundle bundle = null;
    public static ThemeBundle get() {
      if (bundle == null) {
        if (OsDetectionUtils.isAndroid()) {
          bundle = GWT.create(ThemeBundleAndroid.class);
        } else {
          bundle = GWT.create(ThemeBundleIos.class);
        }
      }
      return bundle;
    }
  }
  
  public interface ThemeBundle extends ClientBundle {
    @Source({"css/ons.css"})
    public CssResource css();
    @Source("resources/calendar.png")
    DataResource calendarImage();
    @Source("resources/clock.png")
    DataResource clockImage();
  }
  
  public interface ThemeBundleAndroid extends ThemeBundle {
    @Source({"css/ons.css"})
    public CssResource css();
  }
  
  public interface ThemeBundleIos extends ThemeBundle {
    @Source({"css/ons.css"})
    public CssResource css();
  }
  
}
