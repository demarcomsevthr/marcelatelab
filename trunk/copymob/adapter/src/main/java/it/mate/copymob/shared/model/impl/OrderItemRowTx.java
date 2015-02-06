package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

@SuppressWarnings("serial")
public class OrderItemRowTx implements OrderItemRow, IsMappable {
  
  private Integer id;
  
  private Integer orderItemId;
  
  private String text;
  
  private boolean bold;
  
  private int size;
  
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IsMappable fromRpcMap(RpcMap map) {
    // TODO Auto-generated method stub
    return null;
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

  public boolean isBold() {
    return bold;
  }

  public void setBold(boolean bold) {
    this.bold = bold;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

}
