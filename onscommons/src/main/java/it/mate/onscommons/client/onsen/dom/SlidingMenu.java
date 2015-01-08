package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

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
    PhgUtils.log("PUSHING PAGE " + pageId + " " + JSONUtils.stringify(options));
    setMainPageImpl(pageId, options);
  }
  
  protected final native void setMainPageImpl(String pageId, Options options) /*-{
    this.setMainPage(pageId, options);    
  }-*/;
  
  public final void setMenuPage(String pageId) {
    Options options = Options.create();
    PhgUtils.log("MENU PAGE " + pageId);
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

  public final void setSwipeable(boolean value) {
    setSwipeableImpl(value);
  }
  
  protected final native void setSwipeableImpl(boolean value) /*-{
    this.setSwipeable(value);    
  }-*/;

}
