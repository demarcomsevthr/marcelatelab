package it.mate.ckd.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class TouchImage extends TouchWidget {
  
  private Image image;
  
  private Element elem;

  public TouchImage() {
    elem = DOM.createDiv();
    setElement(elem);
    image = new Image();
  }
  
  public TouchImage(ImageResource resource) {
    this();
    setResource(resource);
  }
  
  public void setResource(ImageResource resource) {
    image.setResource(resource);
    elem.appendChild(image.getElement());
  }
  
}
