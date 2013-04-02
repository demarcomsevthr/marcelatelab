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
  /*
  private Button leftBtn = null;
  private SpinTouchable leftImg = null;
  private SpinAnchor leftAnc = null;
  private Button rightBtn = null;
  private SpinTouchable rightImg = null;
  private SpinAnchor rightAnc = null;
  */
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerIntegerBox_disableSpinButtons(); 
  
//private MGWTCustomClientBundle bundle = MGWTCustomTheme.getInstance().getMGWTClientBundle();
  private CustomTheme.CustomBundle bundle = CustomTheme.Instance.get();
  
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
      
      /*
      leftAnc = new SpinAnchor(bundle.minusImage());
      GwtUtils.setStyleAttribute(leftAnc, "paddingLeft", "4px");
      GwtUtils.setStyleAttribute(leftAnc, "paddingRight", "6px");
      hp.add(leftAnc);
      */

      /*
      leftImg = new TouchImage(bundle.minusImage());
      GwtUtils.setStyleAttribute(leftImg, "paddingRight", "4px");
      leftImg.addStyleName("spin-Button");
      hp.add(leftImg);
      */

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
      
      rightSpin = new SpinControl(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightSpin, "paddingLeft", "6px");
      hp.add(rightSpin);
      
      /*
      rightAnc = new SpinAnchor(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightAnc, "paddingLeft", "6px");
      hp.add(rightAnc);
      */

      /*
      rightImg = new TouchImage(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightImg, "paddingLeft", "4px");
      rightImg.addStyleName("spin-Button");
      hp.add(rightImg);
      */

      /*
      rightBtn = new Button(" + ");
      GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
      rightBtn.addStyleName("spin-Button");
      hp.add(rightBtn);
      */
      
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
      
      /*
      if (leftAnc != null) {
        leftAnc.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            inc(-1);
          }
        });
      }
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
      
      if (rightAnc != null) {
        rightAnc.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            inc(+1);
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
      */
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
