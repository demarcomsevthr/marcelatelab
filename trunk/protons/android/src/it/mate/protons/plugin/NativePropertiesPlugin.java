package it.mate.protons.plugin;

import it.mate.protons.R;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;

public class NativePropertiesPlugin extends CordovaPlugin {

  private final static String ACTION_GET_PROPERTIES = "getProperties";

  private final static String ACTION_GET_PROPERTIES_AS_STRING = "getPropertiesAsString";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_GET_PROPERTIES.equals(action)) {
      getProperties(callbackContext);
      return true;
    } else if (ACTION_GET_PROPERTIES_AS_STRING.equals(action)) {
      getPropertiesAsString(callbackContext);
      return true;
    }
    return false;
  }
  
  private synchronized void getProperties(CallbackContext callbackContext) {
    Resources res = cordova.getActivity().getResources();
    try {
      JSONArray results = new JSONArray();
      JSONObject entry = new JSONObject();
      entry.put("name", "helloWorld");
      entry.put("value", "helloWorld");
      results.put(entry);
      callbackContext.success(results);
    } catch (JSONException e) {
      callbackContext.error(e.getMessage());
    }
  }
  
  private synchronized void getPropertiesAsString(CallbackContext callbackContext) {
    Resources res = cordova.getActivity().getResources();
    try {
      String results = "";
      results += "appName=" + res.getString(R.string.app_name);
      /*
      results += "|helloWorld=" + res.getString(R.string.hello_world);
      results += "|cupFacadeModuleUrl=" + res.getString(R.string.cupFacadeModuleUrl);
      results += "|cupFacadeRelativeUrl=" + res.getString(R.string.cupFacadeRelativeUrl);
      */
      callbackContext.success(results);
    } catch (Exception e) {
      callbackContext.error(e.getMessage());
    }
  }
  
  
}
