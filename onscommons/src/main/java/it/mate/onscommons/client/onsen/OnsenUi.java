package it.mate.onscommons.client.onsen;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;


public class OnsenUi {
  
  private static boolean initialized = false;
  
  public static void initializeOnsen(OnsenReadyHandler handler) {
    if (!initialized) {
      initialized = true;
      initOnsenImpl(handler);
    }
  }
  
  protected static native void initOnsenImpl(OnsenReadyHandler handler) /*-{
    @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('ONSEN BOOTSTRAPING');
    $wnd.ons.bootstrap();
    var jsHandler = $entry(function() {
      @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('ONSEN JS READY');
      handler.@it.mate.onscommons.client.onsen.OnsenReadyHandler::onReady()();
    });
    @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('ONSEN HOOKING READY');
    $wnd.ons.ready(jsHandler);
  }-*/;
  
  public static void pushPage(String pageId) {
    JsOnsOptions options = JsOnsOptions.create();
    pushPageImpl(pageId, options);
  }
  
  protected static native void pushPageImpl(String pageId, JsOnsOptions options) /*-{
    @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('ONSEN PUSHING PAGE ' + pageId);
    $wnd.ons.navigator.pushPage(pageId, options);    
  }-*/;
  
  protected static class JsOnsOptions extends JavaScriptObject {
    protected JsOnsOptions() { }
    protected static JsOnsOptions create() {
      return JavaScriptObject.createObject().cast();
    }
  }

  public static void compile(Element element) {
    compileImpl(element);
  }
  
  protected static native void compileImpl(Element element) /*-{
    @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('COMPILING ELEMENT ' + element);
    $wnd.ons.compile(element);
  }-*/;

  public static void popPage() {
    popPageImpl();
  }
  
  protected static native void popPageImpl() /*-{
    @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('ONSEN POPING PAGE');
    $wnd.ons.navigator.popPage();    
  }-*/;

}
