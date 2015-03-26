package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OrderTx implements Order, IsMappable {
  
  private Integer id;
  
  private String remoteId;
  
  private String codice;
  
  private Integer accountId;
  
  private Integer state = Order.STATE_DEFAULT;
  
  private List<OrderItemTx> items = new ArrayList<OrderItemTx>();
  
  public OrderTx() {

  }
  
  protected OrderTx(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "OrderTx [id=" + id + ", remoteId=" + remoteId + ", codice=" + codice + ", accountId=" + accountId + ", state=" + state + ", items.size=" + (items != null ? items.size() : "null") + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("remoteId", remoteId);
    map.put("codice", codice);
    map.put("accountId", accountId);
    map.put("state", state);
    List<RpcMap> itemMaps = new ArrayList<RpcMap>();
    for (OrderItem item : items) {
      OrderItemTx itemTx = (OrderItemTx)item;
      RpcMap itemMap = itemTx.toRpcMap();
      itemMaps.add(itemMap);
    }
    map.put("itemMaps", itemMaps);
    return map;
  }

  @Override
  @SuppressWarnings("unchecked")
  public OrderTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.remoteId = (String)map.get("remoteId");
    this.codice = (String)map.get("codice");
    this.accountId = (Integer)map.get("accountId");
    this.state = (Integer)map.get("state");
    this.items = new ArrayList<OrderItemTx>();
    List<RpcMap> itemMaps = (List<RpcMap>)map.get("itemMaps");
    for (RpcMap itemMap : itemMaps) {
      OrderItemTx itemTx = new OrderItemTx().fromRpcMap(itemMap);
      this.items.add(itemTx);
    }
    return this;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
    for (OrderItem item : items) {
      item.setOrderId(id);
    }
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

  public List<OrderItem> getItems() {
    if (items != null) {
      for (OrderItem item : items) {
        item.setOrderId(this.id);
      }
    }
    return CollectionPropertyClientUtil.cast(items, OrderItemTx.class);
    /*
    return CollectionPropertyClientUtil.cast(discussions, BlogDiscussionTx.class);
     */
  }

  @CloneableProperty (targetClass=OrderItemTx.class)
  public void setItems(List<OrderItem> items) {
    this.items = CollectionPropertyClientUtil.clone(items, OrderItemTx.class);
    /*
    this.discussions = CollectionPropertyClientUtil.clone(discussions, BlogDiscussionTx.class);
     */
  }

  public Integer getState() {
    return state != null ? state : Order.STATE_DEFAULT;
  }

  public void setState(Integer state) {
    this.state = state != null ? state : 0;
  }

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

}
