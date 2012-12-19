package it.mate.econyx.client.ui;

import it.mate.econyx.client.text.DecimalBox;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.ui.SpinnerIntegerBox;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class QuantitaBox implements Focusable {

  private SpinnerIntegerBox integerQtaBox;
  
  private DecimalBox decimalQtaBox;

  public QuantitaBox(Double initialValue, String um, int decimali, HorizontalPanel qtaBoxPanel, String stylename) {
    createQtaBox(initialValue, um, decimali, qtaBoxPanel, stylename);
  }
  
  private void createQtaBox(Double initialValue, String um, int decimali, HorizontalPanel qtaBoxPanel, String stylename) {
    Label umLab = new Label(um);
    umLab.setHeight("100%");
    GwtUtils.setStyleAttribute(umLab, "verticalAlign", "middle");
    qtaBoxPanel.clear();
    qtaBoxPanel.add(umLab);
    qtaBoxPanel.setCellVerticalAlignment(umLab, HasVerticalAlignment.ALIGN_MIDDLE);
    qtaBoxPanel.add(new Spacer("4px"));
    if (decimali > 0) {
      decimalQtaBox = new DecimalBox();
      if (initialValue != null)
        decimalQtaBox.setValue(initialValue);
      decimalQtaBox.setWidth("3em");
      if (stylename != null)
        decimalQtaBox.addStyleName(stylename);
      qtaBoxPanel.add(decimalQtaBox);
    } else {
      integerQtaBox = new SpinnerIntegerBox((initialValue != null ? initialValue.intValue() : 1), 1, 1);
      integerQtaBox.setWidth("3em");
      if (stylename != null)
        integerQtaBox.addStyleName(stylename);
      qtaBoxPanel.add(integerQtaBox);
    }
  }
  
  public Double getQuantita() {
    Double result = null;
    if (integerQtaBox != null) {
      result = (double)integerQtaBox.getValue();
    } else {
      result = decimalQtaBox.getValue();
    }
    if (result == null) {
      Window.alert("Inserire una quantita");
    } else if (result < 0) {
      Window.alert("Inserire una quantita valida");
      result = null;
    }
    return result;
  }

  public int getTabIndex() {
    return 0;
  }

  public void setAccessKey(char key) {
    
  }

  public void setFocus(boolean focused) {
    if (integerQtaBox != null) {
      integerQtaBox.getInputBox().setFocus(focused);
    } else {
      decimalQtaBox.setFocus(focused);
    }
  }

  public void setTabIndex(int index) {
    
  }

  public void setWidth(String width) {
    if (integerQtaBox != null) {
      integerQtaBox.setWidth(width);
    } else {
      decimalQtaBox.setWidth(width);
    }
  }
  
}
