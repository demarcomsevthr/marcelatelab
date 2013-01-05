package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Timbro;
import it.mate.gwtcommons.shared.model.CloneableBean;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.TimbroDs")
public class TimbroTx extends ArticoloTx implements Timbro {
  
  Integer larghezza;
  
  Integer altezza;
  
  Integer numRighe;
  
  Boolean oval;
  
  Boolean datario;
  
  
  @Override
  public String toString() {
    return "TimbroTx [ "+ super.toString() +" larghezza=" + larghezza + ", altezza=" + altezza + ", numRighe=" + numRighe + "]";
  }

  public Integer getLarghezza() {
    return larghezza;
  }

  public void setLarghezza(Integer larghezza) {
    this.larghezza = larghezza;
  }

  public Integer getAltezza() {
    return altezza;
  }

  public void setAltezza(Integer altezza) {
    this.altezza = altezza;
  }

  public Integer getNumRighe() {
    return numRighe;
  }

  public void setNumRighe(Integer numRighe) {
    this.numRighe = numRighe;
  }

  public Boolean getOval() {
    return oval;
  }

  public void setOval(Boolean oval) {
    this.oval = oval;
  }

  public boolean isOval() {
    return oval != null ? oval : false;
  }

  public Boolean getDatario() {
    return datario;
  }

  public void setDatario(Boolean datario) {
    this.datario = datario;
  }
  
  public boolean isDatario() {
    return datario != null ? datario : false;
  }

}
