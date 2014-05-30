package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.utils.ModelUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class PrescrizioneTx implements Prescrizione {
  
  private Integer id;
  
  private String nome;
  
  private Date dataInizio;
  
  private Date dataFine;
  
  private Double quantita;
  
  private Long idComposizione;
  
  private String tipoRicorrenza;
  
  private Integer valoreRicorrenza;
  
  private String codUdM;
  
  private String tipoRicorrenzaOraria;
  
  private Integer intervalloOrario;

  // START MIGRATION #1
  private Integer flgGstAvvisoRiordino;
  
  private Double qtaPerConfez;
  
  private Double qtaPerAvviso;
  
  private Double qtaRimanente;
  
  private Date ultimoAvvisoRiordino;
  // END MIGRATION #1
  
  private List<Dosaggio> dosaggi = new ArrayList<Dosaggio>();
  
  
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj instanceof PrescrizioneTx) {
      PrescrizioneTx that = (PrescrizioneTx)obj;
      if (!ModelUtils.equals(this.id, that.id))
        return false;
      if (!ModelUtils.equals(this.nome, that.nome))
        return false;
      if (!ModelUtils.equals(this.dataInizio, that.dataInizio))
        return false;
      if (!ModelUtils.equals(this.dataFine, that.dataFine))
        return false;
      if (!ModelUtils.equals(this.quantita, that.quantita))
        return false;
      if (!ModelUtils.equals(this.idComposizione, that.idComposizione))
        return false;
      if (!ModelUtils.equals(this.tipoRicorrenza, that.tipoRicorrenza))
        return false;
      if (!ModelUtils.equals(this.valoreRicorrenza, that.valoreRicorrenza))
        return false;
      if (!ModelUtils.equals(this.codUdM, that.codUdM))
        return false;
      if (!ModelUtils.equals(this.tipoRicorrenzaOraria, that.tipoRicorrenzaOraria))
        return false;
      if (!ModelUtils.equals(this.intervalloOrario, that.intervalloOrario))
        return false;
      if (!ModelUtils.equals(this.flgGstAvvisoRiordino, that.flgGstAvvisoRiordino))
        return false;
      if (!ModelUtils.equals(this.qtaPerConfez, that.qtaPerConfez))
        return false;
      if (!ModelUtils.equals(this.qtaPerAvviso, that.qtaPerAvviso))
        return false;
      if (!ModelUtils.equals(this.qtaRimanente, that.qtaRimanente))
        return false;
      if (!ModelUtils.equals(this.ultimoAvvisoRiordino, that.ultimoAvvisoRiordino))
        return false;
      if (!ModelUtils.equals(this.dosaggi, that.dosaggi))
        return false;
      return true;
    }
    return super.equals(obj);
  }
  

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
    this.flgGstAvvisoRiordino = that.getFlgGstAvvisoRiordino();
    this.qtaPerConfez = that.getQtaPerConfez();
    this.qtaPerAvviso = that.getQtaPerAvviso();
    this.qtaRimanente = that.getQtaRimanente();
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

  public Integer getFlgGstAvvisoRiordino() {
    return flgGstAvvisoRiordino != null ? flgGstAvvisoRiordino : 0;
  }

  public void setFlgGstAvvisoRiordino(Integer flgGstAvvisoRiordino) {
    this.flgGstAvvisoRiordino = flgGstAvvisoRiordino;
  }
  
  public void setGstAvvisoRiordino(Boolean flgGstAvvisoRiordino) {
    this.flgGstAvvisoRiordino = flgGstAvvisoRiordino ? 1 : 0;
  }
  
  public boolean isGstAvvisoRiordino() {
    return getFlgGstAvvisoRiordino() == 1;
  }

  public Double getQtaPerConfez() {
    return qtaPerConfez != null ? qtaPerConfez : 0d;
  }

  public void setQtaPerConfez(Double qtaPerConfez) {
    this.qtaPerConfez = qtaPerConfez;
  }

  public Double getQtaPerAvviso() {
    return qtaPerAvviso != null ? qtaPerAvviso : 0d;
  }

  public void setQtaPerAvviso(Double qtaPerAvviso) {
    this.qtaPerAvviso = qtaPerAvviso;
  }

  public Double getQtaRimanente() {
    return qtaRimanente != null ? qtaRimanente : 0d;
  }

  public void setQtaRimanente(Double qtaRimanente) {
    this.qtaRimanente = qtaRimanente;
  }

  public Date getUltimoAvvisoRiordino() {
    return ultimoAvvisoRiordino;
  }

  public void setUltimoAvvisoRiordino(Date ultimoAvvisoRiordino) {
    this.ultimoAvvisoRiordino = ultimoAvvisoRiordino;
  }
  
  public Double getNumConfezioni() {
    return getQtaPerConfez() > 0 ? getQtaRimanente() / getQtaPerConfez() : 0d;
  }
  
}
