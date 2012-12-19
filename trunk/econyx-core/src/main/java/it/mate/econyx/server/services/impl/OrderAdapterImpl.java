package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.server.model.impl.ModalitaSpedizioneDs;
import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.model.impl.OrderItemDs;
import it.mate.econyx.server.model.impl.OrderStateConfigDs;
import it.mate.econyx.server.model.impl.OrderStateDs;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.model.impl.ProduttoreDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.MailAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ReportAdapter;
import it.mate.econyx.server.services.TemplatesAdapter;
import it.mate.econyx.server.tasks.OrderStateChangeDeferredTask;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.ModalitaSpedizioneTx;
import it.mate.econyx.shared.model.impl.OrderItemTx;
import it.mate.econyx.shared.model.impl.OrderStateConfigTx;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindCallback;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.dao.UpdateCallback;
import it.mate.gwtcommons.server.services.AdapterException;
import it.mate.gwtcommons.server.utils.BooleanUtils;
import it.mate.gwtcommons.server.utils.CacheUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@Service
public class OrderAdapterImpl implements OrderAdapter {

  private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

  @Autowired private Dao dao;
  
  @Autowired GeneralAdapter generalAdapter;
  
  @Autowired PortalUserAdapter portalUserAdapter;

  @Autowired MailAdapter mailAdapter;

  @Autowired TemplatesAdapter templatesAdapter;
  
  @Autowired ReportAdapter reportAdapter;
  
  @Autowired CustomAdapter customAdapter;

  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  public List<Order> findAll() {
    List<Order> orders = CloneUtils.clone(dao.findAll(OrderDs.class), OrderTx.class, Order.class);
    Collections.sort(orders, new Comparator<Order>() {
      public int compare(Order o1, Order o2) {
        return o2.getCode().compareTo(o1.getCode());
      }
    });
    return orders;
  }
  
  public Order findById (String id) {
    Order order = internalFindById(id, true);
    return castTx(order);
  }
  
  public Order findOpenOrderByCustomer(Customer customer) {
    return castTx(findOpenOrderByCustomerDs(customer));
  }

  private OrderDs findOpenOrderByCustomerDs(Customer customer) {
    
    /* 16/11/2012
    FindCallback<OrderDs> callback = new FindCallback<OrderDs>() {
      public void processResultsInTransaction(OrderDs order) {
        resolveOrderItems((Order) order);
        order.setItemsInitialized(true);
      }
    };
    */
    OrderDs orderDs;
    
//  .setCallback(callback)
    FindContext<OrderDs> context = new FindContext<OrderDs>(OrderDs.class)
        .setFilter(String.format("customerId == customerIdParam && currentStateCode == '%s'", OrderStateConfig.OPENED))
        .setParameters(Key.class.getName() + " customerIdParam")
        .setParamValues(new Object[] {KeyFactory.stringToKey(customer.getId())}) 
      ;
    context.includedField("itemKeys");
    orderDs = dao.findSingle(context);
    return orderDs;
  }
  
  public List<Order> findOrdersByCustomer(Customer customer) {
    FindCallback<OrderDs> callback = new FindCallback<OrderDs>() {
      public void processResultsInTransaction(OrderDs order) {
        resolveOrderItems((Order) order);
        order.setItemsInitialized(true);
      }
    };
    List<OrderDs> results = dao.findList(OrderDs.class, "customerId == customerIdParam", Key.class.getName() + " customerIdParam", callback, KeyFactory.stringToKey(customer.getId()));
    if (results != null && results.size() > 0) {
      for (Iterator<OrderDs> it = results.iterator(); it.hasNext();) {
        OrderDs orderDs = it.next();
        if (OrderStateConfig.OPENED.equals(orderDs.getCurrentStateCode())) {
          it.remove();
        }
      }
    }
    return CloneUtils.clone(results, OrderTx.class, Order.class);
  }
  
