package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Produttore;

@SuppressWarnings("serial")
public class ProduttoreTx implements Produttore {

  String id;
  
  String codice;
  
  String nome;

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

  public void setNome(String name) {
    this.nome = name;
  }
  
}
