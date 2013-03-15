package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.OrderItemDetail;

@SuppressWarnings("serial")
public class AbstractOrderItemDetailTx implements OrderItemDetail {

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
}
