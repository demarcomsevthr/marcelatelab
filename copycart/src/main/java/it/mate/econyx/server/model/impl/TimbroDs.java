package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Timbro;
import it.mate.econyx.shared.model.impl.TimbroTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@CacheableEntity (txClass=TimbroTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.TimbroTx")
public class TimbroDs extends AbstractArticoloDs implements Timbro {

  @Persistent
  Integer larghezza;
  
  @Persistent
  Integer altezza;

  @Persistent
  Integer numRighe;
  
  @Persistent
  Boolean oval;
  
  
  @Override
  public String toString() {
    return "TimbroDs [ "+ super.toString() +" larghezza=" + larghezza + ", altezza=" + altezza + ", numRighe=" + numRighe + "]";
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
  
}
