package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEvent;

public class TouchUtils {
  
  public static void fireClickEventFromTouchEvent(HasClickHandlers target, TouchEvent<?> touchEvent) {
    boolean touchPresent = touchEvent.getTouches() != null && touchEvent.getTouches().length() > 0;
    int pageX = touchPresent ? touchEvent.getTouches().get(0).getPageX() : ((Widget)touchEvent.getSource()).getAbsoluteLeft();
    int pageY = touchPresent ? touchEvent.getTouches().get(0).getPageY() : ((Widget)touchEvent.getSource()).getAbsoluteTop();
    NativeEvent clickEvent = Document.get().createClickEvent(0, pageX, pageY, pageX, pageY, false, false, false, false);
    DomEvent.fireNativeEvent(clickEvent, target);
  }

  public static void fireClickEventFromTapEvent(HasClickHandlers target, TapEvent tapEvent) {
    int pageX = tapEvent.getStartX();
    int pageY = tapEvent.getStartY();
    NativeEvent clickEvent = Document.get().createClickEvent(0, pageX, pageY, pageX, pageY, false, false, false, false);
    DomEvent.fireNativeEvent(clickEvent, target);
  }
  

  
  /**
   * 11/12/2013
   * 
   * Ho constatato che ogni tanto "perde" degli eventi, ovvero sembra che gli eventi vengono processati 
   * pero non scatta il rendering grafico dell'elemento.
   * Constato anche che "spostando" il focus su altri elementi nella pagina (tappandoli), 
   * viene fatto il rendering "accodato" (lo noto soprattutto nel simulatore dove i tempi sono piu "dilatati").
   * Detto cio provo a simulare programmaticamente uno spostamento di focus su un elemento invisibile della pagina.
   * Lo attivo solo per Android (su Ios non ho riscontro di questa issue).
   * 
   */

  /*
  public static void applyFocusPatch() {
    executePatch20131211(0);
  }
  */
  
  public static void applyFocusPatch() {
    GwtUtils.deferredExecution(50, new Delegate<Void>() {
      public void execute(Void element) {
        executePatch20131211(0);
      }
    });
  }
  
  /**
   * see http://stackoverflow.com/questions/8335834/how-can-i-hide-the-android-keyboard-using-javascript
   */
  public static void applyQuickFixFocusPatch() {
    PhonegapUtils.log("applying focus patch with readonly - start");
    applyQuickFixFocusPatchImpl(JQuery.select("input"));
    applyQuickFixFocusPatchImpl(JQuery.select("textarea"));
    PhonegapUtils.log("applying focus patch with readonly - end");
  }
  
  
  private static FocusPanel focusPatch1$focusWidget;
  private static void executePatch20131211(int delay) {
    if (focusPatch1$focusWidget != null) {
      //PhonegapUtils.log("hiding focus patch widget");
      focusPatch1$focusWidget.setVisible(false);
    }
    //PhonegapUtils.log("creating focus patch widget");
    focusPatch1$focusWidget = new FocusPanel();
    focusPatch1$focusWidget.addStyleName("phg-InvisibleTouch");
    RootPanel.get().add(focusPatch1$focusWidget);
    GwtUtils.deferredExecution(delay, new Delegate<Void>() {
      public void execute(Void element) {
        focusPatch1$focusWidget.setFocus(true);
        NativeEvent nativeEvent = null;
        if (OsDetectionUtils.isDesktop()) {
          nativeEvent = createClickEvent();
        } else {
          nativeEvent = createTouchEvent();
        }
        DomEvent.fireNativeEvent(nativeEvent, focusPatch1$focusWidget);
      }
    });
  }
  
  /*
   * ORIGINAL VERSION [TESTED 27/01/2014]
  private static FocusPanel focusPatch1$focusWidget;
  private static void executePatch20131211(int delay) {
    if (!OsDetectionUtils.isAndroid())
      return;
    if (focusPatch1$focusWidget == null) {
      focusPatch1$focusWidget = new FocusPanel();
      focusPatch1$focusWidget.setWidth("1px");
      focusPatch1$focusWidget.setHeight("1px");
      RootPanel.get().add(focusPatch1$focusWidget);
    }
    GwtUtils.deferredExecution(delay, new Delegate<Void>() {
      public void execute(Void element) {
        focusPatch1$focusWidget.setFocus(true);
        NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
        DomEvent.fireNativeEvent(clickEvent, focusPatch1$focusWidget);
      }
    });
  }
   */
  
  /************************
   * See com.google.gwt.dom.client.DOMImplStandard.createMouseEvent()
   * e http://stackoverflow.com/questions/5713393/creating-and-firing-touch-events-on-a-touch-enabled-browser
   */
  private static native NativeEvent createTouchEvent() /*-{
    var evt = $doc.createEvent('UIEvent');
    evt.initUIEvent('touchstart', true, true);  
    return evt;
  }-*/;
  
  private static NativeEvent createClickEvent() {
    return Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
  }
  
  private static native void applyQuickFixFocusPatchImpl(JQuery element) /*-{
    element.attr('readonly', 'readonly'); // Force keyboard to hide on input field.
    element.attr('disabled', 'true'); // Force keyboard to hide on textarea field.
    setTimeout(function() {
        element.blur();  //actually close the keyboard
        // Remove readonly attribute after keyboard is hidden.
        element.removeAttr('readonly');
        element.removeAttr('disabled');
    }, 100);
  }-*/;

}
