package it.mate.ckd.client.ui;

import it.mate.ckd.client.config.ClientProperties;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;
//import com.google.gwt.user.client.ui.Button;

public class SpinnerIntegerBox extends Composite implements HasValueChangeHandlers<Integer> {
  
  private MIntegerBox valueBox;
  
  private SpinControl leftSpin;
  private SpinControl rightSpin;
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerIntegerBox_disableSpinButtons(); 
  
  private CustomTheme.CustomBundle bundle = CustomTheme.Instance.get();
  
  private Integer minValue;
  private Integer maxValue;
  
  public SpinnerIntegerBox() {
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
    
    valueBox = new MIntegerBox();
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
          inc(-1);
        }
      });
      
      rightSpin.addHandler(new SpinControl.SpinHandler() {
        public void onSpin() {
          inc(+1);
        }
      });
      
    }
    
  }
  
  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
    return valueBox.addValueChangeHandler(handler);
  }
  
  private void inc(int increment) {
    if (valueBox.getValue() == null)
      return;
    int value = valueBox.getValue();
    value += increment;
    boolean accept = true;
    if (minValue != null) {
      accept = accept && value >= minValue;
    }
    if (maxValue != null) {
      accept = accept && value <= maxValue;
    }
    if (accept) {
      valueBox.setValue(value, true);
    }
  }
  
  public void setValue(Integer value) {
    valueBox.setValue(value);
  }
  
  public Integer getValue() {
    return valueBox.getValue();
  }
  
  public Integer getValueAllowNull() {
    return "".equals(valueBox.getText().trim()) ? null : valueBox.getValue();
  }
  
  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }
  
  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }
  
}
