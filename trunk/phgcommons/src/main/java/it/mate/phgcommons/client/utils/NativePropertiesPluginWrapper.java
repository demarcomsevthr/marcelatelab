package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class NativePropertiesPluginWrapper extends AbstractPluginWrapper {

  public static void getProperties(final Delegate<Map<String, String>> delegate) {
    exec("NativePropertiesPlugin", "getProperties", null, new Delegate<JavaScriptObject>() {
      public void execute(JavaScriptObject results) {
        Map<String, String> properties = new HashMap<String, String>();
        JsArray<JsEntry> array = results.cast();
        for (int it = 0; it < array.length(); it++) {
          String name = array.get(it).getName();
          String value = array.get(it).getValue();
          properties.put(name, value);
        }
        delegate.execute(properties);
      }
    });
  }
  
  protected static class JsEntry extends JavaScriptObject {
    protected JsEntry() { }
    public final String getName() {
      return (String)GwtUtils.getPropertyImpl(this, "name");
    }
    public final String getValue() {
      return (String)GwtUtils.getPropertyImpl(this, "value");
    }
  }
  
  
}
