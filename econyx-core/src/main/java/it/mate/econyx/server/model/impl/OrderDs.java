package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.CollectionUtils;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=OrderTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class OrderDs implements Order, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent
  String code;

  @Persistent
  String description;

  @Persistent
  Date created;

  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> itemKeys;
  @UnownedRelationship (key="itemKeys", itemClass=OrderItemDs.class)
  transient List<OrderItemDs> items;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key customerId;
  
  @UnownedRelationship (key="customerId")
  transient CustomerDs customer; 
  
  @Persistent
  String currentStateCode;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> stateKeys;
  @UnownedRelationship (key="stateKeys", itemClass=OrderStateDs.class)
  transient List<OrderStateDs> states;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key producerKey;
  @UnownedRelationship (key="producerKey")
  transient ProduttoreDs producer;
  
  @Persistent
  Double importoTotale;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key modalitaSpedizioneKey;
  @UnownedRelationship (key="modalitaSpedizioneKey")
  ModalitaSpedizioneDs modalitaSpedizione;

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key modalitaPagamentoKey;
  @UnownedRelationship (key="modalitaPagamentoKey")
  ModalitaPagamentoDs modalitaPagamento;

  @Persistent
  String deliveryInformations;

  public void resolveItems() {
    CollectionUtils.traverseCollection(this.itemKeys);
  }

  public String toString() {
    return this.getClass() + " [code=" + code + ", created=" + created + ", description=" + description + ", id=" + id + ", items=" + (items != null ? items.size() : "null") + "]";
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public List<OrderItem> getItems() {
    return items != null ? new ArrayList<OrderItem>(items) : null;
  }

  public void addItem (OrderItem item) {
    OrderItemDs itemDs = CloneUtils.clone(item, OrderItemDs.class);
    attachItem(itemDs);
  }

  @CloneableProperty (targetClass=OrderItemDs.class)
  public void setItems(List<OrderItem> items) {
    this.items = new ArrayList<OrderItemDs>();
    this.itemKeys = new ArrayList<Key>();
    if (items != null) {
      for (OrderItem item : items) {
        if (item instanceof OrderItemDs) {
          OrderItemDs itemDs = (OrderItemDs)item;
          attachItem(itemDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + item.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  public void attachItem(OrderItemDs item) {
    if (items == null)
      items = new ArrayList<OrderItemDs>();
    this.items.add(item);
    if (itemKeys == null)
      itemKeys = new ArrayList<Key>();
    this.itemKeys.add(item.getKey());
    item.order = this;
  }

  public boolean isItemsInitialized() {
    return items != null;
  }

  public void setItemsInitialized(boolean itemsInitialized) {

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
    }
  }

  public OrderState getCurrentState() {
    return Utils.getCurrentState(this);
  }
  
  public String getCurrentStateCode() {
    return currentStateCode;
  }
  
  public void setCurrentState(OrderState orderState) {
    this.currentStateCode = orderState != null ? orderState.getCode() : null;
  }

  public List<OrderState> getStates() {
    return states != null ? new ArrayList<OrderState>(states) : null;
  }
  
  @CloneableProperty (targetClass=OrderStateDs.class)
  public void setStates(List<OrderState> states) {
    this.stateKeys = new ArrayList<Key>();
    this.states = new ArrayList<OrderStateDs>();
    if (states != null) {
      for (OrderState state : states) {
        if (state instanceof OrderStateDs) {
          this.states.add((OrderStateDs)state);
          this.stateKeys.add(((OrderStateDs)state).getKey());
        } else {
          throw new IllegalArgumentException("cannot add item of type " + state.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
      setCurrentState(Utils.getCurrentState(this));
    }
  }

  public Produttore getProducer() {
    return producer;
  }

  @CloneableProperty (targetClass=ProduttoreDs.class)
  public void setProducer(Produttore producer) {
    this.producer = (ProduttoreDs)producer;
    this.producerKey = this.producer != null ? this.producer.getKey() : null ;
  }
  
  

  public Double getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(Double importo) {
    this.importoTotale = importo;
  }

  
  public ModalitaSpedizione getModalitaSpedizione() {
    return modalitaSpedizione;
  }

  @CloneableProperty (targetClass=ModalitaSpedizioneDs.class)
  public void setModalitaSpedizione(ModalitaSpedizione modalitaSpedizione) {
    this.modalitaSpedizione = (ModalitaSpedizioneDs)modalitaSpedizione;
    this.modalitaSpedizioneKey = modalitaSpedizione != null ? this.modalitaSpedizione.getKey() : null;
  }

  public ModalitaPagamento getModalitaPagamento() {
    return modalitaPagamento;
  }

  @CloneableProperty (targetClass=ModalitaPagamentoDs.class)
  public void setModalitaPagamento(ModalitaPagamento modalitaPagamento) {
    this.modalitaPagamento = (ModalitaPagamentoDs)modalitaPagamento;
    this.modalitaPagamentoKey = modalitaPagamento != null ? this.modalitaPagamento.getKey() : null;
  }

  public String getDeliveryInformations() {
    return deliveryInformations;
  }

  public void setDeliveryInformations(String deliveryInformations) {
    this.deliveryInformations = deliveryInformations;
  }
  
}
