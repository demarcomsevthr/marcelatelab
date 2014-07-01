package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.plugins.GlobalizationPlugin;
import it.mate.phgcommons.client.ui.CalendarDialog;
import it.mate.phgcommons.client.utils.callbacks.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.googlecode.mgwt.ui.client.MGWT;

public class PhgUtils {
  
  private static int tabletWrapperPct = 80;
  
  private static int HEADER_PANEL_HEIGHT = 40;
  
  private static boolean useLogPlugin = false;
  
  private static List<String> trace = null;
  

  public static void logEnvironment() {
    log("os detection = " + (MGWT.getOsDetection().isAndroid() ? "android" : MGWT.getOsDetection().isIOs() ? "ios" : "other"));
    log("CURRENT LAYOUT = " + getLayoutInfo());
    if (OsDetectionUtils.isIOs()) {
      log("IOs phone layout is " + OsDetectionUtils.IPHONE_WIDTH + " x " + OsDetectionUtils.IPHONE_HEIGHT);
      log("IOs landscape layout is " + OsDetectionUtils.IPAD_LAND_WIDTH + " x " + OsDetectionUtils.IPAD_LAND_HEIGHT);
      log("IOs portrait  layout is " + OsDetectionUtils.IPAD_PORT_WIDTH + " x " + OsDetectionUtils.IPAD_PORT_HEIGHT);
    } else {
      log("Android landscape layout is " + OsDetectionUtils.APAD_LAND_WIDTH + " x " + OsDetectionUtils.APAD_LAND_HEIGHT);
      log("Android portrait  layout is " + OsDetectionUtils.APAD_PORT_WIDTH + " x " + OsDetectionUtils.APAD_PORT_HEIGHT);
    }
    log("(see "+ OsDetectionUtils.class.getName() +" for details)");
    log("NavVer " + Window.Navigator.getAppVersion());
    log("Device.name " + getDeviceName());
    log("Device.phonegap " + getDevicePhonegap());
    log("Device.platform " + getDevicePlatform());
    log("Device.uuid " + getDeviceUuid());
    log("Device.version " + getDeviceVersion());
    log("AppLocalLanguage = " + getAppLocalLanguage());
  }
  
  public static void commonInitializations() {
    
    log("Calendar language initialization...");
    CalendarDialog.setLanguage(PhgUtils.getAppLocalLanguage());
    
    log("Date format initialization (TODO)...");
    PhgUtils.getGlobalizationDatePattern(new Delegate<String>() {
      public void execute(String pattern) {
        PhgUtils.log("GLOB DATE PATTERN = " + pattern);
      }
    });
    
    log("Time format initialization...");
    PhgUtils.getGlobalizationTimePattern(new Delegate<String>() {
      public void execute(String pattern) {
        PhgUtils.log("GLOBALIZATION TIME PATTERN = " + pattern);
        if (pattern.contains("HH")) {
          Time.set24HFormat();
        } else {
          Time.set12HFormat();
        }
        PhgUtils.log("CURRENT TIME FORMAT IS " + Time.getCurrentFormat().getPattern());
      }
    });
    
  }
  
  public static String getLayoutInfo() {
    String layoutInfo = "Width " + Window.getClientWidth();
    layoutInfo += " Height " + Window.getClientHeight();
    if (OsDetectionUtils.isTabletLandscape()) {
      layoutInfo += " isTabletLandscape";
    } else if (OsDetectionUtils.isTabletPortrait()) {
      layoutInfo += " isTabletPortrait";
    } else {
      layoutInfo += " isPhone";
    }
    return layoutInfo;
  }
  
  public static void openInAppBrowser(String url) {
    openInAppBrowserImpl(url);
  }
  
  private static native void openInAppBrowserImpl(String url) /*-{
    var inAppBrowser = $wnd.open(url, '_blank', 'location=no');
  }-*/;
  
  public static native Navigator getNavigator() /*-{
    return $wnd.navigator;
  }-*/;
  
  public static native String getLocalStorageItem(String name) /*-{
    return $wnd.localStorage[name];
  }-*/;
  
  public static native void setLocalStorageItem(String name, String value) /*-{
    $wnd.localStorage[name] = value;
  }-*/;

  public static native String getWindowSetting(String name) /*-{
    if ($wnd.Settings) {
      return $wnd.Settings[name];
    }
    return null;
  }-*/;

  public static class Navigator extends JavaScriptObject {
    protected Navigator() {

    }
    public final native String getLanguage() /*-{
      return this.language;
    }-*/;
  }
  
  
  public static void log(String text) {
    String logMsg = GwtUtils.log(text);
    
    if (logMsg != null && !OsDetectionUtils.isDesktop() && "true".equalsIgnoreCase(getWindowSetting("TraceLogToFile"))) {
//    logImpl("Ready to write to trace.log > " + logMsg);
      
      if (trace != null) {
        trace.add(logMsg);
      }
      
    }
    
    if (useLogPlugin && !OsDetectionUtils.isDesktop() && !OsDetectionUtils.isIOs()) {
      LogPlugin.debug(text);
    } else {
      logImpl(text);
    }
  }
  
