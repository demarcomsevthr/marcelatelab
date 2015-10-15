package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.onscommons.client.onsen.OnsenUi;

import java.util.Date;

import com.google.gwt.dom.client.Element;


public class OnsDateBox extends OnsTextBox {
  
  private static boolean USE_JS_DATEPICKER = true; 
  
  private static String JS_FORMAT = "dd/MM/yyyy";
  
  public OnsDateBox() {
    super("date");
    addStyleName("text-input");
    if (USE_JS_DATEPICKER) {
      GwtUtils.deferredExecution(400, new Delegate<Void>() {
        public void execute(Void element) {
          resetPickdater();
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
        resetPickdater();
      }
    });
  }
  
  private void resetPickdater() {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        JQuery.withElement(element).pickdate(JS_FORMAT.toLowerCase(), null, true, true);
      }
    });
  }
  
}
