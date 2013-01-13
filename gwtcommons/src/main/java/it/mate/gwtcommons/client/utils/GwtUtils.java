package it.mate.gwtcommons.client.utils;

import it.mate.gwtcommons.client.ui.MessageBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class GwtUtils {
  
  private static int mockCounter = 0;
  
  private static Map<String, Object> dictionary;
  
  private final static DateTimeFormat dt10FMT = DateTimeFormat.getFormat("dd/MM/yyyy");
  private final static DateTimeFormat dt8FMT = DateTimeFormat.getFormat("yyyyMMdd");
  private final static DateTimeFormat dt8aFMT = DateTimeFormat.getFormat("ddMMyyyy");
  private final static DateTimeFormat dFMT = DateTimeFormat.getFormat("dd");
  private final static DateTimeFormat mFMT = DateTimeFormat.getFormat("MM");
  private final static DateTimeFormat m3FMT = DateTimeFormat.getFormat("MMM");
  private final static DateTimeFormat yFMT = DateTimeFormat.getFormat("yyyy");
  private final static DateTimeFormat eFMT = DateTimeFormat.getFormat("EEE");
  private final static DateTimeFormat logFMT = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss,SSS");
  private final static NumberFormat currencyFMT = NumberFormat.getFormat("0.00");
  
  private static DateTimeFormat defaultDTFormat = dt10FMT;

  private static final String ATTR_CLIENT_WAIT_PANEL = "common.client.waitPanel";
  
  private static final String ATTR_CLIENT_DEFAULT_WAIT_PANEL = "common.client.defaultWaitPanel";
  
  private static int showWaitPanelRequestCounter = 0;
  
  
  
  public static String getPortletContextPath() {
    return GwtUtils.getJSVar("contextPath","");
  }
  
  public static native String getJSVar(String jsVar, String defaultValue) /*-{
		var value = eval('$wnd.' + jsVar);
		if (value) {
			return value;
		}
		return defaultValue;
  }-*/;
  
  public static native String getNavigatorAppName() /*-{
    return $wnd.navigator.appName;
  }-*/;
  
  public static native String getNavigatorUserAgent() /*-{
    return $wnd.navigator.userAgent;
  }-*/;
  
  public static native String getLocationHref() /*-{
    return $wnd.location.href;
  }-*/;
  
  public static boolean isIE () {
    System.out.println("NavigatorUserAgent = " + getNavigatorUserAgent());
    return getNavigatorUserAgent().contains("MSIE");
  }

  public static boolean isChrome () {
    return getNavigatorUserAgent().toLowerCase().contains("chrome");
  }

  public static boolean isGecko () {
    return getNavigatorUserAgent().toLowerCase().contains("gecko");
  }

  @SuppressWarnings("deprecation")
  public static Date getDate0000 (Date date) {
    int d = date.getDate();
    int m = date.getMonth();
    int y = date.getYear();
    return new Date(y, m, d);
  }
  
  @SuppressWarnings("deprecation")
  public static Date getDate2359 (Date date) {
    int d = date.getDate();
    int m = date.getMonth();
    int y = date.getYear();
    return new Date(y, m, d, 23, 59, 59);
  }
  
  public static String formatCurrency (Double number) {
    if (number == null)
      return "";
    return currencyFMT.format(number);
  }
  
  public static String formatDecimal (Double number, int decimals) {
    StringBuffer pattern = new StringBuffer("0");
    for (int ip = 0; ip < decimals; ip++) {
      if (ip == 0)
        pattern.append(".");
      pattern.append("0");
    }
    NumberFormat decimalFMT = NumberFormat.getFormat(pattern.toString());
    return decimalFMT.format(number);
  }
  
  public static Date getDate (int day, int month, int year) {
    return dt10FMT.parse(day+"/"+month+"/"+year);
  }
  
  public static int getDay (Date date) {
    return Integer.valueOf(dFMT.format(date));
  }
  
  public static int getMonth (Date date) {
    return Integer.valueOf(mFMT.format(date));
  }
  
  private static String[] engMM = new String[] {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
  private static String[] itaMM = new String[] {"gen", "feb", "mar", "apr", "mag", "giu", "lug", "ago", "set", "ott", "nov", "dic"};
  public static String getShortMonthName (Date date) {
    String mm = m3FMT.format(date);
    for (int it = 0; it < engMM.length; it++) {
      if (engMM[it].equalsIgnoreCase(mm)) {
        mm = itaMM[it];
      }
    }
    return mm.substring(0, 1).toUpperCase() + mm.substring(1);
  }
  
  private static String[] itaMMMM = new String[] {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"};
  public static String getMonthName (Date date) {
    String mm = m3FMT.format(date);
    for (int it = 0; it < engMM.length; it++) {
      if (engMM[it].equalsIgnoreCase(mm)) {
        mm = itaMMMM[it];
      }
    }
    return mm.substring(0, 1).toUpperCase() + mm.substring(1);
  }
  
  public static int getYear (Date date) {
    return Integer.valueOf(yFMT.format(date));
  }

  public static String getWeekDay (Date date) {
    return eFMT.format(date);
  }
  
  public static void setDefaultDTFormat(DateTimeFormat defaultDTFormat) {
    GwtUtils.defaultDTFormat = defaultDTFormat;
  }

  public static String dateToString (Date date) {
    if (date == null)
      return null;
    return dateToString(date, null);
  }
  
  public static String dateToString (Date date, int len) {
    if (len == 8)
      return dateToString(date, dt8FMT);
    if (len == 10)
      return dateToString(date, dt10FMT);
    return dateToString(date, null);
  }
  
  public static String dateToString (Date date, DateTimeFormat fmt) {
    return fmt != null ? fmt.format(date) : defaultDTFormat.format(date);
  }
  
  private static String[] engDW = new String[] {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
  private static String[] itaDW = new String[] {"lun", "mar", "mer", "gio", "ven", "sab", "dom"};
  public static String getDayOfWeek(Date date) {
    String eee = GwtUtils.dateToString(date, eFMT);
    for (int it = 0; it < engDW.length; it++) {
      if (engDW[it].equals(eee.toLowerCase())) {
        eee = itaDW[it];
      }
    }
    return eee.substring(0, 1).toUpperCase() + eee.substring(1);
  }
  
  public static int getDayNumberOfWeek(Date date) {
    String eee = GwtUtils.dateToString(date, eFMT);
    for (int it = 0; it < engDW.length; it++) {
      if (engDW[it].equals(eee.toLowerCase())) {
        return it + 1;
      }
    }
    for (int it = 0; it < itaDW.length; it++) {
      if (itaDW[it].equals(eee.toLowerCase())) {
        return it + 1;
      }
    }
    throw new RuntimeException("Impossibile stabilire il giorno della settimana per la data " + dateToString(date));
  }
  
  public static Date stringToDate (String text) {
    return dt8FMT.parse(text);
  }
  
  public static Element getElement (String id) {
    return DOM.getElementById(id);
  }
  
  public static void setStyleAttribute(com.google.gwt.dom.client.Element element, String attr, String value) {
    DOM.setStyleAttribute((com.google.gwt.user.client.Element)element, attr, value);
  }
  
  public static void setStyleAttribute(String id, String attr, String value) {
    DOM.setStyleAttribute(DOM.getElementById(id), attr, value);
  }
  
  public static <U extends UIObject> U setStyleAttribute(U widget, String attr, String value) {
    DOM.setStyleAttribute(widget.getElement(), attr, value);
    return widget;
  }
  
  public static <U extends UIObject> U setBorderRadius(U widget, String value) {
    setStyleAttribute(widget, "WebkitBorderRadius", value);
    setStyleAttribute(widget, "MozBorderRadius", value);
    setStyleAttribute(widget, "borderRadius", value);
    return widget;
  }
  
  public static <U extends UIObject> U setOpacity(U widget, int value) {
    if (GwtUtils.isIE()) {
      return GwtUtils.setStyleAttribute(widget, "filter", "alpha(opacity = "+value+")");
    } else {
      return GwtUtils.setStyleAttribute(widget, "opacity", "" + value);
    }
  }
  
  public static void onAvailable (final String id, final Delegate<Element> delegate) {
    new Timer() {
      public void run() {
        Element elem = DOM.getElementById(id);
        if (elem != null) {
          this.cancel();
          delegate.execute(elem);
        }
      }
    }.scheduleRepeating(10);
  }
  
  public static Timer createTimer (int periodMillis, final Delegate<Void> delegate) {
    Timer timer = new Timer() {
      public void run() {
        delegate.execute(null);
      }
    };
    timer.scheduleRepeating(periodMillis);
    return timer;
  }
  
  public static void deferredExecution (final Delegate<Void> callback) {
    deferredExecution(0, callback);
  }
  
  public static void deferredExecution (int delayMillis, final Delegate<Void> delegate) {
    if (delayMillis > 0) {
      new Timer() {
        public void run() {
          deferredExecution(0, delegate);
        }
      }.schedule(delayMillis);
      return;
    }
    createMockElement(new Delegate<Element>() {
      public void execute(Element element) {
        delegate.execute(null);
      }
    });
  }
  
  private static void createMockElement (final Delegate<Element> callback) {
    mockCounter++;
    final SimplePanel div = new SimplePanel();
    String id = "ksdurcgnys"+mockCounter;
    div.getElement().setId(id);
    RootPanel.get().add(div);
    onAvailable(id, new Delegate<Element>() {
      public void execute(Element element) {
        callback.execute(element);
        RootPanel.get().remove(div);
      }
    });
  }
  
  public static void setFlexCellColSpan (FlexTable table, int row, int col, int value) {
    table.getFlexCellFormatter().setColSpan(row, col, value);
  }
  
  public static void setFlexCellRowSpan (FlexTable table, int row, int col, int value) {
    table.getFlexCellFormatter().setRowSpan(row, col, value);
  }
  
  public static void setFlexCellAlignment (FlexTable table, int row, int col, HorizontalAlignmentConstant hor, VerticalAlignmentConstant ver) {
    table.getFlexCellFormatter().setAlignment(row, col, hor, ver);
  }
  
  public static void setFlexCellHeight (FlexTable table, int row, int col, String value) {
    table.getFlexCellFormatter().setHeight(row, col, value);
  }
  
  public static void setFlexCellWidth (FlexTable table, int row, int col, String value) {
    table.getFlexCellFormatter().setWidth(row, col, value);
  }
  
  public static Element getFlexCellElement (FlexTable table, int row, int col) {
    return table.getFlexCellFormatter().getElement(row, col);
  }
  
  public static Element getFlexRowElement (FlexTable table, int row) {
    return table.getRowFormatter().getElement(row);
  }
  
  public static void setFlexCellBckColor (FlexTable table, int row, int col, String value) {
    DOM.setStyleAttribute((com.google.gwt.user.client.Element)GwtUtils.getFlexCellElement(table, row, col), "backgroundColor", value) ;
  }
  
  public static void setFlexCellStyleAttribute (FlexTable table, int row, int col, String attr, String value) {
    DOM.setStyleAttribute((com.google.gwt.user.client.Element)GwtUtils.getFlexCellElement(table, row, col), attr, value) ;
  }
  
  public static void setFlexCellClassName (FlexTable table, int row, int col, String className) {
    GwtUtils.getFlexCellElement(table, row, col).setClassName(className) ;
  }
  
  public static void setFlexRowClassName (FlexTable table, int row, String className) {
//  GwtUtils.getFlexRowElement(table, row).setClassName(className) ;
    for (int col = 0; col < table.getCellCount(row); col++) {
      GwtUtils.setFlexCellClassName(table, row, col, className);
    }
  }
  
  public static native String getContextURL(String ctx) /*-{
    var h = $doc.location.host;
    var p = $doc.location.protocol;
    if (ctx != '') {
      return p + "//" + h + "/" + ctx + "/";
    } else {
      return p + "//" + h + "/" ;
    }
  }-*/;
  
  public static List<Element> getChildsByTagName(Element element, String name) {
    List<Element> childs = new ArrayList<Element>();
    for (int it = 0; it < element.getChildCount(); it++) {
      Element child = (Element)element.getChild(it);
      if (name.equalsIgnoreCase(child.getNodeName())) {
        childs.add(child);
        childs.addAll(getChildsByTagName(child, name));
      }
    }
    return childs;
  }
  
  public static Element getChildByTagName(Element element, String name) {
    for (int it = 0; it < element.getChildCount(); it++) {
      Element child = (Element)element.getChild(it);
      if (name.equalsIgnoreCase(child.getNodeName())) {
        return child;
      }
    }
    return null;
  }
  
  public static Element findParentByTypeAndClass (Element element, String type, String className) {
    Element parent = element.getParentElement();
    if (parent == null)
      return null;
    if (type.equalsIgnoreCase(parent.getNodeName())) {
      String parClassName = parent.getClassName();
      if (parClassName != null && className.equalsIgnoreCase(parClassName)) {
        return parent;
      }
    }
    return findParentByTypeAndClass(parent, type, className);
  }
  
  public static Element getChildByType(Element element, String type) {
    for (int it = 0; it < element.getChildCount(); it++) {
      Element child = (Element)element.getChild(it);
      if (type.equalsIgnoreCase(child.getNodeName())) {
        return child;
      }
    }
    return null;
  }
  
  public static Label createHorizontalSpacer (int width) {
    return createSpacer(width, 1);
  }
  
  public static Label createVerticalSpacer (int height) {
    return createSpacer(1, height);
  }
  
  public static Label createSpacer (int width, int height) {
    Label label = new Label(" ");
    label.setPixelSize(width, height);
    return label;
  }
  
  public static void addEnterKeyPressHandler (FocusWidget widget, final Delegate<KeyPressEvent> delegate) {
    widget.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getNativeEvent().getKeyCode() == 13) {
          delegate.execute(event);
        }
      }
    });
  }
  
  private static Map<String, Object> getDictionary() {
    if (dictionary == null)
      dictionary = new HashMap<String, Object>();
    return dictionary;
  }
  
  public static void setDictionaryAttribute(String name, Object value) {
    getDictionary().put(name, value);
  }
  
  public static Object getDictionaryAttribute(String name) {
    return getDictionary().get(name);
  }
  
  public static void setClientAttribute(Object value) {
    setClientAttribute(value.getClass().getName(), value);
  }
  
  public static void setClientAttribute(String name, Object value) {
    getDictionary().put(name, value);
  }
  
  public static void removeClientAttribute(Class<?> type) {
    removeClientAttribute(type.getName());
  }
  
  public static void removeClientAttribute(String name) {
    getDictionary().remove(name);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T getClientAttribute(Class<T> type) {
    return (T)getClientAttribute(type.getName());
  }
  
  public static Object getClientAttribute(String name) {
    return getDictionary().get(name);
  }
  
  public static boolean existsClientAttribute(String name) {
    return getDictionary().containsKey(name);
  }
  
  public static void setParentWidth (Widget widget, String width) {
    DOM.setStyleAttribute((com.google.gwt.user.client.Element)widget.getElement().getParentElement(), "width", width); 
  }
  
  public static void setParentHeight (Widget widget, String height) {
    DOM.setStyleAttribute((com.google.gwt.user.client.Element)widget.getElement().getParentElement(), "height", height); 
  }
  
  public static void setFocus(final Focusable widget) {
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        widget.setFocus(true);
      }
    });
  }
  
  public static void setDateBoxHandler (final DateBox dateBox) {
    dateBox.getTextBox().addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        GwtUtils.deferredExecution(new Delegate<Void>() {
          public void execute(Void element) {
            TextBox textBox = dateBox.getTextBox();
            String text = textBox.getValue();
            GwtUtils.log(getClass(), "dateBox.onKeyPress", "value = " + text);
            if (text.length() == 8 && isDigit(text)) {
              try {
                Date date = dt8aFMT.parse(text);
                textBox.setValue(dt10FMT.format(date));
                dateBox.hideDatePicker();
                dateBox.showDatePicker();
              } catch (Exception ex) {
              }
            } else {
              char[] chs = text.toCharArray();
              boolean tobechanged = false;
              for (int it = 0; it < chs.length; it++) {
                if (chs[it] >= '0' && chs[it] <= '9') {
                } else if (chs[it] == '/'){
                } else {
                  chs[it] = '/';
                  tobechanged = true;
                }
              }
              if (tobechanged) {
                text = new String(chs);
                textBox.setValue(text);
                dateBox.hideDatePicker();
                dateBox.showDatePicker();
              }
            }
          }
        });
      }
    });
  }
  
  private static boolean isDigit(String text) {
    for (int it = 0; it < text.length(); it++) {
      char ch = text.charAt(it);
      if (ch < '0' || ch > '9') {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isEmpty(Object value) {
    if (value == null)
      return true;
    if (value instanceof String) {
      String text = (String)value;
      return text.trim().isEmpty();
    }
    if (value instanceof Collection) {
      Collection collection = (Collection)value;
      return collection.isEmpty();
    }
    if (value instanceof Object[]) {
      Object[] array = (Object[])value;
      return array.length == 0;
    }
    return false;
  }
  
  public static String purgeSpaces(String text) {
    return text.replace(" ", "");
  }

  public static Element getParentByTagName(Element elem, String name) {
    Element parent = elem.getParentElement();
    while (true) {
      if (parent == null)
        break;
      if (name.equalsIgnoreCase(parent.getNodeName())) {
        return parent;
      }
      parent = parent.getParentElement();
    }
    return null;
  }
  
  public static Element getHead() {
    NodeList<Element> elements = Document.get().getDocumentElement().getElementsByTagName("head");
    if (elements != null && elements.getLength() > 0) {
      Element head = elements.getItem(0);
      return head;
    }
    return null;
  }
  
  public static MessageBox messageBox(String bodyText) {
    return messageBox(bodyText, -1, -1, null, true, null);
  }
  
  public static MessageBox messageBoxHtml(String bodyText) {
    return messageBox(bodyText, -1, -1, null, true, null);
  }
  
  public static MessageBox messageBox(String bodyText, int buttonType) {
    return messageBox(bodyText, buttonType, -1, null, false, null);
  }
  
  public static MessageBox messageBox(String bodyText, int buttonType, int iconType) {
    return messageBox(bodyText, buttonType, iconType, null, false, null);
  }
  
  public static MessageBox messageBox(String bodyText, int buttonType, int iconType, MessageBox.Callbacks callbacks) {
    return messageBox(bodyText, buttonType, iconType, null, false, callbacks);
  }
  
  public static MessageBox messageBox(String bodyText, int buttonType, int iconType, String captionText, boolean isHtml, MessageBox.Callbacks callbacks) {
    MessageBox.Configuration config = MessageBox.configuration();
    if (bodyText != null) {
      if (isHtml) {
        config.setHtmlText(bodyText);
      } else {
        config.setBodyText(bodyText);
      }
    }
    if (buttonType != -1) {
      config.setButtonType(buttonType);
    }
    if (iconType != -1) {
      config.setIconType(iconType);
    } else {
      switch (buttonType) {
      case MessageBox.BUTTONS_OK:
      case MessageBox.BUTTONS_OKCANCEL:
        config.setIconType(MessageBox.ICON_ALERT);
        break;
      case MessageBox.BUTTONS_YESNO:
      case MessageBox.BUTTONS_YESNOCANCEL:
        config.setIconType(MessageBox.ICON_QUESTION);
        break;
      }
    }
    if (captionText != null) {
      config.setCaptionText(captionText);
    }
    if (callbacks != null) {
      config.setCallbacks(callbacks);
    }
    return MessageBox.create(config);
  }
  
  public static void logEnvironment(Class<?> klass, String methodName) {
    log(klass, methodName, "app.codeName = " + Window.Navigator.getAppCodeName());
    log(klass, methodName, "app.name = " + Window.Navigator.getAppName());
    log(klass, methodName, "app.version = " + Window.Navigator.getAppVersion());
    log(klass, methodName, "platform = " + Window.Navigator.getPlatform());
    log(klass, methodName, "user.agent = " + Window.Navigator.getUserAgent());
    log(klass, methodName, "current locale = " + LocaleInfo.getCurrentLocale().getLocaleName());
    log(klass, methodName, "startup module = " + getJSVar("startupModule", "??"));
    log(klass, methodName, "startup activity = " + getJSVar("startupActivity", "??"));
    log(klass, methodName, "client user id = " + getJSVar("clientUserId", "??"));
    log(klass, methodName, "context path = " + getJSVar("contextPath", ""));
  }
  
  public static void log (Class<?> type, String methodName, String message) {
    log(type, -1, methodName, message, null);
  }
  
  public static void log (Class<?> type, int hashcode, String methodName, String message) {
    log(type, hashcode, methodName, message, null);
  }
  
  public static void log (Class<?> type, String methodName, String message, Exception ex) {
    log(type, -1, methodName, message, ex);
  }
  
  public static void log (Class<?> type, int hashcode, String methodName, String message, Exception ex) {
    String dts = GwtUtils.dateToString(new Date(), logFMT); 
    if (ex != null) {
      message = message + " - " + ex.getClass().getName() + " - " + ex.getMessage();
    }
    System.out.println(dts + " DEBUG " + "["+type.getName()+ ":"+methodName+  (hashcode > -1 ? ("@"+ Integer.toHexString(hashcode)) : "") + "] " + message);
  }
  
  public static void setDefaultWaitPanel(PopupPanel popup) {
    setClientAttribute(ATTR_CLIENT_DEFAULT_WAIT_PANEL, popup);
  }
  
  public static void showWait() {
    showWait(null);
  }
  
  public static void showWait(PopupPanel defaultWaitPanel) {
    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
    showWaitPanel(defaultWaitPanel);
  }
  
  public static void showWaitPanel() {
    showWaitPanel(null);
  }
  
  public static void showWaitPanel(boolean glassEnabled) {
    showWaitPanel(null, glassEnabled);
  }
  
  public static void showWaitPanel(PopupPanel defaultWaitPanel) {
    showWaitPanel(defaultWaitPanel, false);
  }
  
  public static void showWaitPanel(PopupPanel defaultWaitPanel, boolean glassEnabled) {
    showWaitPanelRequestCounter++;
    PopupPanel waitPanel = (PopupPanel)getClientAttribute(ATTR_CLIENT_WAIT_PANEL);
    if (waitPanel == null) {
      if (existsClientAttribute(ATTR_CLIENT_DEFAULT_WAIT_PANEL)) {
        defaultWaitPanel = (PopupPanel)getClientAttribute(ATTR_CLIENT_DEFAULT_WAIT_PANEL);
      }
      if (defaultWaitPanel != null) {
        waitPanel = defaultWaitPanel;
      } else {
        
        /* 11/01/2013
        waitPanel = new PopupPanel();
        waitPanel.setGlassEnabled(false);
        waitPanel.setAnimationEnabled(true);
        Label lb = new Label("Attendere prego...");
        GwtUtils.setStyleAttribute(lb, "font", "bold 16px Verdana");
        GwtUtils.setStyleAttribute(lb, "padding", "30px");
        GwtUtils.setStyleAttribute(lb, "color", "red");
        waitPanel.setWidget(lb);
        */
        
        waitPanel = new PopupPanel();
        GwtUtils.setStyleAttribute(waitPanel, "border", "none");
        GwtUtils.setStyleAttribute(waitPanel, "background", "transparent");
        waitPanel.setGlassEnabled(false);
        waitPanel.setAnimationEnabled(true);
        Image waitingImg = new Image(UriUtils.fromTrustedString("/images/commons/transp-loading.gif"));
        waitPanel.setWidget(waitingImg);
        
      }
      waitPanel.setGlassEnabled(glassEnabled);
      waitPanel.center();
      setClientAttribute(ATTR_CLIENT_WAIT_PANEL, waitPanel);
    }
  }
  
  public static void hideWait() {
    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
    hideWaitPanel();
  }
  
  public static void hideWaitPanel() {
    hideWaitPanel(false);
  }
  
  public static void hideWaitPanel(boolean forceCounterReset) {
    if (forceCounterReset)
      showWaitPanelRequestCounter = 1;
    showWaitPanelRequestCounter--;
    if (showWaitPanelRequestCounter <= 0) {
      showWaitPanelRequestCounter = 0;
      PopupPanel waitPanel = (PopupPanel)getClientAttribute(ATTR_CLIENT_WAIT_PANEL);
      if (waitPanel != null) {
        waitPanel.hide();
        removeClientAttribute(ATTR_CLIENT_WAIT_PANEL);
      }
    }
  }
  
  public static void setLocationHash(String newHash) {
    if (newHash == null || "null".equals(newHash))
      return;
    boolean hashchanged = false;
    String href = GwtUtils.getLocationHref();
    if (href.contains("#")) {
      if (href.contains("#"+newHash)) {
        hashchanged = false;
      } else {
        int pound = href.indexOf('#');
        href = href.substring(0, pound) + "#" + newHash;
        hashchanged = true;
      }
    } else {
      href = href + "#" + newHash;
      hashchanged = true;
    }
    if (hashchanged) {
//    Window.Location.replace(href);
      Window.Location.assign(href);
    }
  }
  
  public static String getSelectedValue(ListBox listBox) {
    if (listBox.getSelectedIndex() >= 0) {
      return listBox.getValue(listBox.getSelectedIndex());
    } else {
      return null;
    }
  }
  
}
