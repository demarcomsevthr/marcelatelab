package it.mate.postscriptum.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUserUtils;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;

import java.util.Date;


@SuppressWarnings({ "serial", "deprecation" })
public class StickMailTx2 extends StickMailTx implements StickMail2, IsMappable {

  private String devInfoId;
  
  private String receiverEmail;
  
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
      StickMailTx2 source2 = (StickMailTx2)source;
      map.put("devInfoId", source2.getDevInfoId());
      map.put("receiverEmail", source2.getReceiverEmail());
    }
    return map;
  }

  public static StickMail2 fromRpcMapWithTarget(RpcMap map, StickMail2 target) {
    target.setId((String)map.get("id"));
    target.setState((String)map.get("state"));
    target.setCreated((Date)map.get("created"));
    target.setScheduled((Date)map.get("scheduled"));
    target.setBody((String)map.get("body"));
    target.setSubject((String)map.get("subject"));
    target.setUser(RemoteUserUtils.fromRpcMap((RpcMap)map.get("user")));
    if (target instanceof StickMailTx2) {
      StickMailTx2 target2 = (StickMailTx2)target;
      target2.setDevInfoId((String)map.get("devInfoId"));
      target2.setReceiverEmail((String)map.get("receiverEmail"));
    }
    return target;
  }
  
  public static StickMail2 fromRpcMap2(RpcMap map) {
    StickMailTx2 inst = new StickMailTx2();
    return fromRpcMapWithTarget(map, inst);
  }
  
  @Override
  public RpcMap toRpcMap() {
    return toRpcMap(this);
  }
  
  @Override
  public StickMailTx2 fromRpcMap(RpcMap map) {
    return (StickMailTx2)fromRpcMapWithTarget(map, this);
  }
  
  public String getDevInfoId() {
    return devInfoId;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId;
  }

  @Override
  public void setState(String state) {
    super.setState(state);
  }

  public String getReceiverEmail() {
    return receiverEmail;
  }

  public void setReceiverEmail(String receiverEmail) {
    this.receiverEmail = receiverEmail;
  }
  
}
