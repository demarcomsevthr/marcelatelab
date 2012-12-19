package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface ModalitaSpedizione extends Serializable {
  
  public String getId();

  public void setId(String id);

  public String getCodice();

  public void setCodice(String code);
  
  public String getDescrizione();
  
  public void setDescrizione(String descrizione);

  public Double getPrezzo();
  
  public void setPrezzo(Double prezzo);
  
}
