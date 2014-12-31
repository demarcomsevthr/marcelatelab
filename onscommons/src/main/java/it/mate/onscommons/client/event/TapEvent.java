package it.mate.onscommons.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;

public class TapEvent extends GwtEvent<TapHandler> {

  private static final Type<TapHandler> TYPE = new Type<TapHandler>();
  private final int startX;
  private final int startY;
  private final Element targetElement;
  
  public TapEvent(Object source, Element targetElement, int startX, int startY) {
    this.targetElement = targetElement;
    this.startX = startX;
    this.startY = startY;
    setSource(source);
  }
  
  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<TapHandler> getAssociatedType() {
    return TYPE;
  }

  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(TapHandler handler) {
    handler.onTap(this);
  }
  
  public static Type<TapHandler> getType() {
    return TYPE;
  }

  /**
   * get the x start position of the tap
   * 
   * @return the x start position of the tap
   */
  public int getStartX() {
    return startX;
  }

  /**
   * get the y start position of the tap
   * 
   * @return the y start position of the tap
   */
  public int getStartY() {
    return startY;
  }
  
  /**
   * Returns the element that was the actual target of the Tap event.
   */
  public Element getTargetElement() {
    return targetElement;
  }
  
}
