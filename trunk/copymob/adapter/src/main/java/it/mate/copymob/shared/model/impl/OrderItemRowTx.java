package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

@SuppressWarnings("serial")
public class OrderItemRowTx implements OrderItemRow, IsMappable {
  
  private Integer id;
  
  private Integer orderItemId;
  
  private String text;
  
  private Boolean bold;
  
  private Integer size = 10;
  
  private String fontFamily;
  
  private String remoteId;
  
  public OrderItemRowTx() {

  }
  
  public OrderItemRowTx(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "OrderItemRowTx [id=" + id + ", orderItemId=" + orderItemId + ", text=" + text + ", bold=" + bold + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    
    map.putField("id", id);
    map.putField("orderItemId", orderItemId);
    map.putField("text", text);
    map.putField("bold", bold);
    map.putField("size", size);
    map.putField("fontFamily", fontFamily);
    map.putField("remoteId", remoteId);
    
    /*
    map.put("id", id);
    map.put("orderItemId", orderItemId);
    map.put("text", text);
    map.put("bold", bold);
    map.put("size", size);
    map.put("fontFamily", fontFamily);
    map.put("remoteId", remoteId);
    */
    
    return map;
  }

  @Override
  public OrderItemRowTx fromRpcMap(RpcMap map) {
    
    this.id = map.getField("id");
    this.orderItemId = map.getField("orderItemId");
    this.text = map.getField("text");
    this.bold = map.getField("bold");
    this.size = map.getField("size");
    this.fontFamily = map.getField("fontFamily");
    this.remoteId = map.getField("remoteId");
    
    /*
    this.id = (Integer)map.get("id");
    this.orderItemId = (Integer)map.get("orderItemId");
    this.text = (String)map.get("text");
    this.bold = (Boolean)map.get("bold");
    this.size = (Integer)map.get("size");
    this.fontFamily = (String)map.get("fontFamily");
    this.remoteId = (String)map.get("remoteId");
    */
    
    return this;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Integer orderItemId) {
    this.orderItemId = orderItemId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getBold() {
    return bold;
  }

  public void setBold(Boolean bold) {
    this.bold = bold;
  }

  public boolean isBold() {
    return bold != null && bold;
  }

  public Integer getSize() {
    return size != null ? size : 0;
  }

  public void setSize(Integer size) {
    this.size = size != null ? size : 0;
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }
  
}
