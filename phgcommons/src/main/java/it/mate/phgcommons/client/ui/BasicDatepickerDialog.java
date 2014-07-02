package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.ph.PhTextBox;

import java.util.Date;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;

public class BasicDatepickerDialog {

  private static final String[] MONTH_NAMES_EN = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  private static final String[] MONTH_NAMES_IT = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

  private Date selectedDate = new Date();
  
  private PopupPanel popup;
  
  private String baseStylename = "phg-BasicDatepicker";

  TouchHTML dateBox;
  PhTextBox dayBox;
  PhTextBox monthBox;
  PhTextBox yearBox;

  public BasicDatepickerDialog() {
    this(new Date());
  }
  
  public BasicDatepickerDialog(Date selectedDate) {
    this.selectedDate = selectedDate;
  }
  
  public void show() {
    initUI();
  }
  
  public void hide() {
    if (popup != null) {
      popup.hide();
      popup = null;
    }
  }
  
  private void initUI() {
    
    popup = new PopupPanel();
    popup.addStyleName(baseStylename);
    
    SimpleContainer container = new SimpleContainer();
    
    HorizontalPanel header = new HorizontalPanel();
    header.addStyleName(baseStylename+"-header");
    container.add(header);
    
    dateBox = new TouchHTML();
    dateBox.addStyleName(baseStylename+"-dateBox");
    header.add(dateBox);
    
    HorizontalPanel center = new HorizontalPanel();
    center.addStyleName(baseStylename+"-center");
    container.add(center);
    
    SimpleContainer centerLeft = new SimpleContainer();
    centerLeft.addStyleName(baseStylename+"-centerLeft");
    center.add(centerLeft);
    
    VerticalPanel centerLeftPanel = new VerticalPanel();
    centerLeft.add(centerLeftPanel);
    TouchHTML addDayBox = new TouchHTML("+");
    addDayBox.addStyleName(baseStylename+"-spinControl");
    addDayBox.addTouchEndHandler(createSpinHandler(DAY, +1));
    centerLeftPanel.add(addDayBox);
    dayBox = new PhTextBox();
    dayBox.addStyleName(baseStylename+"-dayBox");
    centerLeftPanel.add(dayBox);
    TouchHTML subDayBox = new TouchHTML("-");
    subDayBox.addStyleName(baseStylename+"-spinControl");
    subDayBox.addTouchEndHandler(createSpinHandler(DAY, -1));
    centerLeftPanel.add(subDayBox);
    
    SimpleContainer centerMiddle = new SimpleContainer();
    centerMiddle.addStyleName(baseStylename+"-centerMiddle");
    center.add(centerMiddle);
    
    VerticalPanel centerMiddlePanel = new VerticalPanel();
    centerMiddle.add(centerMiddlePanel);
    
    TouchHTML addMonthBox = new TouchHTML("+");
    addMonthBox.addStyleName(baseStylename+"-spinControl");
    addMonthBox.addTouchEndHandler(createSpinHandler(MONTH, +1));
    centerMiddlePanel.add(addMonthBox);
    monthBox = new PhTextBox();
    monthBox.addStyleName(baseStylename+"-monthBox");
    centerMiddlePanel.add(monthBox);
    TouchHTML subMonthBox = new TouchHTML("-");
    subMonthBox.addStyleName(baseStylename+"-spinControl");
    subMonthBox.addTouchEndHandler(createSpinHandler(MONTH, -1));
    centerMiddlePanel.add(subMonthBox);
    
    
    SimpleContainer centerRight = new SimpleContainer();
    centerRight.addStyleName(baseStylename+"-centerRight");
    center.add(centerRight);
    
    VerticalPanel centerRightPanel = new VerticalPanel();
    centerRight.add(centerRightPanel);
    
    TouchHTML addYearBox = new TouchHTML("+");
    addYearBox.addStyleName(baseStylename+"-spinControl");
    addYearBox.addTouchEndHandler(createSpinHandler(YEAR, +1));
    centerRightPanel.add(addYearBox);
    yearBox = new PhTextBox();
    yearBox.addStyleName(baseStylename+"-yearBox");
    centerRightPanel.add(yearBox);
    TouchHTML subYearBox = new TouchHTML("-");
    subYearBox.addStyleName(baseStylename+"-spinControl");
    subYearBox.addTouchEndHandler(createSpinHandler(YEAR, -1));
    centerRightPanel.add(subYearBox);
    
    
    HorizontalPanel bottom = new HorizontalPanel();
    bottom.addStyleName(baseStylename+"-bottom");
    container.add(bottom);
    
    TouchHTML doneBtn = new TouchHTML("DONE");
    doneBtn.addStyleName(baseStylename+"-doneBtn");
    bottom.add(doneBtn);
    
    TouchHTML cancelBtn = new TouchHTML("Cancel");
    cancelBtn.addStyleName(baseStylename+"-cancelBtn");
    bottom.add(cancelBtn);
    
    showSelectedDate();
    
    popup.add(container);
    popup.center();
    
  }
  
  private static final int DAY = 0;
  private static final int MONTH = 1;
  private static final int YEAR = 2;
  
  private TouchEndHandler createSpinHandler(final int type, final int amount) {
    return new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (type == DAY) {
          CalendarUtil.addDaysToDate(selectedDate, amount);
        } else if (type == MONTH) {
          CalendarUtil.addMonthsToDate(selectedDate, amount);
        } else if (type == YEAR) {
          CalendarUtil.addMonthsToDate(selectedDate, 12 * amount);
        }
        showSelectedDate();
      }
    };
  }
  
  private void showSelectedDate() {
    dateBox.setHtml(GwtUtils.dateToString(selectedDate, "dd/MM/yyyy"));
    dayBox.setValue(GwtUtils.dateToString(selectedDate, "dd"));
    monthBox.setValue(GwtUtils.dateToString(selectedDate, "MMM"));
    yearBox.setValue(GwtUtils.dateToString(selectedDate, "yyyy"));
  }
  
  
}
