package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.utils.ModelUtils;

@SuppressWarnings("serial")
public class ContattoTx implements Contatto {

  private Integer id;
  
  private String tipo;
  
  private String nome;
  
  private String email;
  
  private String telefono;
  
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
