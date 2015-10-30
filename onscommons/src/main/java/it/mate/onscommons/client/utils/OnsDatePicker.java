package it.mate.onscommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.OnsEventUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsDialog;
import it.mate.onscommons.client.ui.OnsDialogCombo;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.datepicker.client.CalendarUtil;

@SuppressWarnings("deprecation")
public class OnsDatePicker {
  
  private String carouselId;
  private String monthBoxId;
  private String yearBoxId;

  private OnsDialog dialog;
  
  private Date firstDayOfCurrentMonth;

  private Date selectedDate;

  public final static String[] MONTHS_IT = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
  
  public final static String[] MONTHS_EN = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  
  private static String[] monthNames; 
  
  public final static String[] DAYS_IT = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
  
  public final static String[] DAYS_EN = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
  
  private static String[] dayNames; 
  
  private Delegate<Date> onSelectedDateDelegate;
  
  public OnsDatePicker(Date date, String language) {
    resetDates(date);
    if ("it".equalsIgnoreCase(language)) {
      monthNames = MONTHS_IT;
      dayNames = DAYS_IT;
    } else {
      monthNames = MONTHS_EN;
      dayNames = DAYS_EN;
    }
    createDialog();
  }
  
  public void setOnSelectedDateDelegate(Delegate<Date> onSelectedDateDelegate) {
    this.onSelectedDateDelegate = onSelectedDateDelegate;
  }
  
  private void resetDates(Date date) {
    this.selectedDate = CalendarUtil.copyDate(date);
    this.firstDayOfCurrentMonth = CalendarUtil.copyDate(date);
    this.firstDayOfCurrentMonth = getFirstDateOfMonth(this.firstDayOfCurrentMonth);
  }
  
