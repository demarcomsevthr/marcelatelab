package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface PortalResource extends Serializable {

  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);
  
  public Integer getOrderNm();
  
  public void setOrderNm(Integer orderNm);

}
