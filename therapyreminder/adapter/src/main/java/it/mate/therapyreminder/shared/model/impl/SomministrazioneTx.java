package it.mate.therapyreminder.shared.model.impl;

import it.mate.gwtcommons.shared.rpc.IsMappable;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.Date;

@SuppressWarnings("serial")
public class SomministrazioneTx implements Somministrazione, IsMappable {

  private Prescrizione prescrizione;
  
  private Integer id;
  
  private Date data;
  
  private Double quantita = 1d;
  
  private String orario;

  private Integer stato;
  
  private String remoteId;
  
  // salvato solo in remoto
  private String language;

  // utilizzato solo in locale
  private int needSynchronization = 0;
  
  
  @Override
  public RpcMap toRpcMap() {
    RpcMap map = new RpcMap();
    map.put("id", id);
    map.put("data", data);
    map.put("quantita", quantita);
    map.put("orario", orario);
    map.put("stato", stato);
    map.put("remoteId", remoteId);
    map.put("language", language);
    map.put("prescrizione", prescrizione != null ? ((PrescrizioneTx)prescrizione).toRpcMap() : null);
    return map;
  }

  @Override
  public SomministrazioneTx fromRpcMap(RpcMap map) {
    this.id = (Integer)map.get("id");
    this.data = (Date)map.get("data");
    this.quantita = (Double)map.get("quantita");
    this.orario = (String)map.get("orario");
    this.stato = (Integer)map.get("stato");
    this.remoteId = (String)map.get("remoteId");
    this.language = (String)map.get("language");
    this.prescrizione = map.get("prescrizione") != null ? new PrescrizioneTx().fromRpcMap((RpcMap)map.get("prescrizione")) : null;
    return this;
  }
  

  public SomministrazioneTx() {

  }

  public SomministrazioneTx(Prescrizione prescrizione) {
    this.prescrizione = prescrizione;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Somministrazione) {
      Somministrazione that = (Somministrazione)obj;
      return this.id.equals(that.getId());
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "SomministrazioneTx [prescrizione=" + prescrizione + ", id=" + id + ", data=" + data + ", quantita=" + quantita + ", orario=" + orario + ", stato="
        + stato + ", remoteId=" + remoteId + "]";
  }

  @Override
  public Prescrizione getPrescrizione() {
    return prescrizione;
  }
  
  @Override
  public void setPrescrizione(Prescrizione prescrizione) {
    this.prescrizione = prescrizione;
  }
  
  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Date getData() {
    return data;
  }

  @Override
  public void setData(Date data) {
    this.data = data;
  }
  
  @Override
  public Double getQuantita() {
    return quantita != null ? quantita : 0d;
  }

  @Override
  public void setQuantita(Double quantita) {
    this.quantita = quantita;
  }

  @Override
  public String getOrario() {
    return orario;
  }

  @Override
  public void setOrario(String orario) {
    this.orario = orario;
  }

  @Override
  public Integer getStato() {
    return stato;
  }

  @Override
  public void setStato(Integer stato) {
    this.stato = stato;
  }
  
  @Override
  public boolean isSchedulata() {
    return stato == STATO_SCHEDULATA;
  }
  
  @Override
  public void setSchedulata() {
    stato = STATO_SCHEDULATA;
  }
  
  @Override
  public boolean isEseguita() {
    return stato == STATO_ESEGUITA;
  }
  
  @Override
  public void setEseguita() {
    stato = STATO_ESEGUITA;
  }
  
  @Override
  public boolean isAnnullata() {
    return stato == STATO_ANNULLATA;
  }
  
  @Override
  public void setAnnullata() {
    stato = STATO_ANNULLATA;
  }

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public int getNeedSynchronization() {
    return needSynchronization;
  }

  public void setNeedSynchronization(int value) {
    this.needSynchronization = value;
  }
  
}
