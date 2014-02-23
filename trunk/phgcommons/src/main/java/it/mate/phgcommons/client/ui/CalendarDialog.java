package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.EventUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
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
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class CalendarDialog {
  
  private static String language = "en";
  
  private static final String[] MONTH_NAMES_EN = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  private static final String[] MONTH_NAMES_IT = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
  
  private static final String[] DAY_NAMES_EN = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
  private static final String[] DAY_NAMES_IT = {"Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"};
  
  private static final boolean MONDAY_FIRST = true;
  
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
      return "it".equals(language) ? MONTH_NAMES_IT[date.getMonth()] : MONTH_NAMES_EN[date.getMonth()];
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
  
  private boolean glassEnabled = false;
  
  private Element glass;
  
  private HandlerRegistration resizeRegistration;
  
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
    if (glass != null) {
      Document.get().getBody().removeChild(glass);
      resizeRegistration.removeHandler();
      resizeRegistration = null;
    }
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
    
    if (glassEnabled) {
      /*
      popup.setGlassStyleName("phg-PopupPanelGlass");
      popup.setGlassEnabled(true);
      */
      createGlass("phg-PopupPanelGlass");
    }
    
    SimpleContainer container = new SimpleContainer();
    
    HorizontalPanel header = new HorizontalPanel();
    header.addStyleName("phg-CalendarDialog-Header");
    header.setWidth(popupWidth+"px");
    header.setHeight(headerHeight+"px");

    TouchHTML doneBox = new TouchHTML("DONE");
    doneBox.setWidth((popupWidth * 20 / 100) + "px");
    doneBox.addTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
        fireSelectedDateChange(selectedDate);
        CalendarDialog.this.hide();
      }
    });
    doneBox.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        fireSelectedDateChange(selectedDate);
        CalendarDialog.this.hide();
      }
    });
    TouchHTML cancelBox = new TouchHTML("cancel");
    cancelBox.setWidth((popupWidth * 20 / 100) + "px");
    cancelBox.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        CalendarDialog.this.hide();
      }
    });
    
    selectedDateField = new TouchHTML();
    selectedDateField.setWidth((popupWidth * 60 / 100) + "px");
    setSelectedDate(selectedDate);
    header.add(doneBox);
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
    
    if (glass != null) {
      Document.get().getBody().appendChild(glass);
//    impl.onShow(curPanel.glass);
      resizeRegistration = Window.addResizeHandler(glassResizer);
      glassResizer.onResize(null);
    }
    
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

  private void createGlass(String glassStyleName) {
    if (glass == null) {
      glass = Document.get().createDivElement();
      glass.setClassName(glassStyleName);

      glass.getStyle().setPosition(Position.ABSOLUTE);
      glass.getStyle().setLeft(0, Unit.PX);
      glass.getStyle().setTop(0, Unit.PX);
    }
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
    
    for (int idn = 0; idn < 7; idn++) {
      int col = idn;
      if (MONDAY_FIRST) {
        col = idn == 0 ? 6 : idn - 1;
      }
      table.setWidget(1, col, createWeekDayCell("it".equals(language) ? DAY_NAMES_IT[idn] : DAY_NAMES_EN[idn]));
    }
    
    int row = 2;
    
    Date date1 = new Date(month.getYear() - 1900, month.getMonth() - 1, 1);
    int day1 = date1.getDay();
    if (MONDAY_FIRST) {
      if (day1 >= 0) {
        if (day1 == 0) day1 = 7;
        for (int col = 1; col < day1; col++) {
          Date date = CalendarUtil.copyDate(date1);
          CalendarUtil.addDaysToDate(date, -(day1 - col));
          row = createMonthDayCell(table, row, col, date, true);
        }
      }
    } else {
      if (day1 > 0) {
        for (int col = 0; col < day1; col++) {
          Date date = CalendarUtil.copyDate(date1);
          CalendarUtil.addDaysToDate(date, -(day1 - col));
          row = createMonthDayCell(table, row, col, date, true);
        }
      }
    }
    
    for (int day = 1; day <= month.getLastDayOfMonth(); day++) {
      Date date = new Date(month.getYear() - 1900, month.getMonth() - 1, day);
      row = createMonthDayCell(table, row, date.getDay(), date, false);
    }
    
    Date date31 = new Date(month.getYear() - 1900, month.getMonth() - 1, month.getLastDayOfMonth());
    int day31 = date31.getDay();
    if (MONDAY_FIRST) {
      if (day31 > 0 && day31 < 7) {
        for (int col = day31 + 1; col <= 7; col++) {
          Date date = CalendarUtil.copyDate(date31);
          CalendarUtil.addDaysToDate(date, (col - day31));
          row = createMonthDayCell(table, row, col, date, true);
        }
      }
    } else {
      if (day31 < 6) {
        for (int col = day31 + 1; col <= 6; col++) {
          Date date = CalendarUtil.copyDate(date31);
          CalendarUtil.addDaysToDate(date, (col - day31));
          row = createMonthDayCell(table, row, col, date, true);
        }
      }
    }
    
    wrapper.setWidget(table);
    return wrapper;
  }
  
  @SuppressWarnings("deprecation")
  private int createMonthDayCell(FlexTable table, int row, int col, final Date date, boolean outsideCurrentMonth) {
    if (MONDAY_FIRST) {
      col = col == 0 ? 6 : col - 1;
    }
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
    if (CalendarUtil.isSameDate(date, selectedDate)) {
      html.addStyleName("phg-CalendarDialog-Month-Day-selected");
    }
    /*
    html.addTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
      }
    });
    */
    html.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (scrollPending) {
          PhonegapUtils.log("scrollPending!");
        } else {
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
  
  private HTML createWeekDayCell(String text) {
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
  
  public void setGlassEnabled(boolean glassEnabled) {
    this.glassEnabled = glassEnabled;
  }
  
  public boolean isVisible() {
    return popup.isVisible();
  }
  
  public static void setLanguage(String language) {
    if ("it".equalsIgnoreCase(language)) {
      CalendarDialog.language = language;
    } else {
      CalendarDialog.language = "en";
    }
  }
  
  private ResizeHandler glassResizer = new ResizeHandler() {
    public void onResize(ResizeEvent event) {
      Style style = glass.getStyle();

      int winWidth = Window.getClientWidth();
      int winHeight = Window.getClientHeight();

      // Hide the glass while checking the document size. Otherwise it would
      // interfere with the measurement.
      style.setDisplay(Display.NONE);
      style.setWidth(0, Unit.PX);
      style.setHeight(0, Unit.PX);

      int width = Document.get().getScrollWidth();
      int height = Document.get().getScrollHeight();

      // Set the glass size to the larger of the window's client size or the
      // document's scroll size.
      style.setWidth(Math.max(width, winWidth), Unit.PX);
      style.setHeight(Math.max(height, winHeight), Unit.PX);

      // The size is set. Show the glass again.
      style.setDisplay(Display.BLOCK);
    }
  };
  
}
