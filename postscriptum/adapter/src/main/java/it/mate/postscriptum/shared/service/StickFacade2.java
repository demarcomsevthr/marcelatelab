package it.mate.postscriptum.shared.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUser;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".stickFacade2")
public interface StickFacade2 extends RemoteService {
  
  RemoteUser getRemoteUser();
  Date getServerTime();
  String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);
  public void sendSmsTest(String to, String msg);
  
  /////////////////////
  /////////////////////
  /////////////////////
  
  RpcMap create(RpcMap stickMail);
  List<RpcMap> findMailsByUser(RemoteUser user);
  List<RpcMap> findScheduledMailsByUser(RemoteUser user);
  void delete(List<RpcMap> mails);
  
  /////////////////////
  /////////////////////
  /////////////////////
  
  public RpcMap createSMS(RpcMap entity) throws AdapterException;
  public void checkScheduledSMSs();
  public List<RpcMap> findScheduledSMSsByUser(RemoteUser user);
  public void deleteSMS(List<RpcMap> entities);
  
  
}
