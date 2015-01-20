package it.mate.copymob.shared.model;

import java.io.Serializable;
import java.util.List;

public interface Order extends Serializable {

  public Integer getId();

  public void setId(Integer id);

  public String getCodice();

  public void setCodice(String codice);

  public Integer getAccountId();

  public void setAccountId(Integer accountId);

  public void setItems(List<OrderItem> items);

  public List<OrderItem> getItems();

}
