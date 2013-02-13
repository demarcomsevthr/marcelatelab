package it.mate.abaco.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class BallWidget extends TouchWidget {
  
  private int count;
  
  private int color;
  
  public final static int GREEN = 1;

  public final static int RED = 2;

  public final static int BLUE = 3;

  public BallWidget(String prefix, int count, int top, int left, int color) {
    this.count = count;
    this.color = color;
    Element elem = DOM.createDiv();
    elem.setId(prefix+count);
    elem.setClassName("ball");
    elem.getStyle().setTop(top, Unit.PX);
    elem.getStyle().setLeft(left, Unit.PX);
    String colorname = color == GREEN ? "lightgreen" : color == RED ? "red" : "blue";
    elem.getStyle().setBackgroundColor(colorname);
    setElement(elem);
  }

  public int getCount() {
    return count;
  }
  
  public int getColor() {
    return color;
  }
  
}
