package it.mate.phgcommons.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
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

}
