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
  
  private String clientVersion;
  
  private String language;
  
  private String receiverName;
  
  private String ip;
  
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
      StickSmsTx2 source2 = (StickSmsTx2)source;
      map.put("clientType", source2.getClientType());
      map.put("language", source2.getLanguage());
      map.put("receiverName", source2.getReceiverName());
      map.put("clientVersion", source2.getClientVersion());
      map.put("ip", source2.getIp());
    }
    return map;
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
      StickSmsTx2 target2 = (StickSmsTx2)target;
      target2.setClientType((String)map.get("clientType"));
      target2.setLanguage((String)map.get("language"));
      target2.setReceiverName((String)map.get("receiverName"));
      target2.setClientVersion((String)map.get("clientVersion"));
      target2.setIp((String)map.get("ip"));
    }
    return target;
  }

  public static StickSms2 fromRpcMap2(RpcMap map) {
    StickSmsTx2 inst = new StickSmsTx2();
    return fromRpcMap(map, inst);
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getClientVersion() {
    return clientVersion;
  }

  public void setClientVersion(String clientVersion) {
    this.clientVersion = clientVersion;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
  
}
