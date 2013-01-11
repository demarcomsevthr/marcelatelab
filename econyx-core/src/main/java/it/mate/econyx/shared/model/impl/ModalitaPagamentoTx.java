package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ModalitaPagamento;

@SuppressWarnings("serial")
public class ModalitaPagamentoTx implements ModalitaPagamento {

  String id;
  
  String codice;
  
  String descrizione;

  Double prezzo;
  
  String nota;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public Double getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(Double prezzo) {
    this.prezzo = prezzo;
  }

  public String getNota() {
    return nota;
  }

  public void setNota(String nota) {
    this.nota = nota;
  }
  
}
