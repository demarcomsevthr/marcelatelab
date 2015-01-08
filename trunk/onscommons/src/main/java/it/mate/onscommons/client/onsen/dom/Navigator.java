package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Navigator extends JavaScriptObject {

  protected Navigator() { }
  
  public final void pushPage(String pageId) {
    Options options = Options.create();
    pushPage(pageId, options);
  }
  
  protected final void pushPage(String pageId, Options options) {
    PhgUtils.log("PUSHING PAGE " + pageId);
    pushPageImpl(pageId, options);
  }
  
  protected final native void pushPageImpl(String pageId, Options options) /*-{
    this.pushPage(pageId, options);    
  }-*/;
  
  public final void insertPage(int index, String pageId) {
    Options options = Options.create();
    options.setHoge("hoge");
    PhgUtils.log("INSERTING PAGE " + pageId + " AT " + index);
    insertPageImpl(index, pageId, options);
  }
  
  protected final native void insertPageImpl(int index, String pageId, Options options) /*-{
    this.insertPage(index, pageId, options);    
  }-*/;
  
  public final void popPage() {
    popPageImpl();
  }
  
  protected final native void popPageImpl() /*-{
    this.popPage();    
  }-*/;
  
  public final Page getCurrentPage() {
    return getCurrentPageImpl();
  }

  protected final native Page getCurrentPageImpl() /*-{
    return this.getCurrentPage();    
  }-*/;
  
  public final void resetToPage(String pageId) {
    PhgUtils.log("RESET TO PAGE " + pageId);
    Options options = Options.create();
    resetToPageImpl(pageId, options);
  }

  protected final native void resetToPageImpl(String pageId, Options options) /*-{
    this.resetToPage(pageId, options);    
  }-*/;
  
  public final JsArray<Page> getPages() {
    return getPagesImpl().cast();
  }

  protected final native JavaScriptObject getPagesImpl() /*-{
    return this.getPages();    
  }-*/;
  
  public final void onAfterPagePush(final Delegate<NavigatorEvent> delegate) {
    onAfterPagePushImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected final native void onAfterPagePushImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    this.on('postpush', jsCallback);    
  }-*/;

  public final void onBeforePagePop(final Delegate<NavigatorEvent> delegate) {
    onBeforePagePopImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected final native void onBeforePagePopImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    this.on('prepop', jsCallback);    
  }-*/;
  
  public final void log(String prompt) {
    JsArray<Page> pages = getPages();
    for (int it = 0; it < pages.length(); it++) {
      Page page = pages.get(it);
      String pageName = page.getName();
      PhgUtils.log( prompt + " - " + it + " - " + pageName);
    }
    PhgUtils.log("CURRENT PAGE NAME = " + getCurrentPage().getName());
    PhgUtils.log("CURRENT PAGE INDEX = " + getCurrentPage().getIndex());
  }
}
