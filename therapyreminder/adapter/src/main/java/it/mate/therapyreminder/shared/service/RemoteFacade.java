package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.Account;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".remoteFacade")
public interface RemoteFacade extends RemoteService {
  
  Date getServerTime();
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);

  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
}
