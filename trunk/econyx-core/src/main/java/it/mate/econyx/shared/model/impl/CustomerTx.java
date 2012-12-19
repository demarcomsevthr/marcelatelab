package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Indirizzo;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;

@SuppressWarnings("serial")
public class CustomerTx implements Customer {

  String id;
  
  PortalUser portalUser;
  
  Indirizzo indirizzoSpedizione;
  
  Indirizzo indirizzoFatturazione;
  
  @Override
  public String toString() {
    return "ClienteTx [id=" + id + ", portalUser=" + portalUser + ", indirizzoSpedizione=" + indirizzoSpedizione
        + ", indirizzoFatturazione=" + indirizzoFatturazione + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CustomerTx other = (CustomerTx) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PortalUser getPortalUser() {
    return portalUser;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setPortalUser(PortalUser portalUser) {
    this.portalUser = portalUser;
  }

  public Indirizzo getIndirizzoSpedizione() {
    return indirizzoSpedizione;
  }

  @CloneableProperty (targetClass=AbstractIndirizzoTx.class)
  public void setIndirizzoSpedizione(Indirizzo indirizzoSpedizione) {
    this.indirizzoSpedizione = indirizzoSpedizione;
  }

  public Indirizzo getIndirizzoFatturazione() {
    return indirizzoFatturazione;
  }

  @CloneableProperty (targetClass=AbstractIndirizzoTx.class)
  public void setIndirizzoFatturazione(Indirizzo indirizzoFatturazione) {
    this.indirizzoFatturazione = indirizzoFatturazione;
  }
  
}
