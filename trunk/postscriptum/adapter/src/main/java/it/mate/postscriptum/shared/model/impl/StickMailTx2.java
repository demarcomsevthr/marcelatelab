package it.mate.postscriptum.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUserUtils;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.Date;


@SuppressWarnings("serial")
public class StickMailTx2 extends StickMailTx implements IsMappable {

  private String devInfoId;
  
  public String getDevInfoId() {
    return devInfoId;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId;
  }

  @Override
  public RpcMap toRpcMap() {
    return toRpcMap(this);
  }
  
  public static RpcMap toRpcMap(StickMail source) {
    RpcMap map = new RpcMap();
    map.put("id", source.getId());
    map.put("state", source.getState());
    map.put("created", source.getCreated());
    map.put("scheduled", source.getScheduled());
    map.put("body", source.getBody());
    map.put("subject", source.getSubject());
    map.put("user", RemoteUserUtils.toRpcMap(source.getUser()));
    if (source instanceof StickMailTx2) {
      map.put("devInfoId", ((StickMailTx2)source).getDevInfoId());
    }
    return map;
  }

  @Override
  public StickMailTx2 fromRpcMap(RpcMap map) {
    return (StickMailTx2)fromRpcMap(map, this);
  }
  
  public static StickMail fromRpcMap(RpcMap map, StickMail target) {
    target.setId((String)map.get("id"));
    target.setState((String)map.get("state"));
    target.setCreated((Date)map.get("created"));
    target.setScheduled((Date)map.get("scheduled"));
    target.setBody((String)map.get("body"));
    target.setSubject((String)map.get("subject"));
    target.setUser(RemoteUserUtils.fromRpcMap((RpcMap)map.get("user")));
    if (target instanceof StickMailTx2) {
      ((StickMailTx2)target).setDevInfoId((String)map.get("devInfoId"));
    }
    return target;
  }
  
}
