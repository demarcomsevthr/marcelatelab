package it.mate.protoph.server.services;

import it.mate.protoph.shared.model.Account;
import it.mate.protoph.shared.model.Contatto;
import it.mate.protoph.shared.model.Somministrazione;


public interface RemoteAdapter {
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp);  

  public void scheduledChecks();
  
  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
  public Somministrazione saveSomministrazione(Somministrazione somministrazioneTx, Account accountTx, String devInfoId);
  
  public void debugAnticipaDataSomministrazioni();
  
  public void updateDatiContatto(Contatto tutor, Account account);
  
  public void deleteSomministrazione(Somministrazione somministrazione);
  
}
