package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

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
   * 
   */
  private static FocusPanel touchUtils$focusPatch$focusWidget;
  
  public static void applyFocusPatch() {
    executePatch20131211(0);
  }
  private static void executePatch20131211(int delay) {
    if (touchUtils$focusPatch$focusWidget != null) {
      touchUtils$focusPatch$focusWidget.setVisible(false);
    }
    touchUtils$focusPatch$focusWidget = new FocusPanel();
    touchUtils$focusPatch$focusWidget.addStyleName("phg-InvisibleTouch");
    RootPanel.get().add(touchUtils$focusPatch$focusWidget);
    GwtUtils.deferredExecution(delay, new Delegate<Void>() {
      public void execute(Void element) {
        touchUtils$focusPatch$focusWidget.setFocus(true);
        NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
        DomEvent.fireNativeEvent(clickEvent, touchUtils$focusPatch$focusWidget);
      }
    });
  }
  
  public static void applyFocusPatch2() {
    executePatch20140127(0);
  }
  private static void executePatch20140127(int delay) {
    if (touchUtils$focusPatch$focusWidget != null) {
      PhonegapUtils.log("hiding focus patch widget");
      touchUtils$focusPatch$focusWidget.setVisible(false);
    }
    PhonegapUtils.log("creating focus patch widget");
    touchUtils$focusPatch$focusWidget = new FocusPanel();
    touchUtils$focusPatch$focusWidget.addStyleName("phg-InvisibleTouch");
    RootPanel.get().add(touchUtils$focusPatch$focusWidget);
    GwtUtils.deferredExecution(delay, new Delegate<Void>() {
      public void execute(Void element) {
        touchUtils$focusPatch$focusWidget.setFocus(true);
        NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
        DomEvent.fireNativeEvent(clickEvent, touchUtils$focusPatch$focusWidget);
        PhonegapUtils.log("fired click event on focus patch widget");
      }
    });
  }
  

}
