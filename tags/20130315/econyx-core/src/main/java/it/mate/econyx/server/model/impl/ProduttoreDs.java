package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.impl.ProduttoreTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=ProduttoreTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class ProduttoreDs implements Produttore, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String codice;
  
  @Persistent
  String nome;
  
  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
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

  public void setNome(String name) {
    this.nome = name;
  }
  
}
