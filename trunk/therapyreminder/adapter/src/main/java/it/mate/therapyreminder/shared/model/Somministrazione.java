package it.mate.therapyreminder.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface Somministrazione extends Serializable {

  public static final int STATO_SCHEDULATA = 1;

  public static final int STATO_ESEGUITA = 2;

  public static final int STATO_ANNULLATA = 3;

  public void setEseguita();

  public boolean isEseguita();

  public void setSchedulata();

  public boolean isSchedulata();

  public void setStato(Integer stato);

  public Integer getStato();

  public void setOrario(String orario);

  public String getOrario();

  public void setQuantita(Double quantita);

  public Double getQuantita();

  public void setData(Date data);

  public Date getData();

  public void setId(Integer id);

  public Integer getId();

  public Prescrizione getPrescrizione();
  
}
