package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.HasTouchHandlers;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.Button;

public class SmartButton extends Composite implements HasClickHandlers, HasTouchHandlers,
      HasText {

  private Widget impl;
  
  private final boolean useAnchor = MGWT.getOsDetection().isAndroid();
  
  private static boolean changeColorOnClick = false;
  
  private static int revertOriginalColorDelay = -1;
  
  private final String originalColor;
  
  public SmartButton() {
    
    if (useAnchor) {
      impl = new Anchor();
    } else {
      impl = new Button();
    }
    
    initWidget(impl);

    setStyleName("mgwt-SmartButton");
    addStyleName("mgwt-Button");
    addStyleName("mgwt-Button-round");
    originalColor = getElement().getStyle().getColor();
    
    if (useAnchor) {
      if (changeColorOnClick) {
        addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            SmartButton.this.getElement().getStyle().setColor("white");
            if (revertOriginalColorDelay > 0) {
              GwtUtils.deferredExecution(revertOriginalColorDelay, new Delegate<Void>() {
                public void execute(Void element) {
                  SmartButton.this.getElement().getStyle().setColor(originalColor);
                }
              });
            }
          }
        });
      }
    }

  }
  
  public SmartButton(String html) {
    this();
    setHTML(html);
  }
  
  public void setText(String text) {
    setHTML(text);
  }
  
  public String getText() {
    return getElement().getInnerHTML();
  }

  public void setHTML(String html) {
    getElement().setInnerHTML(html);
  }
  
  public void setRound(String round) {
    // do nothing
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    if (useAnchor) {
      return getAnchorImpl().addClickHandler(handler);
    } else {
      return getButtonImpl().addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
          boolean touchPresent = event.getTouches() != null && event.getTouches().length() > 0;
          int pageX = touchPresent ? event.getTouches().get(0).getPageX() : ((Widget)event.getSource()).getAbsoluteLeft();
          int pageY = touchPresent ? event.getTouches().get(0).getPageY() : ((Widget)event.getSource()).getAbsoluteTop();
          NativeEvent nativeEvent = Document.get().createClickEvent(0, pageX, pageY, pageX, pageY, false, false, false, false);
          DomEvent.fireNativeEvent(nativeEvent, SmartButton.this);
        }
      });
    }
  }
  
  public class WrappedTouchStartEvent extends TouchStartEvent {
    
  }

  @Override
  public HandlerRegistration addTouchStartHandler(final TouchStartHandler handler) {
    if (useAnchor) {
      return addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
//        DomEvent.fireNativeEvent(createTouchEvent(Document.get(), "touchstart"), SmartButton.this);
//        handler.onTouchStart(null);
          handler.onTouchStart(new WrappedTouchStartEvent());
        }
      });
    } else {
      return getButtonImpl().addTouchStartHandler(handler);
    }
  }

  public class WrappedTouchEndEvent extends TouchEndEvent {
    
  }

  @Override
  public HandlerRegistration addTouchEndHandler(final TouchEndHandler handler) {
    if (useAnchor) {
      return addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          handler.onTouchEnd(new WrappedTouchEndEvent());
        }
      });
    } else {
      return getButtonImpl().addTouchEndHandler(handler);
    }
  }

  @Override
  public HandlerRegistration addTouchHandler(final TouchHandler handler) {
    if (useAnchor) {
      return addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          handler.onTouchStart(new WrappedTouchStartEvent());
        }
      });
    } else {
      return getButtonImpl().addTouchHandler(handler);
    }
  }
  
  @Override
  public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
    if (useAnchor) {
      return null;
    } else {
      return getButtonImpl().addTouchMoveHandler(handler);
    }
  }

  @Override
  public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler) {
    if (useAnchor) {
      return null;
    } else {
      return getButtonImpl().addTouchCancelHandler(handler);
    }
  }
  
  private Anchor getAnchorImpl() {
    if (useAnchor) {
      return (Anchor)impl;
    } else {
      return null;
    }
  }
  
  private Button getButtonImpl() {
    if (useAnchor) {
      return null;
    } else {
      return (Button)impl;
    }
  }
  
  
  // Per farlo funzionare bisognare verificare se si trova su win/mobile
  // gli eventi touch sono definiti solo sul browser dei mobile
  
  private native NativeEvent createTouchEvent(Document doc, String type) /*-{
    var evt = doc.createEvent('TouchEvent');
//  evt.initTouchEvent(type, true, true);
    evt.initUIEvent(type, true, true);
    return evt;
  }-*/;
  

}
