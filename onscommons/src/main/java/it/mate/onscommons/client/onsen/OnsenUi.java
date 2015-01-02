package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.utils.CdvUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;


public class OnsenUi {
  
  private static boolean initialized = false;
  
  private static List<Delegate<Void>> initializationHandlers = new ArrayList<Delegate<Void>>();
  
  private static Navigator navigator;
  
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
  
  private static void ensureNavigator() {
    if (navigator == null) {
      OnsNavigator navigator = new OnsNavigator();
      RootPanel.get().add(navigator);
      OnsenUi.compileElement(navigator.getElement());
      OnsenUi.navigator = getNavigatorImpl();
    }
  }
  
  public static Navigator getNavigator() {
    ensureNavigator();
    return navigator;
  }
  
  protected static native Navigator getNavigatorImpl() /*-{
    return $wnd.ons.navigator;
  }-*/;
  
  public static void compileElement(Element element) {
    CdvUtils.log("COMPILING ELEMENT " + element);
    compileElementImpl(element);
  }
  
  protected static native void compileElementImpl(Element element) /*-{
    $wnd.ons.compile(element);
  }-*/;

}
