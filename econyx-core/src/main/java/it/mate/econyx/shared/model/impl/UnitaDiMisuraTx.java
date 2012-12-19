package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.UnitaDiMisura;

@SuppressWarnings("serial")
public class UnitaDiMisuraTx implements UnitaDiMisura {

  String id;
  
  String codice;
  
  String nome;
  
  Integer decimali = 0;
  
  @Override
  public String toString() {
    return "UnitOfMeasureTx [id=" + id + ", code=" + codice + ", screenName=" + nome + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String code) {
    this.codice = code;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String screenName) {
    this.nome = screenName;
  }

  public Integer getDecimali() {
    return decimali != null ? decimali : 0;
  }

  public void setDecimali(Integer decimali) {
    this.decimali = decimali;
  }
  
}
