package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.onsen.dom.SlidingMenu;
import it.mate.onscommons.client.ui.HasTapHandlerImpl;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsPage;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.onscommons.client.utils.TransitionUtils;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.ElementCallback;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;


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
  
  public final static String ANIMATION_NATIVE_PUSH = "nativePush";
  
  public final static String ANIMATION_NATIVE_POP = "nativePop";
  
  private static final int REFRESH_CURRENT_PAGE_DELAY = 0;
  
  private static boolean compilationSuspended;
  
  private static long lastElementCompilationTime = -1;
  
  protected static JavaScriptObject jQueryDocumentSelector = null;
  
  private static int uniqueElementId = 0;
  
  public static void initializeOnsen(OnsenReadyHandler handler) {
    if (!initialized) {
      initialized = true;
      initOnsenImpl(handler);
      for (Delegate<Void> initHandler : initializationHandlers) {
        initHandler.execute(null);
      }
      ensureJQueryDocumentSelector();
    }
  }
  
  public static void addInitializationHandler(Delegate<Void> handler) {
    initializationHandlers.add(handler);
  }
  
  public static boolean isInitialized() {
    return initialized;
  }
  
  protected static native void initOnsenImpl(OnsenReadyHandler handler) /*-{
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('CALLING ONSEN BOOTSTRAP');
    $wnd.ons.bootstrap();
    var jsHandler = $entry(function() {
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('ONSEN READY HANDLER');
      handler.@it.mate.onscommons.client.onsen.OnsenReadyHandler::onReady()();
    });
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
  
  public static void setNavigator(Navigator navigator) {
    OnsenUi.navigator = navigator;
  }
  
  public static boolean isNavigatorLayoutPattern() {
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
  
  public static boolean isSlidingMenuLayoutPattern() {
    return slidingMenu != null;
  }
  
  public static void suspendCompilations() {
    OnsenUi.compilationSuspended = true;
  }
  
  public static void resumeCompilations() {
    OnsenUi.compilationSuspended = false;
  }
  
  public static void compileElement(Element element) {
    lastElementCompilationTime = System.currentTimeMillis();
    if (compilationSuspended) {
      PhgUtils.log("COMPILATION DISABLED");
      return;
    }
    if (element == null) {
      return;
    }
    if (element.getNodeName() == null) {
      return;
    }
    PhgUtils.log("COMPILING ELEMENT " + element);
    compileElementImpl(element);
  }
  
  public static void refreshCurrentPage() {
    refreshCurrentPage(null);
  }
  
  public static void refreshCurrentPage(final Delegate<JavaScriptObject> delegate) {
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        long currentTime = System.currentTimeMillis();
        if (lastElementCompilationTime > currentTime - REFRESH_CURRENT_PAGE_DELAY) {
          refreshCurrentPage(delegate);
          return;
        }
        PhgUtils.log("LAST CREATED PAGE ID = " + OnsPage.getLastCreatedPage().getElement().getId());
        OnsenUi.onAvailableElement(OnsPage.getLastCreatedPage().getElement().getId(), new Delegate<Element>() {
          public void execute(Element pageElement) {
            resumeCompilations();
            PhgUtils.log("COMPILING PAGE ELEMENT " + pageElement + " WITH delegate " + delegate);

            addInitCallbackImpl(pageElement, new JSOCallback() {
              public void handle(JavaScriptObject event) {
                PhgUtils.log("PAGE IS COMPILED");
                fadeInCurrentPage();
                if (delegate != null) {
                  delegate.execute(event);
                }
              }
            });
            
            compileElementImpl(pageElement);
          }
        });
      }
    });
  }
  
  public static void fadeInCurrentPage() {
    JavaScriptObject results = queryElements(".ons-fadein");
    if (results != null) {
      JsArray<Element> elements = results.cast();
      for (int it = 0; it < elements.length(); it++) {
        Element fadingElement = elements.get(it);
        PhgUtils.log("EXECUTING FADEIN ON ELEMENT " + fadingElement);
        TransitionUtils.fadeIn(fadingElement, 1000);
      }
    }
  }
  
  protected static void ensureJQueryDocumentSelector() {
    if (jQueryDocumentSelector == null) {
      initJQueryDocumentSelector();
    }
  }
  
  protected static native void initJQueryDocumentSelector() /*-{
    @it.mate.onscommons.client.onsen.OnsenUi::jQueryDocumentSelector = $wnd.$($doc);
  }-*/;
  
  protected static native void addInitCallbackImpl(Element element, JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    var elementId = element.id;
    @it.mate.onscommons.client.onsen.OnsenUi::jQueryDocumentSelector.on('ons-page:init', '#'+elementId, jsCallback);
  }-*/;
    
  protected static native JavaScriptObject queryElements(String query) /*-{
    return $wnd.$(query);
  }-*/;

  protected static native void compileElementImpl(Element element) /*-{
    $wnd.ons.compile(element);
  }-*/;

  public static void goToPreviousPlace(PlaceController placeController, Place initialPlace) {
    if (OnsenUi.isNavigatorLayoutPattern()) {
      OnsenUi.getNavigator().popPage();
    } else {
      if (placeController instanceof PlaceControllerWithHistory) {
        PlaceControllerWithHistory placeControllerHistory = (PlaceControllerWithHistory)placeController;
        Place prevPlace = placeControllerHistory.getPreviousPlace();
        if (prevPlace == null) {
          prevPlace = initialPlace;
        }
        placeControllerHistory.goToWithEvent(new OnsPlaceChangeEvent(prevPlace).setAnimation(OnsenUi.ANIMATION_NATIVE_POP));
        return;
      } else {
        placeController.goTo(initialPlace);
      }
    }
  }
  
  public static Element getParentPageElement(Element childElement) {
    Element parentElement = childElement.getParentElement();
    while (parentElement != null) {
      if (parentElement.getTagName().equalsIgnoreCase("ons-page")) {
        return parentElement;
      }
      parentElement = parentElement.getParentElement();
    }
    return null;
  }
  
  protected static native void setBeforeAppendChildCallback(ElementCallback callback) /*-{
    var defAppendChild = $wnd.Element.prototype.appendChild;
    $wnd.Element.prototype.appendChild = function(){
      callback.@it.mate.phgcommons.client.utils.callbacks.ElementCallback::handle(Lcom/google/gwt/dom/client/Element;)(arguments[0]);
      defAppendChild.apply(this, arguments);
    };
  }-*/;

  /**
   * 
   * DA UTILIZZARE NEI METODI SET DEI WIDGET ONS
   * 
   */
  public static void onAttachedElement(Widget widget, Delegate<Element> delegate) {
    Element attachedElement = GwtUtils.getElementById(widget.getElement().getId());
    if (attachedElement != null) {
      delegate.execute(attachedElement);
    } else {
      delegate.execute(widget.getElement());
    }
  }

  public static void onAvailableElement(Widget widget, Delegate<Element> delegate) {
    onAvailableElement(widget.getElement(), delegate);
  }
  
  public static void onAvailableElement(Element element, Delegate<Element> delegate) {
    onAvailableElement(element.getId(), delegate);
  }

  public static void onAvailableElement(String id, Delegate<Element> delegate) {
    GwtUtils.onAvailable(id, delegate);
  }

  protected static String createUniqueElementId() {
    uniqueElementId ++;
    return "ons-uid-" + uniqueElementId;
  }

  public static void ensureId(Element element) {
    if (element.getId() == null || "".equals(element.getId())) {
      element.setId(OnsenUi.createUniqueElementId());
    }
  }
  
  public static void addTapHandler(Widget widget, TapHandler handler) {
    HasTapHandlerImpl impl = new HasTapHandlerImpl(widget.getElement());
    impl.addTapHandler(handler);
  }
  
}
