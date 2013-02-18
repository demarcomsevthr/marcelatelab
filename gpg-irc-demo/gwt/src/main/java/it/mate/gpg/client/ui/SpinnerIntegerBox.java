package it.mate.gpg.client.ui;

import it.mate.gpg.client.utils.UIConstants;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.Button;

public class SpinnerIntegerBox extends Composite implements HasValueChangeHandlers<Integer> {
  
  private Button leftBtn;
  private IntegerBox valueBox;
  private Button rightBtn;
  
//private Timer longTapTimer = null;

  public SpinnerIntegerBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    leftBtn = new Button(" - ");
    GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
    hp.add(leftBtn);
    valueBox = new IntegerBox();
    valueBox.getElement().getStyle().setFontSize(UIConstants.DEFAULT_SPINNER_FONT_SIZE, Unit.PX);
    valueBox.getElement().setPropertyString("type", "number");
    hp.add(valueBox);
    rightBtn = new Button(" + ");
    GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
    hp.add(rightBtn);
    initWidget(hp);

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
    
    /*
    leftBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        inc(-1);
      }
    });
    rightBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        inc(+1);
      }
    });
    */
    
    /*
    rightBtn.addLongTapHandler(new LongTapHandler() {
      public void onLongTap(LongTapEvent event) {
        longTapTimer = GwtUtils.createTimer(Integer.parseInt(GwtUtils.getJSVar("uiSpinnerLongTapDelay", "10")), new Delegate<Void>() {
          public void execute(Void element) {
            inc(+1);
          }
        });
      }
    });
    rightBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (longTapTimer != null)
          longTapTimer.cancel();
        longTapTimer = null;
      }
    });
    */
    
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
