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

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrderServiceAsync {

  void findAll(AsyncCallback<List<Order>> callback);

  void update(Order entity, AsyncCallback<Order> callback);

  void delete(Order entity, AsyncCallback<Void> callback);

  void create(Order entity, AsyncCallback<Order> callback);

  void fetchItems(Order order, AsyncCallback<Order> callback);

  void findOpenOrderByCustomer(Customer customer, AsyncCallback<Order> callback);

  void orderProduct(Order order, String openOrderId, Articolo product, Customer customer, Double quantity, List<OrderItemDetail> details,
      AsyncCallback<Order> callback);

  void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento, AsyncCallback<Void> callback);

  void findAllModalitaSpedizione(AsyncCallback<List<ModalitaSpedizione>> callback);

  void updateOrderItem(OrderItem item, AsyncCallback<OrderItem> callback);

  void findOrdersByCustomer(Customer customer, AsyncCallback<List<Order>> callback);

  void findById(String id, AsyncCallback<Order> callback);

  void findOrdersByProducer(Produttore produttore, String currentStateCode, AsyncCallback<List<Order>> callback);

  void findOrdersByState(String currentStateCode, AsyncCallback<List<Order>> callback);

  void findAllOrderStates(AsyncCallback<List<OrderStateConfig>> callback);

  void updateOrders(List<Order> orders, AsyncCallback<List<Order>> callback);

  void updateImportoTotale(Order order, Double importoTotale, AsyncCallback<Order> callback);

  void findAllModalitaPagamento(AsyncCallback<List<ModalitaPagamento>> callback);

  void findOrderStateConfig(String code, AsyncCallback<OrderStateConfig> callback);

}
