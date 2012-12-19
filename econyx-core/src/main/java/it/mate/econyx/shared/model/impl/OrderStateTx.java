package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

@SuppressWarnings("serial")
public class OrderStateTx implements OrderState {
  
  String id;
  
  String code;
  
  OrderStateConfigTx orderStateConfig;
  
  Boolean checked;
  
  Date date;
  
  OrderTx order;
  
  PortalUserTx portalUser;

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

  public OrderStateConfig getConfig() {
    return orderStateConfig;
  }

  @CloneableProperty (targetClass=OrderStateConfigTx.class)
  public void setConfig(OrderStateConfig orderState) {
    this.orderStateConfig = (OrderStateConfigTx)orderState;
  }

  public Boolean getChecked() {
    return checked != null ? checked : false;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Order getOrder() {
    return order;
  }

  @CloneableProperty (targetClass=OrderTx.class)
  public void setOrder(Order order) {
    this.order = (OrderTx)order;
  }

  public PortalUser getPortalUser() {
    return portalUser;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setPortalUser(PortalUser portalUser) {
    this.portalUser = (PortalUserTx)portalUser;
  }
  
}
