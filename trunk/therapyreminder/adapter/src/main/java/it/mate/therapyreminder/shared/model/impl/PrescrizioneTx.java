package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  
  // 10/04/2014 ("Confetto issue")
  // Devo mettere il default literal altrimenti da problemi nel passaggio alla DosageEditView
  // nel caso di prescrizione appena creata ("Confetto issue"...)
  // Non trovo una soluzione migliore per mettere il default 
  // (i valori effettivi stanno su db, se cambiano mi devo ricordare di mantenere allineato questo default)
  private String codUdM = "C";
  
  private String tipoRicorrenzaOraria;
  
  private Integer intervalloOrario = 1;
  
  private List<Dosaggio> dosaggi = new ArrayList<Dosaggio>();
  
  private boolean detached = false;

  @Override
  public String toString() {
    return "PrescrizioneTx [id=" + id + ", nome=" + nome + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + ", quantita=" + quantita
        + ", idComposizione=" + idComposizione + ", tipoRicorrenza=" + tipoRicorrenza + ", valoreRicorrenza=" + valoreRicorrenza + ", codUdM=" + codUdM
        + ", tipoRicorrenzaOraria=" + tipoRicorrenzaOraria + ", intervalloOrario=" + intervalloOrario + "]";
  }
  
  @Override
  public boolean isPersistent() {
    return id != null;
  }
  
  public PrescrizioneTx() {

  }
  
  public PrescrizioneTx(Integer id) {
    this.id = id;
    this.detached = true;
  }

  public PrescrizioneTx(Prescrizione that) {
    this.id = that.getId();
    this.nome = that.getNome();
    this.dataInizio = that.getDataInizio();
    this.dataFine = that.getDataFine();
    this.quantita = that.getQuantita();
    this.idComposizione = that.getIdComposizione();
    this.tipoRicorrenza = that.getTipoRicorrenza();
    this.valoreRicorrenza = that.getValoreRicorrenza();
    this.codUdM = that.getCodUdM();
    this.tipoRicorrenzaOraria = that.getTipoRicorrenzaOraria();
    this.intervalloOrario = that.getIntervalloOrario();
    for (Dosaggio dosaggio : that.getDosaggi()) {
      this.dosaggi.add(new DosaggioTx(dosaggio));
    }
  }

  @Override
  public boolean hasEqualSomministrazioneOf(Prescrizione that) {
    if (!isEqualAllowNull(this.getTipoRicorrenza(), that.getTipoRicorrenza())) {
      return false;
    }
    if (!isEqualAllowNull(this.getValoreRicorrenza(), that.getValoreRicorrenza())) {
      return false;
    }
    if (!isEqualAllowNull(this.getTipoRicorrenzaOraria(), that.getTipoRicorrenzaOraria())) {
      return false;
    }
    if (!isEqualAllowNull(this.getIntervalloOrario(), that.getIntervalloOrario())) {
      return false;
    }
    for (Dosaggio thisDosaggio : this.getDosaggi()) {
      boolean match = false;
      for (Dosaggio thatDosaggio : that.getDosaggi()) {
        if (isEqualAllowNull(thisDosaggio.getOrario(), thatDosaggio.getOrario())) {
          match = true;
          break;
        }
      }
      if (!match) {
        return false;
      }
    }
    return true;
  }
  
  private boolean isEqualAllowNull(Object v1, Object v2) {
    if (v1 == null && v2 != null)
      return false;
    if (v1 != null && v2 == null)
      return false;
    if (v1 == null && v2 == null)
      return true;
    return v1.equals(v2);
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

  public List<Dosaggio> getDosaggi() {
    if (dosaggi == null)
      dosaggi = new ArrayList<Dosaggio>();
    return dosaggi;
  }

  public void setDosaggi(List<Dosaggio> dosaggi) {
    this.dosaggi = dosaggi;
  }
  
  public boolean isDetached() {
    return detached;
  }

}
