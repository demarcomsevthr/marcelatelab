package it.mate.ckd.client.ui;

import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.ui.client.widget.MDoubleBox;

public class SpinnerDoubleBox extends Composite implements HasValueChangeHandlers<Double> {
  
  private MDoubleBox valueBox;
  
  private SpinControl leftSpin;
  private SpinControl rightSpin;
  
  private double increment;
  
  private boolean needFireEvents = false;
  
  private boolean disableSpinButtons = AppProperties.IMPL.SpinnerDoubleBox_disableSpinButtons(); 
  
  private Double minValue;
  private Double maxValue;

  private CustomTheme.CustomBundle bundle = CustomTheme.Instance.get();
  
  public SpinnerDoubleBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    
    if (!disableSpinButtons) {
      leftSpin = new SpinControl(bundle.minusImage());
      GwtUtils.setStyleAttribute(leftSpin, "paddingLeft", "4px");
      GwtUtils.setStyleAttribute(leftSpin, "paddingRight", "6px");
      hp.add(leftSpin);
    }
    
    valueBox = new MDoubleBox();
    valueBox.getElement().setPropertyString("type", "number");
    
    hp.add(valueBox);
    
    if (!disableSpinButtons) {
      rightSpin = new SpinControl(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightSpin, "paddingLeft", "6px");
      hp.add(rightSpin);
    }
    
    initWidget(hp);

    if (!disableSpinButtons) {
      
      leftSpin.addHandler(new SpinControl.SpinHandler() {
        public void onSpin() {
          inc(increment * -1);
        }
      });
      
      rightSpin.addHandler(new SpinControl.SpinHandler() {
        public void onSpin() {
          inc(increment * +1);
        }
      });
    }

    if (AppProperties.IMPL.SpinnerDoubleBox_keyPress_fix_enabled()) {
      valueBox.addKeyPressHandler(new KeyPressHandler() {
        public void onKeyPress(KeyPressEvent event) {
          if (event.getCharCode() == ',') {
            event.preventDefault();
          }
        }
      });
    }
    
  }
  
  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
    needFireEvents = true;
    return valueBox.addValueChangeHandler(handler);
  }
  
  private void inc(double increment) {
    if (valueBox.getValue() == null)
      return;
    double value = valueBox.getValue();
    value += increment;
    boolean accept = true;
    if (minValue != null) {
      accept = accept && value >= minValue;
    }
    if (maxValue != null) {
      accept = accept && value <= maxValue;
    }
    if (accept) {
      valueBox.setValue(value, needFireEvents);
    }
  }
  
  public void setIncrement(double increment) {
    this.increment = increment;
  }
  
  public void setValue(double value) {
    valueBox.setValue(value);
  }
  
  public Double getValue() {
    Double res = valueBox.getValue();
    
    if (AppProperties.IMPL.SpinnerDoubleBox_enSeparator_fix_enabled() && valueBox.getText().contains(",") && !("it".equals(getLocalLanguageCookie()))) {
      String appo = valueBox.getText().replace(",", ".");
      res = GwtUtils.getDefaultCurrencyFmt().parse(appo);
    }
    
    return res;
  }
  
  public class SpinAnchor extends Anchor {
    public SpinAnchor(ImageResource resource) {
      super();
      Image image = new Image(resource);
      getElement().appendChild(image.getElement());
      addStyleName("ckd-spinAnchor");
    }
  }
  
  public void setMinValue(Double minValue) {
    this.minValue = minValue;
  }
  
  public void setMaxValue(Double maxValue) {
    this.maxValue = maxValue;
  }
  
  private static native String getLocalLanguageCookie() /*-{
    return $wnd.getLocalLanguageCookie();
  }-*/;

}
