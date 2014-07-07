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
  
  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    if (id != null) map.put("id", id);
    if (tipo != null) map.put("tipo", tipo);
    if (nome != null) map.put("nome", nome);
    if (email != null) map.put("email", email);
    if (telefono != null) map.put("telefono", telefono);
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
  
}
