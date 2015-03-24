package it.mate.copymob.server.model;

import it.mate.commons.server.model.CollectionPropertyServerUtil;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
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
public class OrderDs implements Order, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key remoteId;
  
  @Persistent
  private Integer id;
  
  @Persistent
  private String codice;

  //TODO
  private Integer accountId;
  
  @Persistent
  private Integer state;
  
  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> itemKeys;
  @UnownedRelationship (key="itemKeys", itemClass=OrderItemDs.class)
  transient List<OrderItemDs> items;

  @Override
  public Key getKey() {
    return remoteId;
  }

  public String getRemoteId() {
    return remoteId != null ? KeyFactory.keyToString(remoteId) : null;
  }
  
  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId != null ? KeyFactory.stringToKey(remoteId) : null;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public List<OrderItem> getItems() {
    return items != null ? new ArrayList<OrderItem>(items) : null;
  }

  @CloneableProperty (targetClass=OrderItemDs.class)
  public void setItems(List<OrderItem> items) {
    CollectionPropertyServerUtil<OrderItem, OrderItemDs> wrapper = CollectionPropertyServerUtil.clone(items, OrderItemDs.class);
    this.items = wrapper.getItems();
    this.itemKeys = wrapper.getKeys();
  }

}
