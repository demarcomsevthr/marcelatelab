package it.mate.gpg.client.ui;

import it.mate.gpg.client.utils.UIConstants;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.Button;

public class SpinnerDoubleBox extends Composite implements HasValueChangeHandlers<Double> {
  
  private Button leftBtn;
  private DoubleBox valueBox;
  private Button rightBtn;
  
  private double increment;
  
  private boolean needFireEvents = false;

  public SpinnerDoubleBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    leftBtn = new Button("-");
    GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
    hp.add(leftBtn);
    valueBox = new DoubleBox();
    valueBox.getElement().getStyle().setFontSize(UIConstants.DEFAULT_SPINNER_FONT_SIZE, Unit.PX);
    valueBox.getElement().setPropertyString("type", "number");
    hp.add(valueBox);
    rightBtn = new Button("+");
    GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
    hp.add(rightBtn);
    initWidget(hp);

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