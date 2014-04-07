package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;

@SuppressWarnings("serial")
public class DosaggioTx implements Dosaggio {
  
  private Integer idPrescrizione;
  
  private Double quantita = 1d;
  
  private String orario;
  
  public DosaggioTx() {

  }

  public DosaggioTx(Double quantita, String orario) {
    super();
    this.quantita = quantita;
    this.orario = orario;
  }

  public Integer getIdPrescrizione() {
    return idPrescrizione;
  }

  public void setIdPrescrizione(Integer idPrescrizione) {
    this.idPrescrizione = idPrescrizione;
  }

  public Double getQuantita() {
    return quantita;
  }

  public void setQuantita(Double quantita) {
    if (quantita != null) {
      this.quantita = quantita;
    }
  }

  public String getOrario() {
    return orario;
  }

  public void setOrario(String orario) {
    this.orario = orario;
  }

}
