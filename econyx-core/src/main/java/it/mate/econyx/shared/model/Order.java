package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Order extends Serializable {

  public static class Utils {
    
    public static Double computeImportoTotale(Order order) {
      return computeImportoTotale(order, false);
    }
    
    public static Double computeImportoTotale(Order order, boolean recalculate) {
      Double importoTotale = 0d;
      if (!recalculate) {
        return order.getImportoTotale();
      }
      if (order.getItems() == null || order.getItems().size() == 0) {
        return order.getImportoTotale();
      }
      for (OrderItem item : order.getItems()) {
        importoTotale += computeImportoItem(item);
      }
      return importoTotale;
    }
    
    public static Double computeImportoItem(OrderItem item) {
      Double qta = item.getQuantity();
      Double prz = item.getProduct().getPrezzo();
      Double imp = (qta != null && prz != null) ?  qta * prz : 0d;
      return imp;
    }
    
    public static boolean isOrderInState (Order order, String stateCode) {
      OrderState state = getCurrentState(order);
      if (state != null) {
        if (state.getCode().equals(stateCode)) {
          return true;
        }
      }
      return false;
    }
    
    public static OrderState getCurrentState(Order order) {
      return getCurrentState(order.getStates());
    }
    
    public static OrderState getCurrentState(List<OrderState> states) {
      if (states == null || states.size() == 0) {
        return null;
      }
      OrderState currentState = null;
      for (OrderState state : states) {
        if (state.getChecked()) {
          if (currentState == null || state.getConfig().getOrderNm() > currentState.getConfig().getOrderNm()) {
            currentState = state;
          }
        }
      }
      return currentState;
    }
    
  }
  
  
  public String getId();

  public void setId(String id);

  public String getCode();

  public void setCode(String code);

  public String getDescription();

  public void setDescription(String description);

  public Date getCreated();

  public void setCreated(Date created);
  
  public List<OrderItem> getItems();

  public void setItems(List<OrderItem> items);

  public void addItem(OrderItem item);

  public boolean isItemsInitialized();

  public void setItemsInitialized(boolean isItemsInitialized);
  
  public Customer getCustomer();
  
  public void setCustomer(Customer customer);
  
  
  public OrderState getCurrentState();
  
  public void setCurrentState(OrderState orderState);
  
  
  public List<OrderState> getStates();
  
  public void setStates(List<OrderState> states);
  
  public Produttore getProducer();
  
  public void setProducer(Produttore producer);
  
  
  public Double getImportoTotale();
  
  public void setImportoTotale(Double importo);
  
  
  public ModalitaSpedizione getModalitaSpedizione();
  
  public void setModalitaSpedizione(ModalitaSpedizione modalitaSpedizione);
  
  
  public ModalitaPagamento getModalitaPagamento();
  
  public void setModalitaPagamento(ModalitaPagamento modalitaPagamento);
  

}
