package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.impl.ModalitaSpedizioneTx;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=ModalitaSpedizioneTx.class)
public class ModalitaSpedizioneDs implements ModalitaSpedizione, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String codice;
  
  @Persistent
  String descrizione;
  
  @Persistent
  Double prezzo;

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public Key getKey() {
    return id;
  }
  
  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Double getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(Double prezzo) {
    this.prezzo = prezzo;
  }

}
