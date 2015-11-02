package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.OnsEventUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.OnsDatePicker;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;


public class OnsDateBox extends OnsTextBox {
  

  // 02/11/2015
  private static boolean USE_ONS_DATEPICKER = OsDetectionUtils.isAndroid() || OsDetectionUtils.isDesktop(); 
  private static boolean USE_JS_DATEPICKER = false; 
  /////////////
  
      
  /*
  // 28/10/2015 - SEMBRA FUNZIONARE CON LA PATCH IN pickadate.js/compressed/themes/default.css
  private static boolean USE_JS_DATEPICKER = OsDetectionUtils.isAndroid() && 
      !PhgUtils.getDeviceVersion().startsWith("4.0") && 
      !PhgUtils.getDeviceVersion().startsWith("4.1") && 
      !PhgUtils.getDeviceVersion().startsWith("4.2"); 
      */
  
  
  private static boolean applyAndroidPatch1 = false;
  
  private static boolean applyAndroidPatch2 = false;
  
  private static String JS_FORMAT = "dd/MM/yyyy";
  
  private String actualFormat = "dd/MM/yyyy";
  
  private String language;
  
  public OnsDateBox() {
    super( USE_ONS_DATEPICKER ? "text" : "date");
    language = PhgUtils.getAppLocalLanguage();
    if ("it".equalsIgnoreCase(language)) {
      actualFormat = "dd/MM/yyyy";
    } else {
      actualFormat = "yyyy/MM/dd";
    }
    addStyleName("text-input");
    PhgUtils.log("OnsDateBox: USE_JS_DATEPICKER=" + USE_JS_DATEPICKER + " USE_ONS_DATEPICKER="+USE_ONS_DATEPICKER + " language="+language + " actualFormat="+actualFormat);
    if (USE_JS_DATEPICKER) {
      initializeDatepicker();
      androidPatch2();
    } else if (USE_ONS_DATEPICKER) {
      OnsenUi.onAvailableElement(this, new Delegate<Element>() {
        public void execute(Element element) {
          new OnsDatePicker(OnsDateBox.this, language);
        }
      });
    }
  }
  
  public Date getValueAsDate() {
    String text = getText();
    if (text == null || text.trim().length() == 0) {
      return null;
    }
    Date result = null;
    if (USE_JS_DATEPICKER) {
      result = GwtUtils.stringToDate(text, JS_FORMAT);
    } else {
      String tryFormat = "yyyy-MM-dd";
      result = tryParseDate(tryFormat);
      actualFormat = tryFormat;
      if (result == null) {
        tryFormat = "dd/MM/yyyy";
        result = tryParseDate(tryFormat);
        actualFormat = tryFormat;
      }
    }
    return result;
  }
  
  public void setValueAsDate(Date date) {
    setValue(GwtUtils.dateToString(date, actualFormat));
  }
  
  public String getActualFormat() {
    return actualFormat;
  }
  
  private Date tryParseDate(String pattern) {
    try {
      return GwtUtils.stringToDate(getText(), pattern);
    } catch (IllegalArgumentException e1) {
      return null;
    }
  }
  
  @Override
  public void setValue(String value, boolean fireEvents) {
    super.setValue(value, fireEvents);
    if (USE_JS_DATEPICKER) {
      setDataValue(value);
    } else if (USE_ONS_DATEPICKER) {
      final String fValue = value;
      OnsenUi.onAvailableElement(this, new Delegate<Element>() {
        public void execute(Element element) {
          element.setInnerText(fValue);
        }
      });
    }
  }

  public void setDataValue(final String dataValue) {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setAttribute("data-value", dataValue);
        initializeDatepicker();
      }
    });
  }
  
  private void initializeDatepicker() {
    GwtUtils.deferredExecution(400, new Delegate<Void>() {
      public void execute(Void element) {
        OnsenUi.onAvailableElement(OnsDateBox.this, new Delegate<Element>() {
          public void execute(Element element) {
            androidPatch1();
            String containerId = null;
            JQuery.withElement(element).pickdate(JS_FORMAT.toLowerCase(), containerId, true, true);
          }
        });
      }
    });
  }
  
  
  private void androidPatch1() {
    // 21/10/2015 WORKAROUND: a volte il picker non compare, con questo sembra che funzioni meglio (non togliere le get) 
    if (applyAndroidPatch1) {
      GwtUtils.deferredExecution(500, new Delegate<Void>() {
        public void execute(Void element) {
          onAvailableElementByClassname("picker", new Delegate<Element>() {
            public void execute(Element element) {
//            String msg = "Found picker element " + element.getClassName() + " " + element.getAbsoluteTop() + " " + element.getAbsoluteLeft() + " " + element.getClientHeight() + " " + element.getClientWidth() + " " + element.getStyle().getVisibility();
              if (element.getClassName().contains("focus")) {
                element.focus();
//              PhgUtils.log("focusing picker element");
              }
              //PhgUtils.log(msg);
            }
          });
        }
      });
    }
  }
  
  private static void onAvailableElementByClassname (final String classname, final Delegate<Element> delegate) {
    new Timer() {
      public void run() {
        Element element = JQuery.selectFirstElement("." + classname);
        if (element != null) {
//        this.cancel();
          delegate.execute(element);
        } else {
          PhgUtils.log("Picker element timer cancel");
          this.cancel();
        }
      }
    }.scheduleRepeating(1000);
  }
  
  private void androidPatch2() {
    if (applyAndroidPatch2) {
      OnsenUi.onAvailableElement(getElement(), new Delegate<Element>() {
        public void execute(Element dateboxElement) {
          OnsEventUtils.addHandler(dateboxElement, "touchend", true, new NativeGestureHandler() {
            public void on(NativeGestureEvent event) {
              PhgUtils.log("OnsDateBox.touchend");
              GwtUtils.deferredExecution(100, new Delegate<Void>() {
                public void execute(Void v) {
                  Element pickerHolderElement = JQuery.selectFirstElement(".picker__holder");
                  if (pickerHolderElement != null) {
                    GwtUtils.setJsPropertyString(pickerHolderElement.getStyle(), "transition", "initial");
                    PhgUtils.log("OnsDateBox.touchend: set transition initial on picker_holder");
                  }
                }
              });
            }
          });
        }
      });
    }
  }
  
}
