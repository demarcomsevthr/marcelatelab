package it.mate.portlets.shared.services;

import it.mate.portlets.shared.model.PageTemplate;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath (".portalService")
public interface PortalService extends RemoteService {

  PageTemplate getPage(String historyToken);
  
}
