package it.mate.stickmail.plugins;

import java.util.Calendar;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.ViewGroup.LayoutParams;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * 
 * see https://github.com/flyingzl/phonegap-datepicker/blob/master/DateUtil.java
 * 
 */

public class DatePickerPlugin extends CordovaPlugin {
  
  private final static String ACTION_SHOW_DATE_DIALOG = "showDateDialog";

  private final static String ACTION_SHOW_TIME_DIALOG = "showTimeDialog";

  private final static String ACTION_SHOW_CALENDAR_VIEW = "showCalendarView";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    
    if (ACTION_SHOW_DATE_DIALOG.equals(action)) {
      Integer mm = null, yy = null, dd = null;
      if (args.length() > 0 && args.get(0) != null) {
        JSONObject obj = args.getJSONObject(0);
        mm = obj.getJSONObject("month").getInt("a");
        yy = obj.getJSONObject("year").getInt("a");
        dd = obj.getJSONObject("day").getInt("a");
      }
      showDateDialog(mm, yy, dd, callbackContext);
      return true;
    } else if (ACTION_SHOW_TIME_DIALOG.equals(action)) {
      showTimeDialog(callbackContext);
      return true;
    } else if (ACTION_SHOW_CALENDAR_VIEW.equals(action)) {
      showCalendarView(null, null, null, callbackContext);
      return true;
    }
    return false;
  }
  
  private synchronized void showCalendarView(final Integer mm, final Integer yy, final Integer dd, final CallbackContext callbackContext) {
    Runnable runnable = new Runnable() {
      public void run() {
        final JSONObject result = new JSONObject();
        CalendarView calView = new CalendarView(cordova.getActivity());
        LayoutParams layout = DatePickerPlugin.this.webView.getLayoutParams();
        calView.layout(0, 100, layout.width, layout.height - 100);
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
          public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            try {
              result.put("year", year);
              result.put("month", month);
              result.put("day", dayOfMonth);
              callbackContext.success(result);
            } catch (JSONException e) {
              callbackContext.error(e.getMessage());
            }
          }
        });
        
      }
    };
    this.cordova.getActivity().runOnUiThread(runnable);
  }
  

  private synchronized void showDateDialog(final Integer mm, final Integer yy, final Integer dd, final CallbackContext callbackContext) {
    Runnable runnable = new Runnable() {
      public void run() {
        final JSONObject result = new JSONObject();
        Calendar c = Calendar.getInstance();
        if (mm != null) {
          c.set(Calendar.MONTH, mm);
        }
        if (yy != null) {
          c.set(Calendar.YEAR, yy);
        }
        if (dd != null) {
          c.set(Calendar.DAY_OF_MONTH, dd);
        }
        DatePickerDialog dlg = new DatePickerDialog(cordova.getActivity(), new DatePickerDialog.OnDateSetListener() {
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
              result.put("year", year);
              result.put("month", monthOfYear);
              result.put("day", dayOfMonth);
              callbackContext.success(result);
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

  private synchronized void showTimeDialog(final CallbackContext callbackContext) {
    Runnable runnable = new Runnable() {
      public void run() {
        final JSONObject result = new JSONObject();
        Calendar c = Calendar.getInstance();
        TimePickerDialog dlg = new TimePickerDialog(cordova.getActivity(), new TimePickerDialog.OnTimeSetListener() {
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            try {
              result.put("hour", hourOfDay);
              result.put("minute", minute);
              callbackContext.success(result);
            } catch (JSONException e) {
              callbackContext.error(e.getMessage());
            }
          }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        dlg.show();
      }
    };
    this.cordova.getActivity().runOnUiThread(runnable);
  }

}
