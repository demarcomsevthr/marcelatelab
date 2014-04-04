package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Prescrizione;

import java.util.Date;

@SuppressWarnings("serial")
public class PrescrizioneTx implements Prescrizione {
  
  private Integer id;
  
  private String nome;
  
  private Date dataInizio = new Date();
  
  private Date dataFine;
  
  private Double quantita = 1d;
  
  private Long idComposizione;
  
  private String tipoRicorrenza;
  
  private Integer valoreRicorrenza = 1;
  
  private String codUdM;
  
  private String tipoRicorrenzaOraria;
  
  private Integer intervalloOrario;
  
  private String orari;
  
  @Override
  public String toString() {
    return "PrescrizioneTx [id=" + id + ", nome=" + nome + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + ", quantita=" + quantita
        + ", idComposizione=" + idComposizione + ", tipoRicorrenza=" + tipoRicorrenza + "]";
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

  public Double getQuantita() {
    return quantita;
  }

  public void setQuantita(Double quantita) {
    if (quantita != null) {
      this.quantita = quantita;
    }
  }

  public Long getIdComposizione() {
    return idComposizione;
  }

  public void setIdComposizione(Long idComposizione) {
    this.idComposizione = idComposizione;
  }

  public String getTipoRicorrenza() {
    return tipoRicorrenza;
  }

  public void setTipoRicorrenza(String tipoRicorrenza) {
    this.tipoRicorrenza = tipoRicorrenza;
  }

  public String getCodUdM() {
    return codUdM;
  }

  public void setCodUdM(String codUdM) {
    this.codUdM = codUdM;
  }

  public Integer getValoreRicorrenza() {
    return valoreRicorrenza;
  }

  public void setValoreRicorrenza(Integer valoreRicorrenza) {
    if (valoreRicorrenza != null) {
      this.valoreRicorrenza = valoreRicorrenza;
    }
  }

  public String getTipoRicorrenzaOraria() {
    return tipoRicorrenzaOraria;
  }

  public void setTipoRicorrenzaOraria(String tipoRicorrenzaOraria) {
    this.tipoRicorrenzaOraria = tipoRicorrenzaOraria;
  }

  public Integer getIntervalloOrario() {
    return intervalloOrario;
  }

  public void setIntervalloOrario(Integer intervalloOrario) {
    this.intervalloOrario = intervalloOrario;
  }

  public String getOrari() {
    return orari;
  }

  public void setOrari(String orari) {
    this.orari = orari;
  }

}
