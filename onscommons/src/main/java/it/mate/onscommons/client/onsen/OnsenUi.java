package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;


public class OnsenUi {
  
  private static boolean initialized = false;
  
  private static List<Delegate<Void>> initializationHandlers = new ArrayList<Delegate<Void>>();
  
  public static void initializeOnsen(OnsenReadyHandler handler) {
    if (!initialized) {
      initialized = true;
      initOnsenImpl(handler);
      for (Delegate<Void> initHandler : initializationHandlers) {
        initHandler.execute(null);
      }
    }
  }
  
  public static void addInitializationHandler(Delegate<Void> handler) {
    initializationHandlers.add(handler);
  }
  
  public static boolean isInitialized() {
    return initialized;
  }
  
  protected static native void initOnsenImpl(OnsenReadyHandler handler) /*-{
    @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN BOOTSTRAPING');
    $wnd.ons.bootstrap();
    var jsHandler = $entry(function() {
      @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN JS READY');
      handler.@it.mate.onscommons.client.onsen.OnsenReadyHandler::onReady()();
    });
    @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN HOOKING READY');
    $wnd.ons.ready(jsHandler);
  }-*/;
  
  public static void pushPage(String pageId) {
    JsOnsOptions options = JsOnsOptions.create();
    pushPage(pageId, options);
  }
  
  protected static void pushPage(String pageId, JsOnsOptions options) {
    CdvUtils.log("PUSHING PAGE " + pageId);
    pushPageImpl(pageId, options);
  }
  
  public static void insertPage(int index, String pageId) {
    JsOnsOptions options = JsOnsOptions.create();
    GwtUtils.setJsPropertyString(options, "hoge", "hoge");
    CdvUtils.log("INSERTING PAGE " + pageId + " AT " + index);
    insertPageImpl(0, pageId, options);
  }
  
  protected static native void pushPageImpl(String pageId, JsOnsOptions options) /*-{
    $wnd.ons.navigator.pushPage(pageId, options);    
  }-*/;
  
  protected static native void insertPageImpl(int index, String pageId, JsOnsOptions options) /*-{
    $wnd.ons.navigator.insertPage(index, pageId, options);    
  }-*/;

  protected static class JsOnsOptions extends JavaScriptObject {
    protected JsOnsOptions() { }
    protected static JsOnsOptions create() {
      return JavaScriptObject.createObject().cast();
    }
  }

  public static void compile(Element element) {
    CdvUtils.log("COMPILING ELEMENT " + element);
    compileImpl(element);
  }
  
  protected static native void compileImpl(Element element) /*-{
    $wnd.ons.compile(element);
  }-*/;

  public static void popPage() {
    popPageImpl();
  }
  
  protected static native void popPageImpl() /*-{
    $wnd.ons.navigator.popPage();    
  }-*/;

  public static void onPageChanging(final Delegate<JavaScriptObject> delegate) {
    onPageChangingImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute(jso);
      }
    });
  }
  
  protected static native void onPageChangingImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
//    $wnd.glbDebugHook(event);
    });
    $wnd.ons.navigator.on('prepush', jsCallback);    
    $wnd.ons.navigator.on('prepop', jsCallback);    
  }-*/;

  public static void onPageChanged(final Delegate<JavaScriptObject> delegate) {
    onPageChangedImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute(jso);
      }
    });
  }
  
  protected static native void onPageChangedImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
      $wnd.glbDebugHook(event);
    });
    $wnd.ons.navigator.on('postpush', jsCallback);    
//  $wnd.ons.navigator.on('postpop', jsCallback);    
  }-*/;
  
  public static Element getCurrentPageElement() {
    JavaScriptObject currentPage = getCurrentPageImpl();
    return getPageElement(currentPage);
  }
  
  public static Element getPageElement(JavaScriptObject page) {
    JavaScriptObject pageElement = GwtUtils.getJsPropertyJso(page, "element");
    Element pageContext = GwtUtils.getJsPropertyJso(pageElement, "context").cast();
    return pageContext;
  }
  
  public static Element getInnerPageElement(Element pageElement) {
    NodeList<Element> nodeList = pageElement.getElementsByTagName("div");
    for (int it = 0; it < nodeList.getLength(); it++) {
      Element innerElem = nodeList.getItem(it);
      if (innerElem.getClassName().contains("ons-page-inner")) {
        return innerElem;
      }
    }
    return null;
  }

  public static String getCurrentPageName() {
    JavaScriptObject page = getCurrentPageImpl();
    if (page == null) {
      return null;
    }
    String name = GwtUtils.getJsPropertyString(page, "name");
    return name;
  }

  public static JavaScriptObject getCurrentPage() {
    return getCurrentPageImpl();
  }

  protected static native JavaScriptObject getCurrentPageImpl() /*-{
    return $wnd.ons.navigator.getCurrentPage();    
  }-*/;

  public static void resetToPage(String pageId) {
    CdvUtils.log("RESET TO PAGE " + pageId);
    JsOnsOptions options = JsOnsOptions.create();
    resetToPageImpl(pageId, options);
  }

  protected static native void resetToPageImpl(String pageId, JsOnsOptions options) /*-{
    $wnd.ons.navigator.resetToPage(pageId, options);    
  }-*/;

  public static JsArray<JavaScriptObject> getPages() {
    return getPagesImpl().cast();
  }

  protected static native JavaScriptObject getPagesImpl() /*-{
    return $wnd.ons.navigator.getPages();    
  }-*/;

  public static void onPagePoping(final Delegate<JavaScriptObject> delegate) {
    onPagePopingImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute(jso);
      }
    });
  }
  
  protected static native void onPagePopingImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    $wnd.ons.navigator.on('prepop', jsCallback);    
  }-*/;
  
  public static void cancelEvent(JavaScriptObject event) {
    cancelEventImpl(event);
  }
  
  protected static native void cancelEventImpl(JavaScriptObject event) /*-{
    event.cancel();
  }-*/;

  public static void destroyPage(JavaScriptObject page) {
    destroyPageImpl(page);
  }

  protected static native void destroyPageImpl(JavaScriptObject page) /*-{
    page.destroy();    
  }-*/;
  
  public static void logNavigator(String prompt) {
    JsArray<JavaScriptObject> pages = OnsenUi.getPages();
    for (int it = 0; it < pages.length(); it++) {
      JavaScriptObject page = pages.get(it);
      String pageName = GwtUtils.getJsPropertyString(page, "name");
      CdvUtils.log( prompt + " - " + it + " - " + pageName);
    }
    CdvUtils.log("CURRENT PAGE NAME = " + getCurrentPageName());
  }
  
  public static void destroyPage(String name) {
    JsArray<JavaScriptObject> pages = getPages();
    for (int it = 0; it < pages.length(); it++) {
      JavaScriptObject page = pages.get(it);
      String pageName = GwtUtils.getJsPropertyString(page, "name");
      if (name.equals(pageName)) {
        CdvUtils.log("DESTROYED PAGE " + pageName);
        OnsenUi.destroyPage(page);
        break;
      }
    }
  }
  
}