  public static native void logImpl(String text) /*-{
    $wnd.console.log(text);
  }-*/;

  public static void addOrientationChangeHandler(Delegate<Void> delegate) {
    JQuery.select("body").bind("orientationchange", delegate);
  }
  
  private static boolean suspendUncaughtExceptionAlerts;
  
  public static void setSuspendUncaughtExceptionAlerts(boolean flag) {
    suspendUncaughtExceptionAlerts = flag;
  }

  public static boolean isSuspendUncaughtExceptionAlerts() {
    return suspendUncaughtExceptionAlerts;
  }
  
  public static String getNativeProperty(String name) {
    return getNativePropertyImpl(name.substring(0, 1).toUpperCase()+name.substring(1));
  }

  private static native String getNativePropertyImpl(String name) /*-{
    if (!$wnd.jsNativePropertiesWrapper) {
      return null;
    }
    return eval("window.jsNativePropertiesWrapper.get"+name+"()");
  }-*/;

  public static void adaptWrapperPanel(Panel wrapperPanel, String id, final boolean adaptVerMargin, final int headerPanelHeight, final Delegate<Element> delegate) {
    wrapperPanel.getElement().setId(id);
    if (OsDetectionUtils.isTablet()) {
      GwtUtils.onAvailable(id, new Delegate<Element>() {
        public void execute(Element wrapperPanelElem) {
          int height = getTabletWrapperHeight();
          GwtUtils.log("applying wrapperPanel height = " + height);
          wrapperPanelElem.getStyle().setHeight(height, Unit.PX);
          if (adaptVerMargin) {
            int verMargin = ( Window.getClientHeight() - height ) / 2 - headerPanelHeight;
            wrapperPanelElem.getStyle().setMarginTop(verMargin, Unit.PX);
            wrapperPanelElem.getStyle().setMarginBottom(verMargin, Unit.PX);
          }
          int width = getTabletWrapperWidth();
          wrapperPanelElem.getStyle().setWidth(width, Unit.PX);
          int horMargin = ( Window.getClientWidth() - width ) / 2;
          wrapperPanelElem.getStyle().setMarginLeft(horMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginRight(horMargin, Unit.PX);
          if (delegate != null) {
            delegate.execute(wrapperPanelElem);
          }
        }
      });
      
    } else {
      
      //09/10/2013
      // Patch per IPhone per far funzionare le animazioni jquery
      wrapperPanel.setHeight((Window.getClientHeight() - HEADER_PANEL_HEIGHT ) + "px");
      wrapperPanel.setWidth(Window.getClientWidth() + "px");
      
    }
  }
  
  private static int getTabletWrapperHeight() {
    int height = Window.getClientHeight() * tabletWrapperPct / 100;
    return height;
  }
  
  private static int getTabletWrapperWidth() {
    int width = Window.getClientWidth() * tabletWrapperPct / 100;
    return width;
  }
  
  public static void setTabletWrapperPct(int tabletWrapperPct) {
    PhgUtils.tabletWrapperPct = tabletWrapperPct;
  }
  
  public static native String getDeviceName() /*-{
    if (!$wnd.device) {
      return "desktop";
    }
    return $wnd.device.name;
  }-*/;

  public static native String getDevicePhonegap() /*-{
    if (!$wnd.device) {
      return "desktop";
    }
    return $wnd.device.phonegap;
  }-*/;

  public static native String getDevicePlatform() /*-{
    if (!$wnd.device) {
      return "desktop";
    }
    return $wnd.device.platform;
  }-*/;

  public static native String getDeviceUuid() /*-{
    if (!$wnd.device) {
      return "desktop";
    }
    return $wnd.device.uuid;
  }-*/;

  public static native String getDeviceVersion() /*-{
    if (!$wnd.device) {
      return "desktop";
    }
    return $wnd.device.version;
  }-*/;

  public static void setUseLogPlugin(boolean useLogPlugin) {
    PhgUtils.useLogPlugin = useLogPlugin;
  }
  
  public static String dateToString(Date date) {
    String lang = getAppLocalLanguage();
    if (lang == null) {
      lang = getNavigator().getLanguage();
    }
    if (lang == null) {
      lang = "en";
    }
    if ("it".equals(lang)) {
      return GwtUtils.dateToString(date, "dd/MM/yyyy");
    } else {
      return GwtUtils.dateToString(date, "M/d/yyyy");
    }
  }
  
  public static void startTrace() {
    PhgUtils.trace = new ArrayList<String>();
  }
  
  public static List<String> getTrace() {
    return trace;
  }
  
  public static void clearTrace() {
    PhgUtils.trace = null;
  }
  
  public static void callDebugHook(JavaScriptObject jso) {
    if (isGlobalDebugHookUndefined()) {
      createGlobalDebugHookImpl();
    }
    callGlobalDebugHookImpl(jso);
  }
  
  private native static boolean isGlobalDebugHookUndefined() /*-{
    return typeof($wnd.glbDebugHook) == "undefined";
  }-*/;
  
  private native static void createGlobalDebugHookImpl() /*-{
    $wnd.glbDebugHook = function (jso) {
        var _tt = "INSERT BREAK POINT HERE";
      };
  }-*/;
  
  private native static void callGlobalDebugHookImpl(JavaScriptObject jso) /*-{
    $wnd.glbDebugHook(jso);
  }-*/;

  public static void getCurrentLanguage(final Delegate<String> delegate) {
    GlobalizationPlugin.getLocaleName(new StringCallback() {
      public void handle(String language) {
        if (language != null) {
          delegate.execute(language);
        } else {
          delegate.execute(getCurrentLanguage());
        }
      }
    });
  }
  
  public static String getCurrentLanguage() {
    String language = LocaleInfo.getCurrentLocale().getLocaleName();
    if ("default".equals(language)) {
      language = getNavigatorLanguage();
    }
    return language;
  }
  
  private static native String getNavigatorLanguage() /*-{
    return navigator.language;
  }-*/;

  private static String defaultDatePattern;
  
  public static String getDefaultDatePattern() {
    return defaultDatePattern;
  }
  
  private static String defaultTimePattern;
  
  public static String getDefaultTimePattern() {
    return defaultTimePattern;
  }
  
  public static void getGlobalizationDatePattern(final Delegate<String> resultDelegate) {
    GlobalizationPlugin.getDatePattern(new Delegate<String>() {
      public void execute(String pattern) {
        if (pattern == null) {
          PhgUtils.log("MAYBE MISSING GLOBALIZATION PLUGIN (cordova plugin add org.apache.cordova.globalization)?");
          pattern = PhgUtils.getLocalStorageItem("debug-date-pattern");
          if (pattern == null) {
            if ("it".equals(getAppLocalLanguage())) {
              pattern = "dd/MM/yyyy";
            } else {
              pattern = "MM/dd/yyyy";
            }
            PhgUtils.setLocalStorageItem("debug-date-pattern", pattern);
          }
        }
        defaultDatePattern = pattern;
        PhgUtils.log("DEFAULT DATE PATTERN = " + defaultDatePattern);
        resultDelegate.execute(pattern);
      }
    });
  }
  public static void getGlobalizationTimePattern(final Delegate<String> resultDelegate) {
    GlobalizationPlugin.getTimePattern(new Delegate<String>() {
      public void execute(String pattern) {
        if (pattern == null) {
          PhgUtils.log("MAYBE MISSING GLOBALIZATION PLUGIN (cordova plugin add org.apache.cordova.globalization)?");
          pattern = PhgUtils.getLocalStorageItem("debug-time-pattern");
          if (pattern == null) {
            if ("it".equals(getAppLocalLanguage())) {
              pattern = "HH:mm";
            } else {
              pattern = "h:mm a";
            }
            PhgUtils.setLocalStorageItem("debug-time-pattern", pattern);
          }
        }
        defaultTimePattern = pattern;
        PhgUtils.log("DEFAULT TIME PATTERN = " + defaultTimePattern);
        resultDelegate.execute(pattern);
      }
    });
  }
    
  public static void setAppLocalLanguageAndReload(final String language) {
    setAppLocalLanguageImpl(language);
    // se lanciato da una combo da una IllegalStateException su una onDetach
    // workaround: disabilito l'handler delle eccezioni
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        // do nothing
      }
    });
    Window.Location.reload();
  }
  
  private static native void setAppLocalLanguageImpl(String language) /*-{
    $wnd.setAppLocalLanguage(language);
  }-*/;

  public static String getAppLocalLanguage() {
    String lang = getAppLocalLanguageImpl();
    if (lang == null || lang.trim().length() == 0) {
      lang = getCurrentLanguage();
      if (lang != null) {
        if (lang.length() > 2) {
          lang = lang.substring(0, 2);
        }
        lang = lang.toLowerCase();
      }
    }
    return lang;
  }
  
  private static native String getAppLocalLanguageImpl() /*-{
    if (typeof($wnd.getAppLocalLanguage) == 'undefined') {
      return null;
    }
    return $wnd.getAppLocalLanguage();
  }-*/;

}
