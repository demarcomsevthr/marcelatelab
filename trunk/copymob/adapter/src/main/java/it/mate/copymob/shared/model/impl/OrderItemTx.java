package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.List;

@SuppressWarnings("serial")
public class OrderItemTx implements OrderItem, IsMappable {
  
  private Integer id;
  
  private Integer orderId;
  
  private Integer timbroId;
  
  private Double quantity;
  
  private List<OrderItemRow> rows;
  
  
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

  public Integer getTimbroId() {
    return timbroId;
  }

  public void setTimbroId(Integer timbroId) {
    this.timbroId = timbroId;
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
    return rows;
  }

  public void setRows(List<OrderItemRow> rows) {
    this.rows = rows;
  }

}
