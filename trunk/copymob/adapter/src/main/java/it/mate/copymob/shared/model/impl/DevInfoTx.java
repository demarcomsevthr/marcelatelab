package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.DevInfo;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;

@SuppressWarnings("serial")
public class DevInfoTx implements DevInfo, IsMappable {
  
  String id;

  String os;
  
  String layout;
  
  String devName;

  String phgVersion;
  
  String platform;
  
  String devUuid;
  
  String devVersion;
  
  Date created;
  
  String devIp;

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("os", os);
    map.put("layout", layout);
    map.put("devName", devName);
    map.put("phgVersion", phgVersion);
    map.put("platform", platform);
    map.put("devUuid", devUuid);
    map.put("devVersion", devVersion);
    map.put("created", created);
    map.put("devIp", devIp);
    return map;
  }

  @Override
  public DevInfoTx fromRpcMap(RpcMap map) {
    this.id = (String)map.get("id");
    this.os = (String)map.get("os");
    this.layout = (String)map.get("layout");
    this.devName = (String)map.get("devName");
    this.phgVersion = (String)map.get("phgVersion");
    this.platform = (String)map.get("platform");
    this.devUuid = (String)map.get("devUuid");
    this.devVersion = (String)map.get("devVersion");
    this.created = (Date)map.get("created");
    this.devIp = (String)map.get("devIp");
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getLayout() {
    return layout;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public String getDevName() {
    return devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public String getPhgVersion() {
    return phgVersion;
  }

  public void setPhgVersion(String phgVersion) {
    this.phgVersion = phgVersion;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getDevUuid() {
    return devUuid;
  }

  public void setDevUuid(String devUuid) {
    this.devUuid = devUuid;
  }

  public String getDevVersion() {
    return devVersion;
  }

  public void setDevVersion(String devVersion) {
    this.devVersion = devVersion;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getDevIp() {
    return devIp;
  }

  public void setDevIp(String devIp) {
    this.devIp = devIp;
  }

}
