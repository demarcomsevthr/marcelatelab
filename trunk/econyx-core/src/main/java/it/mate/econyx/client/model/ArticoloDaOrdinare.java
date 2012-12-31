package it.mate.econyx.client.model;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.impl.ArticoloTx;

import java.util.List;

// 23/11/2012
//public class ArticoloDaOrdinare {
public class ArticoloDaOrdinare extends ArticoloTx {
  
  private Articolo articolo;
  
  private Double quantity;
  
  private List<OrderItemDetail> details;
  
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

  public List<OrderItemDetail> getDetails() {
    return details;
  }

  public void setDetails(List<OrderItemDetail> details) {
    this.details = details;
  }

}
