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
  
  private Timbro timbro;
  
  private List<OrderItemRow> rows = new ArrayList<OrderItemRow>();
  
  private Boolean inCart;
  
  
  @Override
  public String toString() {
    return "OrderItemTx [id=" + id + ", orderId=" + orderId + ", quantity=" + quantity + ", timbro=" + timbro + ", rows=" + rows + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    // TODO Auto-generated method stub
    return null;
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

  public Boolean getInCart() {
    return inCart;
  }

  public void setInCart(Boolean inCart) {
    this.inCart = inCart;
  }
  
  public boolean isInCart() {
    return inCart != null && inCart == true;
  }
  
  
  
}
