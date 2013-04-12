package it.mate.econyx.client.ui;

import it.mate.econyx.shared.model.Period;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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
  
  private List<Model<M>> model = new ArrayList<Model<M>>();
  
  private Delegate<M> cellClickDelegate;
  
  private Delegate<Period> changedMonthDelegate;
  
  private static final int FIRST_DAY_ROW = 2;
  
  private static final String DAY_CELL_SIZE = "22px";
  
  private ButtonBase leftWidget;
  
  private ButtonBase rightWidget;
  
  interface CellTemplate extends SafeHtmlTemplates {

    @Template("<div class=\"ecxCalDay\">{0}</div>")
    SafeHtml renderDay(String day);
    
    @Template("<b><div class=\"ecxCalDay\">{0}</div></b>")
    SafeHtml renderDayCurrent(String day);
    
    @Template("<a><b><div class=\"day\">{0}</div></b></a>")
    SafeHtml renderDayClickable(String day);
    
  }
  private static CellTemplate cellTemplate = GWT.create(CellTemplate.class);
  
  public CalendarWidget() {
    super();
    initUI();
  }
  
  private void initUI () {
    calendarTable = new FlexTable();
    calendarTable.setStyleName("ecxCalendarWidget", true);
    calendarTable.setCellSpacing(0);
    initWidget(calendarTable);
    setDate(new Date());
  }
  
  public void setCellClickDelegate (Delegate<M> callback) {
    this.cellClickDelegate = callback;
  }

  public void setChangedMonthDelegate (Delegate<Period> delegate) {
    this.changedMonthDelegate = delegate;
    fireChangedMonthDelegate();
  }
  
  public void setModel (List<Model<M>> model) {
    this.model = model;
    if (model.size() > 0) {
      paintCalendarModel();
//    setDate(model.get(0).date);
    }
  }
  
  private void setDate (Date date) {
    currentState = new State(date);
    paintCalendarMonth();
  }
  
  private void decrementMonth() {
    clearCalendar();
    currentState.decrementMonth();
    paintCalendarMonth();
  }
  
  private void incrementMonth() {
    clearCalendar();
    currentState.incrementMonth();
    paintCalendarMonth();
  }
  
  private void setToday() {
    clearCalendar();
    currentState.setDate(new Date());
    paintCalendarMonth();
  }
  
  private void paintCalendarMonth() {
    calendarTable.clear();
    paintCalendarHeader();
    paintCalendarDays();
//  paintCalendarModel();
    fireChangedMonthDelegate();
  }
  
  private void fireChangedMonthDelegate() {
    if (changedMonthDelegate != null) {
      changedMonthDelegate.execute(new Period(currentState.getFirstDateOfMonth(), currentState.getLastDateOfMonth()));
    }
  }
  
  private void paintCalendarHeader() {
    
//  leftWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/left.jpg"));
//  leftWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/cal-month-bwd.png"));
    leftWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/cal-month-prev.png"));
    GwtUtils.setStyleAttribute(leftWidget, "textAlign", "center");
    leftWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        decrementMonth();
      }
    });
    
