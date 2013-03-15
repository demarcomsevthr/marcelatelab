package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Indirizzo;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=CustomerTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class CustomerDs implements Customer, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key portalUserId;

  @UnownedRelationship (key="portalUserId")
  transient PortalUserDs portalUser;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key indirizzoSpedizioneId;

  @UnownedRelationship (key="indirizzoSpedizioneId")
  transient IndirizzoSpedizioneDs indirizzoSpedizione;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key indirizzoFatturazioneId;

  @UnownedRelationship (key="indirizzoFatturazioneId")
  transient IndirizzoFatturazioneDs indirizzoFatturazione;
  
  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public PortalUser getPortalUser() {
    return portalUser;
  }

  @CloneableProperty (targetClass=PortalUserDs.class)
  public void setPortalUser(PortalUser portalUser) {
    this.portalUser = (PortalUserDs)portalUser;
    this.portalUserId = this.portalUser != null ? this.portalUser.getKey() : null;
  }

  public Indirizzo getIndirizzoSpedizione() {
    return indirizzoSpedizione;
  }

  @CloneableProperty (targetClass=IndirizzoSpedizioneDs.class)
  public void setIndirizzoSpedizione(Indirizzo indirizzoSpedizione) {
    this.indirizzoSpedizione = (IndirizzoSpedizioneDs)indirizzoSpedizione;
    this.indirizzoSpedizioneId = this.indirizzoSpedizione != null ? this.indirizzoSpedizione.getKey() : null;
  }

  public Indirizzo getIndirizzoFatturazione() {
    return indirizzoFatturazione;
  }

  @CloneableProperty (targetClass=IndirizzoFatturazioneDs.class)
  public void setIndirizzoFatturazione(Indirizzo indirizzoFatturazione) {
    this.indirizzoFatturazione = (IndirizzoFatturazioneDs)indirizzoFatturazione;
    this.indirizzoFatturazioneId = this.indirizzoFatturazione != null ? this.indirizzoFatturazione.getKey() : null;
  }
  
}