  public Order orderProduct(Order order, String openOrderId, Articolo product, Customer customer, Double quantity, List<OrderItemDetail> details) {

    // 14/11/2012
    // introdotto con la gestione UserOrderClientUtils
    // se arriva un order != null significa che l'ordine sta nella cache del client
    // e quindi non lo aggiorno sul datastore
    boolean updateDatastore = true;
    
    // 19/11/2012
    if (openOrderId != null) {
      order = castTx((Order)CacheUtils.get(openOrderId));
    }
    
    if (order != null) {
      updateDatastore = false;
    }
    
    if (order == null) {
      order = findOpenOrderByCustomer(customer);
    }
    if (order == null) {
      order = new OrderTx();
      String orderCode = "O" + generalAdapter.findNextCounterValue();
      order.setCode(orderCode);
      order.setCreated(new Date());
      order.setDescription("ORDER");
      order.setCustomer(customer);
      order = create(order);
    }
    boolean found = false;
    for (OrderItem item : order.getItems()) {
      if (item.getProduct().getCodice().equals(product.getCodice())) {
        found = true;
        item.setQuantity(item.getQuantity() + quantity);
        if (updateDatastore) {
          deleteDetails(item.getDetails());
        }
        item.setDetails(details);
      }
    }
    if (!found) {
      OrderItem item = null;
      item = new OrderItemTx();
      item.setOrder(order);
      item.setProduct(product);
      item.setQuantity(quantity);
      item.setDetails(details);
      order.addItem(item);
      if (product.getProducer() != null) {
        
        // 22/11/2012
        if (!PropertiesHolder.getBoolean("orderAdapter.allowOpenOrderMultiProducer", false)) {
          if (order.getProducer() != null && !(order.getProducer().getId().equals(product.getProducer().getId()))) {
            throw new ServiceException("Hai articoli di altri produttori nel carrello. Devi prima confermare quelli per ordinare articoli di questo produttore.");
          }
        }
        order.setProducer(product.getProducer());
      }
    }
    
    customAdapter.validateProductToOrder(order);
    
    updateImportoTotale(order, null, false);
    if (updateDatastore) {
      order = update(order);
    } else {
      CacheUtils.put(castDs(order));
    }
    return castTx(order);
  }
  
  public OrderItem updateOrderItem (OrderItem item) {
    OrderItemDs itemDs = castDs(item);
    
    //14/12/2012
    boolean importoTotaleDaRicalcolare = false;
    if (itemDs.getKey() != null) {
      OrderItemDs prevItemDs = dao.findById(OrderItemDs.class, itemDs.getKey());
      if (itemDs.getQuantity() != prevItemDs.getQuantity()) {
        // serve un ricalcolo dell'importo totale
        if (itemDs.getOrder() != null && itemDs.getOrder().getId() != null) {
          importoTotaleDaRicalcolare = true;
        }
      }
    }
    
    itemDs = createOrUpdateOrderItemDs(itemDs);
    
    if (importoTotaleDaRicalcolare) {
      OrderDs orderDs = internalFindById(itemDs.getOrder().getId(), true);
      List<OrderItem> items = orderDs.getItems();
      for (OrderItem nItem : items) {
        if (nItem.getProduct().getCodice().equals(itemDs.getProduct().getCodice())) {
          nItem.setQuantity(itemDs.getQuantity());
        }
      }
      orderDs.setItems(items);
      updateImportoTotale(orderDs, null, true);
    }
    
    // 19/11/2012
    if (itemDs.getOrder() != null && itemDs.getOrder().getId() != null) {
      Order cachedOrder = (Order)CacheUtils.get(itemDs.getOrder().getId());
      if (cachedOrder != null && cachedOrder.getItems() != null) {
        List<OrderItem> cachedItems = cachedOrder.getItems();
        for (int it = 0; it < cachedItems.size(); it++) {
          if (cachedItems.get(it).getProduct().getCodice().equals(itemDs.getProduct().getCodice())) {
            cachedItems.set(it, itemDs);
          }
        }
        cachedOrder.setItems(cachedItems);
        updateImportoTotale(cachedOrder, null, false);
        CacheUtils.put(cachedOrder);
      }
      
    }
    
    return castTx(itemDs);
  }
  
