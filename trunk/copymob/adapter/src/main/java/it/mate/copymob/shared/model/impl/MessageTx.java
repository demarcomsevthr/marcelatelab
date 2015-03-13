package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.Message;
import it.mate.copymob.shared.model.Order;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;

@SuppressWarnings("serial")
public class MessageTx implements Message, IsMappable {

  private Integer id;
  
  private Date data;
  
  private String text;
  
  private Order order;
  
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

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
  
  public Integer getOrderId() {
    return order != null ? order.getId() : null;
  }
  
  public void setOrderId(Integer id) {
    this.order = id != null ? new OrderTx(id) : null;
  }
  
}
