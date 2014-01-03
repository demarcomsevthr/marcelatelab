package it.mate.gendt.plugin;

import it.mate.gendt.R;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;

public class NativePropertiesPlugin extends CordovaPlugin {

  private final static String ACTION_GET_PROPERTY = "getProperties";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_GET_PROPERTY.equals(action)) {
      getProperties(callbackContext);
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
      entry.put("value", res.getString(R.string.hello_world));
      results.put(entry);
      callbackContext.success(results);
    } catch (JSONException e) {
      callbackContext.error(e.getMessage());
    }
    
  }
  
  
}
