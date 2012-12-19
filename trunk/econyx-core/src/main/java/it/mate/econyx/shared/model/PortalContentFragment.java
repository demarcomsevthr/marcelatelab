package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface PortalContentFragment extends Serializable {

  public String getId();
  
  public void setId(String id);
  
  public WebContent getEntity();
  
  public void setEntity(WebContent entity);
  
  
}
