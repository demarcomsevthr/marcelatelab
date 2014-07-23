package it.mate.postscriptum.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUser;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacade2Async {

  void checkScheduledSMSs(AsyncCallback<Void> callback);

  void createSMS(RpcMap entity, AsyncCallback<RpcMap> callback);

  void create(RpcMap stickMail, AsyncCallback<RpcMap> callback);

  void deleteSMS(List<RpcMap> entities, AsyncCallback<Void> callback);

  void delete(List<RpcMap> mails, AsyncCallback<Void> callback);

  void findMailsByUser(RemoteUser user, AsyncCallback<List<RpcMap>> callback);

  void findScheduledMailsByUser(RemoteUser user, AsyncCallback<List<RpcMap>> callback);

  void findScheduledSMSsByUser(RemoteUser user, AsyncCallback<List<RpcMap>> callback);

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

  void getServerTime(AsyncCallback<Date> callback);

  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);

  void sendSmsTest(String to, String msg, AsyncCallback<Void> callback);

}
