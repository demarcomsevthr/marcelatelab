package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
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

public class PhonegapUtils {
  
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
    log("Locale.language " + getAppLocalLanguage());
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
    PhonegapUtils.tabletWrapperPct = tabletWrapperPct;
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
    PhonegapUtils.useLogPlugin = useLogPlugin;
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
      return GwtUtils.dateToString(date, "MM/dd/yyyy");
    }
  }
  
  public static void startTrace() {
    PhonegapUtils.trace = new ArrayList<String>();
  }
  
  public static List<String> getTrace() {
    return trace;
  }
  
  public static void clearTrace() {
    PhonegapUtils.trace = null;
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

  public static String getCurrentLanguage() {
    String language = LocaleInfo.getCurrentLocale().getLocaleName();
    if ("default".equals(language)) {
      language = getNavigatorLanguage();
    }
    return language;
  }
  
  public static void getCurrentLanguage(final Delegate<String> delegate) {
    getGlobalizationLanguage(new StringCallback() {
      public void handle(String language) {
        if (language != null) {
          delegate.execute(language);
        } else {
          delegate.execute(getCurrentLanguage());
        }
      }
    });
  }
  
  private static native String getNavigatorLanguage() /*-{
    return navigator.language;
  }-*/;

  private static native void getGlobalizationLanguage(StringCallback callback) /*-{
    if (typeof(navigator.globalization) == 'undefined') {
      callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(null);
    } else {
      var jsSuccessCallback = $entry(function(locale) {
        callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(locale.value);
      });
      var jsErrorCallback = $entry(function(locale) {
        callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(null);
      });
      navigator.globalization.getLocaleName(jsSuccessCallback,jsErrorCallback);    
    }
  }-*/;

  public static void getGlobalizationDatePattern(final Delegate<String> resultDelegate) {
    JavaScriptObject options = JavaScriptObject.createObject();
    GwtUtils.setJSOProperty(options, "formatLength", "short");
    GwtUtils.setJSOProperty(options, "selector", "date");
    getGlobalizationDatePatternImpl(options, new StringCallback() {
      public void handle(String result) {
        resultDelegate.execute(result);
      }
    });
  }
  public static void getGlobalizationTimePattern(final Delegate<String> resultDelegate) {
    JavaScriptObject options = JavaScriptObject.createObject();
    GwtUtils.setJSOProperty(options, "formatLength", "short");
    GwtUtils.setJSOProperty(options, "selector", "time");
    getGlobalizationDatePatternImpl(options, new StringCallback() {
      public void handle(String result) {
        resultDelegate.execute(result);
      }
    });
  }
    
  private static native void getGlobalizationDatePatternImpl(JavaScriptObject options, StringCallback callback) /*-{
    if (typeof(navigator.globalization) == 'undefined') {
      
      if (typeof($wnd.cordova) != 'undefined' && typeof($wnd.cordova.exec) != 'undefined') {
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("js globalization wrapper is undefined");
        var jsSuccessCallback = $entry(function(date) {
          callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(date.pattern);
        });
        var jsErrorCallback = $entry(function() {
          @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("js error during globalization getDatePattern");
          callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(null);
        });
        $wnd.cordova.exec(jsSuccessCallback, jsErrorCallback, "Globalization", "getDatePattern", [{"options": options}]);
      } else {
        callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(null);
      }
      
    } else {
      var jsSuccessCallback = $entry(function(date) {
        callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(date.pattern);
      });
      var jsErrorCallback = $entry(function() {
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("js error during globalization getDatePattern");
        callback.@it.mate.phgcommons.client.utils.callbacks.StringCallback::handle(Ljava/lang/String;)(null);
      });
      navigator.globalization.getDatePattern(jsSuccessCallback, jsErrorCallback );
//    navigator.globalization.getDatePattern(jsSuccessCallback, jsErrorCallback , options );
    }
    
    
  }-*/;

  //TODO
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
    }
    return lang;
  }
  
  private static native String getAppLocalLanguageImpl() /*-{
    return $wnd.getAppLocalLanguage();
  }-*/;

  /*
  public static String getLocalStorageLanguage() {
    return getLocalStorageProperty("PhonegapUtils.language");
  }
  
  public static void setLocalStorageLanguage(String value) {
    setLocalStorageProperty("PhonegapUtils.language", value);
  }
  */
  
}
