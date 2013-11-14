package it.mate.stickmail;

import java.util.Calendar;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

/**
 * 
 * see https://github.com/flyingzl/phonegap-datepicker/blob/master/DateUtil.java
 * 
 */

public class DateUtil extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    showDateDialog(callbackContext);
    return true;
  }

  private synchronized void showDateDialog(final CallbackContext callbackContext) {
    Runnable runnable = new Runnable() {

      @Override
      public void run() {
        final JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(cordova.getActivity(), new DatePickerDialog.OnDateSetListener() {

          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
              object.put("year", year);
              object.put("month", monthOfYear);
              object.put("day", dayOfMonth);
              callbackContext.success(object);

            } catch (JSONException e) {
              callbackContext.error(e.getMessage());
            }

          }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dlg.show();
      }
    };

    this.cordova.getActivity().runOnUiThread(runnable);
  }

}
