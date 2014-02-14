package it.mate.phgcommons.client.ui.ph;

import it.mate.phgcommons.client.ui.HasTag;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class PhCheckBox extends TouchWidget implements HasTag {

  private String tag;
  
  private Element elem;
  
  private boolean selected;
  
  public PhCheckBox() {
    elem = DOM.createDiv();
    setElement(elem);
    elem.addClassName("phg-CheckBox");
    
    addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        selected = !selected;
        if (selected) {
          elem.addClassName("phg-CheckBox-selected");
        } else {
          elem.removeClassName("phg-CheckBox-selected");
        }
      }
    });
    
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

}
