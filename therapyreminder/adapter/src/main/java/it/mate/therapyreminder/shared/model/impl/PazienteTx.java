package it.mate.therapyreminder.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.therapyreminder.shared.model.Paziente;

@SuppressWarnings("serial")
public class PazienteTx implements Paziente, IsMappable {

  private Integer id;
  
  private String nome;

  public PazienteTx() {

  }

  public PazienteTx(Paziente paziente) {
    if (paziente instanceof PazienteTx) {
      PazienteTx that = (PazienteTx)paziente;
      this.id = that.id;
      this.nome = that.nome;
    }
  }
  
  @Override
  public RpcMap toRpcMap() {
    return null;
  }

  @Override
  public IsMappable fromRpcMap(RpcMap map) {
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

}
