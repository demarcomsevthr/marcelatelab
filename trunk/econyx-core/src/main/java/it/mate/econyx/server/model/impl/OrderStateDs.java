package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.OrderStateTx;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=OrderStateTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class OrderStateDs implements OrderState, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent (dependentKey="false")
  Key orderStateConfigId;
  
  @UnownedRelationship (key="orderStateConfigId")
  transient OrderStateConfigDs orderStateConfig;
  
  @Persistent
  Boolean checked;
  
  @Persistent
  Date date;
  
  @Persistent (dependentKey="false")
  Key orderId;
  
  @Persistent
  String orderCode;
  
  
  @Persistent (dependentKey="false")
  Key portalUserId;
  
  @UnownedRelationship (key="portalUserId")
  transient PortalUserDs portalUser;
  
  // serve solo per comunicare all'adapter di non inviare l'email
  transient Boolean disableEmailToCustomerSubmission;

  public Key getKey() {
    return id;
  }
  
  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
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

  @CloneableProperty (targetClass=OrderStateConfigDs.class)
  public void setConfig(OrderStateConfig orderState) {
    this.orderStateConfig = (OrderStateConfigDs)orderState;
    this.orderStateConfigId = orderState != null ? ((OrderStateConfigDs)orderState).getKey() : null;
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
    OrderDs order = new OrderDs();
    order.setId(orderId != null ? KeyFactory.keyToString(orderId) : null);
    order.setCode(orderCode);
    return order;
  }

  @CloneableProperty (targetClass=OrderDs.class)
  public void setOrder(Order order) {
    OrderDs orderDs = (OrderDs)order;
    this.orderId = orderDs.getKey();
    this.orderCode = orderDs.getCode();
  }
  
  public PortalUser getPortalUser() {
    return portalUser;
  }

  @CloneableProperty (targetClass=PortalUserDs.class)
  public void setPortalUser(PortalUser portalUser) {
    this.portalUser = (PortalUserDs)portalUser;
    this.portalUserId = this.portalUser != null ? this.portalUser.getKey() : null;
  }
  
  public Boolean getDisableEmailToCustomerSubmission() {
    return disableEmailToCustomerSubmission != null ? disableEmailToCustomerSubmission : false;
  }

  public void setDisableEmailToCustomerSubmission(Boolean disableEmailToCustomerSubmission) {
    this.disableEmailToCustomerSubmission = disableEmailToCustomerSubmission;
  }
  
}
