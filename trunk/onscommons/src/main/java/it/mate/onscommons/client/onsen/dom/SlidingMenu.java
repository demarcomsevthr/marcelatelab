package it.mate.onscommons.client.onsen.dom;

import it.mate.onscommons.client.utils.CdvUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class SlidingMenu extends JavaScriptObject {

  protected SlidingMenu() { }
  
  public final void setMainPage(String pageId) {
    Options options = Options.create();
    setMainPage(pageId, options);
  }
  
  protected final void setMainPage(String pageId, Options options) {
    CdvUtils.log("PUSHING PAGE " + pageId);
    setMainPageImpl(pageId, options);
  }
  
  protected final native void setMainPageImpl(String pageId, Options options) /*-{
    this.setMainPage(pageId, options);    
  }-*/;
  
  public final void setMenuPage(String pageId) {
    Options options = Options.create();
    CdvUtils.log("MENU PAGE " + pageId);
    setMenuPageImpl(pageId, options);
  }
  
  protected final native void setMenuPageImpl(String pageId, Options options) /*-{
    this.setMenuPage(pageId, options);    
  }-*/;
  
}
