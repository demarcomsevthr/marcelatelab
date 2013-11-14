package it.mate.stickmail.client.ui;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import com.google.gwt.core.client.JavaScriptObject;


public class DatePickerPlugin {
  
  public static void showDateDialog() {
    execImpl("showDateDialog", new PluginCallback() {
      public void execute(JavaScriptObject jso) {
        PhonegapUtils.log("received " + ShowDateDialogResult.cast(jso));
      }
    }, new PluginCallback() {
      public void execute(JavaScriptObject jso) {
        
      }
    });
  }
  
  public static class ShowDateDialogResult extends JavaScriptObject {
    protected ShowDateDialogResult() { }
    public static ShowDateDialogResult cast(JavaScriptObject jso) {
      return jso.cast();
    }
    public final int getYear() {
      return (Integer)GwtUtils.getPropertyImpl(this, "year");
    }
    public final int getMonth() {
      return (Integer)GwtUtils.getPropertyImpl(this, "month");
    }
    public final int getDay() {
      return (Integer)GwtUtils.getPropertyImpl(this, "day");
    }
    public final String asString() {
      return "ShowDateDialogResult [getYear()=" + getYear() + ", getMonth()=" + getMonth() + ", getDay()=" + getDay() + ", asString()=" + asString() + "]";
    }
  }
  
  public static interface PluginCallback {
    public void execute(JavaScriptObject jso);
  }
  
  private static native void execImpl (String action, PluginCallback success, PluginCallback failure) /*-{
    
    var jsSuccess = $entry(function(results) {
      success.@it.mate.stickmail.client.ui.DatePickerPlugin.PluginCallback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(results);
    });
    
    var jsFailure = $entry(function(err) {
      failure.@it.mate.stickmail.client.ui.DatePickerPlugin.PluginCallback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(err);
    });
    
    $wnd.cordova.exec(jsSuccess, jsFailure, "DatePickerPlugin", action, []);
    
  }-*/;
  

}
