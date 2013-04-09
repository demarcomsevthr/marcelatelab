package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.PortalResource;

@SuppressWarnings("serial")
public abstract class AbstractPortalResourceTx implements PortalResource {
  
  protected String id;
  
  protected String name;
  
  protected Integer orderNm;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
  
}
