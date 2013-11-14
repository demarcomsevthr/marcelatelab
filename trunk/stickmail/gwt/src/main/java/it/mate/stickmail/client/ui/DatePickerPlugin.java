package it.mate.stickmail.client.ui;

import com.google.gwt.core.client.JavaScriptObject;


public class DatePickerPlugin {
  
  public static void showDateDialog() {
    execImpl("showDateDialog", new PluginCallback() {
      public void execute(JavaScriptObject jso) {
        
      }
    }, new PluginCallback() {
      public void execute(JavaScriptObject jso) {
        
      }
    });
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
