package it.mate.phgcommons.client.plugins;

import it.mate.phgcommons.client.utils.callbacks.JSOSuccess;

public class ContactsPlugin {

  
  
  protected static native void pickContactImpl (JSOSuccess success) /*-{
    var jsSuccess = $entry(function(message) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(message);
    });
    $wnd.cordova.exec(jsSuccess, jsFailure, "Calendar", methodName, [{
      "title": title,
      "location": location,
      "notes": notes,
      "startTime": startTime,
      "endTime": endTime
    }]);
  }-*/;
  
}
