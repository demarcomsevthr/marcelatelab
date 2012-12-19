package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Image;

@SuppressWarnings("serial")
public class ImageTx implements Image {

  private String id;
  
  private String name;
  
  private String code;
  
  private Integer orderNm;

  protected String content;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public String getContentString() {
    return content;
  }

  public void setContentString(String contentString) {
    this.content = contentString;
  }
  
}
