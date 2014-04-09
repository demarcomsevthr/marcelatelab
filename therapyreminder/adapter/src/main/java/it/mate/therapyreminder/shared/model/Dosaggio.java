package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

public interface Dosaggio extends Serializable {
  
  public Prescrizione getPrescrizione();

  public void setQuantita(Double quantita);

  public Double getQuantita();

  public String getOrario();

  public void setOrario(String orario);
  
  public String getCodUdM();

  public void setCodUdM(String codUdM);
    
}
