package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.onsen.dom.SlidingMenu;
import it.mate.onscommons.client.ui.HasTapHandlerImpl;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsPage;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.onscommons.client.utils.TransitionUtils;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.ElementCallback;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
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
  
  private static int fadeinDuration = 500;
  
  private static boolean removeToolbarDuringPageRefresh = true; 
  
  private static boolean doLog = false;
  
  private static boolean preventTapHandlerWherScrollerMoves = false;
  
  public static final String EXCLUDE_FROM_PAGE_REFRESH_ATTR = "excludeFromPageRefresh";
  
  public static void initializeOnsen(OnsenReadyHandler handler) {
    if (!initialized) {
      initialized = true;
      initOnsenImpl(handler);
      for (Delegate<Void> initHandler : initializationHandlers) {
        initHandler.execute(null);
      }
      ensureJQueryDocumentSelector();
      
      
      // TODO: dovrebbe servire solo per AND 5+
      // verificare se da fastidio su altri device
      if (OsDetectionUtils.isAndroid() && PhgUtils.getDeviceVersion().startsWith("5")) {
        setPreventTapHandlerWherScrollerMoves(true);
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
      OnsenUi.navigator = navigator.getControllerSingleton();
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
  
  public static void setSlidingMenu(SlidingMenu slidingMenu) {
    OnsenUi.slidingMenu = slidingMenu;
  }
  
  public static boolean isSlidingMenuLayoutPattern() {
    return slidingMenu != null;
  }
  
  public static void suspendCompilations() {
    OnsenUi.compilationSuspended = true;
    HasTapHandlerImpl.setUseDocEventListener(true);
  }
  
  public static void resumeCompilations() {
    OnsenUi.compilationSuspended = false;
  }

  public static void compileElementImmediately(Element element) {
    compileElementInternal(element);
  }
  
  public static void compileElement(Element element) {
    lastElementCompilationTime = System.currentTimeMillis();
    if (compilationSuspended) {
      if (doLog) PhgUtils.log("COMPILATION DISABLED");
      return;
    }
    compileElementInternal(element);
  }
  
  private static void compileElementInternal(Element element) {
    if (element == null) {
      return;
    }
    if (element.getNodeName() == null) {
      return;
    }
    if (doLog) PhgUtils.log("COMPILING ELEMENT " + element);
    compileElementImpl(element);
  }
  
  public static void getCurrentPageElement(Delegate<Element> delegate) {
    if (OnsPage.getLastCreatedPage() == null) {
      delegate.execute(null);
    } else {
      OnsenUi.onAvailableElement(OnsPage.getLastCreatedPage().getElement().getId(), delegate);
    }
  }
  
  public static void refreshCurrentPage() {
    refreshCurrentPage(100);
  }
  
  public static void refreshCurrentPage(int delay) {
    refreshCurrentPage(delay, null);
  }
  
  private static String currentPageId = null;
  
  public static void setCurrentPageId(String currentPageId) {
    PhgUtils.log("CURRENT PAGE ID = " + currentPageId);
    OnsenUi.currentPageId = currentPageId;
  }
  
  public static void refreshCurrentPage(final int delay, final Delegate<JavaScriptObject> delegate) {
    GwtUtils.deferredExecution(delay, new Delegate<Void>() {
      public void execute(Void element) {
        long currentTime = System.currentTimeMillis();
        if (lastElementCompilationTime > currentTime - REFRESH_CURRENT_PAGE_DELAY) {
          refreshCurrentPage(delay, delegate);
          return;
        }
        if (doLog) PhgUtils.log("LAST CREATED PAGE ID = " + OnsPage.getLastCreatedPage().getElement().getId());

        // 18/05/2015
//      String actualCurrentPageId = OnsPage.getLastCreatedPage().getElement().getId();
        String actualCurrentPageId = currentPageId;
        
        PhgUtils.log("FINDING PAGE WITH ID " + actualCurrentPageId);
        
        OnsenUi.onAvailableElement(actualCurrentPageId, new Delegate<Element>() {
          public void execute(final Element pageElement) {
            
            resumeCompilations();
            
            //TODO: [13/05/2015]
            //TODO: ATTENZIONE
            //TODO: ATTENZIONE
            //TODO: ATTENZIONE
            //TODO: 
            //TODO: INTRODOTTA LA SEGUENTE OTTIMIZZAZIONE:
            //TODO:     PRIMA DI RICOMPILARE LA PAGE RIMUOVO LA TOOLBAR E LA REINSERISCO DOPO LA COMPILAZIONE (!!!)
            
            if (doLog) PhgUtils.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            
            final ObjectWrapper<Element> toolbarElement = new ObjectWrapper<Element>();
            
            if (removeToolbarDuringPageRefresh) {
              for (int it = 0; it < pageElement.getChildCount(); it++) {
                Element pageChildElement = pageElement.getChild(it).cast();
                if (pageChildElement.getNodeName() != null && pageChildElement.getNodeName().equalsIgnoreCase("ons-toolbar")) {
                  toolbarElement.set(pageChildElement);
                  if (doLog) PhgUtils.log("REMOVING TOOLBAR ELEMENT " + toolbarElement.get());
                  toolbarElement.get().getParentElement().removeChild(toolbarElement.get());
                }
              }
            }
            
            final List<ExcludedElement> excludedElements = new ArrayList<OnsenUi.ExcludedElement>();
            findAndSaveExcludedElements(pageElement, excludedElements);

            addInitCallbackImpl(pageElement, new JSOCallback() {
              public void handle(JavaScriptObject event) {
                
                if (toolbarElement.get() != null) {
                  if (doLog) PhgUtils.log("REINSERTING TOOLBAR ELEMENT " + toolbarElement.get());
                  pageElement.insertFirst(toolbarElement.get());
                }
                
                if (excludedElements != null) {
                  insertExcludedElements(excludedElements);
                }
                
                if (doLog) PhgUtils.log("PAGE IS COMPILED");
                fadeInCurrentPage();
                if (delegate != null) {
                  delegate.execute(event);
                }
              }
            });
            

            if (doLog) PhgUtils.log("COMPILING PAGE ELEMENT " + pageElement);
            compileElementImpl(pageElement);
            
            if (doLog) PhgUtils.log("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            
            GwtUtils.deferredExecution(500, new Delegate<Void>() {
              public void execute(Void element) {
                HasTapHandlerImpl.setUseDocEventListener(false);
              }
            });
           
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
        if (doLog) PhgUtils.log("EXECUTING FADEIN ON ELEMENT " + fadingElement);
        TransitionUtils.fadeIn(fadingElement, fadeinDuration);
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

  public static void goToPreviousPlace(PlaceController placeController, Place initialPlace, boolean usePlaceControllerHistory) {
    if (OnsenUi.isNavigatorLayoutPattern() && !usePlaceControllerHistory) {
      if (doLog) PhgUtils.log("GO TO PREVIOUS PLACE USING NAVIGATOR POP");
      OnsenUi.getNavigator().popPage();
    } else {
      if (doLog) PhgUtils.log("GO TO PREVIOUS PLACE USING PLACE CONTROLLER");
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
  
  public static void setFadeinDuration(int fadeinDuration) {
    OnsenUi.fadeinDuration = fadeinDuration;
  }
  
  public static void setPreventTapHandlerWherScrollerMoves(boolean preventTapHandlerWherScrollerMoves) {
    OnsenUi.preventTapHandlerWherScrollerMoves = preventTapHandlerWherScrollerMoves;
  }
  
  public static boolean isPreventTapHandlerWherScrollerMoves() {
    return preventTapHandlerWherScrollerMoves;
  }
  
  private static class ExcludedElement {
    private Element parentElement;
    private Element element;
    private Node nextSibling;
  }
  
  private static void findAndSaveExcludedElements(Element rootElement, List<ExcludedElement> excludedElements) {
    for (int it = 0; it < rootElement.getChildCount(); it++) {
      Element childElement = rootElement.getChild(it).cast();
      if (childElement.getNodeType() != Node.TEXT_NODE) {
        if (childElement.getNodeName() != null && childElement.getAttribute(EXCLUDE_FROM_PAGE_REFRESH_ATTR) != null && childElement.getAttribute(EXCLUDE_FROM_PAGE_REFRESH_ATTR).trim().length() > 0) {
          PhgUtils.log("FOUND EXCLUDED ELEMENT " + childElement.getNodeName()+"#"+childElement.getId());
          ExcludedElement excludedElement = new ExcludedElement();
          excludedElement.element = childElement;
          excludedElement.parentElement = childElement.getParentElement();
          excludedElement.nextSibling = childElement.getNextSibling();
          excludedElements.add(excludedElement);
          childElement.getParentElement().removeChild(childElement);
        } else {
          findAndSaveExcludedElements(childElement, excludedElements);
        }
      }
    }
  }
  
  private static void insertExcludedElements(List<ExcludedElement> excludedElements) {
    for (ExcludedElement excludedElement : excludedElements) {
      Element childElement = excludedElement.element;
      PhgUtils.log("INSERTING EXCLUDED ELEMENT " + childElement.getNodeName()+"#"+childElement.getId());
      if (excludedElement.nextSibling != null) {
        excludedElement.parentElement.insertBefore(excludedElement.element, excludedElement.nextSibling);
      } else {
        excludedElement.parentElement.appendChild(excludedElement.element);
      }
    }
  }
  
  public static void setDoLog(boolean doLog) {
    OnsenUi.doLog = doLog;
    OnsList.setDoLog(doLog);
  }
  
}
