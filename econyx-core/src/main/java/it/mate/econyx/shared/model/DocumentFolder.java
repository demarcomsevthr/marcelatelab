package it.mate.econyx.shared.model;

import java.util.List;

public interface DocumentFolder extends PortalEntity, PortalResource {

  public String getCode();

  public void setCode(String code);
  
  public List<Document> getDocuments();

  public void setDocuments(List<Document> documents);
  
}