  private void createDialog() {
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        carouselId = OnsenUi.createUniqueElementId();
        monthBoxId = OnsenUi.createUniqueElementId();
        yearBoxId = OnsenUi.createUniqueElementId();
        final String leftId = OnsenUi.createUniqueElementId();
        final String rightId = OnsenUi.createUniqueElementId();
        final String todayId = OnsenUi.createUniqueElementId();
        String html = "<ons-page>";
        html += "<table cellspacing='0' class='ons-date-picker-header'>";
        html += "<tr>";
        html += "<td class='ons-date-picker-header-left'>";
        html += "<ons-icon icon='fa-chevron-left'id='"+leftId+"'/>";
        html += "</td>";
        html += "<td class='ons-date-picker-header-month'>";
        html += "<input id='"+monthBoxId+"' type='text' class='text-input text-input--underbar' value=''></input>";
        html += "</td>";
        html += "<td class='ons-date-picker-header-year'>";
        html += "<input id='"+yearBoxId+"' type='text' class='text-input text-input--underbar' value=''></input>";
        html += "</td>";
        html += "<td class='ons-date-picker-header-right'>";
        html += "<ons-icon icon='fa-chevron-right' id='"+rightId+"'/>";
        html += "</td>";
        html += "</tr>";
        html += "</table>";
        html += "<ons-carousel id='"+carouselId+"' var='onsDatePickerCarousel' direction='horizontal' class='ons-date-picker-carousel' swipeable>";
        html += "<ons-carousel-item>";
        html += "</ons-carousel-item>";
        html += "</ons-carousel>";
        html += "<table cellspacing='0' class='ons-date-picker-footer'>";
        html += "<tr>";
        html += "<td class='ons-date-picker-footer-today'>";
        html += "<span id='"+todayId+"'>Oggi</span>";
        html += "</td>";
        html += "<td class='ons-date-picker-footer-clear'>";
        html += "<span>Imposta</span>";
        html += "</td>";
        html += "<td class='ons-date-picker-footer-close'>";
        html += "<span>Annulla</span>";
        html += "</td>";
        html += "</tr>";
        html += "</table>";
        html += "</ons-page>";
        dialog = OnsDialogUtils.createDialogMulti(html, false, "ons-date-picker-dialog");

        refreshDate();
        
        OnsenUi.onAvailableElement(monthBoxId, new Delegate<Element>() {
          public void execute(Element element) {
            OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
              public void on(NativeGestureEvent event) {
                new MonthDialog(getMonth()) {
                  protected void onMonthSelected(int month) {
                    firstDayOfCurrentMonth.setMonth(month);
                    refreshDate();
                  }
                };
              }
            });
          }
        });
        
        OnsenUi.onAvailableElement(yearBoxId, new Delegate<Element>() {
          public void execute(Element element) {
            OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
              public void on(NativeGestureEvent event) {
                new YearDialog(getYear()) {
                  protected void onYearSelected(int year) {
                    firstDayOfCurrentMonth.setYear(year - 1900);;
                    refreshDate();
                  }
                };
              }
            });
          }
        });
        
        OnsenUi.onAvailableElement(leftId, new Delegate<Element>() {
          public void execute(Element element) {
            OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
              public void on(NativeGestureEvent event) {
                CalendarUtil.addMonthsToDate(firstDayOfCurrentMonth, -1);
                refreshDate();
              }
            });
          }
        });
        
        OnsenUi.onAvailableElement(rightId, new Delegate<Element>() {
          public void execute(Element element) {
            OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
              public void on(NativeGestureEvent event) {
                CalendarUtil.addMonthsToDate(firstDayOfCurrentMonth, +1);
                refreshDate();
              }
            });
          }
        });
        
        OnsenUi.onAvailableElement(todayId, new Delegate<Element>() {
          public void execute(Element element) {
            OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
              public void on(NativeGestureEvent event) {
                Date today = new Date();
                resetDates(today);
                refreshDate();
              }
            });
          }
        });
        
        
      }
    });
  }
  
  private void refreshDate() {
    OnsenUi.onAvailableElement(monthBoxId, new Delegate<Element>() {
      public void execute(Element boxElement) {
        boxElement.setAttribute("value", monthNames[getMonth()]);
      }
    });
    OnsenUi.onAvailableElement(yearBoxId, new Delegate<Element>() {
      public void execute(Element boxElement) {
        boxElement.setAttribute("value", ""+getYear());
      }
    });
    refreshMonth(CalendarUtil.copyDate(firstDayOfCurrentMonth));
  }
  
  private int getMonth() {
    return firstDayOfCurrentMonth.getMonth();
  }
  
  private int getYear() {
    return firstDayOfCurrentMonth.getYear() + 1900;
  }
  
  private void refreshMonth(final Date date) {
    OnsenUi.onAvailableElement(carouselId, new Delegate<Element>() {
      public void execute(Element carouselElement) {
        
        carouselElement.setInnerHTML("");
        
        Date tmpDate = null;
        
        /*
        tmpDate = CalendarUtil.copyDate(date);
        CalendarUtil.addMonthsToDate(tmpDate, -1);
        carouselElement.appendChild(createCarouselItem(tmpDate));
        */
        
        tmpDate = CalendarUtil.copyDate(date);
        carouselElement.appendChild(createCarouselItem(tmpDate));

        /*
        tmpDate = CalendarUtil.copyDate(date);
        CalendarUtil.addMonthsToDate(tmpDate, +1);
        carouselElement.appendChild(createCarouselItem(tmpDate));
        */

        /*
        setActiveIndex(1);
        */
        
        GwtUtils.deferredExecution(100, new Delegate<Void>() {
          public void execute(Void element) {
            refreshCarousel();
          }
        });
        
      }
    });
  }
  
  private Element createCarouselItem(Date date) {
    
    PhgUtils.log("creating carousel item for " + date);
    
    String html = "";
    
    Date firstDayOfCurrentMonth = getFirstDateOfMonth(date);
    int firstWeekDay = GwtUtils.getDayNumberOfWeek(firstDayOfCurrentMonth);
//  int month = firstDayOfCurrentMonth.getMonth();
    
    Date lastDateOfMonth = getLastDateOfMonth(firstDayOfCurrentMonth);
    int lastDayOfMonth = lastDateOfMonth.getDate();
    
    html += "<table cellspacing='0' class='ons-date-picker-month-table'>";
    
    html += "<tr class='ons-date-picker-month-days-header'>";
    for (int col = 0; col < 7; col++) {
      html += "<td>";
      html +=  dayNames[col];
      html += "</td>";
    }
    html += "</tr>";
    
    html += "<tr class='ons-date-picker-month-table-line'>";
    html += "<td colspan='7'><hr/></td>";
    html += "</tr>";
    
    int firstWeekDayCol = firstWeekDay - 1;
    int dayColWidth = getDialogWidthPx() / 8;
    int dayColHeight = getDialogHeightPx() / 10;
    String dayColStyle = "style='width:"+dayColWidth+"px;height:"+dayColHeight+"px'";
    
    final Date fDate = firstDayOfCurrentMonth;
    
    int day = 1;
    for (int row = 0; day <= lastDayOfMonth; row++) {
      html += "<tr>";
      for (int col = 0; col < 7; col++) {
        if (row > 0 || col >= firstWeekDayCol) {
          if (day <= lastDayOfMonth) {
            String selectedClass = "";
            if (selectedDate.getYear() == date.getYear() &&
                selectedDate.getMonth() == date.getMonth() &&
                selectedDate.getDate() == day) {
              selectedClass = "ons-date-picker-selected";
            }
            String dayCellId = OnsenUi.createUniqueElementId();
            html += "<td><div id='"+dayCellId+"' class='ons-date-picker-month-day "+selectedClass+"' "+dayColStyle+">"+day+"</div>";
            
            final int fDay = day;
            OnsenUi.onAvailableElement(dayCellId, new Delegate<Element>() {
              public void execute(Element element) {
                OnsEventUtils.addTapHandler(element, new NativeGestureHandler() {
                  public void on(NativeGestureEvent event) {
                    selectedDate = CalendarUtil.copyDate(fDate);
                    selectedDate.setDate(fDay);
                    closeDialog(true);
                  }
                });
              }
            });
            
          }
          day ++;
        } else {
          html += "<td>";
        }
        html += "</td>";
      }
      html += "</tr>";
    }
    
    String carouselItemElemId = OnsenUi.createUniqueElementId();
    Element carouselItemElem = DOM.createElement("ons-carousel-item");
    carouselItemElem.setId(carouselItemElemId);
    carouselItemElem.setInnerHTML(html);
    
    // creazione degli handler
    OnsenUi.onAvailableElement(carouselItemElemId, new Delegate<Element>() {
      public void execute(Element carouselItemElement) {
        
      }
    });
    
    return carouselItemElem;
    
  }
  
  private void closeDialog(boolean fireDelegate) {
    dialog.hide();
    if (fireDelegate && onSelectedDateDelegate != null) {
      onSelectedDateDelegate.execute(selectedDate);
    }
  }
  
  protected static int getDialogHeightPx() {
    return Window.getClientHeight() - (int)(Window.getClientHeight() * 0.25);
  }

  public static String getDialogHeight() {
    return ""+getDialogHeightPx()+"px";
  }

  protected static int getDialogWidthPx() {
    return Window.getClientWidth() - (int)(Window.getClientWidth() * 0.025);
  }
  
  public static String getDialogWidth() {
    return ""+getDialogWidthPx()+"px";
  }

  protected final native void refreshCarousel() /*-{
    $wnd.onsDatePickerCarousel.refresh();
  }-*/;
  
  protected final native void setActiveIndex(int index) /*-{
    $wnd.onsDatePickerCarousel.setActiveCarouselItemIndex(index);
  }-*/;

  private static Date getFirstDateOfMonth(Date date) {
    Date result = CalendarUtil.copyDate(date);
    result.setDate(1);
    return result;
  }
  
  private static Date getLastDateOfMonth(Date date) {
    Date result = CalendarUtil.copyDate(date);
    CalendarUtil.addMonthsToDate(result, 1);
    result.setDate(1);
    CalendarUtil.addDaysToDate(result, -1);
    return result;
  }
  
  
  public abstract static class MonthDialog extends OnsDialogCombo {
    public MonthDialog(final int initialMonth) {
      super(monthsAsItemList("it"));
      setInitialIndex(initialMonth - 1);
    }
    private static List<OnsDialogCombo.Item> monthsAsItemList(String language) {
      List<OnsDialogCombo.Item> items = new ArrayList<OnsDialogCombo.Item>();
      for (int it = 0; it < monthNames.length; it++) {
        items.add(new OnsDialogCombo.Item(SafeHtmlUtils.fromTrustedString(monthNames[it]), ""+it));
      }
      return items;
    }
    @Override
    public void onItemSelected(Item item) {
      PhgUtils.log("Received item " + item.getValue());
      onMonthSelected(Integer.parseInt(item.getValue()));
    }
    protected abstract void onMonthSelected(int month);
  }
  
  public abstract static class YearDialog extends OnsDialogCombo {
    public YearDialog(final int initialYear) {
      super(10, 20, new OnsDialogCombo.ItemDelegate() {
        public Item getItem(int index) {
          int year = initialYear + index;
          PhgUtils.log("Creating item for index " + index + " with value " + year);
          return new OnsDialogCombo.Item(SafeHtmlUtils.fromTrustedString(""+year), ""+year);
        }
      });
      setLazyLoading(true);
    }
    @Override
    public void onItemSelected(Item item) {
      PhgUtils.log("Received item " + item.getValue());
      onYearSelected(Integer.parseInt(item.getValue()));
    }
    protected abstract void onYearSelected(int year);
  }
  
  public static void addMonthsToDate(Date date, int months) {
    Date tmp = CalendarUtil.copyDate(date);
    tmp = getFirstDateOfMonth(tmp);
    CalendarUtil.addMonthsToDate(tmp, months);
    date.setMonth(tmp.getMonth());
    date.setYear(tmp.getYear());
    PhgUtils.log("tmp date = "+ tmp +" current date = " + date);
  }
  
}
