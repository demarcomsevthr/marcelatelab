package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.utils.ModelUtils;

@SuppressWarnings("serial")
public class DosaggioTx implements Dosaggio {
  
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
  
  public DosaggioTx() {
    
  }
  
  public DosaggioTx(Double quantita, String orario) {
    this.quantita = quantita;
    this.orario = orario;
  }
  
  public DosaggioTx(Dosaggio that) {
    this.quantita = that.getQuantita();
    this.orario = that.getOrario();
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
