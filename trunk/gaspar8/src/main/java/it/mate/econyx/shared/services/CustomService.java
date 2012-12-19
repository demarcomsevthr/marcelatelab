package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.ContoUtente;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/customService")
public interface CustomService extends RemoteService {
  
  public ContoUtente findContoUtenteByPortalUser(String portalUserId);
  
  public ContoUtente updateContoUtente(ContoUtente contoUtente);
  
}
