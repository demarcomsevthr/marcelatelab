package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.Customer;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ContoUtenteTx implements ContoUtente {

  String id;
  
  Customer customer;
  
  Double saldo;
  
  List<ContoUtenteMovimentoTx> movimenti;
  

  @Override
  public String toString() {
    return "ContoUtenteTx [id=" + id + ", customer=" + customer + ", saldo=" + saldo + ", movimenti=" + movimenti + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  @CloneableProperty (targetClass=CustomerTx.class)
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

  public List<ContoUtenteMovimento> getMovimenti() {

    if (movimenti == null)
      return null;
    
    return new AbstractList<ContoUtenteMovimento>() {
      public int size() {
        return movimenti.size();
      }
      public ContoUtenteMovimento get(int index) {
        return movimenti.get(index);
      }
      public ContoUtenteMovimento set(int index, ContoUtenteMovimento element) {
        if (element instanceof ContoUtenteMovimentoTx) {
          return movimenti.set(index, (ContoUtenteMovimentoTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(ContoUtenteMovimento element) {
        if (element instanceof ContoUtenteMovimentoTx) {
          return movimenti.add((ContoUtenteMovimentoTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return movimenti.remove(o);
      }
      public ContoUtenteMovimento remove(int index) {
        return movimenti.remove(index);
      }
    };
    
  }

  @CloneableProperty (targetClass=ContoUtenteMovimentoTx.class)
  public void setMovimenti(List<ContoUtenteMovimento> movimenti) {
    this.movimenti = new ArrayList<ContoUtenteMovimentoTx>();
    if (movimenti != null) {
      for (ContoUtenteMovimento movimento : movimenti) {
        if (movimento instanceof ContoUtenteMovimentoTx) {
          ContoUtenteMovimentoTx movimentoTx = (ContoUtenteMovimentoTx)movimento;
          this.movimenti.add(movimentoTx);
          movimentoTx.conto = this;
        } else {
          throw new IllegalArgumentException("cannot add item of type " + movimento.getClass() + ", you must use CloneableProperty annotation");
        }
      }
    }
    
  }
  
  public void setMovimentiTx(List<ContoUtenteMovimentoTx> movimenti) {
    this.movimenti = movimenti;
  }
  
  
}
