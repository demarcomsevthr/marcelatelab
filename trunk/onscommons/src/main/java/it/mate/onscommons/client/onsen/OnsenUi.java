package it.mate.onscommons.client.onsen;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.onsen.dom.SlidingMenu;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.ElementCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;


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
  
  public static void initializeOnsen(OnsenReadyHandler handler) {
    if (!initialized) {
      initialized = true;
      createEnsureUniqueIdCallback();
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
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('ONSEN BOOTSTRAP');
    $wnd.ons.bootstrap();
    var jsHandler = $entry(function() {
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('ONSEN READY HANDLER');
      handler.@it.mate.onscommons.client.onsen.OnsenReadyHandler::onReady()();
    });
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('ONSEN READY HOOK');
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
  
  public static void compileElement(Element element) {
    if (element == null) {
      return;
    }
    if (element.getNodeName() == null) {
      return;
    }
    PhgUtils.log("COMPILING ELEMENT " + element);
    compileElementImpl(element);
//  traverseAndCompileChildElements(element);
  }
  
  /* provato per l'insert di elementi nella setModel (OrderItemComposeView))
  protected static void traverseAndCompileChildElements(Element element) {
    if (element == null) {
      return;
    }
    if (element.getChildNodes() == null || element.getChildNodes().getLength() == 0) {
      return;
    }
    for (int it = 0; it < element.getChildNodes().getLength(); it++) {
      Element childElem = (com.google.gwt.user.client.Element)element.getChildNodes().getItem(it);
      if (childElem.getNodeName().toLowerCase().startsWith("ons")) {
        PhgUtils.log("COMPILING ELEMENT " + childElem);
        compileElementImpl(childElem);
      }
      traverseAndCompileChildElements(childElem);
    }
  }
  */
  
  protected static native void compileElementImpl(Element element) /*-{
    $wnd.ons.compile(element);
  }-*/;

  /* SPOSTATO IN TouchEventUtils
  public static native boolean isContained(final Element elem, String containerId) /*-{
    do {
      if (elem.id == containerId) {
        return true;
      }
    } while(elem = elem.parentElement );
    return false;
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

  // LO TOLGO PERCHE' NON SERVE, L'ID DEVE ESSERE ASSICURATO DENTRO I WIDGET GWT
  public static void createEnsureUniqueIdCallback() {
    /*
    setBeforeAppendChildCallback(new ElementCallback() {
      public void handle(Element element) {
        if (element != null) {
          PhgUtils.ensureId(element);
//        PhgUtils.log("before append child " + element);
        }
      }
    });
    */
  }

  /*
  public static void createDialog (final OnsDialog dialog, final Delegate<Dialog> delegate) {
    final String dialogId = dialog.getElement().getId();
    GwtUtils.onAvailable(dialogId, new Delegate<Element>() {
      public void execute(Element dialogElem) {
        String templateId = dialogId + "Template";
        OnsTemplate template = new OnsTemplate(templateId);
        template.add(dialog);
        RootPanel.get().add(template);
        templateId = template.getElement().getId();
        PhgUtils.log("finding template " + templateId);
        GwtUtils.onAvailable(templateId, new Delegate<Element>() {
          public void execute(Element templateElem) {
            OnsenUi.compileElement(templateElem);
            PhgUtils.log("creating dialog " + templateElem.getId());
            createDialogImpl(templateElem.getId(), dialog.getVarName(), new JSOCallback() {
              public void handle(JavaScriptObject jso) {
                Dialog dialog = jso.cast();
                PhgUtils.log("returning dialog " + dialog);
                delegate.execute(dialog);
              }
            });
          }
        });
      }
    });
  }
  */

  /**
   * NB: nella callback il reference dlg e' undefined!
   */
  /*
  protected static native void createDialogImpl(String templateId, String varName, JSOCallback callback) /*-{
    $wnd.ons.createDialog(templateId).then(function(dlg) {
      var dialog = $wnd[varName];
      dialog.show();
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('showing dialog ' + dialog);
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(dialog);
    });
  }-*/;

}
