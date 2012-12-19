package it.mate.econyx.shared.model;

public interface PortalEntityPage <PE extends PortalEntity> extends PortalPage {
  
  public PE getEntity();
  
  public void setEntity(PE entity);
  
}
