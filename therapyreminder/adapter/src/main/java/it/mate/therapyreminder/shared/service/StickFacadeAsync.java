package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.RemoteUser;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

  void getServerTime(AsyncCallback<Date> callback);


}
