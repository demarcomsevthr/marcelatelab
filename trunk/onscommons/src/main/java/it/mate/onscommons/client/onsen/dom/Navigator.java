package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Navigator extends JavaScriptObject {

  protected Navigator() { }
  
  public final void pushPage(String pageId) {
    Options options = Options.create();
    pushPage(pageId, options);
  }
  
  protected final void pushPage(String pageId, Options options) {
    CdvUtils.log("PUSHING PAGE " + pageId);
    pushPageImpl(pageId, options);
  }
  
  protected final native void pushPageImpl(String pageId, Options options) /*-{
    this.pushPage(pageId, options);    
  }-*/;
  
  public final void onPagePushed(final Delegate<NavigatorEvent> delegate) {
    onPagePushedImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected final native void onPagePushedImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    this.on('postpush', jsCallback);    
  }-*/;

  public final void onPagePoping(final Delegate<NavigatorEvent> delegate) {
    onPagePopingImpl(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        delegate.execute((NavigatorEvent)jso.cast());
      }
    });
  }
  
  protected final native void onPagePopingImpl(JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    this.on('prepop', jsCallback);    
  }-*/;
  
  public final void insertPage(int index, String pageId) {
    Options options = Options.create();
    options.setHoge("hoge");
    CdvUtils.log("INSERTING PAGE " + pageId + " AT " + index);
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
    CdvUtils.log("RESET TO PAGE " + pageId);
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
  
  public final void log(String prompt) {
    JsArray<Page> pages = getPages();
    for (int it = 0; it < pages.length(); it++) {
      Page page = pages.get(it);
      String pageName = page.getName();
      CdvUtils.log( prompt + " - " + it + " - " + pageName);
    }
    CdvUtils.log("CURRENT PAGE NAME = " + getCurrentPage().getName());
    CdvUtils.log("CURRENT PAGE INDEX = " + getCurrentPage().getIndex());
  }
}
