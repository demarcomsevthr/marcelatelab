package it.mate.gpg.client.ui;

import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class CkdCheckBox extends TouchWidget implements HasValue<Boolean> {

  private Image image;
  
  private Spacer spacer;
  
  private Element div;
  
  private boolean checked = false;
  
  public CkdCheckBox() {
    div = DOM.createDiv();
    setElement(div);
    addStyleName("ckd-CheckBox");
    
    addTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
        if (MGWT.getOsDetection().isAndroid()) {
          GwtUtils.deferredExecution(new Delegate<Void>() {
            public void execute(Void element) {
              checked = !checked;
              update();
            }
          });
        } else {
          checked = !checked;
          update();
        }
      }
    });
    
    image = new Image();
    
    spacer = new Spacer("20px", "22px");
    
    update();
    
  }
  
  private void update() {
    if (checked) {
      spacer.getElement().removeFromParent();
      div.appendChild(image.getElement());
    } else {
      image.getElement().removeFromParent();
      div.appendChild(spacer.getElement());
    }
  }
  
  public void setImage(ImageResource img) {
    image.setResource(img);
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
    return null;
  }

  @Override
  public Boolean getValue() {
    return checked;
  }

  @Override
  public void setValue(Boolean value) {
    this.checked = value;
    update();
  }

  @Override
  public void setValue(Boolean value, boolean fireEvents) {
    this.checked = value;
    update();
  }
  
  
  
}
