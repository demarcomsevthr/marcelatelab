package it.mate.copymob.shared.model;

import java.io.Serializable;

public interface OrderItemRow extends Serializable {

  public Integer getId();

  public void setId(Integer id);

  public void setText(String text);

  public String getText();

  public void setOrderItemId(Integer orderItemId);

  public Integer getOrderItemId();

  public void setBold(Boolean bold);

  public Boolean getBold();
  
  public boolean isBold();

  public void setSize(Integer size);

  public Integer getSize();

  public void setFontFamily(String fontFamily);

  public String getFontFamily();

  public void setRemoteId(String remoteId);

  public String getRemoteId();

}
