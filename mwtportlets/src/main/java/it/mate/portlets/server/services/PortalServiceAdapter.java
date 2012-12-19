package it.mate.portlets.server.services;

import it.mate.portlets.shared.model.PageTemplate;

public interface PortalServiceAdapter {

  public PageTemplate getPage(String historyToken);
  
}
