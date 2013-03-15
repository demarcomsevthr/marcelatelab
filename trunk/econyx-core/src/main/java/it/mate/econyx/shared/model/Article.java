package it.mate.econyx.shared.model;

public interface Article extends WebContentSimple, PortalEntity {
  
  public String getCode();

  public void setCode(String code);
  
}
