package it.mate.postscriptum.shared.service;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.impl.ExtensibleTx;
import it.mate.postscriptum.shared.model.impl.TestTx;

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
  
  ////////////////////////////////////

  public StickSms createSMS(StickSms entity) throws AdapterException;
  
  public void checkScheduledSMSs();
  
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user);

  public void deleteSMS(List<StickSms> entities);
  
  ////////////////////////////////////

  public void doTest(TestTx test);
  
  public void doExtensibleTest(ExtensibleTx test);
  
}
