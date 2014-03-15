package it.mate.postscriptum.shared.service;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".stickFacade")
public interface StickFacade extends RemoteService {
  
  RemoteUser getRemoteUser();
  
  Date getServerTime();
  
  StickMail create(StickMail stickMail);
  
  StickMail createV101(StickMail stickMail, String devInfoId);
  
  List<StickMail> findMailsByUser(RemoteUser user);

  List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  void delete(List<StickMail> mails);
  
  String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  
  public void sendSmsTest(String to, String msg);

}
