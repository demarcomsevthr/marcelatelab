package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

public interface UdM extends Serializable {

  public String getCodice();

  public void setCodice(String codice);

  public String getDescrizione();

  public void setDescrizione(String descrizione);
  
  public Integer getSequenza();

  public void setSequenza(Integer sequenza);
    
}
