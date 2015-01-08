package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.Carousel;
import it.mate.onscommons.client.utils.HammerUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsCarousel extends HTMLPanel {

  private final static String TAG_NAME = "ons-carousel";
  
  public OnsCarousel() {
    this(TAG_NAME, "");
  }
  
  public OnsCarousel(String html) {
    this(TAG_NAME, html);
  }
  
  protected OnsCarousel(String tag, String html) {
    super(tag, html);
    getElement().addClassName(TAG_NAME);
    PhgUtils.ensureId(getElement());
    getElement().setAttribute("var", "app.onsCarousel");
  }
  
  private void addItem(Widget widget, Element element) {
    super.add(widget, element);
  }

  @Override
  public void add(final Widget widget) {
    
    
    GwtUtils.onAvailable(getElement().getId(), new Delegate<Element>() {
      public void execute(Element element) {
        PhgUtils.log("adding item to carousel " + element);
        addItem(widget, element);
        
        OnsenUi.compileElement(element);
        
        if (getController() != null) {
          getController().refresh();
        }
        
        
        HammerUtils.addDragHandlerImpl(widget.getElement(), "swipe", new JSOCallback() {
          public void handle(JavaScriptObject jso) {
            PhgUtils.log("oh");
          }
        });
        
      }
    });
    
    
    
//  super.add(widget, getElement());
    
    /*
    String id = getElement().getId();
    Element carouselElement = DOM.getElementById(id);
    Element itemElement = widget.getElement();
    carouselElement.appendChild(itemElement);
    OnsenUi.compileElement(carouselElement);
    */

    /*
    if (widget instanceof OnsCarouselItem) {
      PhgUtils.log("add item");
      OnsCarouselItem item = (OnsCarouselItem)widget;
      item.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          PhgUtils.log("tapped");
        }
      });
    }
    */
    
  }
  
  public void addDragHandlerTest() {
    onControllerAvailable(new Delegate<Carousel>() {
      public void execute(Carousel controller) {
        PhgUtils.log("adding drag handler");
        
        HammerUtils.addDragHandlerImpl(getElement(), "swipe", new JSOCallback() {
          public void handle(JavaScriptObject touch) {
            int pageX = GwtUtils.getJsPropertyInt(touch, "pageX");
            int pageY = GwtUtils.getJsPropertyInt(touch, "pageY");
            PhgUtils.log("drag at " + pageX + "," + pageY);
          }
        });
        
      }
    });
  }
  
  public void setAutoscroll(String value) {
    getElement().setAttribute("auto-scroll", value);
  }

  public void setDraggable(String value) {
    getElement().setAttribute("draggable", value);
  }
  
  public final native Carousel getController() /*-{
    return $wnd.app.onsCarousel;    
  }-*/;
  
  protected void onControllerAvailable (final Delegate<Carousel> delegate) {
    final long t0 = System.currentTimeMillis();
    new Timer() {
      public void run() {
        long t1 = System.currentTimeMillis();
        Carousel carousel = OnsCarousel.this.getController();
        if (carousel != null) {
          this.cancel();
          delegate.execute(carousel);
        } else if (t1 - t0 > 10000) {
          this.cancel();
        }
      }
    }.scheduleRepeating(100);
  }

}
