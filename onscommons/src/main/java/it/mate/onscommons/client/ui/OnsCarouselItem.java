package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.event.TouchEventUtils;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class OnsCarouselItem extends HTMLPanel implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  private HandlerRegistration dragStartHandlerReg;
  
  private HandlerRegistration dragEndHandlerReg;
  
  private boolean duringDragOperation = false;
  
  public OnsCarouselItem() {
    this("");
  }

  public OnsCarouselItem(Widget widget) {
    this("");
    add(widget);
  }

  public OnsCarouselItem(String html) {
    super("ons-carousel-item", html);
    hasTapHandlerImpl = new HasTapHandlerImpl(this);
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

  @Override
  public HandlerRegistration addTapHandler(final TapHandler handler) {

    if (dragStartHandlerReg == null) {
      dragStartHandlerReg = TouchEventUtils.addDragStartHandler(getElement(), new NativeGestureHandler() {
        public void on(NativeGestureEvent event) {
          duringDragOperation = true;
        }
      });
    }
    
    /*
    if (dragEndHandlerReg == null) {
      dragEndHandlerReg = TouchEventUtils.addDragStartHandler(getElement(), new NativeGestureHandler() {
        public void on(NativeGestureEvent event) {
          duringDragOperation = false;
        }
      });
    }
    */
    
    return hasTapHandlerImpl.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        if (!duringDragOperation) {
          handler.onTap(event);
        }
        duringDragOperation = false;
      }
    });
    
  }
  
  public void setModifier(String modifier) {
    getElement().setAttribute("modifier", modifier);
  }
  
}
