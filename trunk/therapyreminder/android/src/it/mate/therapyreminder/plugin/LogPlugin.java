package it.mate.therapyreminder.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class LogPlugin extends CordovaPlugin {

  private final static String ACTION_DEBUG = "debug";

  private final static String ACTION_INFO = "info";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_DEBUG.equals(action)) {
      debug(args.getString(0), callbackContext);
      return true;
    } else if (ACTION_INFO.equals(action)) {
      info(args.getString(0), callbackContext);
      return true;
    }
    return false;
  }
  
  private synchronized void debug(String msg, CallbackContext callbackContext) {
    try {
      Log.d("LogPlugin", msg);
      callbackContext.success();
    } catch (Exception e) {
      callbackContext.error(e.getMessage());
    }
  }
  
  private synchronized void info(String msg, CallbackContext callbackContext) {
    try {
      Log.i("LogPlugin", msg);
      callbackContext.success();
    } catch (Exception e) {
      callbackContext.error(e.getMessage());
    }
  }
  
}
