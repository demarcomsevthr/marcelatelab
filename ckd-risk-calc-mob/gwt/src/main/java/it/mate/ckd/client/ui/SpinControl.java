package it.mate.ckd.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class SpinControl extends Composite {

  private SpinAnchor anchor;
  private SpinTouchable touchable;
  
  public SpinControl(ImageResource resource) {
    if (MGWT.getOsDetection().isIOs()) {
      touchable = new SpinTouchable(resource);
      this.initWidget(touchable);
    } else {
      anchor = new SpinAnchor(resource);
      this.initWidget(anchor);
    }
    
  }
  public void addHandler(final SpinHandler handler) {
    if (touchable != null) {
      touchable.addTouchStartHandler(new TouchStartHandler() {
        public void onTouchStart(TouchStartEvent event) {
          handler.onSpin();
        }
      });
    } else if (anchor != null) {
      anchor.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          handler.onSpin();
        }
      });
    }
  }
  
  public class SpinAnchor extends Anchor {
    public SpinAnchor(ImageResource resource) {
      super();
      Image image = new Image(resource);
      getElement().appendChild(image.getElement());
      addStyleName("ckd-spinControl");
    }
  }
  
  public class SpinTouchable extends TouchWidget {
    public SpinTouchable(ImageResource resource) {
      Element elem = DOM.createDiv();
      setElement(elem);
      Image image = new Image();
      image.setResource(resource);
      elem.appendChild(image.getElement());
      addStyleName("ckd-spinControl");
    }
  }
  
  public interface SpinHandler {
    void onSpin();
  }
  
}
