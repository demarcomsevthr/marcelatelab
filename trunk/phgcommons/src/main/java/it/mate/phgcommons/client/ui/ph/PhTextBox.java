package it.mate.phgcommons.client.ui.ph;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.mgwt.ui.client.theme.base.InputCss;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class PhTextBox extends MTextBox implements HasModel {

  private Object model;
  
  public PhTextBox() {
    super();
    applyPatch();
  }

  public PhTextBox(InputCss css, TextBox textBox) {
    super(css, textBox);
    applyPatch();
  }

  public PhTextBox(InputCss css) {
    super(css);
    applyPatch();
  }

  private void applyPatch() {
    if (OsDetectionUtils.isAndroid()) {
      String version = PhgUtils.getDeviceVersion().trim();
      if (version.startsWith("2") || version.startsWith("3") || version.startsWith("4.0")) {
        box.getElement().getStyle().setProperty("webkitUserModify", "initial");
      }
    }
  }

  /**
   * Available types: https://developer.apple.com/library/safari/documentation/AppleApplications/Reference/SafariHTMLRef/Articles/InputTypes.html
   */
  public void setType(String type) {
    if ("password".equalsIgnoreCase(type)) {
    } else if ("date".equalsIgnoreCase(type)) {
    } else if ("datetime".equalsIgnoreCase(type)) {
    } else if ("email".equalsIgnoreCase(type)) {
    } else if ("month".equalsIgnoreCase(type)) {
    } else if ("number".equalsIgnoreCase(type)) {
    } else if ("password".equalsIgnoreCase(type)) {
    } else if ("range".equalsIgnoreCase(type)) {
    } else if ("search".equalsIgnoreCase(type)) {
    } else if ("tel".equalsIgnoreCase(type)) {
    } else if ("time".equalsIgnoreCase(type)) {
    } else if ("url".equalsIgnoreCase(type)) {
    } else if ("week".equalsIgnoreCase(type)) {
    } else {
      PhgUtils.log("cannot set input type " + type);
      return;
    }
    box.getElement().setAttribute("type", type.toLowerCase());
  }
  
  public Double getValueAsDouble() {
    String textValue = getText();
    textValue = GwtUtils.replaceEx(textValue, ",", ".");
    try {
      return NumberFormat.getDecimalFormat().parse(textValue);
    } catch (NumberFormatException ex) {
      return null;
    }
  }
  
  public int getValueAsInt() {
    String textValue = getText();
    try {
      return Integer.parseInt(textValue);
    } catch (NumberFormatException ex) {
      return 0;
    }
  }
  
  public Integer getValueAsInteger() {
    String textValue = getText();
    try {
      return Integer.parseInt(textValue);
    } catch (NumberFormatException ex) {
      return null;
    }
  }
  
  public Long getValueAsLong() {
    String textValue = getText();
    try {
      return Long.parseLong(textValue);
    } catch (NumberFormatException ex) {
      return null;
    }
  }
  
  public void setValue(Number value) {
    if (value != null) {
      setValue(NumberFormat.getDecimalFormat().format(value), true);
    }
  }

  @Override
  public HasModel setModel(Object model) {
    this.model = model;
    return this;
  }

  @Override
  public Object getModel() {
    return model;
  }

}
