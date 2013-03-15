package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.impl.TipoArticoloTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=TipoArticoloTx.class)
public class TipoArticoloDs implements TipoArticolo, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent
  String codice;

  @Persistent
  String codiceInterno;
  
  @Override
  public String toString() {
    return "ProductTypeDs [code=" + codice + ", internalCode=" + codiceInterno + ", id=" + id + "]";
  }
  
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

  public void setCodice(String code) {
    this.codice = code;
  }

  public String getCodiceInterno() {
    return codiceInterno;
  }

  public void setCodiceInterno(String internalCode) {
    this.codiceInterno = internalCode;
  }
  
}
