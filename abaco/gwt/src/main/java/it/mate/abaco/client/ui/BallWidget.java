package it.mate.abaco.client.ui;

import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.googlecode.mgwt.dom.client.event.touch.Touch;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveHandler;
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
    
    
    /*
    this.addTouchMoveHandler(new TouchMoveHandler() {
      public void onTouchMove(TouchMoveEvent event) {
        BallWidget ball = (BallWidget)event.getSource();
        GwtUtils.log("touch move source " + ball);
        for (int it = 0; it < event.getTouches().length(); it++) {
          Touch touch = event.getTouches().get(it);
          GwtUtils.log("touch move touch " + touch.getIdentifier()+" "+touch.getPageY());
          BallWidget.moveImpl(ball.getElement().getId(), touch.getPageY());
        }
        event.preventDefault();
      }
    });
    */
    
  }

  public int getCount() {
    return count;
  }
  
  public int getColor() {
    return color;
  }
  
  private static native void moveImpl(String divId, int newY) /*-{
    
    $wnd.$("#"+divId).animate({
      top: newY+"px"
    }, 100);
    
  }-*/;
  
  
}
