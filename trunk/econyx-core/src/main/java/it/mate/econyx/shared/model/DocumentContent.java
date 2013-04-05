package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface DocumentContent extends Serializable {

  public String getId();
  
  public void setId(String id);
  
  public String getContent();
  
  public void setContent(String content);
  
}
