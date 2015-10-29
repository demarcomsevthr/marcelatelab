package it.mate.onscommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.OnsEventUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsDialog;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

public class OnsDatePickerYearDialog {

  private OnsDialog dialog;
  
  private static final String TOUCHSTART_EVENT_NAME = OsDetectionUtils.isDesktop() ? "mousedown" : "touchstart"; 
  
  private static final String TOUCHEND_EVENT_NAME = OsDetectionUtils.isDesktop() ? "mouseup" : "touchend"; 
  
  private String carouselId;
  
  private int currentYear;
  
  private int activeIndex;
  
  private static final int VISIBLE_YEARS_BEFORE = 10; 
  
  private static final int VISIBLE_YEARS_AFTER = 20; 
  
  public OnsDatePickerYearDialog(int currentYear) {
    this.currentYear = currentYear;
    createYearDialog();
  }
  
  public static String getDialogHeight() {
    int height = Window.getClientHeight() - Window.getClientHeight() / 6;
    return ""+height+"px";
  }

  private void createYearDialog() {
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        carouselId = OnsenUi.createUniqueElementId();
        String html = "<ons-page>";
        html += "<ons-carousel id='"+carouselId+"' var='onsDatePickerYearDialogCarousel' direction='vertical' class='ons-date-picker-year-dialog-carousel' item-height='12%' swipeable>";
        // il primo item vuoto serve per And4.2
        html += "<ons-carousel-item>";
        html += "</ons-carousel-item>";
        html += "</ons-carousel>";
        html += "</ons-page>";
        dialog = OnsDialogUtils.createDialog(html, false, null, "ons-date-picker-year-dialog");
        init();
      }
    });
  }
  
  private void init() {
    OnsenUi.onAvailableElement(carouselId, new Delegate<Element>() {
      public void execute(Element carouselElement) {
        carouselElement.setInnerHTML("");
        for (int year = (currentYear - VISIBLE_YEARS_BEFORE); year < (currentYear + VISIBLE_YEARS_AFTER); year++) {
          carouselElement.appendChild(createCarouselItemElement(year));
        }
        GwtUtils.deferredExecution(200, new Delegate<Void>() {
          public void execute(Void element) {
            setActiveIndex(VISIBLE_YEARS_BEFORE);
            refreshCarousel();
            initCarousel(new JSOCallback() {
              public void handle(JavaScriptObject event) {
                PhgUtils.log("OnPostchange: event {"+
                      "carousel="+ GwtUtils.getJsPropertyObject(event, "carousel") +
                      ", activeIndex="+ GwtUtils.getJsPropertyInt(event, "activeIndex") +
                      ", lastActiveIndex="+ GwtUtils.getJsPropertyInt(event, "lastActiveIndex")
                      +"}");
                final int newActiveIndex = GwtUtils.getJsPropertyInt(event, "activeIndex");
                if (newActiveIndex != activeIndex) {
                  OnsenUi.onAvailableElement(carouselId, new Delegate<Element>() {
                    public void execute(Element carouselElement) {
                      if (newActiveIndex < activeIndex) {
                        int diff = activeIndex - newActiveIndex;
                        for (int it = 0; it < diff; it++) {
                          currentYear -= 1;
                          int year = currentYear - VISIBLE_YEARS_BEFORE;
                          carouselElement.insertFirst(createCarouselItemElement(year));
                          carouselElement.getLastChild().removeFromParent();
                          PhgUtils.log("Added year " + year);
                        }
                      } else if (newActiveIndex > activeIndex) {
                        int diff = newActiveIndex - activeIndex;
                        for (int it = 0; it < diff; it++) {
                          currentYear += 1;
                          int year = currentYear + VISIBLE_YEARS_AFTER;
                          carouselElement.appendChild(createCarouselItemElement(year));
                          carouselElement.getFirstChild().removeFromParent();
                          PhgUtils.log("Added year " + year);
                        }
                      }
                      setActiveIndex(VISIBLE_YEARS_BEFORE);
                      refreshCarousel();
                    }
                  });
                }
              }
            });
          }
        });
      }
    });
  }
  
  private Element createCarouselItemElement(int year) {
    String id = OnsenUi.createUniqueElementId();
    Element carouselItemElem = DOM.createElement("ons-carousel-item");
    carouselItemElem.setId(id);
    carouselItemElem.setAttribute("data-value", ""+year);
    carouselItemElem.setInnerHTML(""+year);
    OnsenUi.onAvailableElement(id, new Delegate<Element>() {
      public void execute(Element carouselItemElement) {
        OnsEventUtils.addHandler(carouselItemElement, TOUCHSTART_EVENT_NAME, new NativeGestureHandler() {
          public void on(NativeGestureEvent event) {
            event.getTarget().setPropertyInt("lastX", event.getTarget().getAbsoluteLeft());
            event.getTarget().setPropertyInt("lastY", event.getTarget().getAbsoluteTop());
          }
        });
        OnsEventUtils.addHandler(carouselItemElement, TOUCHEND_EVENT_NAME, new NativeGestureHandler() {
          public void on(NativeGestureEvent event) {
            int lastX = event.getTarget().getPropertyInt("lastX");
            int lastY = event.getTarget().getPropertyInt("lastY");
            if (lastX == event.getTarget().getAbsoluteLeft() && lastY == event.getTarget().getAbsoluteTop()) {
              PhgUtils.log("SELECTED ITEM: " + event.getTarget());
              PhgUtils.log("SELECTED ITEM DATA-VALUE: " + event.getTarget().getAttribute("data-value"));
              dialog.hide();
            }
          }
        });
      }
    });
    return carouselItemElem;
  }
  
  protected final native void refreshCarousel() /*-{
    $wnd.onsDatePickerYearDialogCarousel.refresh();
  }-*/;
  
  protected final native void initCarousel(JSOCallback onPostchange) /*-{
    var jsOnPostchange = $entry(function(e) {
      onPostchange.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
    });
    $wnd.onsDatePickerYearDialogCarousel.on("postchange", jsOnPostchange);
  }-*/;

  protected void setActiveIndex(int index) {
    activeIndex = index;
    _setActiveIndex(activeIndex);
  }
    
  protected final native void _setActiveIndex(int index) /*-{
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("setting active index = " + index);
    $wnd.onsDatePickerYearDialogCarousel.setActiveCarouselItemIndex(index);
  }-*/;

}
