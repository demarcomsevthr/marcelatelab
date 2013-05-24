package it.mate.econyx.server.services.impl;

import it.mate.commons.server.utils.CloneUtils;
import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.model.impl.ProduttoreDs;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.server.util.ServletThreadUtils;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.econyx.shared.services.OrderService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {

  private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

  private OrderAdapter adapter;
  
  private GeneralAdapter generalAdapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getOrderAdapter();
    this.generalAdapter = AdaptersUtil.getGeneralAdapter();
    logger.debug("initialized " + this);
  }
  
  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    ServletThreadUtils.set(getThreadLocalRequest(), getThreadLocalResponse());
    PortalSessionStateServerUtils.setInThread(generalAdapter.retrievePortalSessionState(getThreadLocalRequest()));
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  @Override
  protected void onAfterResponseSerialized(String serializedResponse) {
    ServletThreadUtils.remove();
    super.onAfterResponseSerialized(serializedResponse);
  }
  
  public List<Order> findAll() {
    return adapter.findAll();
  }
  
  private PortalUser getLoggedUser() {
    PortalSessionState portalSessionState = generalAdapter.retrievePortalSessionState(getThreadLocalRequest());
    if (portalSessionState != null) {
      return portalSessionState.getLoggedUser();
    }
    return null;
  }

  public Order create(Order entity) {
    return adapter.create(entity, getLoggedUser());
  }

  public void delete(Order entity) {
    adapter.delete(entity);
  }

  public Order update(Order entity) {
    return adapter.update(entity, getLoggedUser());
  }
  
  @Override
  public List<Order> updateOrders(List<Order> orders) throws ServiceException {
    List<Order> results = new ArrayList<Order>();
    for (Order order : orders) {
      results.add(update(order));
    }
    return results;
  }

  public Order fetchItems (Order order) {
    return adapter.fetchItems(order);
  }

  @Override
  public Order findOpenOrderByCustomer(Customer customer) {
    return adapter.findOpenOrderByCustomer(customer);
  }

  @Override
  public Order orderProduct(Order order, String openOrderId, Articolo product, Customer customer, Double quantity, List<OrderItemDetail> details) {
    return adapter.orderProduct(order, openOrderId, product, customer, quantity, details, getLoggedUser());
  }

  @Override
  public void closeOrder(String id, ModalitaSpedizione modalitaSpedizione, ModalitaPagamento modalitaPagamento) {
    adapter.closeOrder(id, modalitaSpedizione, modalitaPagamento, getLoggedUser());
  }
  
  @Override
  public OrderItem updateOrderItem (OrderItem item) {
    return adapter.updateOrderItem(item);
  }
  
  @Override
  public List<Order> findOrdersByCustomer(Customer customer) {
    return adapter.findOrdersByCustomer(customer);
  }

  @Override
  public List<ModalitaSpedizione> findAllModalitaSpedizione() {
    return adapter.findAllModalitaSpedizione();
  }
  
  @Override
  public List<ModalitaPagamento> findAllModalitaPagamento() {
    return adapter.findAllModalitaPagamento();
  }
  
  public Order findById(String id) {
    return adapter.findById(id);
  }
  
  @Override
  public List<Order> findOrdersByProducer(Produttore produttore, String currentStateCode) {
    List<OrderDs> orders = adapter.findOrdersByProducer(CloneUtils.clone(produttore, ProduttoreDs.class), currentStateCode);
    return CloneUtils.clone(orders, OrderTx.class, Order.class);
  }
  
  @Override
  public List<Order> findOrdersByState(String currentStateCode) {
    List<OrderDs> orders = adapter.findOrdersByState(currentStateCode);
    return CloneUtils.clone(orders, OrderTx.class, Order.class);
  }
  
  @Override
  public List<String> findOrdersIdByState(String currentStateCode) {
    return adapter.findOrdersIdByState(currentStateCode);
  }
  
  public List<OrderStateConfig> findAllOrderStates() {
    return adapter.findAllOrderStates();
  }
  
  public Order updateImportoTotale(Order order, Double importoTotale) {
    return adapter.updateImportoTotale(order, importoTotale, true);
  }

  public OrderStateConfig findOrderStateConfig(String code) {
    return adapter.findOrderStateConfig(code);
  }
  
  @Override
  public List<Order> findOrdersByIds(List<String> ids) {
    return adapter.findOrdersByIds(ids);
  }

}
