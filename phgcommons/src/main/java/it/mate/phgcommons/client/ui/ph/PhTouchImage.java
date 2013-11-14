package it.mate.phgcommons.client.ui.ph;

import it.mate.phgcommons.client.ui.HasTag;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class PhTouchImage extends TouchWidget implements HasTag {
  
  private Image image;
  
  private Element elem;
  
  private String tag;

  public PhTouchImage() {
    elem = DOM.createDiv();
    setElement(elem);
    image = new Image();
  }
  
  public PhTouchImage(ImageResource resource) {
    this();
    setResource(resource);
  }
  
  public void setResource(ImageResource resource) {
    image.setResource(resource);
    elem.appendChild(image.getElement());
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
  
}
