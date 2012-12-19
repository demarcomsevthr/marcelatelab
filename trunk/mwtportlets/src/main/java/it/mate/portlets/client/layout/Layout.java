package it.mate.portlets.client.layout;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Layout implements Serializable {

  private boolean vertical;
  
  private String width;
  
  private String height;
  
  private String stylename;
  
  private String style;
  
  public Layout setVertical(boolean vertical) {
    this.vertical = vertical;
    return this;
  }

  public boolean isVertical() {
    return vertical;
  }

  public String getWidth() {
    return width;
  }

  public Layout setWidth(String width) {
    this.width = width;
    return this;
  }

  public String getHeight() {
    return height;
  }

  public Layout setHeight(String height) {
    this.height = height;
    return this;
  }

  public String getStylename() {
    return stylename;
  }

  public void setStylename(String stylename) {
    this.stylename = stylename;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }
  
}
