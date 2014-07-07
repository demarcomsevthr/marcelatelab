package it.mate.therapyreminder.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".remoteFacade")
public interface RemoteFacade extends RemoteService {
  
  Date getServerTime();
  
  String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);

  RpcMap createAccount(RpcMap entity);
  
  RpcMap updateAccount(RpcMap entity);
  
  Boolean checkConnection();

  List<RpcMap> saveSomministrazioni(List<RpcMap> somministrazioni, RpcMap account, String devInfoId);
  
  void updateDatiContatto(RpcMap tutor, RpcMap account);
  
  void deleteSomministrazioni(List<RpcMap> somministrazioni);
  
}
