package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.UdM;

@SuppressWarnings("serial")
public class UdMTx implements UdM {

  private String codice;
  
  private String descrizione;
  
  private Integer sequenza;

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Integer getSequenza() {
    return sequenza;
  }

  public void setSequenza(Integer sequenza) {
    this.sequenza = sequenza;
  }
  
}
