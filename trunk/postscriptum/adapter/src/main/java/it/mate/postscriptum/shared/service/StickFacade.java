package it.mate.postscriptum.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".stickFacade")
public interface StickFacade extends RemoteService {
  
  RemoteUser getRemoteUser();
  
  Date getServerTime();
  
  String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  
  public void sendSmsTest(String to, String msg);
  
  StickMail create(StickMail stickMail);
  
  StickMail createV101(StickMail stickMail, String devInfoId);
  
  List<StickMail> findMailsByUser(RemoteUser user);

  List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  void delete(List<StickMail> mails);
  
  public StickSms createSMS(StickSms entity) throws AdapterException;
  
  public void checkScheduledSMSs();
  
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user);

  public void deleteSMS(List<StickSms> entities);
  
  ////////////////////////////////////

  RpcMap createV2(RpcMap stickMail);
  
  
}
