package it.mate.econyx.shared.model;

import java.util.List;

public interface PortalFolderPage extends PortalPage {
  
  public List<PortalPage> getChildreen();
  
  public void setChildreen(List<PortalPage> childreen);
  
  public Boolean getHideChildreen();
  
  public void setHideChildreen (Boolean flag);
  
  public Boolean getShowChildreenContent();
  
  public void setShowChildreenContent(Boolean value);
  
}
