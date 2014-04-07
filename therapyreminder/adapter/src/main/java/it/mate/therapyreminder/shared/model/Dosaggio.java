package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

public interface Dosaggio extends Serializable {
  
  public Integer getIdPrescrizione();

  public void setIdPrescrizione(Integer idPrescrizione);

  public void setQuantita(Double quantita);

  public Double getQuantita();

  public String getOrario();

  public void setOrario(String orario);
  
}
