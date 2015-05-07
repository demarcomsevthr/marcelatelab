package it.mate.onscommons.client.onsen.dom;

import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class SlidingMenu extends JavaScriptObject {
  
  protected static final double DURATION = 0.21;

  protected SlidingMenu() { }
  
  public final void setMainPage(String pageId, String animation) {
    Options options = Options.create();
    options.setCloseMenu(true);
    JavaScriptObject jsAnimation = null;
    String msg = "";
    if (animation != null) {
      if (OnsenUi.ANIMATION_NATIVE_PUSH.equals(animation)) {
        jsAnimation = getPushAnimationImpl();
        msg = " WITH **** NEW **** NATIVE PUSH ANIMATION";
//      msg = " WITH NATIVE PUSH ANIMATION";
      } else if (OnsenUi.ANIMATION_NATIVE_POP.equals(animation)) {
        jsAnimation = getPopAnimationImpl();
        msg = " WITH NATIVE POP ANIMATION";
      }
    }
    PhgUtils.log("PUSHING PAGE " + pageId + " " + msg + " " + JSONUtils.stringify(options));
    setMainPageImpl(pageId, options, jsAnimation);
  }
  
  protected final native void setMainPageImpl(String pageId, Options options, JavaScriptObject animation) /*-{
    if (animation != null) {
      options.callback = animation;
      @it.mate.onscommons.client.onsen.dom.SlidingMenu::hideBlackMask(Lcom/google/gwt/dom/client/Element;)(this._mainPage[0]);
    }
    this.setMainPage(pageId, options);    
  }-*/;
  
  protected static void hideBlackMask(Element mainPageElem) {
    if (mainPageElem == null) {
      return;
    }
    Element menuElem = mainPageElem.getParentElement();
    if (menuElem == null) {
      return;
    }
    for (int it = 0; it < menuElem.getChildCount(); it++) {
      Element menuChildElem = menuElem.getChild(it).cast();
      String color = menuChildElem.getStyle().getBackgroundColor();
      if ("black".equalsIgnoreCase(color)) {
        menuChildElem.getStyle().setBackgroundColor("transparent");
      }
    }
  }
  
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
  
  protected final native JavaScriptObject getPushAnimationImpl__TEST_2__ () /*-{
    var menu = this;
    var animation = function() {
      $wnd.animit(menu._mainPage[0])
        .queue({
          css: {
            transform: 'translate3D(100%, 0, 0)',
          },
          duration: 0
        })
        .queue({
          css: {
            transform: 'translate3D(0, 0, 0)',
          },
          duration: @it.mate.onscommons.client.onsen.dom.SlidingMenu::DURATION,
          timing: 'cubic-bezier(.1, .7, .4, 1)'
        })
        .play();
    }
    return animation;
  }-*/;

  protected final native JavaScriptObject getPushAnimationImpl__TEST_1__ () /*-{
    
      var menu = this;
    
      var backgroundMask = $wnd.angular.element(
        '<div style="z-index: 2; position: absolute; width: 100%;' +
        'height: 100%; background-color: black; opacity: 0;"></div>'
      );
      
      var timing = 'cubic-bezier(.1, .7, .4, 1)';
      var duration = 0.3; 
      var blackMaskOpacity = 0.4;
      
      var mainPage = menu._mainPage[0];
      
      var mask = backgroundMask.remove();
      mainPage.parentNode.insertBefore(mask[0], mainPage.nextSibling);
      
      var animation = function() {
        
        $wnd.animit.runAll(

          $wnd.animit(mask[0])
            .queue({
              opacity: 0,
              transform: 'translate3d(0, 0, 0)'
            })
            .queue({
              opacity: blackMaskOpacity
            }, {
              duration: duration,
              timing: timing
            })
            .resetStyle()
            .queue(function(done) {
              mask.remove();
              done();
            }),

          $wnd.animit(mainPage)
            .queue({
              css: {
                transform: 'translate3D(100%, 0, 0)',
              },
              duration: 0
            })
            .queue({
              css: {
                transform: 'translate3D(0, 0, 0)',
              },
              duration: duration,
              timing: timing
            })
            .resetStyle()
            .wait(0.2)
            .queue(function(done) {
              callback();
              done();
            })
            
        );
        
        
      }
      return animation;
      
      
    
  }-*/;
  
  
  protected final native JavaScriptObject getPushAnimationImpl() /*-{
    var menu = this;
    var animation = function() {
      $wnd.animit(menu._mainPage[0])
        .queue({
          css: {
            transform: 'translate3D(100%, 0, 0)',
          },
          duration: 0
        })
        .queue({
          css: {
            transform: 'translate3D(0, 0, 0)',
          },
          duration: @it.mate.onscommons.client.onsen.dom.SlidingMenu::DURATION
        })
        .play();
    }
    return animation;
  }-*/;

  protected final native JavaScriptObject getPopAnimationImpl() /*-{
    var menu = this;
    var animation = function() {
      $wnd.animit(menu._mainPage[0])
        .queue({
          css: {
            transform: 'translate3D(-100%, 0, 0)',
          },
          duration: 0
        })
        .queue({
          css: {
            transform: 'translate3D(0, 0, 0)',
          },
          duration: @it.mate.onscommons.client.onsen.dom.SlidingMenu::DURATION
        })
        .play();
    }
    return animation;
  }-*/;

}
