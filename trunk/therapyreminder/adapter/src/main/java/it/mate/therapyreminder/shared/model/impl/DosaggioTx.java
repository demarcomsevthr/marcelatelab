package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.utils.ModelUtils;

@SuppressWarnings("serial")
public class DosaggioTx implements Dosaggio {
  
  private Prescrizione prescrizione;
  
  private Double quantita = 1d;
  
  private String orario;

  //serve solo nella ui
  private String codUdM;
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj instanceof DosaggioTx) {
      DosaggioTx that = (DosaggioTx)obj;
      if (this.prescrizione != null && that.prescrizione == null) {
        return false;
      } else if (this.prescrizione == null && that.prescrizione != null) {
        return false;
      } else if (this.prescrizione == null && that.prescrizione == null) {
        //
      } else if (this.prescrizione != null && that.prescrizione != null) {
        if (!ModelUtils.equals(this.prescrizione.getId(), that.prescrizione.getId()))
          return false;
      }
      if (!ModelUtils.equals(this.quantita, that.quantita))
        return false;
      if (!ModelUtils.equals(this.orario, that.orario))
        return false;
      if (!ModelUtils.equals(this.codUdM, that.codUdM))
        return false;
      return true;
    }
    return super.equals(obj);
  }
  
  public DosaggioTx(Prescrizione prescrizione) {
    this.prescrizione = prescrizione;
  }

  public DosaggioTx(Prescrizione prescrizione, Double quantita, String orario) {
    this(prescrizione);
    this.quantita = quantita;
    this.orario = orario;
  }
  
  public DosaggioTx(Dosaggio that) {
    this.prescrizione = that.getPrescrizione();
    this.quantita = that.getQuantita();
    this.orario = that.getOrario();
  }

  public Prescrizione getPrescrizione() {
    return prescrizione;
  }

  public Double getQuantita() {
    return quantita;
  }

  public void setQuantita(Double quantita) {
    if (quantita != null) {
      this.quantita = quantita;
    }
  }

  public String getOrario() {
    return orario;
  }

  public void setOrario(String orario) {
    this.orario = orario;
  }

  public String getCodUdM() {
    return codUdM;
  }

  public void setCodUdM(String codUdM) {
    this.codUdM = codUdM;
  }
  
}
