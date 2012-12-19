package it.mate.econyx.client.model;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.impl.ArticoloTx;

// 23/11/2012
//public class ArticoloDaOrdinare {
public class ArticoloDaOrdinare extends ArticoloTx {
  
  private Articolo articolo;
  
  Double quantity;
  
  public Articolo getArticolo() {
    return articolo;
  }

  public void setArticolo(Articolo articolo) {
    this.articolo = articolo;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

}
