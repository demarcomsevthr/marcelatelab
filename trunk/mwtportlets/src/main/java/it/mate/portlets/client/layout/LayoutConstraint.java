package it.mate.portlets.client.layout;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LayoutConstraint implements Serializable {

  public static final String OVERFLOW_AUTO = "auto";
  public static final String OVERFLOW_VISIBLE = "visible";
  public static final String OVERFLOW_HIDDEN = "hidden";
  public static final String OVERFLOW_SCROLL = "scroll";

  private String size;
  private String overflow = "auto";
  
  public String getSize() {
    return size;
  }
  public LayoutConstraint setSize(String size) {
    this.size = size;
    return this;
  }
  public boolean isOverflowVisible() {
    return OVERFLOW_VISIBLE.equals(overflow);
  }
  public String getOverflow() {
    return overflow;
  }
  public LayoutConstraint setOverflow(String overflow) {
    this.overflow = overflow;
    return this;
  }
  
  
}