  public void closeOrder (String id) {
    // non faccio la fetch degli items
    final OrderDs detachedEntity = internalFindById(id, false);
    // 15/11/2012
//  final OrderDs detachedEntity = internalFindById(id, true);
    updateStatesWithInsertedState(detachedEntity);
//  update(order);
    dao.update(OrderDs.class, detachedEntity.getKey(), new UpdateCallback<OrderDs>() {
      public OrderDs updateEntityValues(PersistenceManager pm, OrderDs attachedEntity) {
        attachedEntity.setStates(detachedEntity.getStates());
        attachedEntity.setCurrentState(detachedEntity.getCurrentState());
        return attachedEntity;
      }
    });
  }
  
  public Order create(Order entity) {
    Order order = castDs(entity);
    order.setItems(createItems(order.getItems()));
    updateStatesWithInitialState(order);
    order = dao.create(order);
    return castTx(order);
  }
  
  public Order update(Order entity) {
    Order order = castDs(entity);
    order.setItems(updateItems(order.getItems()));
    updateStates(order, null);
    updateImportoTotale(order, null, false);
    order = dao.update(order);
    return castTx(order);
  }
  
  public void delete(Order entity) {
    Order order = castDs(entity);
    deleteItems(order.getItems());
    deleteStates(order.getStates());
    dao.delete(order);
  }
  
  private void updateStatesWithInsertedState(Order order) {
    updateStatesWithOrderStateConfig(order, getInsertedOrderStateConfig());
  }
  
  private void updateStatesWithInitialState(Order order) {
    updateStatesWithOrderStateConfig(order, getInitialOrderStateConfig());
  }
  
  private void updateStatesWithOrderStateConfig(Order order, OrderStateConfig orderStateConfig) {
    OrderState state = new OrderStateDs();
    state.setCode(orderStateConfig.getCode());
    state.setConfig(castDs(orderStateConfig));
    state.setDate(new Date());
    state.setChecked(true);
    state.setPortalUser(castDs(order).getCustomer().getPortalUser());
    updateStates(order, state);
  }
  
