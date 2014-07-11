package it.mate.postscriptum.shared.model;

import it.mate.gwtcommons.shared.rpc.RpcMap;

public class RemoteUserUtils {
  
  public static RpcMap toRpcMap(RemoteUser user) {
    RpcMap map = new RpcMap();
    map.put("userId", user.getUserId());
    map.put("email", user.getEmail());
    map.put("nickname", user.getNickname());
    map.put("authDomain", user.getAuthDomain());
    map.put("federatedIdentity", user.getFederatedIdentity());
    return map;
  }

  public static RemoteUser fromRpcMap(RpcMap map) {
    RemoteUser user = new RemoteUser();
    user.setUserId((String)map.get("userId"));
    user.setEmail((String)map.get("email"));
    user.setNickname((String)map.get("nickname"));
    user.setAuthDomain((String)map.get("authDomain"));
    user.setFederatedIdentity((String)map.get("federatedIdentity"));
    return user;
  }
  

}
