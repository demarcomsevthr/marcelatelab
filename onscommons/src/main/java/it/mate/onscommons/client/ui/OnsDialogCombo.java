package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.StringUtils;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.OnsEventUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.OnsDialogUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

public abstract class OnsDialogCombo {
  
  private OnsDialog dialog;
  
  private String carouselId;
  
  private int internalActiveIndex;
  
  private int externalActiveIndex;
  
  private int itemCount;
  
  private ItemDelegate itemDelegate;
  
  private List<Item> items = null;
  
  private int visibleItemsBefore = 10; 
  
  private int visibleItemsAfter = 20; 
  
  private boolean lazyLoading = false;
  
  private static final String TOUCHSTART = OsDetectionUtils.isDesktop() ? "mousedown" : "touchstart"; 
  
  private static final String TOUCHEND = OsDetectionUtils.isDesktop() ? "mouseup" : "touchend"; 
  
  public static interface ItemDelegate {
    public Item getItem(int index);
  }
  
  public static class Item {
    private SafeHtml html;
    private String value;
    public Item(SafeHtml html, String value) {
      this.html = html;
      this.value = value;
    }
    public SafeHtml getHtml() {
      return html;
    }
    public String getValue() {
      return value;
    }
  }

  public OnsDialogCombo(List<Item> items) {
    this(items, items.size(), null, -1, -1);
  }
  
  public OnsDialogCombo(int itemCount, ItemDelegate itemDelegate) {
    this(null, itemCount, itemDelegate, -1, -1);
  }
  
  public OnsDialogCombo(int visibleItemsBefore, int visibleItemsAfter, ItemDelegate itemDelegate) {
    this(null, (visibleItemsBefore + visibleItemsAfter), itemDelegate, visibleItemsBefore, visibleItemsAfter);
  }
  
  protected void setLazyLoading(boolean lazyLoading) {
    this.lazyLoading = lazyLoading;
  }
  
  protected OnsDialogCombo(List<Item> items, int itemCount, ItemDelegate itemDelegate, int visibleItemsBefore, int visibleItemsAfter) {
    this.items = items;
    this.itemCount = itemCount;
    this.itemDelegate = itemDelegate;
    if (visibleItemsBefore > -1) {
      this.visibleItemsBefore = visibleItemsBefore; 
    } else {
      this.visibleItemsBefore = itemCount / 2; 
    }
    if (visibleItemsAfter > -1) {
      this.visibleItemsAfter = visibleItemsAfter; 
    } else {
      this.visibleItemsAfter = itemCount / 2; 
    }
    createDialog();
  }
  
  protected OnsDialogCombo() {

  }
  
  public abstract void onItemSelected(Item item);
  
