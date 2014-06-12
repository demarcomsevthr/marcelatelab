package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Somministrazione;


public interface RemoteAdapter {
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp);  

  public void refresh();
  
  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
  public Somministrazione saveSomministrazione(Somministrazione somministrazioneTx, Account accountTx, String devInfoId);
  
}
