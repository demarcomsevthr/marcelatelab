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
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;

public class SpinnerIntegerBox extends Composite implements HasValueChangeHandlers<Integer> {
  
  private Button leftBtn;
  private MIntegerBox valueBox;
  private Button rightBtn;
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerIntegerBox_disableSpinButtons(); 
  
  public SpinnerIntegerBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    
    if (!disableSpinButtons) {
      leftBtn = new Button(" - ");
      GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
      leftBtn.addStyleName("spin-Button");
      hp.add(leftBtn);
    }
    
    valueBox = new MIntegerBox();
    valueBox.getElement().setPropertyString("type", "number");
    hp.add(valueBox);
    
    if (!disableSpinButtons) {
      rightBtn = new Button(" + ");
      GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
      rightBtn.addStyleName("spin-Button");
      hp.add(rightBtn);
    }
    
    initWidget(hp);

    if (!disableSpinButtons) {
      leftBtn.addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
          inc(-1);
        }
      });
      rightBtn.addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
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
    int value = valueBox.getValue();
    value += increment;
    valueBox.setValue(value, true);
  }
  
  public void setValue(Integer value) {
    valueBox.setValue(value);
  }
  
  public Integer getValue() {
    return valueBox.getValue();
  }
  
}
