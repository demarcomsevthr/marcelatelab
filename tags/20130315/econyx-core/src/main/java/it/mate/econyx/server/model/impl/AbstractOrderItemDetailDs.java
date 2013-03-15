package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true", identityType=IdentityType.APPLICATION)
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class AbstractOrderItemDetailDs implements OrderItemDetail, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  public Key getKey() {
    return id;
  }
  
  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }
  
}
