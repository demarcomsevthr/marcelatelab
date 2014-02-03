package it.mate.stickmail.shared.service;

import it.mate.stickmail.shared.model.RemoteUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

}
