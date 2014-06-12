package it.mate.therapyreminder.shared.model.impl;

import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;

public class DosaggioEditModel {
  
  private Prescrizione prescrizione;
  
  private Dosaggio dosaggio;
  
  public DosaggioEditModel(Prescrizione prescrizione, Dosaggio dosaggio) {
    super();
    this.prescrizione = prescrizione;
    this.dosaggio = dosaggio;
  }

  public Prescrizione getPrescrizione() {
    return prescrizione;
  }

  public Dosaggio getDosaggio() {
    return dosaggio;
  }

}
