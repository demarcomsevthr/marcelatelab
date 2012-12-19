package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Indirizzo;

@SuppressWarnings("serial")
public abstract class AbstractIndirizzoTx implements Indirizzo {

  String id;
  
  String sesso;
  
  String nome;
  
  String cognome;
  
  String azienda;
  
  String via;
  
  String cap;
  
  String citta;
  
  String telefono;
  
  String email;
  
  @Override
  public String toString() {
    return "IndirizzoTx [id=" + id + ", sesso=" + sesso + ", nome=" + nome + ", cognome=" + cognome + ", azienda=" + azienda + ", via="
        + via + ", cap=" + cap + ", citta=" + citta + ", telefono=" + telefono + ", email=" + email + "]";
  }
  
  public Indirizzo toUpperCase() {
    nome = nome != null ? nome.toUpperCase() : null;
    cognome = cognome != null ? cognome.toUpperCase() : null;
    azienda = azienda != null ? azienda.toUpperCase() : null;
    via = via != null ? via.toUpperCase() : null;
    cap = cap != null ? cap.toUpperCase() : null;
    citta = citta != null ? citta.toUpperCase() : null;
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAzienda() {
    return azienda;
  }

  public void setAzienda(String azienda) {
    this.azienda = azienda;
  }

  public String getSesso() {
    return sesso;
  }

  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getCitta() {
    return citta;
  }

  public void setCitta(String citta) {
    this.citta = citta;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
}
