package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.onsen.dom.SlidingMenu;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.onscommons.client.utils.CdvUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;


public class OnsenUi {
  
  private static boolean initialized = false;
  
  private static List<Delegate<Void>> initializationHandlers = new ArrayList<Delegate<Void>>();
  
  private static Navigator navigator;
  
  private static SlidingMenu slidingMenu;
  
  public final static String ANIMATION_SLIDE = "slide";
  
  public final static String ANIMATION_REVERSE_SLIDE = "reverseSlide";
  
  public final static String ANIMATION_LIFT = "lift";
  
  public final static String ANIMATION_FADE = "fade";
  
  public final static String ANIMATION_NONE = "none";
  
  public final static String ANIMATION_REVEAL = "reveal";
  
  public final static String ANIMATION_PUSH = "push";
  
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
    @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN BOOTSTRAP');
    $wnd.ons.bootstrap();
    var jsHandler = $entry(function() {
      @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN READY HANDLER');
      handler.@it.mate.onscommons.client.onsen.OnsenReadyHandler::onReady()();
    });
    @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('ONSEN READY');
    $wnd.ons.ready(jsHandler);
  }-*/;
  
  public static Navigator getNavigator() {
    if (navigator == null) {
      OnsNavigator navigator = new OnsNavigator();
      navigator.attachToBody();
      OnsenUi.navigator = navigator.getController();
    }
    return navigator;
  }
  
  public static boolean isNavigatorLayout() {
    return navigator != null;
  }
  
  public static SlidingMenu getSlidingMenu() {
    if (slidingMenu == null) {
      OnsSlidingMenu slidingMenu = new OnsSlidingMenu();
      slidingMenu.attachToBody();
      OnsenUi.slidingMenu = slidingMenu.getController();
    }
    return slidingMenu;
  }
  
  public static boolean isSlidingMenuLayout() {
    return slidingMenu != null;
  }
  
  public static void compileElement(Element element) {
    CdvUtils.log("COMPILING ELEMENT " + element);
    compileElementImpl(element);
  }
  
  protected static native void compileElementImpl(Element element) /*-{
    $wnd.ons.compile(element);
  }-*/;

}
