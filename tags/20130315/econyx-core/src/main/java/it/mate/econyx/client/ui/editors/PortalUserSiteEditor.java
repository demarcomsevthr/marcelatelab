package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.PortalUser;

public interface PortalUserSiteEditor {
  
  public void setVisible(boolean visible);

  public void setEnabled(boolean enabled);

  public void setModel(PortalUser user);

  public PortalUser flushPortalUser();

}
