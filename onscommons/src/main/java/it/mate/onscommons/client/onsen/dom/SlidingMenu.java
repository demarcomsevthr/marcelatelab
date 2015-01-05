package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.JSONUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class SlidingMenu extends JavaScriptObject {

  protected SlidingMenu() { }
  
  public final void setMainPage(String pageId) {
    String animation = null;
    setMainPage(pageId, animation);
  }
  
  public final void setMainPage(String pageId, String animation) {
    Options options = Options.create();
    options.setCloseMenu(true);
    if (animation != null) {
      if (OnsenUi.ANIMATION_REVERSE_SLIDE.equals(animation)) {
        GwtUtils.setJsPropertyJso(options, "animation", getReverseSlideAnimationImpl());
      } else {
        options.setAnimation(animation);
      }
    }
    setMainPage(pageId, options);
  }
  
  protected final native JavaScriptObject getReverseSlideAnimationImpl() /*-{
    return new $wnd.ReverseSlideTransitionAnimator();    
  }-*/;
  
  protected final void setMainPage(String pageId, Options options) {
    CdvUtils.log("PUSHING PAGE " + pageId + " " + JSONUtils.stringify(options));
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
  
  public final void toggleMenu() {
    toggleMenuImpl();
  }
  
  protected final native void toggleMenuImpl() /*-{
    this.toggleMenu();    
  }-*/;

}
