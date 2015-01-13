package it.mate.copymob.shared.model.impl;

import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;

@SuppressWarnings("serial")
public class TimbroTx implements Timbro, IsMappable {
  
  private Integer id;
  
  private String nome;
  
  private String codice;
  
  private String image;
  
  @Override
  public String toString() {
    return "TimbroTx [id=" + id + ", nome=" + nome + ", codice=" + codice + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IsMappable fromRpcMap(RpcMap map) {
    // TODO Auto-generated method stub
    return null;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public String getImage() {
    return image;
  }
  
  public String getImageData() {
    return !image.startsWith("data:") ? ("data:image/jpeg;base64," + image) : image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