  private void createDialog() {
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        carouselId = OnsenUi.createUniqueElementId();
        String html = "<ons-page>";
        html += "<ons-carousel id='"+carouselId+"' var='onsComboDialogCarousel' direction='vertical' class='ons-combo-dialog-carousel' item-height='12%' swipeable>";
        // il primo item vuoto serve per And4.2
        html += "<ons-carousel-item>";
        html += "</ons-carousel-item>";
        html += "</ons-carousel>";
        html += "</ons-page>";
        dialog = OnsDialogUtils.createDialog(html, false, null, "ons-combo-dialog");
        populateCarousel();
      }
    });
  }
  
  public static String getDialogHeight() {
    int height = Window.getClientHeight() - Window.getClientHeight() / 6;
    return ""+height+"px";
  }

  private void populateCarousel() {
    OnsenUi.onAvailableElement(carouselId, new Delegate<Element>() {
      public void execute(Element carouselElement) {
        
        carouselElement.setInnerHTML("");
        
        if (items != null) {
          for (int index = 0; index < items.size(); index++) {
            Item item = items.get(index);
            carouselElement.appendChild(createCarouselItemElement(item.getHtml(), item.getValue(), null));
          }
        } else {
          for (int index = 0; index < itemCount; index++) {
            Item item = itemDelegate.getItem(index - visibleItemsBefore);
            carouselElement.appendChild(createCarouselItemElement(item.getHtml(), item.getValue(), null));
          }
        }
        
        GwtUtils.deferredExecution(100, new Delegate<Void>() {
          public void execute(Void element) {
            externalActiveIndex = 0;
            setActiveIndex(visibleItemsBefore);
            refreshCarousel();
            if (lazyLoading) {
              setOnPostchange(new JSOCallback() {
                public void handle(JavaScriptObject event) {
                  onPostchange(event);
                }
              });
            }
          }
        });
        
      }
    });
  }
  
  private void onPostchange(JavaScriptObject event) {
    PhgUtils.log("OnPostchange: event {"+
        " carousel="+ GwtUtils.getJsPropertyObject(event, "carousel") +
        ", activeIndex="+ GwtUtils.getJsPropertyInt(event, "activeIndex") +
        ", lastActiveIndex="+ GwtUtils.getJsPropertyInt(event, "lastActiveIndex") +
        ", internalActiveIndex="+ internalActiveIndex
        +"}");
    final int newActiveIndex = GwtUtils.getJsPropertyInt(event, "activeIndex");
    if (newActiveIndex != internalActiveIndex) {
      OnsenUi.onAvailableElement(carouselId, new Delegate<Element>() {
        public void execute(Element carouselElement) {
          if (newActiveIndex < internalActiveIndex) {
            int diff = internalActiveIndex - newActiveIndex;
            for (int it = 0; it < diff; it++) {
              externalActiveIndex -= 1;
              Item item = itemDelegate.getItem(externalActiveIndex - visibleItemsBefore);
              carouselElement.insertFirst(createCarouselItemElement(item.getHtml(), item.getValue(), null));
              carouselElement.getLastChild().removeFromParent();
            }
          } else if (newActiveIndex > internalActiveIndex) {
            int diff = newActiveIndex - internalActiveIndex;
            for (int it = 0; it < diff; it++) {
              externalActiveIndex += 1;
              Item item = itemDelegate.getItem(externalActiveIndex + visibleItemsAfter - 1);
              carouselElement.appendChild(createCarouselItemElement(item.getHtml(), item.getValue(), null));
              carouselElement.getFirstChild().removeFromParent();
            }
          }
          setActiveIndex(visibleItemsBefore);
          refreshCarousel();
        }
      });
    }
  }
  
  private Element createCarouselItemElement(SafeHtml html, String dataValue, Integer index) {
    String id = OnsenUi.createUniqueElementId();
    Element carouselItemElem = DOM.createElement("ons-carousel-item");
    carouselItemElem.setId(id);
    carouselItemElem.setAttribute("data-value", dataValue);
    if (index != null) {
      carouselItemElem.setAttribute("data-item-index", ""+index);
    }
    carouselItemElem.setInnerHTML(html.asString());
    OnsenUi.onAvailableElement(id, new Delegate<Element>() {
      public void execute(Element carouselItemElement) {
        OnsEventUtils.addHandler(carouselItemElement, TOUCHSTART, new NativeGestureHandler() {
          public void on(NativeGestureEvent event) {
            event.getTarget().setPropertyInt("lastX", event.getTarget().getAbsoluteLeft());
            event.getTarget().setPropertyInt("lastY", event.getTarget().getAbsoluteTop());
          }
        });
        OnsEventUtils.addHandler(carouselItemElement, TOUCHEND, new NativeGestureHandler() {
          public void on(NativeGestureEvent event) {
            int lastX = event.getTarget().getPropertyInt("lastX");
            int lastY = event.getTarget().getPropertyInt("lastY");
            if (lastX == event.getTarget().getAbsoluteLeft() && lastY == event.getTarget().getAbsoluteTop()) {
              String dataValue = event.getTarget().getAttribute("data-value");
              String dataItemIndex = event.getTarget().getAttribute("data-item-inex");
              PhgUtils.log("SELECTED ITEM: " + event.getTarget());
              PhgUtils.log("SELECTED ITEM DATA-VALUE: " + dataValue);
              PhgUtils.log("SELECTED ITEM INDEX: " + dataItemIndex);
              dialog.hide();
              if (StringUtils.isNumber(dataItemIndex) && items != null) {
                int index = Integer.parseInt(dataItemIndex);
                onItemSelected(items.get(index));
              } else {
                onItemSelected(new Item(SafeHtmlUtils.fromString(event.getTarget().getInnerHTML()), dataValue));
              }
            }
          }
        });
      }
    });
    return carouselItemElem;
  }
  
  protected final native void refreshCarousel() /*-{
    $wnd.onsComboDialogCarousel.refresh();
  }-*/;
  
  protected final native void setOnPostchange(JSOCallback onPostchange) /*-{
    var jsOnPostchange = $entry(function(e) {
      onPostchange.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
    });
    $wnd.onsComboDialogCarousel.on("postchange", jsOnPostchange);
  }-*/;
  
  protected void setActiveIndex(int index) {
    internalActiveIndex = index;
    _setActiveIndex(internalActiveIndex);
  }
    
  protected final native void _setActiveIndex(int index) /*-{
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("setting active index = " + index);
    $wnd.onsComboDialogCarousel.setActiveCarouselItemIndex(index);
  }-*/;
  
}
