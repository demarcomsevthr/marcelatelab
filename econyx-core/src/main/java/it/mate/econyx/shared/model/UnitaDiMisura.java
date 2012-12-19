package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface UnitaDiMisura extends Serializable {

  public String getId();

  public void setId(String id);

  public String getCodice();

  public void setCodice(String code);
  
  public String getNome();

  public void setNome(String screenName);
  
  public Integer getDecimali();

  public void setDecimali(Integer decimali);
  
}
