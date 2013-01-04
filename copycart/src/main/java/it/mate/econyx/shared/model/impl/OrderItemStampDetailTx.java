package it.mate.econyx.shared.model.impl;

import it.mate.gwtcommons.shared.model.CloneableBean;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.OrderItemStampDetailDs")
public class OrderItemStampDetailTx extends AbstractOrderItemDetailTx {

  private String type;
  
  private String text;
  
  private Integer fontType;
  
  private Integer fontSize;
  
  private Boolean bold;
  
  private Boolean italic;
  
  private Boolean underline;
  
  private String align;
  
  private String logo;
  
  private Integer logoX;
  
  private Integer logoY;
  
  private Integer borderSize;
  
  public OrderItemStampDetailTx() {
    super();
  }
  
  public OrderItemStampDetailTx(String type) {
    super();
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getBold() {
    return bold;
  }

  public void setBold(Boolean bold) {
    this.bold = bold;
  }
  
  public Integer getFontType() {
    return fontType;
  }

  public void setFontType(Integer fontType) {
    this.fontType = fontType;
  }

  public Integer getFontSize() {
    return fontSize;
  }

  public void setFontSize(Integer fontSize) {
    this.fontSize = fontSize;
  }

  public Boolean getItalic() {
    return italic;
  }

  public void setItalic(Boolean italic) {
    this.italic = italic;
  }

  public Boolean getUnderline() {
    return underline;
  }

  public void setUnderline(Boolean underline) {
    this.underline = underline;
  }

  public String getAlign() {
    return align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public Integer getLogoX() {
    return logoX;
  }

  public void setLogoX(Integer logoX) {
    this.logoX = logoX;
  }

  public Integer getLogoY() {
    return logoY;
  }

  public void setLogoY(Integer logoY) {
    this.logoY = logoY;
  }

  public Integer getBorderSize() {
    return borderSize;
  }

  public void setBorderSize(Integer borderSize) {
    this.borderSize = borderSize;
  }
  
}
