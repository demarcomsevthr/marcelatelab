package it.mate.copymob.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".remoteFacade")
public interface RemoteFacade extends RemoteService {
  
  Date getServerTime();
  
  String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);

  RpcMap createAccount(RpcMap entity);
  
  RpcMap updateAccount(RpcMap entity);
  
  Boolean checkConnection();

}
