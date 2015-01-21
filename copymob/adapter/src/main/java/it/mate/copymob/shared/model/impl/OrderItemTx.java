package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OrderItemTx implements OrderItem, IsMappable {
  
  private Integer id;
  
  private Integer orderId;
  
//private Integer timbroId;
  
  private Double quantity;
  
  private List<OrderItemRow> rows = new ArrayList<OrderItemRow>();
  
  private Timbro timbro;
  
  
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
    for (OrderItemRow row : rows) {
      row.setOrderItemId(id);
    }
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
    return rows;
  }

  public void setRows(List<OrderItemRow> rows) {
    this.rows = rows;
  }

  public Timbro getTimbro() {
    return timbro;
  }

  public void setTimbro(Timbro timbro) {
    this.timbro = timbro;
  }
  
}