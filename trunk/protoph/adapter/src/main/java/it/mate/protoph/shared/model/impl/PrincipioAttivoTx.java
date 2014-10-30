package it.mate.protoph.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.protoph.shared.model.PrincipioAttivo;

@SuppressWarnings("serial")
public class PrincipioAttivoTx implements PrincipioAttivo, IsMappable {

  private Integer id;
  
  private String nome;
  
  private String path;
  
  
  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("nome", nome);
    map.put("path", path);
    return null;
  }

  @Override
  public PrincipioAttivoTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.nome = (String)map.get("nome");
    this.path = (String)map.get("path");
    return this;
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

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
