package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.List;

public interface OrderItem extends Serializable {

  public String getId();
  
  public void setId(String id);

  public Order getOrder();

  public void setOrder(Order order);

  public Articolo getProduct();

  public void setProduct(Articolo product);
  
  public Double getQuantity();
  
  public void setQuantity(Double quantity);
  
  public List<OrderItemDetail> getDetails();
  
  public void setDetails(List<OrderItemDetail> details);
  
}
