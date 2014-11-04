package it.mate.protoph.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.protoph.shared.model.Applicazione;
import it.mate.protoph.shared.model.PrincipioAttivo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ApplicazioneTx implements Applicazione, IsMappable {

  private Integer id;
  
  private String nome;
  
  private Date dataInizio;
  
  private Date dataFine;
  
  private List<PrincipioAttivo> principiAttivi = new ArrayList<PrincipioAttivo>();
  
  @Override
  public String toString() {
    return "ApplicazioneTx [id=" + id + ", nome=" + nome + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + ", principiAttivi=" + principiAttivi + "]";
  }

  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("nome", nome);
    map.put("dataInizio", dataInizio);
    map.put("dataFine", dataFine);
    return null;
  }

  @Override
  public ApplicazioneTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.nome = (String)map.get("nome");
    this.dataInizio = (Date)map.get("dataInizio");
    this.dataFine = (Date)map.get("dataFine");
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

  public Date getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(Date dataInizio) {
    this.dataInizio = dataInizio;
  }

  public Date getDataFine() {
    return dataFine;
  }

  public void setDataFine(Date dataFine) {
    this.dataFine = dataFine;
  }

  public List<PrincipioAttivo> getPrincipiAttivi() {
    return principiAttivi;
  }

  /*
  public void setPrincipiAttivi(List<PrincipioAttivo> principiAttivi) {
    this.principiAttivi = principiAttivi;
  }
  */
  
}
