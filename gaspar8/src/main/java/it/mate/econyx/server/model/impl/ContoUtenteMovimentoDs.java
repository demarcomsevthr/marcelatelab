package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class ContoUtenteMovimentoDs implements ContoUtenteMovimento, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  Double importo;
  
  @Persistent
  String segno;
  
  @Persistent
  String causale;
  
  @Persistent
  Date data;

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key orderId;
  @UnownedRelationship (key="orderId")
  transient OrderDs order;
  
  
  @Persistent (dependentKey="false")
  Key registeringPortalUserId;
  @UnownedRelationship (key="registeringPortalUserId")
  transient PortalUserDs registeringPortalUser;
  

  // 16/05/2013
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key contoId;
  @UnownedRelationship (key="contoId")
  transient ContoUtenteDs conto;
  
  @Persistent
  String customerEmail;
  
  
  @Override
  public String toString() {
    return "ContoUtenteMovimentoTx [id=" + id + ", importo=" + importo + ", segno=" + segno + ", causale=" + causale + ", data=" + data
        + ", order=" + order + "]";
  }

  public Key getKey() {
    return id;
  }
  
  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
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

  @CloneableProperty (targetClass=OrderDs.class)
  public void setOrder(Order order) {
    if (order == null) {
      this.order = null;
      this.orderId = null;
    } else {
      this.order = (OrderDs)order;
      this.orderId = this.order.getKey();
    }
  }
  
  
  public PortalUser getRegisteringPortalUser() {
    return registeringPortalUser;
  }
  
  @CloneableProperty (targetClass=PortalUserDs.class)
  public void setRegisteringPortalUser(PortalUser portalUser) {
    this.registeringPortalUser = (PortalUserDs)portalUser;
    this.registeringPortalUserId = this.registeringPortalUser != null ? this.registeringPortalUser.getKey() : null;
  }

  public ContoUtente getConto() {
    return conto;
  }

  @CloneableProperty (targetClass=ContoUtenteDs.class)
  public void setConto(ContoUtente conto) {
    this.conto = (ContoUtenteDs)conto;
    this.contoId = this.conto != null ? this.conto.getKey() : null;
    this.customerEmail = (this.conto != null && this.conto.getCustomer() != null && this.conto.getCustomer().getPortalUser() != null) ?
        this.conto.getCustomer().getPortalUser().getEmailAddress() : null;
  }
  
}
