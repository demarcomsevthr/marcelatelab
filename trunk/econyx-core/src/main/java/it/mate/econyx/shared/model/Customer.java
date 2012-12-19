package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface Customer extends Serializable {
  
  public String getId();

  public void setId(String id);

  public void setIndirizzoFatturazione(Indirizzo indirizzoFatturazione);

  public Indirizzo getIndirizzoFatturazione();

  public void setIndirizzoSpedizione(Indirizzo indirizzoSpedizione);

  public Indirizzo getIndirizzoSpedizione();

  public void setPortalUser(PortalUser portalUser);

  public PortalUser getPortalUser();

}
