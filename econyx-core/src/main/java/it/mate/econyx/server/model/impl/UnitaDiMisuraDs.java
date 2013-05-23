package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.econyx.shared.model.impl.UnitaDiMisuraTx;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=UnitaDiMisuraTx.class)
public class UnitaDiMisuraDs implements UnitaDiMisura, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent String codice;

  @Persistent String nome;
  
  @Persistent Integer decimali = 0;
  
  @Override
  public String toString() {
    return "UnitOfMeasureDs [id=" + id + ", code=" + codice + ", screenName=" + nome + "]";
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

  public String getNome() {
    return nome;
  }

  public void setNome(String screenName) {
    this.nome = screenName;
  }
  
  public Integer getDecimali() {
    return decimali;
  }

  public void setDecimali(Integer decimali) {
    this.decimali = decimali;
  }
  
}
