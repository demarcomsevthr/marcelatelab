package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.Date;

@SuppressWarnings("serial")
public class SomministrazioneTx implements Somministrazione {

  protected static final int STATO_SCHEDULATA = 1;

  protected static final int STATO_ESEGUITA = 2;

  private Prescrizione prescrizione;
  
  private Integer id;
  
  private Date data;
  
  private Double quantita = 1d;
  
  private String orario;

  private Integer stato;
  
  public SomministrazioneTx() {

  }

  public SomministrazioneTx(Prescrizione prescrizione) {
    this.prescrizione = prescrizione;
  }
  
  @Override
  public String toString() {
    return "Somministrazione [prescrizione.id=" + prescrizione.getId() + ", id=" + id + ", data=" + data + ", quantita=" + quantita + ", orario=" + orario + ", stato="
        + stato + "]";
  }

  @Override
  public Prescrizione getPrescrizione() {
    return prescrizione;
  }
  
  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Date getData() {
    return data;
  }

  @Override
  public void setData(Date data) {
    this.data = data;
  }
  
  @Override
  public Double getQuantita() {
    return quantita;
  }

  @Override
  public void setQuantita(Double quantita) {
    this.quantita = quantita;
  }

  @Override
  public String getOrario() {
    return orario;
  }

  @Override
  public void setOrario(String orario) {
    this.orario = orario;
  }

  @Override
  public Integer getStato() {
    return stato;
  }

  @Override
  public void setStato(Integer stato) {
    this.stato = stato;
  }
  
  @Override
  public boolean isSchedulata() {
    return stato == STATO_SCHEDULATA;
  }
  
  @Override
  public void setSchedulata() {
    stato = STATO_SCHEDULATA;
  }
  
  @Override
  public boolean isEseguita() {
    return stato == STATO_ESEGUITA;
  }
  
  @Override
  public void setEseguita() {
    stato = STATO_ESEGUITA;
  }
  
}
