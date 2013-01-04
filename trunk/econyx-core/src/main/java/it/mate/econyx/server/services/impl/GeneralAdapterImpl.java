package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.Counter;
import it.mate.econyx.server.model.impl.CounterDs;
import it.mate.econyx.server.model.impl.CustomerDs;
import it.mate.econyx.server.model.impl.IndirizzoSpedizioneDs;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.DataNotFoundException;
import it.mate.gwtcommons.server.dao.FindCallback;
import it.mate.gwtcommons.server.dao.JdoDao;
import it.mate.gwtcommons.server.dao.JdoDaoWithCache;
import it.mate.gwtcommons.server.dao.UpdateCallback;
import it.mate.gwtcommons.server.utils.CacheUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;

@Service
public class GeneralAdapterImpl implements GeneralAdapter {
  
  private static Logger logger = Logger.getLogger(GeneralAdapterImpl.class);
  
  private static final long INITIAL_COUNTER = 1000; 

  /*
  private static final String PORTAL_SESSION_STATE_KEY = "PORTAL_SESSION_STATE";
  */
  
  @Autowired private Dao dao;
  
  @Autowired private PortalUserAdapter portalUserAdapter;
  
  @Autowired private CustomerAdapter customerAdapter;
  
  @Autowired private PortalPageAdapter portalPageAdapter;
  
  @Autowired private ProductAdapter productAdapter;
  
  @Autowired private OrderAdapter orderAdapter;
  
  @Autowired private ImageAdapter imageAdapter;
  
  private Key counterId = null;
  
  @PostConstruct
  public void init() {
    logger.debug("initialized " + this);
  }
  
  private Class<it.mate.econyx.server.model.impl.CounterDs> getCounterDsClass () {
    return it.mate.econyx.server.model.impl.CounterDs.class;
  }
  
  private Counter newCounterDsInstance () {
    return new it.mate.econyx.server.model.impl.CounterDs();
  }
  
  private Counter findCounter() {
    logger.debug("finding counter...");
    Counter counter = null;
    List<? extends Counter> results;
    results = dao.findAll(getCounterDsClass(), new FindCallback<CounterDs>() {
      public void processResultsInTransaction(CounterDs counter) {
        counter.getValue();
      }
    });
    if (results != null && results.size() == 1) {
      logger.debug("found counter");
      counter = results.get(0);
    }
    return counter;
  }
  

  private Counter findOrCreateCounter() {
    Counter counter = null;
    if (this.counterId != null) {
      try {
        counter = (Counter)dao.findById(CounterDs.class, this.counterId, new FindCallback<CounterDs>() {
          public void processResultsInTransaction(CounterDs counter) {
            counter.getValue();
          }
        });
      } catch (DataNotFoundException ex) {
        this.counterId = null;
      }
    }
    if (counter == null) {
      counter = findCounter();
    }
    if (counter == null) {
      logger.debug("counter not found, creating new one...");
      counter = newCounterDsInstance();
      counter.setValue(INITIAL_COUNTER);
      counter = dao.create(counter);
    }
    if (counter != null) {
      this.counterId = counter.getKey();
    }
    return counter;
  }

  @Override
  public void deleteCounter () {
    Counter counter = findCounter();
    if (counter != null) {
      dao.delete(counter);
    }
  }
  
  @Override
  public long findNextCounterValue () {
    Counter counter = findOrCreateCounter();
    logger.debug("retrieved counter is " + counter);
    final long value = counter.getValue();
    logger.debug("before update counter is " + counter);
    counter = dao.update(getCounterDsClass(), counter.getKey(), new UpdateCallback<CounterDs>() {
      public CounterDs updateEntityValues(PersistenceManager pm, CounterDs counter) {
        counter.setValue(value + 1);
        return counter;
      }
    });
    logger.debug("after update counter is " + counter);
    return counter.getValue();
  }
  
  public void clearCache() {
    if (dao instanceof JdoDaoWithCache) {
      JdoDaoWithCache daoWithCache = (JdoDaoWithCache)dao;
      daoWithCache.clearCache();
    }
  }
  
  @Override
  public void storePortalSessionState(HttpServletRequest request, PortalSessionState state) {
    PortalSessionStateServerUtils.setInSession(request, state);
  }

  @Override
  public PortalSessionState retrievePortalSessionState(HttpServletRequest request) {
    return PortalSessionStateServerUtils.getFromSession(request);
  }
  
  public void deleteAll () {
    try {
      // serve fare due clear delle cache, prima e dopo
      CacheUtils.clearAll();
      JdoDao.setSuppressExceptionThrowOnInternalFind(true);
      deleteOrders();
      deletePages();
      deleteCustomers();
      deleteUsers();
      deleteImages();
      deleteProducts();
      CacheUtils.clearAll();

      // 04/01/2013
      // workaround: una sola passata lascia dati sporchi >> DA APPROFONDIRE!
      List<PortalPage> pages = portalPageAdapter.findAll();
      if (pages != null && pages.size() > 0) {
        deleteAll();
      }
      
    } finally {
      JdoDao.setSuppressExceptionThrowOnInternalFind(false);
    }
  }
  
  private void deleteUsers() {
    List<PortalUser> users = portalUserAdapter.findAll();
    for (PortalUser user : users) {
      portalUserAdapter.delete(user);
    }
  }
  
  private void deleteCustomers() {
    List<Customer> customers = customerAdapter.findAll();
    for (Customer customer : customers) {
      customerAdapter.delete(customer);
    }
  }
  
  private void deleteImages() {
    List<Image> images = imageAdapter.findAll();
    for (Image image : images) {
      imageAdapter.delete(image);
    }
  }
  
