package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".remoteFacade")
public interface RemoteFacade extends RemoteService {
  
  Date getServerTime();
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion);

  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
  public Boolean checkConnection();

  public List<Somministrazione> saveSomministrazioni(List<Somministrazione> somministrazioni, Account account, String devInfoId);
  
  public void updateDatiContatto(Contatto tutor, Account account);
  
  public void deleteSomministrazioni(List<Somministrazione> somministrazioni);
  
}
