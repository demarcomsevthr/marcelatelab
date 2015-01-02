package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.dom.NavigatorEvent;
import it.mate.onscommons.client.onsen.dom.Options;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;


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
    Options options = Options.create();
    pushPage(pageId, options);
  }
  
  protected static void pushPage(String pageId, Options options) {
    CdvUtils.log("PUSHING PAGE " + pageId);
    pushPageImpl(pageId, options);
  }
  
  protected static native void pushPageImpl(String pageId, Options options) /*-{
    $wnd.ons.navigator.pushPage(pageId, options);    
  }-*/;
  
  public static void insertPage(int index, String pageId) {
    Options options = Options.create();
    options.setHoge("hoge");
    CdvUtils.log("INSERTING PAGE " + pageId + " AT " + index);
    insertPageImpl(index, pageId, options);
  }
  
  protected static native void insertPageImpl(int index, String pageId, Options options) /*-{
    $wnd.ons.navigator.insertPage(index, pageId, options);    
  }-*/;

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

  public static void onPageChanging(final Delegate<NavigatorEvent> delegate) {
    onPageChangingImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
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

  public static void onPagePushed(final Delegate<NavigatorEvent> delegate) {
    onPagePushedImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected static native void onPagePushedImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
      $wnd.glbDebugHook(event);
    });
    $wnd.ons.navigator.on('postpush', jsCallback);    
//  $wnd.ons.navigator.on('postpop', jsCallback);    
  }-*/;
  
  /*
  public static Element getCurrentPageElement() {
    Page currentPage = getCurrentPageImpl();
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
  */

  public static Page getCurrentPage() {
    return getCurrentPageImpl();
  }

  protected static native Page getCurrentPageImpl() /*-{
    return $wnd.ons.navigator.getCurrentPage();    
  }-*/;

  public static void resetToPage(String pageId) {
    CdvUtils.log("RESET TO PAGE " + pageId);
    Options options = Options.create();
    resetToPageImpl(pageId, options);
  }

  protected static native void resetToPageImpl(String pageId, Options options) /*-{
    $wnd.ons.navigator.resetToPage(pageId, options);    
  }-*/;

  public static JsArray<Page> getPages() {
    return getPagesImpl().cast();
  }

  protected static native JavaScriptObject getPagesImpl() /*-{
    return $wnd.ons.navigator.getPages();    
  }-*/;

  public static void onPagePoping(final Delegate<NavigatorEvent> delegate) {
    onPagePopingImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected static native void onPagePopingImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    $wnd.ons.navigator.on('prepop', jsCallback);    
  }-*/;
  
  public static void cancelEvent(NavigatorEvent event) {
    cancelEventImpl(event);
  }
  
  protected static native void cancelEventImpl(NavigatorEvent event) /*-{
    event.cancel();
  }-*/;

  public static void destroyPage(Page page) {
    destroyPageImpl(page);
  }

  protected static native void destroyPageImpl(Page page) /*-{
    page.destroy();    
  }-*/;
  
  public static void logNavigator(String prompt) {
    JsArray<Page> pages = OnsenUi.getPages();
    for (int it = 0; it < pages.length(); it++) {
      Page page = pages.get(it);
      String pageName = page.getName();
      CdvUtils.log( prompt + " - " + it + " - " + pageName);
    }
    CdvUtils.log("CURRENT PAGE NAME = " + OnsenUi.getCurrentPage().getName());
    CdvUtils.log("CURRENT PAGE INDEX = " + OnsenUi.getCurrentPage().getIndex());
  }
  
  public static void destroyPage(String name) {
    JsArray<Page> pages = getPages();
    for (int it = 0; it < pages.length(); it++) {
      Page page = pages.get(it);
      String pageName = page.getName();
      if (name.equals(pageName)) {
        CdvUtils.log("DESTROYED PAGE " + pageName);
        OnsenUi.destroyPage(page);
        break;
      }
    }
  }
  
}
