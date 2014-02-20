package it.mate.stickmail.shared.service;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);

  void getServerTime(AsyncCallback<Date> callback);

  void create(StickMail stickMail, AsyncCallback<StickMail> callback);

  void findMailsByUser(RemoteUser user, AsyncCallback<List<StickMail>> callback);

  void findScheduledMailsByUser(RemoteUser user, AsyncCallback<List<StickMail>> callback);

  void delete(List<StickMail> mails, AsyncCallback<Void> callback);

}
