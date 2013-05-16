package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.impl.ContoUtenteTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=ContoUtenteTx.class)
public class ContoUtenteDs implements ContoUtente, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key customerId;
  @UnownedRelationship (key="customerId")
  transient CustomerDs customer; 
  
  @Persistent
  Double saldo;
  
  /* 16/05/2013 - eliminata la relazione in questo verso
  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> movimentiKeys;
  @UnownedRelationship (key="movimentiKeys", itemClass=ContoUtenteMovimentoDs.class)
  */
  transient List<ContoUtenteMovimentoDs> movimenti;
  
  @Persistent
  String customerEmail;
  
  
  @Override
  public String toString() {
    return "ContoUtenteDs [id=" + id + ", customer=" + customerId + ", valoreCorrente=" + saldo + "]";
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

  public Customer getCustomer() {
    return customer;
  }

  @CloneableProperty (targetClass=CustomerDs.class)
  public void setCustomer(Customer customer) {
    if (customer == null) {
      this.customer = null;
      this.customerId = null;
    } else {
      this.customer = (CustomerDs)customer;
      this.customerId = this.customer.getKey();
      this.customerEmail = this.customer.getPortalUser() != null ? this.customer.getPortalUser().getEmailAddress() : null;
    }
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

  public List<ContoUtenteMovimento> getMovimenti() {
    return movimenti != null ? new ArrayList<ContoUtenteMovimento>(movimenti) : null;
  }

  @CloneableProperty (targetClass=ContoUtenteMovimentoDs.class)
  public void setMovimenti(List<ContoUtenteMovimento> movimenti) {
//  this.movimentiKeys = new ArrayList<Key>();
    this.movimenti = new ArrayList<ContoUtenteMovimentoDs>();
    if (movimenti != null) {
      for (ContoUtenteMovimento movimento : movimenti) {
        if (movimento instanceof ContoUtenteMovimentoDs) {
          this.movimenti.add((ContoUtenteMovimentoDs)movimento);
          
          // 16/05/2013
//        this.movimentiKeys.add(((ContoUtenteMovimentoDs)movimento).getKey());
          
          movimento.setConto(this);
          
        } else {
          throw new IllegalArgumentException("cannot add item of type " + movimento.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  
  
}
