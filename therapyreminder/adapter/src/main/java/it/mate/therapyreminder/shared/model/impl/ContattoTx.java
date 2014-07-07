package it.mate.therapyreminder.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.utils.ModelUtils;

@SuppressWarnings("serial")
public class ContattoTx implements Contatto, IsMappable {

  private Integer id;
  
  private String tipo;
  
  private String nome;
  
  private String email;
  
  private String telefono;
  
  private String indirizzo;
  
  private String orari;
  
  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("tipo", tipo);
    map.put("nome", nome);
    map.put("email", email);
    map.put("telefono", telefono);
    return map;
  }

  @Override
  public ContattoTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.tipo = (String)map.get("tipo");
    this.nome = (String)map.get("nome");
    this.email = (String)map.get("email");
    this.telefono = (String)map.get("telefono");
    return this;
  }

  @Override
  public String toString() {
    return "ContattoTx [id=" + id + ", tipo=" + tipo + ", nome=" + nome + ", email=" + email + ", telefono=" + telefono + "]";
  }

  public ContattoTx() {

  }
  
  public ContattoTx(String tipo) {
    this.tipo = tipo;
  }
  
  public ContattoTx(Contatto contatto) {
    if (contatto instanceof ContattoTx) {
      ContattoTx that = (ContattoTx)contatto;
      this.id = that.id;
      this.tipo = that.tipo;
      this.nome = that.nome;
      this.email = that.email;
      this.telefono = that.telefono;
      this.indirizzo = that.indirizzo;
      this.orari = that.orari;
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj instanceof ContattoTx) {
      ContattoTx that = (ContattoTx)obj;
      if (!ModelUtils.equals(this.id, that.id))
        return false;
      if (!ModelUtils.equals(this.tipo, that.tipo))
        return false;
      if (!ModelUtils.equals(this.nome, that.nome))
        return false;
      if (!ModelUtils.equals(this.email, that.email))
        return false;
      if (!ModelUtils.equals(this.telefono, that.telefono))
        return false;
      if (!ModelUtils.equals(this.indirizzo, that.indirizzo))
        return false;
      if (!ModelUtils.equals(this.orari, that.orari))
        return false;
      return true;
    }
    return super.equals(obj);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String name) {
    this.nome = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String phoneNumber) {
    this.telefono = phoneNumber;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getOrari() {
    return orari;
  }

  public void setOrari(String orari) {
    this.orari = orari;
  }
  
}
