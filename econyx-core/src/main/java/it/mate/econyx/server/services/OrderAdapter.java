package it.mate.econyx.server.services;

import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.model.impl.ProduttoreDs;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderStateConfig;

import java.util.Date;
import java.util.List;

public interface OrderAdapter {
  
  public List<Order> findAll();

  public Order update(Order entity);

  public void delete(Order entity);

  public Order create(Order entity);

  public Order fetchItems(Order order);
  
  public List<OrderItem> findAllItems();

  public Order findById (String id);
  
  public Order findOpenOrderByCustomer(Customer customer);
  
  public List<Order> findOrdersByCustomer(Customer customer);
  
  public Order orderProduct(Order order, String openOrderId, Articolo product, Customer customer, Double quantity, List<OrderItemDetail> details);
  
  public void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento);
  
  public void closeOrder (String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento, Date dataGenerazioneOrdine);
  
  public OrderItem updateOrderItem (OrderItem item);
  
  
  public List<ModalitaSpedizione> findAllModalitaSpedizione();
  
  public void delete (ModalitaSpedizione entity);
  
  public ModalitaSpedizione create (ModalitaSpedizione entity);

  
  public List<ModalitaPagamento> findAllModalitaPagamento();
  
  public void delete (ModalitaPagamento entity);
  
  public ModalitaPagamento create (ModalitaPagamento entity);
  
  
  public OrderStateConfig create(OrderStateConfig entity);
  
  public List<OrderStateConfig> findAllOrderStates();
  
  public void delete (OrderStateConfig entity);
  
  
  public void onOrderStateChange(String orderId, String orderStateCode);
  
  public List<OrderDs> findOrdersByProducer(ProduttoreDs produttore, String currentStateCode);
  
  public List<OrderDs> findOrdersByState(String currentStateCode);
  
  public Order updateImportoTotale(Order order, Double importoRettificato, boolean doUpdateDs);

  public abstract OrderStateConfig findOrderStateConfig(String code);
  
  
  public Order createWithoutInitialState(Order entity);
  
  public void setDisableOrderStateChangeCustomAdapter(boolean disableOrderStateChangeCustomAdapter);

  public void setDisableOrderStateChangeDeferredTask(boolean disableOrderStateChangeDeferredTask);
  
}
