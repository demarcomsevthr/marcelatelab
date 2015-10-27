package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;


public class OnsDateBox extends OnsTextBox {
  
//private static boolean USE_JS_DATEPICKER = true; 
//private static boolean USE_JS_DATEPICKER = OsDetectionUtils.isAndroid(); 

  // 27/10/2015 - TORNO INDIETRO, ANCORA PERSISTE IL PROBLEMA DELLA SOVRAPPOSIZIONE SUL GLASS PANEL
  private static boolean USE_JS_DATEPICKER = false; 
  
  private static String JS_FORMAT = "dd/MM/yyyy";
  
  public OnsDateBox() {
    super("date");
    addStyleName("text-input");
    if (USE_JS_DATEPICKER) {
      initializeDatepicker();
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
      result = GwtUtils.stringToDate(text, "yyyy-MM-dd");
    }
    return result;
  }
  
  @Override
  public void setValue(String value, boolean fireEvents) {
    super.setValue(value, fireEvents);
    if (USE_JS_DATEPICKER) {
      setDataValue(value);
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

            String containerId = null;
            
            /*
            if (OnsToolbar.getGlobalToolbarElement() != null) {
              containerId = OnsToolbar.getGlobalToolbarElement().getId();
              PhgUtils.log("******* Datepicker container = " + containerId);
            }
            */
            
            // 21/10/2015 WORKAROUND: a volte il picker non compare, con questo sembra che funzioni meglio (non togliere le get) 
            GwtUtils.deferredExecution(500, new Delegate<Void>() {
              public void execute(Void element) {
                onAvailableElementByClassname("picker", new Delegate<Element>() {
                  public void execute(Element element) {
                    String msg = "Found picker element " + element.getClassName() + " " + element.getAbsoluteTop() + " " + element.getAbsoluteLeft() + " " + element.getClientHeight() + " " + element.getClientWidth() + " " + element.getStyle().getVisibility();
                    if (element.getClassName().contains("focus")) {
                      element.focus();
                      PhgUtils.log("focusing picker element");
                    }
                    //PhgUtils.log(msg);
                  }
                });
              }
            });
            
            JQuery.withElement(element).pickdate(JS_FORMAT.toLowerCase(), containerId, true, true);
          }
        });
      }
    });
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
  
}
