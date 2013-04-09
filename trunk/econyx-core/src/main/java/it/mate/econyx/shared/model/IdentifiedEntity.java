package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface IdentifiedEntity extends Serializable {

  public String getId();

  public void setId(String id);

}