  private void updateStates(Order order, OrderState stateToUpdate) {
    OrderState previousState = null;
    List<OrderState> states = order.getStates();
    if (states == null) {
      states = new ArrayList<OrderState>();
    }
    if (states.size() == 0) {
      List<OrderStateConfig> allOrderStateConfigs = findAllOrderStates();
      for (OrderStateConfig orderStateConfig : allOrderStateConfigs) {
        OrderState state = new OrderStateDs();
        state.setCode(orderStateConfig.getCode());
        state.setConfig(castDs(orderStateConfig));
        state.setChecked(false);
        states.add(state);
      }
    }
    if (stateToUpdate != null) {
      boolean found = false;
      for (int it = 0; it < states.size(); it++) {
        OrderState state = states.get(it);
        if (state.getCode().equals(stateToUpdate.getCode())) {
          //mantengo l'id dello stato già salvato
          stateToUpdate.setId(state.getId());
          states.set(it, stateToUpdate);
          found = true;
        }
      }
      if (!found) {
        states.add(stateToUpdate);
      }
    }
    
    // confronto gli stati da salvare con gli stati già salvati precedentemente
    if (states != null && states.size() > 0) {
      
      List<OrderStateDs> oldStates = dao.findList(OrderStateDs.class, "orderId == orderIdParam", 
          Key.class.getName() + " orderIdParam", null, castDs(order).getKey());

      previousState = Order.Utils.getCurrentState(new ArrayList<OrderState>(oldStates));
      
      for (OrderState newState : states) {
        
        boolean stateChangedAsChecked = newState.getChecked();
        if (oldStates != null && oldStates.size() > 0) {
          for (OrderStateDs oldState : oldStates) {
            if (oldState.getCode().equals(newState.getCode())) {
              stateChangedAsChecked = false;
              if (oldState.getChecked().booleanValue() != newState.getChecked().booleanValue()) {
                if (newState.getChecked()) {
                  stateChangedAsChecked = true;
                }
                newState.setDate(new Date());
                PortalSessionState portalSessionState = PortalSessionStateServerUtils.getFromThread();
                if (portalSessionState != null) {
                  newState.setPortalUser(CloneUtils.clone(portalSessionState.getLoggedUser(), PortalUserDs.class));
                } else {
                  logger.error("PortalSessionState NON FOUND IN SESSION!");
                }
              }
              break;
            }
          }
        }
        if (stateChangedAsChecked) {
          if (OrderStateConfig.YES.equals(newState.getConfig().getEmailToCustomerSendType())) {
            // 15/11/2012
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder.withPayload(new OrderStateChangeDeferredTask(order.getId(), newState.getCode())));
          }
        }
      }
      
    }
    if (states != null) {
      for (int it = 0; it < states.size(); it++) {
        OrderStateDs stateDs = castDs(states.get(it));
        stateDs.setOrder(castDs(order));
        if (stateDs.getKey() == null) {
          stateDs = dao.create(stateDs);
        } else {
          stateDs = dao.update(stateDs);
        }
        states.set(it, stateDs);
      }
    }
    if (states == null || states.size() == 0) {
      logger.error("error", new AdapterException(String.format("Illegal State for order %s : states = NULL | zero size", order.getCode())));
    }
    
