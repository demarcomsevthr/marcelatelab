package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.RemoteUser;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RemoteFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

  void getServerTime(AsyncCallback<Date> callback);

  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);


}