  private void deleteProducts() {
    List<Articolo> products = productAdapter.findAll();
    for (Articolo product : products) {
      productAdapter.delete(product);
    }
    List<TipoArticolo> productTypes = productAdapter.findAllProductTypes();
    for (TipoArticolo type : productTypes) {
      productAdapter.delete(type);
    }
    List<UnitaDiMisura> unitOfMeasures = productAdapter.findAllUnitOfMeasures();
    for (UnitaDiMisura unitOfMeasure : unitOfMeasures) {
      productAdapter.delete(unitOfMeasure);
    }
    List<Produttore> producers = productAdapter.findAllProducers();
    for (Produttore producer : producers) {
      productAdapter.delete(producer);
    }
  }
  
  private void deletePages() {
    List<PortalPage> pages = portalPageAdapter.findAll();
    for (PortalPage page : pages) {
      portalPageAdapter.delete(page);
    }
  }
  
  private void deleteOrders() {
    List<Order> orders = orderAdapter.findAll();
    for (Order order : orders) {
      orderAdapter.delete(order);
    }
    List<ModalitaSpedizione> modalitaSpediziones = orderAdapter.findAllModalitaSpedizione();
    for (ModalitaSpedizione mod : modalitaSpediziones) {
      orderAdapter.delete(mod);
    }
    List<OrderStateConfig> orderStates = orderAdapter.findAllOrderStates();
    for (OrderStateConfig orderState : orderStates) {
      orderAdapter.delete(orderState);
    }
  }

  String[] cognomi = {"Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci", "Marino", "Greco"};
  String[] nomi = {"Rodrigo", "Salvatore", "Oronzo", "Vittorio", "Ramiro", "Giancarlo", "Lara", "Pamela", "Pancrazio"};
  
  public void generateRandomCustomers(int number) {
    List<String> nominativi = new ArrayList<String>();
    int it = 0;
    while (it < number) {
      String cognome = cognomi[(int) (Math.random() * cognomi.length)];
      String nome = nomi[(int) (Math.random() * nomi.length)];
      if (nominativi.contains(cognome+nome)) {
        continue;
      }
      nominativi.add(cognome+nome);
      CustomerDs customer = new CustomerDs();
      IndirizzoSpedizioneDs indirizzoSpedizione = new IndirizzoSpedizioneDs();
      indirizzoSpedizione.setCognome(cognome);
      indirizzoSpedizione.setNome(nome);
      customer.setIndirizzoSpedizione(indirizzoSpedizione);
      PortalUserDs portalUser = new PortalUserDs();
      portalUser.setScreenName(cognome+" "+nome);
      portalUser.setEmailAddress(cognome.toLowerCase()+"."+nome.substring(0, 1).toLowerCase()+"@gmail.com");
      portalUser.setPassword("123");
      portalUser.setActive(true);
      customer.setPortalUser(CloneUtils.clone(portalUserAdapter.create(portalUser, true), PortalUserDs.class));
      customerAdapter.create(customer);
      it++;
    }
  }
  
  
  public void generateRandomOrders(int number) {
    List<Customer> customers = customerAdapter.findAll();
    List<Articolo> products = productAdapter.findAll();
    int it = 0;
    
    Customer customer = customers.get((int) (Math.random() * customers.size()));
    
    while (it < number) {
      
      String openOrderId = null;
      Order openOrder = orderAdapter.findOpenOrderByCustomer(customer);
      if (openOrder != null) {
        openOrderId = openOrder.getId();
        if (openOrder.getItems() == null || openOrder.getItems().size() == 0) {
          openOrder = null;
          openOrderId = null;
        } else {
          
          List<Articolo> articoliProduttore = new ArrayList<Articolo>();
          for (Articolo articolo : products) {
            if (articolo.getProducer().getCodice().equals( openOrder.getProducer().getCodice())) {
              articoliProduttore.add(articolo);
            }
          }
          
          int numeroArticoliDaOrdinare = (int)(Math.random() * articoliProduttore.size());
          
          try {
            while (numeroArticoliDaOrdinare > 0) {
              Articolo articoloDaOrdinare = articoliProduttore.get((int)(Math.random() * articoliProduttore.size()));
              openOrder = orderAdapter.orderProduct(null, openOrderId, articoloDaOrdinare, customer, 1d, null);
              numeroArticoliDaOrdinare --;
            }
            orderAdapter.closeOrder(openOrder.getId());
          } catch (ServiceException ex) {
            logger.error("error: " + ex.getMessage());
          }
          
          it ++;
          customers.remove(customer);
          if (customers.size() == 0)
            break;
          customer = customers.get((int) (Math.random() * customers.size()));

          /*
          for (Articolo articolo : products) {
            if (articolo.getProducer().getCodice().equals( openOrder.getProducer().getCodice())) {
              if ( !(articolo.getCodice().equals( openOrder.getItems().get(0).getProduct().getCodice() ))) {
                articoloDaOrdinare = articolo;
                break;
              }
            }
          }
          */
          
        }
      }
      
      if (openOrder == null) {
        Articolo articoloDaOrdinare = products.get((int) (Math.random() * products.size()));
        try {
          openOrder = orderAdapter.orderProduct(null, openOrderId, articoloDaOrdinare, customer, 1d, null);
        } catch (ServiceException ex) {
          logger.error("error: " + ex.getMessage());
          it ++;
          customers.remove(customer);
          if (customers.size() == 0)
            break;
          customer = customers.get((int) (Math.random() * customers.size()));
        }
      }
      
      /*
      if (articoloDaOrdinare == null) {
        articoloDaOrdinare = products.get((int) (Math.random() * products.size()));
      }
      openOrder = orderAdapter.orderProduct(null, openOrderId, articoloDaOrdinare, customer, 1d, null);
      orderAdapter.closeOrder(openOrder.getId());
      */
    }
  }
  
}
