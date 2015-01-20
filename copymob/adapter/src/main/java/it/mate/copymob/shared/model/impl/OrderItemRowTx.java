package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

@SuppressWarnings("serial")
public class OrderItemRowTx implements OrderItemRow, IsMappable {
  
  private Integer id;
  
  private Integer orderItemId;
  
  private String text;
  
  
  
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


}
