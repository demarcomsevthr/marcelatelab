package it.mate.ckd.client.ui;

import it.mate.ckd.client.config.ClientProperties;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.MDoubleBox;

public class SpinnerDoubleBox extends Composite implements HasValueChangeHandlers<Double> {
  
  private Button leftBtn;
  private MDoubleBox valueBox;
  private Button rightBtn;
  
  private double increment;
  
  private boolean needFireEvents = false;
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerDoubleBox_disableSpinButtons(); 

  public SpinnerDoubleBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    
    if (!disableSpinButtons) {
      leftBtn = new Button("-");
      GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
      leftBtn.addStyleName("spin-Button");
      hp.add(leftBtn);
    }
    
    /** SEE:
    NumberFormat.getDecimalFormat();
    LocaleInfo.getCurrentLocale().getNumberConstants();
    */
    
    valueBox = new MDoubleBox();
    valueBox.getElement().setPropertyString("type", "number");
    
    hp.add(valueBox);
    
    if (!disableSpinButtons) {
      rightBtn = new Button("+");
      GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
      rightBtn.addStyleName("spin-Button");
      hp.add(rightBtn);
    }
    
    initWidget(hp);

    if (!disableSpinButtons) {
      leftBtn.addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
          inc(increment * -1);
        }
      });
      rightBtn.addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
          inc(increment);
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
    valueBox.setValue(value, needFireEvents);
  }
  
  public void setIncrement(double increment) {
    this.increment = increment;
  }
  
  public void setValue(double value) {
    valueBox.setValue(value);
  }
  
  public Double getValue() {
    return valueBox.getValue();
  }
  
}
