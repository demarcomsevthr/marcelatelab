package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class OrderItemDs implements OrderItem, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent
  Key productId;
  
  @UnownedRelationship (key="productId")
  transient AbstractArticoloDs product;
  
  @Persistent
  Double quantity;
  
  protected transient OrderDs order;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> detailKeys;
  @UnownedRelationship (key="detailKeys", itemClass=AbstractOrderItemDetailDs.class)
  transient List<AbstractOrderItemDetailDs> details = new ArrayList<AbstractOrderItemDetailDs>();

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public Articolo getProduct() {
    return product;
  }

  @CloneableProperty (targetClass=AbstractArticoloDs.class)
  public void setProduct(Articolo product) {
    this.product = (AbstractArticoloDs)product;
    this.productId = this.product != null ? this.product.getKey() : null;
  }

  public Order getOrder() {
    return order;
  }

  @CloneableProperty (targetClass=OrderDs.class)
  public void setOrder(Order order) {
    this.order = (OrderDs)order;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public List<OrderItemDetail> getDetails() {
    return details != null ? new ArrayList<OrderItemDetail>(details) : null;
  }

  @CloneableProperty (targetClass=AbstractOrderItemDetailDs.class)
  public void setDetails(List<OrderItemDetail> details) {
    this.detailKeys = new ArrayList<Key>();
    this.details = new ArrayList<AbstractOrderItemDetailDs>();
    if (details != null) {
      for (OrderItemDetail detail : details) {
        if (detail instanceof AbstractOrderItemDetailDs) {
          AbstractOrderItemDetailDs detailDs = (AbstractOrderItemDetailDs)detail;
          attachDetail(detailDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + detail.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  protected void attachDetail(AbstractOrderItemDetailDs detail) {
    if (details == null)
      details = new ArrayList<AbstractOrderItemDetailDs>();
    this.details.add(detail);
    if (detailKeys == null)
      detailKeys = new ArrayList<Key>();
    detailKeys.add(detail.getKey());
  }
  
}
