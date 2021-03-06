package it.mate.postscriptum.shared.service;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StickFacadeAsync {

  void getRemoteUser(AsyncCallback<RemoteUser> callback);
  void getServerTime(AsyncCallback<Date> callback);
  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);
  void sendSmsTest(String to, String msg, AsyncCallback<Void> callback);

  @Deprecated
  void create(StickMail stickMail, AsyncCallback<StickMail> callback);
  @Deprecated
  void createV101(StickMail stickMail, String devInfoId, AsyncCallback<StickMail> callback);
  @Deprecated
  void findMailsByUser(RemoteUser user, AsyncCallback<List<StickMail>> callback);
  @Deprecated
  void findScheduledMailsByUser(RemoteUser user, AsyncCallback<List<StickMail>> callback);
  @Deprecated
  void delete(List<StickMail> mails, AsyncCallback<Void> callback);

  /////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////

  void createSMS(StickSms stickSMS, AsyncCallback<StickSms> callback);
  void checkScheduledSMSs(AsyncCallback<Void> callback);
  void findScheduledSMSsByUser(RemoteUser user, AsyncCallback<List<StickSms>> callback);
  void deleteSMS(List<StickSms> entities, AsyncCallback<Void> callback);

}
