package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class CalendarWidget <M> extends Composite {
  
  private FlexTable calendarTable;
  
  private State currentState;
  
  private final String[] days = {"Lu", "Ma", "Me", "Gi", "Ve", "Sa", "Do"}; 
  
  private List<Model<M>> model;
  
  private Delegate<M> cellClickCallback;
  
  private static final int FIRST_DAY_ROW = 2;
  
  private static final String DAY_CELL_SIZE = "22px";
  
  private ButtonBase leftWidget;
  
  private ButtonBase rightWidget;
  
  interface CellTemplate extends SafeHtmlTemplates {

    /*
    final static String dayWithTextHtmlConst = "<div class=\"day\">{0}</div><div class=\"text\"><table cellspacing=\"0\"><tr><td><img src=\"{2}\"/></td><td><div style=\"height:0.5em\"></div><p>{1}</p></td></tr></table></div>";
    final static String dayWithTextEnabledHtmlConst = "<a>" + dayWithTextHtmlConst + "</a>";
    @Template(dayWithTextEnabledHtmlConst)
    SafeHtml renderDayAndTextEnabled(String day, String text, SafeUri imgUrl);
    final static String dayWithTextHtmlConst = "<div class=\"day\">{0}</div>";
    */
    
    @Template("<div class=\"day\">{0}</div>")
    SafeHtml renderDay(String day);
    
    @Template("<b><div class=\"day\">{0}</div></b>")
    SafeHtml renderDayCurrent(String day);
    
    @Template("<a><b><div class=\"day\">{0}</div></b></a>")
    SafeHtml renderDayClickable(String day);
    
  }
  private static CellTemplate cellTemplate = GWT.create(CellTemplate.class);
  
  public CalendarWidget() {
    super();
    initUI();
  }
  
  public void setModel (List<Model<M>> model) {
    this.model = model;
    if (model.size() > 0) {
      initDate(model.get(0).date);
    }
    paintCalendarCellData();
  }
  
  private void initDate (Date date) {
    currentState = new State(date);
    paintCalendar();
  }
  
  public static class Model <M> {
    private Date date;
    private String text;
    private M data;
    public Model<M> setDate(Date date) {
      this.date = date;
      return this;
    }
    public Model<M> setText(String text) {
      this.text = text;
      return this;
    }
    public Model<M> setData(M data) {
      this.data = data;
      return this;
    }
    /*
    private boolean forbidden;
    public Model<M> setForbidden(boolean forbidden) {
      this.forbidden = forbidden;
      return this;
    }
    private boolean enabled = true;
    public Model<M> setEnabled(boolean enabled) {
      this.enabled = enabled;
      return this;
    }
    */
  }
  
  public void setCellClickCallback (Delegate<M> callback) {
    this.cellClickCallback = callback;
  }
  
  private void initUI () {
    calendarTable = new FlexTable();
    calendarTable.setStyleName("ecxCalendarWidget", true);
    calendarTable.setCellSpacing(0);
    initWidget(calendarTable);
  }
  
  private void paintCalendar() {
    calendarTable.clear();
    paintCalendarHeader();
    paintCalendarDays();
    paintCalendarCellData();
  }
  
  private void decrementMonth() {
    clearCalendar();
    currentState.decrementMonth();
    paintCalendar();
  }
  
  private void incrementMonth() {
    clearCalendar();
    currentState.incrementMonth();
    paintCalendar();
  }
  
  private void setToday() {
    clearCalendar();
    currentState.setDate(new Date());
    paintCalendar();
  }
  
  private void paintCalendarHeader() {
    
    leftWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/left.jpg"));
    GwtUtils.setStyleAttribute(leftWidget, "textAlign", "center");
    leftWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        decrementMonth();
      }
    });
    
    rightWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/right.jpg"));
    GwtUtils.setStyleAttribute(rightWidget, "textAlign", "center");
    rightWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        incrementMonth();
      }
    });
    
    Button centerWidget = new Button(currentState.getCurrentMonthName()+" "+currentState.year);
    centerWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        setToday();
      }
    });

    /*
    cell(calendarTable, 0, 0).setWidget(leftWidget).addStyleName("ecxCalendarWidgetHeaderMonth")
                            .setAlignment(HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
    GwtUtils.setFlexCellStyleAttribute(calendarTable, 0, 0, "borderLeft", "1px solid #C0C0C0");
    cell(calendarTable, 0, 1).setWidget(centerWidget)
                            .setColSpan(5)
                            .addStyleName("ecxCalendarWidgetHeaderMonth");
    cell(calendarTable, 0, 2).setWidget(rightWidget).addStyleName("ecxCalendarWidgetHeaderMonth")
                             .setAlignment(HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
    */
    HorizontalPanel header = new HorizontalPanel();
    header.setSpacing(0);
    header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    header.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    header.addStyleName("ecxCalendarWidgetHeaderMonth");
    header.add(leftWidget);
    header.add(centerWidget);
    header.add(rightWidget);
    cell(calendarTable, 0, 0).setWidget(header).setColSpan(7);
    
    for (int col = 0; col < days.length; col++) {
      cell(calendarTable, 1, col).setText(days[col]).addStyleName("ecxCalendarWidgetHeaderWeek")
                            .setAlignment(HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
    }
  }
  
  private void paintCalendarDays() {
    
    for (int ir = calendarTable.getRowCount() - 1; ir > 1; ir--) {
      calendarTable.removeRow(ir);
    } 
    
    Date today = new Date();
    Date date = currentState.getFirstVisibleDate();
    while (currentState.isVisibleDate(date)) {
      CellUtil cell = cell(calendarTable, date);
      if (!currentState.isDateInCurrentMonth(date)) {
        cell.addStyleName("ecxCalendarWidgetCellOutOfMonth");
      }
      cell.setWidth(DAY_CELL_SIZE)
          .setHeight(DAY_CELL_SIZE)
          .setAlignment(HasHorizontalAlignment.ALIGN_DEFAULT, HasVerticalAlignment.ALIGN_TOP)
          .setHtml( GwtUtils.dateEquals(date, today, 8) ? cellTemplate.renderDayCurrent(""+GwtUtils.getDay(date)) : cellTemplate.renderDay(""+GwtUtils.getDay(date)));
      CalendarUtil.addDaysToDate(date, +1);
    }
  }
  
  private void paintCalendarCellData() {
    if (model == null)
      return;
    for (final Model<M> cell : model) {
      int cellDay = GwtUtils.getDay(cell.date);
      if (currentState.isDateInCurrentMonth(cell.date)) {
        String styleName = "ecxCalendarWidgetCellWithData";
        HTMLPanel hp;
//      String imgurl = cell.forbidden ? "images/common/forbidden.png" : "images/common/clock.png";
//      hp = new HTMLPanel(cellTemplate.renderDayClickable(""+cellDay, cell.text, UriUtils.fromTrustedString(GWT.getModuleBaseURL() + imgurl)));
        hp = new HTMLPanel(cellTemplate.renderDayClickable(""+cellDay));
        hp.addDomHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            event.stopPropagation();
            cellClickCallback.execute(cell.data);
          }
        }, ClickEvent.getType());
        cell(calendarTable, cell.date, "").setWidget(hp)
                .setAlignment(HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP)
                .addStyleName(styleName);
        
      }
    }
    if (model.size() > 0) {
      Model<M> firstEvent = model.get(0);
      Model<M> lastEvent =  model.get(model.size() - 1);
      /*
      leftWidget.setEnabled(!currentState.isDateInCurrentMonth(firstEvent.date));
      rightWidget.setEnabled(!currentState.isDateInCurrentMonth(lastEvent.date));
      */
    }
  }
  
  private void clearCalendar () {
    Date date = currentState.getFirstVisibleDate();
    while (currentState.isVisibleDate(date)) {
      cell(calendarTable, date, "");
      CalendarUtil.addDaysToDate(date, +1);
    }
  }
  
  private CellUtil cell(FlexTable table, int row, int column) {
    return new CellUtil(table, row, column);
  }
  
  private CellUtil cell(FlexTable table, Date date) {
    return new CellUtil(table, date, null);
  }
  
  private CellUtil cell(FlexTable table, Date date, String text) {
    return new CellUtil(table, date, text);
  }
  
  private class CellUtil {
    private FlexTable table;
    private int row;
    private int column;
    private StringBuffer styleNames = new StringBuffer();
    public CellUtil(FlexTable table, Date date, String text) {
      init(table, getRowNumber(date), getColumnNumber(date));
      if (text == null) {
        text = ""+GwtUtils.getDay(date);
      }
      setText(text);
    }
    public CellUtil(FlexTable table, int row, int column) {
      init(table, row, column);
    }
    private void init(FlexTable table, int row, int column) {
      this.table = table;
      this.row = row;
      this.column = column;
      setText("");
      addStyleName("ecxCalendarWidgetCell");
      setAlignment(HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
    }
    public CellUtil setText(String text) {
      table.setText(row, column, text);
      return this;
    }
    public CellUtil setColSpan (int value) {
      GwtUtils.setFlexCellColSpan(table, row, column, value);
      return this;
    }
    public CellUtil addStyleName(String style) {
      if (styleNames.length() > 0)
        styleNames.append(" ");
      styleNames.append(style);
      GwtUtils.setFlexCellClassName(table, row, column, styleNames.toString());
      return this;
    }
    public CellUtil setWidget(Widget widget) {
      table.setWidget(row, column, widget);
      return this;
    }
    public CellUtil setWidth(String width) {
      GwtUtils.setFlexCellWidth(table, row, column, width);
      return this;
    }
    public CellUtil setHeight(String height) {
      GwtUtils.setFlexCellHeight(table, row, column, height);
      return this;
    }
    public CellUtil setHtml(SafeHtml html) {
      table.setHTML(row, column, html);
      return this;
    }
    public CellUtil setBckColor(String value) {
      GwtUtils.setFlexCellBckColor(table, row, column, value);
      return this;
    }
    public CellUtil setAlignment(HorizontalAlignmentConstant hor, VerticalAlignmentConstant ver) {
      table.getFlexCellFormatter().setAlignment(row, column, hor, ver);
      return this;
    }
  }
  
  private class State {
    private int month;
    private int year;
    private Date firstDateOfMonth;
    private Date lastDateOfMonth;
    private Date firstVisibleDate;
    private Date lastVisibleDate;
    private int firstWeekDayOfMonth;
    private State(Date date) {
      setDate(date);
    }
    private void setDate(Date date) {
      this.month = GwtUtils.getMonth(date);
      this.year = GwtUtils.getYear(date);
      this.firstDateOfMonth = GwtUtils.getDate(1, month, year);
      this.firstWeekDayOfMonth = GwtUtils.getDayNumberOfWeek(firstDateOfMonth);
      
      this.lastDateOfMonth = getFirstDateOfCurrentMonth();
      CalendarUtil.addMonthsToDate(this.lastDateOfMonth, +1);
      CalendarUtil.addDaysToDate(this.lastDateOfMonth, -1);
      
      this.firstVisibleDate = getFirstDateOfCurrentMonth();
      CalendarUtil.addDaysToDate(this.firstVisibleDate, - getColumnNumber(this.firstVisibleDate));
      
      this.lastVisibleDate = getLastDateOfCurrentMonth();
      CalendarUtil.addDaysToDate(this.lastVisibleDate, 6 - getColumnNumber(this.lastVisibleDate));
      
    }
    private int getFirstWeekDayOfCurrentMonth() {
      return firstWeekDayOfMonth;
    }
    private Date getFirstDateOfCurrentMonth() {
      return CalendarUtil.copyDate(firstDateOfMonth);
    }
    private Date getFirstVisibleDate() {
      return CalendarUtil.copyDate(firstVisibleDate);
    }
    private Date getLastVisibleDate() {
      return CalendarUtil.copyDate(lastVisibleDate);
    }
    private Date getLastDateOfCurrentMonth() {
      return CalendarUtil.copyDate(lastDateOfMonth);
    }
    private String getCurrentMonthName() {
      return GwtUtils.getShortMonthName(getFirstDateOfCurrentMonth());
    }
    private void incrementMonth() {
      Date date = getFirstDateOfCurrentMonth();
      CalendarUtil.addMonthsToDate(date, +1);
      setDate(date);
    }
    private void decrementMonth() {
      Date date = getFirstDateOfCurrentMonth();
      CalendarUtil.addMonthsToDate(date, -1);
      setDate(date);
    }
    private boolean isVisibleDate(Date date) {
      return date.compareTo(getFirstVisibleDate()) >= 0 &&
          date.compareTo(getLastVisibleDate()) <= 0;
    }
    private boolean isDateInCurrentMonth(Date date) {
      return date.compareTo(getFirstDateOfCurrentMonth()) >= 0 &&
          date.compareTo(getLastDateOfCurrentMonth()) <= 0;
    }
  }
  
  private int getColumnNumber (Date date) {
    int weekday = GwtUtils.getDayNumberOfWeek(date);
    int column = (weekday - 1) % 7;
    return column;
  }
  
  private int getRowNumber (Date date) {
    if (date.before(currentState.getFirstDateOfCurrentMonth())) {
      return FIRST_DAY_ROW;
    } else if (date.after(currentState.getLastDateOfCurrentMonth())) {
      return getRowNumber(currentState.getLastDateOfCurrentMonth());
    } else {
      int day = GwtUtils.getDay(date);
      int row = (day - 1 + currentState.getFirstWeekDayOfCurrentMonth() - 1) / 7 + FIRST_DAY_ROW;
      return row;
    }
  }
  
}
