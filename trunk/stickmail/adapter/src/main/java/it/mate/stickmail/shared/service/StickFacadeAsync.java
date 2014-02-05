package it.mate.stickmail.shared.service;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

  void createStickMail(StickMail stickMail, AsyncCallback<Void> callback);

  void getServerTime(AsyncCallback<Date> callback);

}
