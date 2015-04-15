package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.gwtcommons.shared.rpc.ValueConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class OrderTx implements Order, IsMappable {
  
  private Integer id;
  
  private String remoteId;
  
  private String codice;
  
  private Integer state = Order.STATE_DEFAULT;
  
  private List<OrderItemTx> items = new ArrayList<OrderItemTx>();
  
  private AccountTx account;
  
  private Date lastUpdate;
  
  public OrderTx() {

  }
  
  protected OrderTx(Integer id) {
    this.id = id;
  }

  
  @Override
  public String toString() {
    return "OrderTx [id=" + id + ", remoteId=" + remoteId + ", codice=" + codice + ", state=" + state + ", account=" + account + ", lastUpdate=" + lastUpdate
        + ", items.size=" + items.size() + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    
    map.putField("id", id);
    map.putField("remoteId", remoteId);
    map.putField("codice", codice);
    map.putField("state", state);
    map.putField("items", items);
    map.putField("account", account);
    map.putField("lastUpdate", lastUpdate);
    
    /*
    map.put("id", id);
    map.put("remoteId", remoteId);
    map.put("codice", codice);
    map.put("state", state);
    List<RpcMap> itemMaps = new ArrayList<RpcMap>();
    if (items != null) {
      for (OrderItem item : items) {
        OrderItemTx itemTx = (OrderItemTx)item;
        RpcMap itemMap = itemTx.toRpcMap();
        itemMaps.add(itemMap);
      }
    }
    map.put("itemMaps", itemMaps);
    map.put("account", account.toRpcMap());
    map.put("lastUpdate", lastUpdate);
    */
    return map;
  }

  @Override
  @SuppressWarnings("unchecked")
  public OrderTx fromRpcMap(RpcMap map) {
    
    this.id = map.getField("id");
    this.remoteId = map.getField("remoteId");
    this.codice = map.getField("codice");
    this.state = map.getField("state");
    this.items = map.getField("items", new ValueConstructor<OrderItemTx>() {
      public OrderItemTx newInnstance() {
        return new OrderItemTx(OrderTx.this);
      }
    });
    this.account = map.getField("account", new ValueConstructor<AccountTx>() {
      public AccountTx newInnstance() {
        return new AccountTx();
      }
    });
    this.lastUpdate = map.getField("lastUpdate");
    
    /*
    this.id = (Integer)map.get("id");
    this.remoteId = (String)map.get("remoteId");
    this.codice = (String)map.get("codice");
    this.state = (Integer)map.get("state");
    this.items = new ArrayList<OrderItemTx>();
    List<RpcMap> itemMaps = (List<RpcMap>)map.get("itemMaps");
    for (RpcMap itemMap : itemMaps) {
      OrderItemTx itemTx = new OrderItemTx().fromRpcMap(itemMap);
      this.items.add(itemTx);
    }
    RpcMap accountMap = (RpcMap)map.get("account");
    if (accountMap != null) {
      this.account = new AccountTx().fromRpcMap(accountMap);
    }
    this.lastUpdate = (Date)map.get("lastUpdate");
    */
    
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

  public List<OrderItem> getItems() {
    linkItems();
    return CollectionPropertyClientUtil.cast(items, OrderItemTx.class);
  }

  @CloneableProperty (targetClass=OrderItemTx.class)
  public void setItems(List<OrderItem> items) {
    this.items = CollectionPropertyClientUtil.clone(items, OrderItemTx.class);
    linkItems();
  }
  
  private void linkItems() {
    if (items != null) {
      for (OrderItemTx item : items) {
        item.setOrderId(this.id);
        item.setOrder(this);
      }
    }
  }

  public Integer getState() {
    return state != null ? state : Order.STATE_DEFAULT;
  }
  
  public boolean isCartOrder() {
    return new Integer(Order.STATE_IN_CART).equals(state);
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

  public Account getAccount() {
    return account;
  }

  @CloneableProperty (targetClass=AccountTx.class)
  public void setAccount(Account account) {
    this.account = (AccountTx)account;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
