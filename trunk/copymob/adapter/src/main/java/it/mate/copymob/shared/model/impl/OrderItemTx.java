package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.Message;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OrderItemTx implements OrderItem, IsMappable {
  
  private Integer id;
  
  private String remoteId;
  
  private Integer orderId;
  
  private Double quantity;
  
  private TimbroTx timbro;
  
  private Boolean inCart;
  
  private List<OrderItemRowTx> rows = new ArrayList<OrderItemRowTx>();
  
  private List<MessageTx> messages = new ArrayList<MessageTx>();
  
  private String previewImage;

  
  @Override
  public String toString() {
    return "OrderItemTx [id=" + id + ", orderId=" + orderId + ", quantity=" + quantity + ", timbro=" + timbro + ", rows=" + rows + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("remoteId", remoteId);
    map.put("orderId", orderId);
    map.put("quantity", quantity);
    map.put("inCart", inCart);
    map.put("timbro", timbro.toRpcMap());
    List<RpcMap> rowMaps = new ArrayList<RpcMap>();
    for (OrderItemRow row : rows) {
      OrderItemRowTx rowTx = (OrderItemRowTx)row;
      RpcMap rowMap = rowTx.toRpcMap();
      rowMaps.add(rowMap);
    }
    map.put("rowMaps", rowMaps);
    List<RpcMap> messageMaps = new ArrayList<RpcMap>();
    for (Message message : messages) {
      MessageTx messageTx = (MessageTx)message;
      RpcMap messageMap = messageTx.toRpcMap();
      messageMaps.add(messageMap);
    }
    map.put("messageMaps", messageMaps);
    map.put("previewImage", previewImage);
    return map;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public OrderItemTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.remoteId = (String)map.get("remoteId");
    this.orderId = (Integer)map.get("orderId");
    this.quantity = (Double)map.get("quantity");
    this.inCart = (Boolean)map.get("inCart");
    RpcMap timbroMap = (RpcMap)map.get("timbro");
    if (timbroMap != null) {
      this.timbro = new TimbroTx().fromRpcMap(timbroMap);
    }
    this.rows = new ArrayList<OrderItemRowTx>();
    List<RpcMap> rowMaps = (List<RpcMap>)map.get("rowMaps");
    if (rowMaps != null) {
      for (RpcMap rowMap : rowMaps) {
        OrderItemRowTx rowTx = new OrderItemRowTx().fromRpcMap(rowMap);
        this.rows.add(rowTx);
      }
    }
    this.messages = new ArrayList<MessageTx>();
    List<RpcMap> messageMaps = (List<RpcMap>)map.get("messageMaps");
    if (messageMaps != null) {
      for (RpcMap messageMap : messageMaps) {
        MessageTx messageTx = new MessageTx().fromRpcMap(messageMap);
        this.messages.add(messageTx);
      }
    }
    this.previewImage = (String)map.get("previewImage");
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OrderItemTx) {
      OrderItemTx that = (OrderItemTx)obj;
      if (this.id != null && that.id != null && this.id.equals(that.id)) {
        return true;
      }
      return (this.id == null && that.id == null);
    }
    return super.equals(obj);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
    for (OrderItemRow row : rows) {
      row.setOrderItemId(id);
    }
  }
  
  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }
  
  public Integer getTimbroId() {
    return timbro != null ? timbro.getId() : null;
  }

  public void setTimbroId(Integer timbroId) {
    this.timbro = new TimbroTx(timbroId);
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public List<OrderItemRow> getRows() {
    if (rows != null) {
      for (OrderItemRow row : rows) {
        row.setOrderItemId(this.id);
      }
    }
    return CollectionPropertyClientUtil.cast(rows, OrderItemRowTx.class);
  }

  @CloneableProperty (targetClass=OrderItemRowTx.class)
  public void setRows(List<OrderItemRow> rows) {
    this.rows = CollectionPropertyClientUtil.clone(rows, OrderItemRowTx.class);
  }
  
  public List<Message> getMessages() {
    if (messages != null) {
      for (Message message : messages) {
        message.setOrderItemId(getId());
      }
    }
    return CollectionPropertyClientUtil.cast(messages, MessageTx.class);
  }
  
  @CloneableProperty (targetClass=MessageTx.class)
  public void setMessages(List<Message> messages) {
    this.messages = CollectionPropertyClientUtil.clone(messages, MessageTx.class);
  }

  public Timbro getTimbro() {
    return timbro;
  }

  @CloneableProperty (targetClass=TimbroTx.class)
  public void setTimbro(Timbro timbro) {
    this.timbro = (TimbroTx)timbro;
  }

  public Boolean getInCart() {
    return inCart;
  }

  public void setInCart(Boolean inCart) {
    this.inCart = inCart;
  }
  
  public boolean isInCart() {
    return inCart != null && inCart == true;
  }

  public String getPreviewImage() {
    return previewImage;
  }

  public void setPreviewImage(String previewImage) {
    this.previewImage = previewImage;
  }

}
