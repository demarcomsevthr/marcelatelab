package it.mate.econyx.client.factories;

import it.mate.econyx.shared.model.PortalSessionState;

public interface SiteClientFactory {
  
  public PortalSessionState getPortalSessionState();
  
  public void setPortalSessionState(PortalSessionState portalSessionState);

}
