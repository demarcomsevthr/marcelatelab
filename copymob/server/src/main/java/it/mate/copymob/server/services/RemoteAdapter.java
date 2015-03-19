package it.mate.copymob.server.services;

import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.DevInfo;
import it.mate.copymob.shared.model.Timbro;

import java.util.List;


public interface RemoteAdapter {
  
  public void scheduledChecks();
  
  public DevInfo sendDevInfo(DevInfo devInfo);  

  public Account saveAccount(Account tx);
  
  public List<Timbro> getTimbri() throws Exception;
  
}
