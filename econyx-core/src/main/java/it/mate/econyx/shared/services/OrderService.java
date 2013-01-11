package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".orderService")
public interface OrderService extends RemoteService {

  public List<Order> findAll() throws ServiceException;

  public Order update(Order entity) throws ServiceException;

  public void delete(Order entity) throws ServiceException;

  public Order create(Order entity) throws ServiceException;

  public Order fetchItems(Order order) throws ServiceException;
  

  public Order findById(String id);
  
  public Order findOpenOrderByCustomer(Customer customer) throws ServiceException;
  
  public List<Order> findOrdersByCustomer(Customer customer);
  
  Order orderProduct(Order order, String openOrderId, Articolo product, Customer customer, Double quantity, List<OrderItemDetail> details) throws ServiceException ;
  
  public OrderItem updateOrderItem (OrderItem item);
  
  public void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento);

  
  public List<ModalitaSpedizione> findAllModalitaSpedizione() throws ServiceException;
  
  public List<ModalitaPagamento> findAllModalitaPagamento() throws ServiceException;
  
  public List<Order> findOrdersByProducer(Produttore produttore, String currentStateCode);

  public List<Order> findOrdersByState(String currentStateCode);
  
  public List<OrderStateConfig> findAllOrderStates();

  
  public List<Order> updateOrders(List<Order> orders) throws ServiceException;
  
  public Order updateImportoTotale(Order order, Double importoTotale);
  
}
