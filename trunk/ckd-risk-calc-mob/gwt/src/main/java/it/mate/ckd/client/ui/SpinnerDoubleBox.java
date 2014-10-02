package it.mate.ckd.client.ui;

import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.MDoubleBox;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;

public class SpinnerDoubleBox extends Composite implements HasValueChangeHandlers<Double> {
  
  private ValueBox valueBox;
  
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

    if (MGWT.getOsDetection().isIOs()) {
      valueBox = new ValueBoxGwt();      /*  1.0.5.F  */
//    valueBox = new ValueBoxPatch();    /*  1.0.5.G  */
//    valueBox = new ValueBoxGwtText();  /*  1.0.5.H  */
    } else {
      valueBox = new ValueBoxGwt();
    }
//  valueBox = new ValueBoxMgwt();

    hp.add(valueBox.create());
    
    if (!disableSpinButtons) {
      rightSpin = new SpinControl(bundle.plusImage());
      GwtUtils.setStyleAttribute(rightSpin, "paddingLeft", "6px");
      GwtUtils.setStyleAttribute(rightSpin, "marginLeft", "10px");
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
    valueBox.setValue(value, false);
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
  
  public void setMinValue(Double minValue) {
    this.minValue = minValue;
  }
  
  public void setMaxValue(Double maxValue) {
    this.maxValue = maxValue;
  }
  
  private static native String getLocalLanguageCookie() /*-{
    return $wnd.getLocalLanguageCookie();
  }-*/;
  
  
  
  public interface ValueBox {
    public Widget create();
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler);
    public Double getValue();
    public void setValue(double value, boolean fireEvents);
  }
  
  public class ValueBoxPatch implements ValueBox {
    private MIntegerBox intValueBox;
    private MIntegerBox decValueBox;
    
    public Widget create() {
      HorizontalPanel innerHp = new HorizontalPanel();
      innerHp.setBorderWidth(0);
      innerHp.setSpacing(0);
      intValueBox = new MIntegerBox();
      intValueBox.addStyleName("mgwt-SpinnerDouble-int-part");
      decValueBox = new MIntegerBox();
      decValueBox.addStyleName("mgwt-SpinnerDouble-dec-part");
      
//    intValueBox.getElement().setPropertyString("type", "number");
//    decValueBox.getElement().setPropertyString("type", "number");
      
      intValueBox.getElement().setPropertyString("type", "text");
      intValueBox.getElement().setPropertyString("pattern", "[0-9]*");
      decValueBox.getElement().setPropertyString("type", "text");
      decValueBox.getElement().setPropertyString("pattern", "[0-9]*");
      
      innerHp.add(intValueBox);
      innerHp.add(decValueBox);
      
      return innerHp;
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
      return null;
    }

    public Double getValue() {
      if (intValueBox.getValue() == null)
        return null;
      Double value = GwtUtils.composeDouble(intValueBox.getValue(), decValueBox.getValue(), 1);
      return value;
    }
    
    public void setValue(double value, boolean fireEvents) {
      intValueBox.setValue((int)value, fireEvents);
      decValueBox.setValue(GwtUtils.getDecimals(value, 1), fireEvents);
    }
    
  }
  
  public class ValueBoxMgwt implements ValueBox {
    private CMDoubleBox valueBox;
    public Widget create() {
      valueBox = new CMDoubleBox();
      valueBox.getElement().setPropertyString("type", "number");
      
      valueBox.getBox().getElement().setPropertyString("type", "number");
      valueBox.getBox().getElement().setPropertyString("step", "0.1");
      
//    ((CMDoubleBox)valueBox).getBox().getElement().setPropertyString("type", "text");
//    ((CMDoubleBox)valueBox).getBox().getElement().setPropertyString("pattern", "[0-9]*[.][0-9]*");

      if (AppProperties.IMPL.SpinnerDoubleBox_keyPress_fix_enabled()) {
        valueBox.addKeyPressHandler(new KeyPressHandler() {
          public void onKeyPress(KeyPressEvent event) {
            char separatorToAvoid = ',';
            
            /*
//          String language = PhgUtils.getNavigator().getLanguage();
            if (language != null && language.toLowerCase().startsWith("it")) {
              separatorToAvoid = '.';
            }
            */
            
            if (event.getCharCode() == separatorToAvoid) {
              event.preventDefault();
            }
          }
        });
      }
      
      return valueBox;
    }
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
      return valueBox.addValueChangeHandler(handler);
    }
    public Double getValue() {
      return valueBox.getValueAsDouble();
    }
    public void setValue(double value, boolean fireEvents) {
      valueBox.setValue(value, fireEvents);
    }
  }
  
  public class ValueBoxGwt implements ValueBox {
    private DoubleBox valueBox;
    public Widget create() {
      valueBox = new DoubleBox();
      // simulo i css di mgwt
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          valueBox.getElement().addClassName("mgwt-InputBox-box");
          valueBox.getElement().getParentElement().addClassName("mgwt-TextBox");
        }
      });
      valueBox.getElement().setPropertyString("type", "number");
      
      valueBox.addKeyPressHandler(new KeyPressHandler() {
        public void onKeyPress(KeyPressEvent event) {
          if (event.getCharCode() == ',') {
            event.preventDefault();
          }
          if (event.getCharCode() >= ' ') {
            if (event.getCharCode() == '.') {
              //OK
            } else if (event.getCharCode() >= '0' && event.getCharCode() <= '9') {
              //OK
            } else {
              event.preventDefault();
            }
          }
        }
      });
      
      return valueBox;
    }
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
      return valueBox.addValueChangeHandler(handler);
    }
    public Double getValue() {
      return valueBox.getValue();
    }
    public void setValue(double value, boolean fireEvents) {
      valueBox.setValue(value, fireEvents);
    }
  }
  
  public class ValueBoxGwtText implements ValueBox {
    private TextBox valueBox;
    public Widget create() {
      valueBox = new TextBox();
      // simulo i css di mgwt
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          valueBox.getElement().addClassName("mgwt-InputBox-box");
          valueBox.getElement().getParentElement().addClassName("mgwt-TextBox");
        }
      });
      valueBox.getElement().setPropertyString("type", "text");
      valueBox.getElement().setPropertyString("pattern", "[0-9]*[.][0-9]*");
      
      valueBox.addKeyPressHandler(new KeyPressHandler() {
        public void onKeyPress(KeyPressEvent event) {
          if (event.getCharCode() == ',') {
            event.preventDefault();
          }
          if (event.getCharCode() >= ' ') {
            if (event.getCharCode() == '.') {
              //OK
            } else if (event.getCharCode() >= '0' && event.getCharCode() <= '9') {
              //OK
            } else {
              event.preventDefault();
            }
          }
        }
      });
      
      return valueBox;
    }
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
//    return valueBox.addValueChangeHandler(handler);
      return null;
    }
    public Double getValue() {
      String text = valueBox.getValue();
      text = GwtUtils.replace(text, ",", ".");
      return GwtUtils.parseDecimal(text);
    }
    public void setValue(double value, boolean fireEvents) {
      String text = GwtUtils.formatDecimal(value, 2);
      valueBox.setValue(text, fireEvents);
    }
  }
  
  
  public class CMDoubleBox extends MDoubleBox {
    public ValueBoxBase<Double> getBox() {
      return box;
    }
    public double getValueAsDouble() {
      return getValueAsNumber(box.getElement());
    }
    private native double getValueAsNumber(JavaScriptObject elem) /*-{
      return elem.valueAsNumber;
    }-*/;
  }
  

}
