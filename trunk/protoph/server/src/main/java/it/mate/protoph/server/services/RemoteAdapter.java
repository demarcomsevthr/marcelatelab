package it.mate.protoph.server.services;

import it.mate.protoph.server.model.PrincipioAttivoDs;
import it.mate.protoph.shared.model.Account;


public interface RemoteAdapter {
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp);  

  public void scheduledChecks();
  
  public Account createAccount(Account entity);
  
  public Account updateAccount(Account entity);
  
  public PrincipioAttivoDs findPrincipioAttivoByName (String name);
  
  public PrincipioAttivoDs savePrincipioAttivoDs(PrincipioAttivoDs entity);
  
}
