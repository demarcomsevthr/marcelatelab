package it.mate.protons.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RemoteFacadeAsync {

  void getServerTime(AsyncCallback<Date> callback);

  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);

  void createAccount(RpcMap entity, AsyncCallback<RpcMap> callback);

  void updateAccount(RpcMap entity, AsyncCallback<RpcMap> callback);

  void checkConnection(AsyncCallback<Boolean> callback);

}
