package it.mate.ckd.client.ui;

import it.mate.ckd.client.config.ClientProperties;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomClientBundle;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;
//import com.google.gwt.user.client.ui.Button;

public class SpinnerIntegerBox extends Composite implements HasValueChangeHandlers<Integer> {
  
  private Button leftBtn = null;
  private TouchImage leftImg = null;
  private MIntegerBox valueBox;
  private Button rightBtn = null;
  private TouchImage rightImg = null;
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerIntegerBox_disableSpinButtons(); 
  
  private MGWTCustomClientBundle bundle = MGWTCustomTheme.getInstance().getMGWTClientBundle();
  
  public SpinnerIntegerBox() {
    initUI();
  }
  
  private void initUI() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    
    if (!disableSpinButtons) {
      
      leftImg = new TouchImage(bundle.minusImage());
      GwtUtils.setStyleAttribute(leftImg, "paddingRight", "4px");
      leftImg.addStyleName("spin-Button");
      hp.add(leftImg);

      /*
      leftBtn = new Button(" - ");
      GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
      leftBtn.addStyleName("spin-Button");
      hp.add(leftBtn);
      */
      
    }
    
    valueBox = new MIntegerBox();
    valueBox.getElement().setPropertyString("type", "number");
    hp.add(valueBox);
    
    if (!disableSpinButtons) {
      
      rightImg = new TouchImage(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightImg, "paddingLeft", "4px");
      rightImg.addStyleName("spin-Button");
      hp.add(rightImg);

      /*
      rightBtn = new Button(" + ");
      GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
      rightBtn.addStyleName("spin-Button");
      hp.add(rightBtn);
      */
      
    }
    
    initWidget(hp);

    if (!disableSpinButtons) {
      if (leftBtn != null) {
        leftBtn.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            inc(-1);
          }
        });
      }
      if (leftImg != null) {
        leftImg.addTouchStartHandler(new TouchStartHandler() {
          public void onTouchStart(TouchStartEvent event) {
            inc(-1);
          }
        });
      }
      if (rightBtn != null) {
        rightBtn.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            inc(+1);
          }
        });
      }
      if (rightImg != null) {
        rightImg.addTouchStartHandler(new TouchStartHandler() {
          public void onTouchStart(TouchStartEvent event) {
            inc(+1);
          }
        });
      }
      /*
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
      */
      /*
      leftBtn.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          inc(-1);
        }
      });
      rightBtn.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          inc(+1);
        }
      });
      */
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
    valueBox.setValue(value, true);
  }
  
  public void setValue(Integer value) {
    valueBox.setValue(value);
  }
  
  public Integer getValue() {
    return valueBox.getValue();
  }
  
}
