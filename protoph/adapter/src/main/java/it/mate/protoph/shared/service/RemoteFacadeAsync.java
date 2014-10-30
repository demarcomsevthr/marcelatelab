package it.mate.protoph.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RemoteFacadeAsync {

  void getServerTime(AsyncCallback<Date> callback);

  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);

  void createAccount(RpcMap entity, AsyncCallback<RpcMap> callback);

  void updateAccount(RpcMap entity, AsyncCallback<RpcMap> callback);

  void checkConnection(AsyncCallback<Boolean> callback);

  void saveSomministrazioni(List<RpcMap> somministrazioni, RpcMap account, String devInfoId, AsyncCallback<List<RpcMap>> callback);

  void updateDatiContatto(RpcMap tutor, RpcMap account, AsyncCallback<Void> callback);

  void deleteSomministrazioni(List<RpcMap> somministrazioni, AsyncCallback<Void> callback);



}
