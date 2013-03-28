package it.mate.ckd.client.ui;

import it.mate.ckd.client.config.ClientProperties;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomClientBundle;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gwtcommons.client.utils.GwtUtils;

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
//import com.google.gwt.user.client.ui.Button;

public class SpinnerDoubleBox extends Composite implements HasValueChangeHandlers<Double> {
  
  private MDoubleBox valueBox;
  
  private SpinControl leftSpin;
  private SpinControl rightSpin;
  
  /*
  private Button leftBtn = null;
  private TouchImage leftImg = null;
  private SpinAnchor leftAnc = null;
  private Button rightBtn = null;
  private TouchImage rightImg = null;
  private SpinAnchor rightAnc = null;
  */
  
  private double increment;
  
  private boolean needFireEvents = false;
  
  private boolean disableSpinButtons = ClientProperties.IMPL.SpinnerDoubleBox_disableSpinButtons(); 

  private MGWTCustomClientBundle bundle = MGWTCustomTheme.getInstance().getMGWTClientBundle();
  
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
      leftBtn = new Button("-");
      GwtUtils.setStyleAttribute(leftBtn, "fontSize", "14px");
      leftBtn.addStyleName("spin-Button");
      hp.add(leftBtn);
      */
    }
    
    /** SEE:
    NumberFormat.getDecimalFormat();
    LocaleInfo.getCurrentLocale().getNumberConstants();
    */
    
    valueBox = new MDoubleBox();
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
      rightBtn = new Button("+");
      GwtUtils.setStyleAttribute(rightBtn, "fontSize", "14px");
      rightBtn.addStyleName("spin-Button");
      hp.add(rightBtn);
      */
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
      /*
      if (leftAnc != null) {
        leftAnc.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            inc(increment * -1);
          }
        });
      }
      if (leftBtn != null) {
        leftBtn.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            inc(increment * -1);
          }
        });
      }
      if (leftImg != null) {
        leftImg.addTouchStartHandler(new TouchStartHandler() {
          public void onTouchStart(TouchStartEvent event) {
            inc(increment * -1);
          }
        });
      }
      
      if (rightAnc != null) {
        rightAnc.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            inc(increment * +1);
          }
        });
      }
      if (rightBtn != null) {
        rightBtn.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            inc(increment * +1);
          }
        });
      }
      if (rightImg != null) {
        rightImg.addTouchStartHandler(new TouchStartHandler() {
          public void onTouchStart(TouchStartEvent event) {
            inc(increment * +1);
          }
        });
      }
      */
      /*
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
      */
      /*
      leftBtn.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          inc(increment * -1);
        }
      });
      rightBtn.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          inc(increment);
        }
      });
      */
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
  
  public class SpinAnchor extends Anchor {
    public SpinAnchor(ImageResource resource) {
      super();
      Image image = new Image(resource);
      getElement().appendChild(image.getElement());
      addStyleName("ckd-spinAnchor");
    }
  }
  
}
