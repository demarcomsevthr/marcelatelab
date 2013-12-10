package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.EventUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.Date;

import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class CalendarDialog {
  
  @SuppressWarnings("deprecation")
  private class Month {
    private Date date;
    protected Month() {
      date = new Date();
      CalendarUtil.setToFirstDayOfMonth(date);
    }
    private Month(Date date) {
      this.date = date;
    }
    protected Month clone() {
      return new Month(CalendarUtil.copyDate(date));
    }
    public int getYear() {
      return date.getYear() + 1900;
    }
    public int getMonth() {
      return date.getMonth() + 1;
    }
    public Month addMonths(int months) {
      CalendarUtil.addMonthsToDate(date, months);
      return this;
    }
    public int getLastDayOfMonth() {
      Date temp = CalendarUtil.copyDate(date);
      CalendarUtil.addMonthsToDate(temp, 1);
      CalendarUtil.addDaysToDate(temp, -1);
      return temp.getDate();
    }
  }
  
  private PopupPanel popup;
  
  private ScrollPanel scrollPanel;
  
  private int lastX;
  
  private boolean internalScrolling = false;
  
  private boolean needRecreatePopup = false;
  
  private boolean assistedScrollPending = false;
  
  private boolean scrollPending = false;
  
  private Month curMonth;
  
  private PopupPanel backPopup = null;
  
  private int width = 280;
  
  private int height = 300;
  
  private TouchHTML selectedDateField;
  
  private Date selectedDate = new Date();
  
  private HandlerRegistration modalHandlerRegistration;
  
  public CalendarDialog() {
    curMonth = new Month();
  }
  
  public void show() {
    initPopup();
  }
  
  public void hide() {
    popup.hide();
    /*
    if (modalHandlerRegistration != null) {
      GwtUtils.deferredExecution(500, new Delegate<Void>() {
        public void execute(Void element) {
          PhonegapUtils.log("removing modalHandler");
          modalHandlerRegistration.removeHandler();
        }
      });
    }
    */
    EventUtils.removeModalHandler(modalHandlerRegistration);
  }
  
  private void initPopup() {
    
    backPopup = popup;
    
    popup = new PopupPanel();
    popup.addStyleName("phg-CalendarDialog");
    setHidden(popup);
    
    makePopupModal();
    
    SimpleContainer container = new SimpleContainer();
    
    scrollPanel = new ScrollPanel();
    
    scrollPanel.setWidth(width+"px");
    scrollPanel.setHeight(height+"px");
    scrollPanel.setScrollingEnabledY(false);
    scrollPanel.setScrollingEnabledX(true);
    scrollPanel.setHideScrollBar(true);
    
    SimplePanel wrapperPanel = new SimplePanel();
    wrapperPanel.setHeight(height+"px");
    wrapperPanel.setWidth((width*3)+"px");
    scrollPanel.setWidget(wrapperPanel);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.setHeight(height+"px");
    horizontalPanel.setWidth((width*3)+"px");
    wrapperPanel.setWidget(horizontalPanel);
    
    horizontalPanel.add(createMonthWidget(curMonth.clone().addMonths(-1)));
    horizontalPanel.add(createMonthWidget(curMonth));
    horizontalPanel.add(createMonthWidget(curMonth.clone().addMonths(+1)));

    container.add(scrollPanel);
    
    HorizontalPanel bottom = new HorizontalPanel();

    TouchHTML setBox = new TouchHTML("set");
    TouchHTML cancelBox = new TouchHTML("cancel");
    cancelBox.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        CalendarDialog.this.hide();
      }
    });
    
    selectedDateField = new TouchHTML();
    selectedDateField.setHeight("20px");
    selectedDateField.getElement().getStyle().setColor("black");
    selectedDateField.getElement().getStyle().setTextAlign(TextAlign.CENTER);
    setSelectedDate(selectedDate);
    bottom.add(setBox);
    bottom.setCellWidth(setBox, (width * 15 / 100) + "px");
    bottom.add(selectedDateField);
    bottom.setCellWidth(selectedDateField, (width * 70 / 100) + "px");
    bottom.add(cancelBox);
    bottom.setCellWidth(cancelBox, (width * 15 / 100) + "px");
    
    container.add(bottom);
    
    popup.add(container);
    
    popup.center();
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        internalScrollTo(width, 0);
        if (backPopup != null) {
          backPopup.hide();
        }
      }
    });
    
    scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {
      public void onScrollMove(ScrollMoveEvent event) {
        scrollPending = true;
      }
    });
    
    scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
      public void onScrollEnd(ScrollEndEvent event) {
        if (internalScrolling) {
          setVisible(popup);
          internalScrolling = false;
          lastX = getCurrentX();
          if (needRecreatePopup) {
            needRecreatePopup = false;
            initPopup();
          } else {
            GwtUtils.log("assistedScrollPending end");
            assistedScrollPending = false;
            scrollPending = false;
          }
        } else if (assistedScrollPending) {
          // do nothing
        } else {
          int startX = lastX;
          int endX = getCurrentX();
          if (endX < (startX - 50)) {
            curMonth.addMonths(-1);
            assistedScrollPending = true;
            GwtUtils.log("assistedScrollPending start");
            needRecreatePopup = true;
            internalScrollTo(startX - endX - width, 360);
          }
          if (endX > (startX + 50)) {
            curMonth.addMonths(+1);
            assistedScrollPending = true;
            GwtUtils.log("assistedScrollPending start");
            needRecreatePopup = true;
            internalScrollTo(startX - endX + width, 360);
          } else {
            scrollPending = false;
          }
        }
      }
    });
    
  }
  
  private void setVisible(Widget w) {
    w.getElement().getStyle().setVisibility(Visibility.VISIBLE);
    w.getElement().getStyle().setZIndex(+1);
  }

  private void setHidden(Widget w) {
    w.getElement().getStyle().setZIndex(-1);
    w.getElement().getStyle().setVisibility(Visibility.HIDDEN);
  }

  private void internalScrollTo(int x, int time) {
    internalScrolling = true;
    scrollPanel.scrollTo(x, 0, time, true);
  }
  
  private int getCurrentX() {
    return (scrollPanel.getX() * -1);
  }
  
  @SuppressWarnings("deprecation")
  private Widget createMonthWidget (Month month) {
    
    SimplePanel wrapper = new SimplePanel();
    wrapper.addStyleName("phg-CalendarDialog-MonthWrapper");
    wrapper.setWidth((width-2)+"px");
    wrapper.setHeight((height-2)+"px");
    
    FlexTable table = new FlexTable();
    table.addStyleName("phg-CalendarDialog-MonthTable");
    
    HTML header = new HTML("" + month.getMonth()+"/"+month.getYear());
    header.addStyleName("phg-CalendarDialog-MonthHeader");
    table.setWidget(0, 0, header);
    GwtUtils.setFlexCellColSpan(table, 0, 0, 7);
    
    int row = 1;
    for (int day = 1; day <= month.getLastDayOfMonth(); day++) {
      final Date date = new Date(month.getYear() - 1900, month.getMonth() - 1, day);
      int col = date.getDay();
      TouchHTML html = new TouchHTML("" + day);
      html.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          PhonegapUtils.log("scrollPending = " + scrollPending);
          if (!scrollPending) {
            PhonegapUtils.log("touch on " + GwtUtils.dateToString(date, "dd/MM/yyyy"));
            setSelectedDate(date);
          }
        }
      });
      table.setWidget(row, col, html);
      if (col == 6) {
        row++;
      }
    }
    
    wrapper.setWidget(table);
    return wrapper;
  }
  
  public void setSelectedDate(Date selectedDate) {
    this.selectedDate = selectedDate;
    if (selectedDate != null) {
      selectedDateField.setText(GwtUtils.dateToString(selectedDate, "dd/MM/yyyy"));
    } else {
      selectedDateField.setHtml(SafeHtmlUtils.fromTrustedString(" "));
    }
  }
  
  private void makePopupModal() {
    if (modalHandlerRegistration == null) {
      
      EventUtils.createModalHandler(new EventUtils.PanelGetter() {
        public Panel getPanel() {
          return popup;
        }
      }, new Delegate<HandlerRegistration>() {
        public void execute(HandlerRegistration registration) {
          modalHandlerRegistration = registration;
        }
      });

      /*
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          modalHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
            public void onPreviewNativeEvent(NativePreviewEvent event) {
              previewNativeEvent(event);
            }
          });
        }
      });
      */
      
    }
  }
  

  /*
  private void previewNativeEvent(NativePreviewEvent event) {
    
    Event nativeEvent = Event.as(event.getNativeEvent());
    String msg = "nativeEvent type " + nativeEvent.getType() + " ("+nativeEvent.getTypeInt()+")";
    
    boolean eventTargetsThis = false;
    EventTarget target = nativeEvent.getEventTarget();
    if (Element.is(target)) {
      eventTargetsThis = popup.getElement().isOrHasChild(Element.as(target));
    }
    if (eventTargetsThis) {
      msg += " targets popup";
    } else {
      msg += " not targets popup";
    }
    
    if (event.isFirstHandler()) {
      msg += " first handler";
    } else {
      msg += " not first handler";
    }
    
    if (event.isCanceled()) {
      msg += " canceled";
    }
    if (event.isConsumed()) {
      msg += " consumed";
    }
    
    // If the event has been canceled or consumed, ignore it
    if (event.isCanceled() || event.isConsumed()) {
      
      // We need to ensure that we cancel the event even if its been consumed so
      // that popups lower on the stack do not auto hide
      event.cancel();
      
    } else {
      
      if (eventTargetsThis) {
        event.consume();
      }
      
      event.cancel();
      
    }
    
    int type = nativeEvent.getTypeInt();
    
    if (type != 64 && type != 32 && type != 16) {
      PhonegapUtils.log(msg);
    }
    
  }
  */
  
}
