package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class OrderTx implements Order {

  String id;
  
  String code;
  
  String description;
  
  Date created;
  
  boolean isItemsInitialized;
  
  List<OrderItemTx> items = new ArrayList<OrderItemTx>();
  
  Customer customer;
  
  List<OrderStateTx> states = new ArrayList<OrderStateTx>();
  
  ProduttoreTx producer;
  
  Double importoTotale;
  
  ModalitaSpedizioneTx modalitaSpedizione;

  ModalitaPagamentoTx modalitaPagamento;
  
  
  String deliveryInformations;

  
  
  public String toString() {
    return "OrderTx [code=" + code + ", created=" + created + ", description=" + description + ", id=" + id
        + ", items=" + items + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    
    if (items == null)
      return null;
    
    return new AbstractList<OrderItem>() {
      public int size() {
        return items.size();
      }
      public OrderItem get(int index) {
        return items.get(index);
      }
      public OrderItem set(int index, OrderItem element) {
        if (element instanceof OrderItemTx) {
          return items.set(index, (OrderItemTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(OrderItem element) {
        if (element instanceof OrderItemTx) {
          return items.add((OrderItemTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return items.remove(o);
      }
      public OrderItem remove(int index) {
        return items.remove(index);
      }
    };
    
    
  }
  
  @CloneableProperty (targetClass=OrderItemTx.class)
  public void setItems(List<OrderItem> items) {
//  this.items = items;
    this.items = new ArrayList<OrderItemTx>();
    if (items != null) {
      for (OrderItem item : items) {
        addItem(item);
      }
    }
  }
  
  public void addItem (OrderItem item) {
    if (items == null) 
      this.items = new ArrayList<OrderItemTx>();
    if (item instanceof OrderItemTx) {
      OrderItemTx itemTx = (OrderItemTx)item;
      items.add(itemTx);
      itemTx.order = this;
    } else {
      throw new IllegalArgumentException("cannot add item of type " + item.getClass() + ", you must use CloneableProperty annotation");
    }
  }
  
  public void removeItem (OrderItem item) {
    for (Iterator<? extends OrderItem> it = items.iterator(); it.hasNext();) {
      OrderItem elem = it.next();
      if (elem.equals(item))
        it.remove();
    }
  }

  public boolean isItemsInitialized() {
    return this.items != null;
  }

  public void setItemsInitialized(boolean isItemsInitialized) {
    this.isItemsInitialized = isItemsInitialized;
  }

  public Customer getCustomer() {
    return customer;
  }
  
  @CloneableProperty (targetClass=CustomerTx.class)
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public OrderState getCurrentState() {
    return Utils.getCurrentState(this);
  }
  
  public void setCurrentState(OrderState orderState) {
    
  }

  public List<OrderState> getStates() {
    
    if (states == null)
      return null;
    
    return new AbstractList<OrderState>() {
      public int size() {
        return states.size();
      }
      public OrderState get(int index) {
        return states.get(index);
      }
      public OrderState set(int index, OrderState element) {
        if (element instanceof OrderStateTx) {
          return states.set(index, (OrderStateTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(OrderState element) {
        if (element instanceof OrderStateTx) {
          return states.add((OrderStateTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return states.remove(o);
      }
    };
    
    
  }
  
  @CloneableProperty (targetClass=OrderStateTx.class)
  public void setStates(List<OrderState> states) {
    this.states = new ArrayList<OrderStateTx>();
    if (states != null) {
      for (OrderState state : states) {
        if (state instanceof OrderStateTx) {
          this.states.add((OrderStateTx)state);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + state.getClass() + ", you must use CloneableProperty annotation");
        }
      }
    }
  }

  public Produttore getProducer() {
    return producer;
  }

  @CloneableProperty (targetClass=ProduttoreTx.class)
  public void setProducer(Produttore producer) {
    this.producer = (ProduttoreTx)producer;
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

  @CloneableProperty (targetClass=ModalitaSpedizioneTx.class)
  public void setModalitaSpedizione(ModalitaSpedizione modalitaSpedizione) {
    this.modalitaSpedizione = (ModalitaSpedizioneTx)modalitaSpedizione;
  }

  public ModalitaPagamento getModalitaPagamento() {
    return modalitaPagamento;
  }

  @CloneableProperty (targetClass=ModalitaPagamentoTx.class)
  public void setModalitaPagamento(ModalitaPagamento modalitaPagamento) {
    this.modalitaPagamento = (ModalitaPagamentoTx)modalitaPagamento;
  }

  public String getDeliveryInformations() {
    return deliveryInformations;
  }

  public void setDeliveryInformations(String deliveryInformations) {
    this.deliveryInformations = deliveryInformations;
  }
  
}
