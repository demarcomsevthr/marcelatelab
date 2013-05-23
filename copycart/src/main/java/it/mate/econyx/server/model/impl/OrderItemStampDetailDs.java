package it.mate.econyx.server.model.impl;

import it.mate.commons.server.utils.BlobUtils;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.OrderItemStampDetailTx")
public class OrderItemStampDetailDs extends AbstractOrderItemDetailDs {

  @Persistent
  String type;
  
  @Persistent
  String text;

  @Persistent
  Integer fontType;
  
  @Persistent
  Integer fontSize;
  
  @Persistent
  Boolean bold;

  @Persistent
  Boolean italic;
  
  @Persistent
  Boolean underline;
  
  @Persistent
  String align;
  
  @Persistent
  Blob logo;
  
  @Persistent
  Integer logoX;
  
  @Persistent
  Integer logoY;
  
  @Persistent
  Integer borderSize;
  
  
  @Override
  public String toString() {
    return "OrderItemStampDetailDs [type=" + type + ", text=" + text + ", fontType=" + fontType + ", fontSize=" + fontSize + ", bold=" + bold + ", italic="
        + italic + ", underline=" + underline + ", align=" + align + ", logo=" + (logo != null ? "SET" : "NULL")+ ", logoX=" + logoX + ", logoY=" + logoY + "]";
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
    return bold != null ? bold : false;
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
    return italic != null ? italic : false;
  }

  public void setItalic(Boolean italic) {
    this.italic = italic;
  }

  public Boolean getUnderline() {
    return underline != null ? underline : false;
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
    return logo != null ? BlobUtils.blobToString(logo) : null;
  }

  public void setLogo(String base64Logo) {
    this.logo = base64Logo != null && base64Logo.length() > 0 ? BlobUtils.stringToBlob(base64Logo) : null;
  }
  
  public Blob getLogoAsBlob() {
    return logo;
  }

  public void setLogoAsBlob(Blob logo) {
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