//  rightWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/right.jpg"));
//  rightWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/cal-month-fwd.png"));
    rightWidget = new PushButton(new Image(GWT.getModuleBaseURL()+"images/commons/cal-month-next.png"));
    GwtUtils.setStyleAttribute(rightWidget, "textAlign", "center");
    rightWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        incrementMonth();
      }
    });
    
    Button centerWidget = new Button(currentState.getMonthName()+" "+currentState.year);
    centerWidget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        setToday();
      }
    });

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
      
      SafeHtml html = null;
      if (currentState.isDateInMonth(date)) {
        if (GwtUtils.dateEquals(date, today, 8)) {
          html = cellTemplate.renderDayCurrent(""+GwtUtils.getDay(date));
        } else {
          html = cellTemplate.renderDay(""+GwtUtils.getDay(date));
        }
      } else {
        html = SafeHtmlUtils.fromTrustedString("");
        cell.addStyleName("ecxCalendarWidgetCellOutOfMonth");
      }
      
      cell.addStyleName("ecxCalendarWidgetCellNum");
      cell.setWidth(DAY_CELL_SIZE)
          .setHeight(DAY_CELL_SIZE)
          .setAlignment(HasHorizontalAlignment.ALIGN_DEFAULT, HasVerticalAlignment.ALIGN_TOP)
          .setHtml( html );
      CalendarUtil.addDaysToDate(date, +1);
    }
  }
  
  private void paintCalendarModel() {
    if (model == null)
      return;
    for (final Model<M> cell : model) {
      int cellDay = GwtUtils.getDay(cell.date);
      if (currentState.isDateInMonth(cell.date)) {
        String styleName = "ecxCalendarWidgetCellWithData";
        HTMLPanel hp;
        hp = new HTMLPanel(cellTemplate.renderDayClickable(""+cellDay));
        hp.addDomHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            event.stopPropagation();
            cellClickDelegate.execute(cell.data);
          }
        }, ClickEvent.getType());
        cell(calendarTable, cell.date, "").setWidget(hp)
                .setAlignment(HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP)
                .addStyleName(styleName);
        
      }
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
      
      this.lastDateOfMonth = getFirstDateOfMonth();
      CalendarUtil.addMonthsToDate(this.lastDateOfMonth, +1);
      CalendarUtil.addDaysToDate(this.lastDateOfMonth, -1);
      
      this.firstVisibleDate = getFirstDateOfMonth();
      CalendarUtil.addDaysToDate(this.firstVisibleDate, - getColumnNumber(this.firstVisibleDate));
      
      this.lastVisibleDate = getLastDateOfMonth();
      CalendarUtil.addDaysToDate(this.lastVisibleDate, 6 - getColumnNumber(this.lastVisibleDate));
      
    }
    private int getFirstWeekDayOfMonth() {
      return firstWeekDayOfMonth;
    }
    private Date getFirstDateOfMonth() {
      return CalendarUtil.copyDate(firstDateOfMonth);
    }
    private Date getLastDateOfMonth() {
      return CalendarUtil.copyDate(lastDateOfMonth);
    }
    private Date getFirstVisibleDate() {
      return CalendarUtil.copyDate(firstVisibleDate);
    }
    private Date getLastVisibleDate() {
      return CalendarUtil.copyDate(lastVisibleDate);
    }
    private String getMonthName() {
      return GwtUtils.getShortMonthName(getFirstDateOfMonth());
    }
    private void incrementMonth() {
      Date date = getFirstDateOfMonth();
      CalendarUtil.addMonthsToDate(date, +1);
      setDate(date);
    }
    private void decrementMonth() {
      Date date = getFirstDateOfMonth();
      CalendarUtil.addMonthsToDate(date, -1);
      setDate(date);
    }
    private boolean isVisibleDate(Date date) {
      return date.compareTo(getFirstVisibleDate()) >= 0 &&
          date.compareTo(getLastVisibleDate()) <= 0;
    }
    private boolean isDateInMonth(Date date) {
      return date.compareTo(getFirstDateOfMonth()) >= 0 &&
          date.compareTo(getLastDateOfMonth()) <= 0;
    }
  }
  
  private int getColumnNumber (Date date) {
    int weekday = GwtUtils.getDayNumberOfWeek(date);
    int column = (weekday - 1) % 7;
    return column;
  }
  
  private int getRowNumber (Date date) {
    if (date.before(currentState.getFirstDateOfMonth())) {
      return FIRST_DAY_ROW;
    } else if (date.after(currentState.getLastDateOfMonth())) {
      return getRowNumber(currentState.getLastDateOfMonth());
    } else {
      int day = GwtUtils.getDay(date);
      int row = (day - 1 + currentState.getFirstWeekDayOfMonth() - 1) / 7 + FIRST_DAY_ROW;
      return row;
    }
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
  }
  
}
