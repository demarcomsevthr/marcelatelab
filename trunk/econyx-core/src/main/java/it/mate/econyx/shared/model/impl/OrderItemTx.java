package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class OrderItemTx implements OrderItem {

  String id;
  
  OrderTx order;
  
  ArticoloTx product;
  
  Double quantity;
  
  List<AbstractOrderItemDetailTx> details = new ArrayList<AbstractOrderItemDetailTx>();

  public String toString() {
    return "OrderItemTx [id=" + id + ", product=" + product + ", qta=" + quantity + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OrderItem) {
      OrderItem that = (OrderItem)obj;
      if (this.id == null) {
        return that.getId() != null;
      }
      return this.id.equals(that.getId());
    }
    return super.equals(obj);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  @CloneableProperty (targetClass=OrderTx.class)
  public void setOrder(Order order) {
    if (order == null)
      return;
    if (order instanceof OrderTx) {
      this.order = (OrderTx)order;
    } else {
      throw new IllegalArgumentException("cannot set order of type " + order.getClass());
    }
  }

  public Articolo getProduct() {
    return product;
  }

  @CloneableProperty (targetClass=ArticoloTx.class)
  public void setProduct(Articolo product) {
    if (product instanceof ArticoloTx) {
      this.product = (ArticoloTx)product;
    } else {
      throw new IllegalArgumentException("cannot set product of type " + product.getClass());
    }
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }
  
  
  public List<OrderItemDetail> getDetails() {
    
    if (details == null)
      return null;
    
    return new AbstractList<OrderItemDetail>() {
      public int size() {
        return details.size();
      }
      public OrderItemDetail get(int index) {
        return details.get(index);
      }
      public OrderItemDetail set(int index, OrderItemDetail element) {
        if (element instanceof AbstractOrderItemDetailTx) {
          return details.set(index, (AbstractOrderItemDetailTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(OrderItemDetail element) {
        if (element instanceof AbstractOrderItemDetailTx) {
          return details.add((AbstractOrderItemDetailTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return details.remove(o);
      }
    };
    
    
  }
  
  @CloneableProperty (targetClass=AbstractOrderItemDetailTx.class)
  public void setDetails(List<OrderItemDetail> details) {
    this.details = new ArrayList<AbstractOrderItemDetailTx>();
    if (details != null) {
      for (OrderItemDetail detail : details) {
        addDetail(detail);
      }
    }
  }
  
  
  protected void addDetail (OrderItemDetail detail) {
    if (details == null) 
      this.details = new ArrayList<AbstractOrderItemDetailTx>();
    if (detail instanceof AbstractOrderItemDetailTx) {
      AbstractOrderItemDetailTx detailTx = (AbstractOrderItemDetailTx)detail;
      details.add(detailTx);
    } else {
      throw new IllegalArgumentException("cannot add item of type " + detail.getClass() + ", you must use CloneableProperty annotation");
    }
  }
  
}
