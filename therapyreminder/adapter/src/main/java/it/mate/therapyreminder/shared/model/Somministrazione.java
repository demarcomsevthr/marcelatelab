package it.mate.therapyreminder.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface Somministrazione extends Serializable {

  public abstract void setEseguita();

  public abstract boolean isEseguita();

  public abstract void setSchedulata();

  public abstract boolean isSchedulata();

  public abstract void setStato(Integer stato);

  public abstract Integer getStato();

  public abstract void setOrario(String orario);

  public abstract String getOrario();

  public abstract void setQuantita(Double quantita);

  public abstract Double getQuantita();

  public abstract void setData(Date data);

  public abstract Date getData();

  public abstract void setId(Integer id);

  public abstract Integer getId();

  public abstract Prescrizione getPrescrizione();
  
}