    order.setStates(states);
    OrderState currentState = order.getCurrentState();
    if (previousState == null || !currentState.getCode().equals(previousState.getCode())) {
      customAdapter.orderStateChanged(order);
    }
  }
  
  public void onOrderStateChange(String orderId, String orderStateCode) {
    Order order = findById(orderId);
    for (OrderState orderState : order.getStates()) {
      if (orderState.getCode().equals(orderStateCode)) {
        if (OrderStateConfig.YES.equals(orderState.getConfig().getEmailToCustomerSendType())) {
          try {
            Map<String, Object> templModel = new HashMap<String, Object>();
            templModel.put("order", order);
            String mailText = templatesAdapter.processTemplateToString(orderState.getCode()+"_orderStateTemplate", orderState.getConfig().getEmailContent(), templModel);
            byte[] attachment = null;
            if (BooleanUtils.isTrue(orderState.getConfig().getAttachOrderReport())) {
              attachment = reportAdapter.printOrder(order);
            }
            mailAdapter.sendOrderStateMail(order.getCustomer().getPortalUser(), mailText, "ordine"+order.getCode()+".pdf" , attachment);
          } catch (Exception ex) {
            logger.error("error", ex);
          }
        }
        break;
      }
    }
  }
  
  private void deleteStates(List<OrderState> states) {
    if (states != null) {
      for (OrderState state : states) {
        OrderStateDs stateDs = castDs(state);
        dao.delete(stateDs);
      }
    }
  }
  
  private OrderStateConfig getInitialOrderStateConfig() {
    List<OrderStateConfig> allOrderStateConfigs = findAllOrderStates();
    OrderStateConfig defaultOrderStateConfig = null;
    int firstOrderNm = Integer.MAX_VALUE;
    for (OrderStateConfig orderStateConfig : allOrderStateConfigs) {
      if (orderStateConfig.getOrderNm() < firstOrderNm) {
        defaultOrderStateConfig = orderStateConfig;
        firstOrderNm = orderStateConfig.getOrderNm();
      }
    }
    return defaultOrderStateConfig;
  }
  
  private OrderStateConfig getInsertedOrderStateConfig() {
    List<OrderStateConfig> allOrderStateConfigs = findAllOrderStates();
    for (OrderStateConfig orderStateConfig : allOrderStateConfigs) {
      if (OrderStateConfig.INSERTED.equals(orderStateConfig.getCode())) {
        return orderStateConfig;
      }
    }
    return null;
  }
  
  private List<OrderItem> createItems(List<OrderItem> items) {
    if (items != null) {
      for (int it = 0; it < items.size(); it++) {
        OrderItemDs itemDs = castDs(items.get(it));
        itemDs.setDetails(createDetails(itemDs.getDetails()));
        itemDs = dao.create(itemDs);
        items.set(it, itemDs);
      }
    }
    return items;
  }
  
  private void deleteItems(List<OrderItem> items) {
    if (items != null) {
      for (OrderItem item : items) {
        OrderItemDs itemDs = castDs(item);
        deleteDetails(itemDs.getDetails());
        dao.delete(itemDs);
      }
    }
  }

  private List<OrderItem> updateItems(List<OrderItem> items) {
    if (items != null) {
      for (int it = 0; it < items.size(); it++) {
        OrderItemDs itemDs = castDs(items.get(it));
        itemDs = createOrUpdateOrderItemDs(itemDs);
        items.set(it, itemDs);
      }
    }
    return items;
  }
  
  private OrderItemDs createOrUpdateOrderItemDs (OrderItemDs itemDs) {
    if (itemDs.getKey() == null) {
      itemDs.setDetails(createDetails(itemDs.getDetails()));
      itemDs = dao.create(itemDs);
    } else {
      itemDs.setDetails(updateDetails(itemDs.getDetails()));
      itemDs = dao.update(itemDs);
    }
    return itemDs;
  }
  
  private List<OrderItemDetail> createDetails(List<OrderItemDetail> details) {
    if (details != null) {
      for (int it = 0; it < details.size(); it++) {
        AbstractOrderItemDetailDs detailDs = (AbstractOrderItemDetailDs)CloneUtils.clone(details.get(it), AbstractOrderItemDetailDs.class);
        detailDs = dao.create(detailDs);
        details.set(it, detailDs);
      }
    }
    return details;
  }

  private void deleteDetails(List<OrderItemDetail> details) {
    if (details != null) {
      for (int it = 0; it < details.size(); it++) {
        AbstractOrderItemDetailDs detailDs = (AbstractOrderItemDetailDs)CloneUtils.clone(details.get(it), AbstractOrderItemDetailDs.class);
        dao.delete(detailDs);
      }
    }
  }

  private List<OrderItemDetail> updateDetails(List<OrderItemDetail> details) {
    if (details != null) {
      for (int it = 0; it < details.size(); it++) {
        AbstractOrderItemDetailDs detailDs = (AbstractOrderItemDetailDs)CloneUtils.clone(details.get(it), AbstractOrderItemDetailDs.class);
        if (detailDs.getKey() == null) {
          detailDs = dao.create(detailDs);
        } else {
          detailDs = dao.update(detailDs);
        }
        details.set(it, detailDs);
      }
    }
    return details;
  }
  
  public Order fetchItems(Order order) {
    return castTx(internalFindById(order.getId(), true));
  }

  private OrderDs internalFindById(String id, final boolean fetchItems) {

    // 16/11/2012
    FindContext<OrderDs> context = new FindContext<OrderDs>(OrderDs.class).setId(id);
    if (fetchItems) {
      context.includedField("itemKeys");
    }
    OrderDs order = dao.findById(context);
    if (fetchItems)
      order.setItemsInitialized(true);
    
    return order;
  }
  
  public Order updateImportoTotale(Order order, Double importoRettificato, boolean doUpdateDs) {
    Double importoTotale = importoRettificato == null ? Order.Utils.computeImportoTotale(order, true) : importoRettificato;
    order.setImportoTotale(importoTotale);
    if (doUpdateDs) {
      order = dao.update(castDs(order));
    }
    return castTx(order);
  }

  public List<OrderItem> findAllItems() {
    return CloneUtils.clone(dao.findAll(OrderItemDs.class), OrderItemTx.class, OrderItem.class);
  }

  public void resolveOrderItems(Order order) {
    it.mate.econyx.server.model.impl.OrderDs ds = (it.mate.econyx.server.model.impl.OrderDs) order;
    ds.resolveItems();
  }

  public List<ModalitaSpedizione> findAllModalitaSpedizione() {
    return CloneUtils.clone(dao.findAll(ModalitaSpedizioneDs.class), ModalitaSpedizioneTx.class, ModalitaSpedizione.class);
  }
  
  public void delete (ModalitaSpedizione entity) {
    dao.delete(CloneUtils.clone(entity, ModalitaSpedizioneDs.class));
  }

  public ModalitaSpedizione create (ModalitaSpedizione entity) {
    return CloneUtils.clone(dao.create(CloneUtils.clone(entity, ModalitaSpedizioneDs.class)), ModalitaSpedizioneTx.class);
  }
  
  
  public List<OrderStateConfig> findAllOrderStates() {
    List<OrderStateConfigDs> allStates = dao.findAll(OrderStateConfigDs.class);
    Collections.sort(allStates, new Comparator<OrderStateConfigDs>() {
      public int compare(OrderStateConfigDs s1, OrderStateConfigDs s2) {
        return s1.getOrderNm().compareTo(s2.getOrderNm());
      }
    });
    return CloneUtils.clone(allStates, OrderStateConfigTx.class, OrderStateConfig.class);
  }
  
  public OrderStateConfig create(OrderStateConfig entity) {
    OrderStateConfig orderStateDs = castDs(entity);
    orderStateDs = dao.create(orderStateDs);
    return castTx(orderStateDs);
  }
  
  public void delete (OrderStateConfig entity) {
    dao.delete(castDs(entity));
  }
  
  private OrderDs castDs (Order order) {
    return CloneUtils.clone(order, OrderDs.class);
  }
  
  private OrderTx castTx (Order order) {
    return CloneUtils.clone(order, OrderTx.class);
  }
  
  private OrderItemDs castDs(OrderItem item) {
    return CloneUtils.clone(item, OrderItemDs.class);
  }
  
  private OrderItemTx castTx(OrderItem item) {
    return CloneUtils.clone(item, OrderItemTx.class);
  }
  
  private OrderStateDs castDs(OrderState state) {
    return CloneUtils.clone(state, OrderStateDs.class);
  }
  
  private OrderStateConfigDs castDs(OrderStateConfig config) {
    return CloneUtils.clone(config, OrderStateConfigDs.class);
  }
  
  private OrderStateConfigTx castTx(OrderStateConfig config) {
    return CloneUtils.clone(config, OrderStateConfigTx.class);
  }
  
  @Override
  public List<OrderDs> findOrdersByProducer(ProduttoreDs produttore, String currentStateCode) {
    FindContext<OrderDs> context = new FindContext<OrderDs>(OrderDs.class)
        .setFilter(String.format("producerKey == producerKeyParam && currentStateCode == '%s'", currentStateCode))
        .setParameters(Key.class.getName() + " producerKeyParam")
        .setParamValues(new Object[] {KeyFactory.stringToKey(produttore.getId())}) 
      ;
    List<OrderDs> orders = dao.findList(context);
    return orders;
  }
  
  @Override
  public List<OrderDs> findOrdersByState(String currentStateCode) {
    FindContext<OrderDs> context = new FindContext<OrderDs>(OrderDs.class)
        .setFilter(String.format("currentStateCode == '%s'", currentStateCode))
      ;
    List<OrderDs> orders = dao.findList(context);
    return orders;
  }
  
}
