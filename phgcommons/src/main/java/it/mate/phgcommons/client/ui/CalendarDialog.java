package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.EventUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
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
  
  public interface SelectedDateChangeHandler {
    public void onSelectedDateChange(Date date);
  }
  
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
    public String getName() {
      return enNames[date.getMonth()];
    }
    private String[] enNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
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
  
  private int popupTop;
  
  private int popupLeft;
  
  private int popupWidth;
  
  private int popupHeight;
  
  private int headerHeight;
  
  private int dayHeight;
  
  private int scrollHeight;
  
  private TouchHTML selectedDateField;
  
  private Date selectedDate = new Date();
  
  private HandlerRegistration modalHandlerRegistration;
  
  private boolean modal = true;
  
  private List<SelectedDateChangeHandler> selectedDateChangeHandlers = new ArrayList<SelectedDateChangeHandler>();
  
  public CalendarDialog() {
    curMonth = new Month();
    initDefaults();
  }
  
  private void initDefaults() {
    popupLeft = 0;
    popupTop = 52;
    popupHeight = 392;
    popupWidth = Window.getClientWidth();
    headerHeight = 36;
    dayHeight = 36;
    scrollHeight = popupHeight - headerHeight;
  }
  
  public void show() {
    PhonegapUtils.setSuspendUncaughtExceptionAlerts(true);
    initUI();
  }
  
  public void hide() {
    popup.hide();
    EventUtils.removeModalHandler(modalHandlerRegistration);
    PhonegapUtils.setSuspendUncaughtExceptionAlerts(false);
    onHide();
  }
  
  protected void onHide() {
    
  }
  
  public HandlerRegistration addSelectedDateChangeHandler(final SelectedDateChangeHandler handler) {
    selectedDateChangeHandlers.add(handler);
    return new HandlerRegistration() {
      public void removeHandler() {
        selectedDateChangeHandlers.remove(handler);
      }
    };
  }
  
  private void fireSelectedDateChange(Date date) {
    for (SelectedDateChangeHandler handler : selectedDateChangeHandlers) {
      handler.onSelectedDateChange(date);
    }
  }
  
  private void initUI() {
    
    backPopup = popup;
    
    popup = new PopupPanel();
    popup.addStyleName("phg-CalendarDialog");
    setHidden(popup);
    
    if (modal) {
      makePopupModal();
    }
    
    SimpleContainer container = new SimpleContainer();
    
    HorizontalPanel header = new HorizontalPanel();
    header.addStyleName("phg-CalendarDialog-Header");
    header.setWidth(popupWidth+"px");
    header.setHeight(headerHeight+"px");

    TouchHTML setBox = new TouchHTML("DONE");
    setBox.setWidth((popupWidth * 20 / 100) + "px");
    setBox.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        fireSelectedDateChange(selectedDate);
        CalendarDialog.this.hide();
      }
    });
    TouchHTML cancelBox = new TouchHTML("cancel");
    cancelBox.setWidth((popupWidth * 20 / 100) + "px");
    cancelBox.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        CalendarDialog.this.hide();
      }
    });
    
    selectedDateField = new TouchHTML();
    selectedDateField.setWidth((popupWidth * 60 / 100) + "px");
    setSelectedDate(selectedDate);
    header.add(setBox);
    header.add(selectedDateField);
    header.add(cancelBox);
    
    container.add(header);
    
    scrollPanel = new ScrollPanel();
    scrollPanel.setWidth(popupWidth+"px");
    scrollPanel.setHeight(scrollHeight+"px");
    scrollPanel.setScrollingEnabledY(false);
    scrollPanel.setScrollingEnabledX(true);
    scrollPanel.setHideScrollBar(true);
    
    SimplePanel scrolledAreaPanel = new SimplePanel();
    scrolledAreaPanel.setHeight(scrollHeight+"px");
    scrolledAreaPanel.setWidth((popupWidth*3)+"px");
    scrollPanel.setWidget(scrolledAreaPanel);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.setHeight(scrollHeight+"px");
    horizontalPanel.setWidth((popupWidth*3)+"px");
    scrolledAreaPanel.setWidget(horizontalPanel);
    horizontalPanel.add(createMonthPanel(curMonth.clone().addMonths(-1)));
    horizontalPanel.add(createMonthPanel(curMonth));
    horizontalPanel.add(createMonthPanel(curMonth.clone().addMonths(+1)));

    container.add(scrollPanel);
    
    popup.add(container);
    
    popup.setPopupPosition(popupLeft, popupTop);
    popup.show();
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        internalScrollTo(popupWidth, 0);
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
            initUI();
          } else {
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
            needRecreatePopup = true;
            internalScrollTo(startX - endX - popupWidth, 360);
          } else if (endX > (startX + 50)) {
            curMonth.addMonths(+1);
            assistedScrollPending = true;
            needRecreatePopup = true;
            internalScrollTo(startX - endX + popupWidth, 360);
          } else if (endX != startX) {
            assistedScrollPending = true;
            internalScrollTo(startX - endX, 360);
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
  private Widget createMonthPanel (Month month) {
    
    SimplePanel wrapper = new SimplePanel();
    wrapper.addStyleName("phg-CalendarDialog-MonthWrapper");
    wrapper.setWidth((popupWidth-2)+"px");
    wrapper.setHeight((scrollHeight-2)+"px");
    
    FlexTable table = new FlexTable();
    table.addStyleName("phg-CalendarDialog-MonthTable");
    table.setCellSpacing(0);
    
    HTML header = new HTML("" + month.getName()+" "+month.getYear());
    header.addStyleName("phg-CalendarDialog-MonthHeader");
    table.setWidget(0, 0, header);
    GwtUtils.setFlexCellColSpan(table, 0, 0, 7);
    
    table.setWidget(1, 0, createWeekDay("Sun"));
    table.setWidget(1, 1, createWeekDay("Mon"));
    table.setWidget(1, 2, createWeekDay("Tue"));
    table.setWidget(1, 3, createWeekDay("Wed"));
    table.setWidget(1, 4, createWeekDay("Thu"));
    table.setWidget(1, 5, createWeekDay("Fri"));
    table.setWidget(1, 6, createWeekDay("Sat"));
    
    int row = 2;
    
    Date date1 = new Date(month.getYear() - 1900, month.getMonth() - 1, 1);
    int day1 = date1.getDay();
    if (day1 > 0) {
      for (int col = 0; col < day1; col++) {
        Date date = CalendarUtil.copyDate(date1);
        CalendarUtil.addDaysToDate(date, -(day1 - col));
        row = createDayCell(table, row, col, date, true);
      }
    }
    
    for (int day = 1; day <= month.getLastDayOfMonth(); day++) {
      Date date = new Date(month.getYear() - 1900, month.getMonth() - 1, day);
      row = createDayCell(table, row, date.getDay(), date, false);
    }
    
    Date date31 = new Date(month.getYear() - 1900, month.getMonth() - 1, month.getLastDayOfMonth());
    int day31 = date31.getDay();
    if (day31 < 6) {
      for (int col = day31 + 1; col <= 6; col++) {
        Date date = CalendarUtil.copyDate(date31);
        CalendarUtil.addDaysToDate(date, (col - day31));
        row = createDayCell(table, row, col, date, true);
      }
    }
    
    wrapper.setWidget(table);
    return wrapper;
  }
  
  private int createDayCell(FlexTable table, int row, int col, final Date date, boolean outsideCurrentMonth) {
    int day = date.getDate();
    TouchHTML html = new TouchHTML("" + day);
    html.addStyleName("phg-CalendarDialog-Month-Day");
    html.setHeight(dayHeight+"px");
    if (row == 2) {
      html.addStyleName("phg-firstRow");
    }
    if (col == 0) {
      html.addStyleName("phg-firstCol");
    }
    if (outsideCurrentMonth) {
      html.addStyleName("phg-outsideMonth");
    }
    html.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (!scrollPending) {
          setSelectedDate(date);
        }
      }
    });
    customizeDayCell(html, table, row, col, date, outsideCurrentMonth);
    table.setWidget(row, col, html);
    if (col == 6) {
      row++;
    }
    return row;
  }
  
  private HTML createWeekDay(String text) {
    HTML weekDay = new HTML();
    weekDay.addStyleName("phg-CalendarDialog-MonthHeader-WeekDay");
    weekDay.setText(text);
    return weekDay;
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
    }
  }
  
  protected void customizeDayCell(TouchHTML dayHtml, FlexTable table, int row, int col, final Date date, boolean outsideCurrentMonth) {
    
  }
  
  public PopupPanel getPopupPanel() {
    return popup;
  }
  
  public void setModal(boolean modal) {
    this.modal = modal;
  }
  
}
