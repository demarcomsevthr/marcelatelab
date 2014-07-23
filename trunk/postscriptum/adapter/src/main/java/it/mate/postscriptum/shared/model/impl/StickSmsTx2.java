package it.mate.postscriptum.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUserUtils;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;

import java.util.Date;


@SuppressWarnings({ "serial", "deprecation" })
public class StickSmsTx2 extends StickSmsTx implements StickSms2, IsMappable {

  private String clientType;
  
  public static RpcMap toRpcMap(StickSms source) {
    RpcMap map = new RpcMap();
    map.put("id", source.getId());
    map.put("state", source.getState());
    map.put("created", source.getCreated());
    map.put("scheduled", source.getScheduled());
    map.put("body", source.getBody());
    map.put("receiverNumber", source.getReceiverNumber());
    map.put("devInfoId", source.getDevInfoId());
    map.put("user", RemoteUserUtils.toRpcMap(source.getUser()));
    if (source instanceof StickSmsTx2) {
      map.put("clientType", ((StickSmsTx2)source).getClientType());
    }
    return map;
  }

  public static StickSms2 fromRpcMap2(RpcMap map) {
    StickSmsTx2 inst = new StickSmsTx2();
    return fromRpcMap(map, inst);
  }
  
  public static StickSms2 fromRpcMap(RpcMap map, StickSms2 target) {
    target.setId((String)map.get("id"));
    target.setState((String)map.get("state"));
    target.setCreated((Date)map.get("created"));
    target.setScheduled((Date)map.get("scheduled"));
    target.setBody((String)map.get("body"));
    target.setReceiverNumber((String)map.get("receiverNumber"));
    target.setDevInfoId((String)map.get("devInfoId"));
    target.setUser(RemoteUserUtils.fromRpcMap((RpcMap)map.get("user")));
    if (target instanceof StickSmsTx2) {
      ((StickSmsTx2)target).setClientType((String)map.get("clientType"));
    }
    return target;
  }

  @Override
  public RpcMap toRpcMap() {
    return toRpcMap(this);
  }

  @Override
  public StickSmsTx2 fromRpcMap(RpcMap map) {
    return (StickSmsTx2)fromRpcMap(map, this);
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }
  
}
