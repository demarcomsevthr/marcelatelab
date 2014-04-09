package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;

@SuppressWarnings("serial")
public class DosaggioTx implements Dosaggio {
  
  private Prescrizione prescrizione;
  
  private Double quantita = 1d;
  
  private String orario;

  //serve solo nella ui
  private String codUdM;
  
  public DosaggioTx(Prescrizione prescrizione) {
    this.prescrizione = prescrizione;
  }

  public DosaggioTx(Prescrizione prescrizione, Double quantita, String orario) {
    this(prescrizione);
    this.quantita = quantita;
    this.orario = orario;
  }

  public Prescrizione getPrescrizione() {
    return prescrizione;
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

  public String getCodUdM() {
    return codUdM;
  }

  public void setCodUdM(String codUdM) {
    this.codUdM = codUdM;
  }
  
}
