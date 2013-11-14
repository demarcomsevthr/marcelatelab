package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;


public class DatePickerPluginUtil {
  
  private static boolean doSimulation = OsDetectionUtils.isDesktop();
  
  public static void showDateDialog(final Delegate<Date> delegate) {
    if (doSimulation) {
      execSimulImpl("showDateDialog", new JsCallback() {
        public void execute(JavaScriptObject jso) {
          JsDateResult res = JsDateResult.from(jso);
          PhonegapUtils.log("received " + res.asString());
          delegate.execute(res.asDate());
        }
      }, new JsCallback() {
        public void execute(JavaScriptObject jso) {
          PhonegapUtils.log("error executing plugin");
        }
      });
    } else {
      execImpl("showDateDialog", new JsCallback() {
        public void execute(JavaScriptObject jso) {
          JsDateResult res = JsDateResult.from(jso);
          PhonegapUtils.log("received " + res.asString());
          delegate.execute(res.asDate());
        }
      }, new JsCallback() {
        public void execute(JavaScriptObject jso) {
          PhonegapUtils.log("error executing plugin");
        }
      });
    }
  }
  
  public static class JsDateResult extends JavaScriptObject {
    protected JsDateResult() { }
    public static JsDateResult from(JavaScriptObject jso) {
      return jso.<JsDateResult>cast();
    }
    public final int getYear() {
      return GwtUtils.getPropertyIntImpl(this, "year");
    }
    public final int getMonth() {
      return GwtUtils.getPropertyIntImpl(this, "month");
    }
    public final int getDay() {
      return GwtUtils.getPropertyIntImpl(this, "day");
    }
    @SuppressWarnings("deprecation")
    public final Date asDate() {
      return new Date(getYear() - 1900, getMonth(), getDay());
    }
    public final String asString() {
      return "DateResult [year=" + getYear() + ", month=" + getMonth() + ", day=" + getDay() + "]";
    }
  }
  
  public static interface JsCallback {
    public void execute(JavaScriptObject jso);
  }
  
  private static native void execImpl (String action, JsCallback success, JsCallback failure) /*-{
    var jsSuccess = $entry(function(results) {
      success.@it.mate.phgcommons.client.utils.DatePickerPluginUtil.JsCallback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(results);
    });
    var jsFailure = $entry(function(err) {
      failure.@it.mate.phgcommons.client.utils.DatePickerPluginUtil.JsCallback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(err);
    });
    $wnd.cordova.exec(jsSuccess, jsFailure, "DatePickerPlugin", action, []);
  }-*/;
  
  private static native void execSimulImpl (String action, JsCallback success, JsCallback failure) /*-{
    var jsSuccess = $entry(function(results) {
      success.@it.mate.phgcommons.client.utils.DatePickerPluginUtil.JsCallback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(results);
    });
    var result = {"year": 2013, "month":11, "day":14};
    jsSuccess(result);
  }-*/;

}
