package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

@SuppressWarnings("serial")
public class ContoUtenteMovimentoTx implements ContoUtenteMovimento {

  String id;
  
  Double importo;
  
  String segno;
  
  String causale;
  
  Date data;

  Order order;
  
  ContoUtente conto;
  
  PortalUserTx registeringPortalUser;
  
  @Override
  public String toString() {
    return "ContoUtenteMovimentoTx [id=" + id + ", importo=" + importo + ", segno=" + segno + ", causale=" + causale + ", data=" + data
        + ", order=" + order + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public String getSegno() {
    return segno;
  }

  public void setSegno(String segno) {
    this.segno = segno;
  }

  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Order getOrder() {
    return order;
  }

  @CloneableProperty (targetClass=OrderTx.class)
  public void setOrder(Order order) {
    this.order = order;
  }
  
  public PortalUser getRegisteringPortalUser() {
    return registeringPortalUser;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setRegisteringPortalUser(PortalUser portalUser) {
    this.registeringPortalUser = (PortalUserTx)portalUser;
  }

  public ContoUtente getConto() {
    return conto;
  }

  public void setConto(ContoUtente conto) {
    this.conto = conto;
  }

}
