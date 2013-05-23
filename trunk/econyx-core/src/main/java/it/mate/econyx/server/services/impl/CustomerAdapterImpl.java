package it.mate.econyx.server.services.impl;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.econyx.server.model.impl.CustomerDs;
import it.mate.econyx.server.model.impl.IndirizzoFatturazioneDs;
import it.mate.econyx.server.model.impl.IndirizzoSpedizioneDs;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.CustomerTx;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Service
public class CustomerAdapterImpl implements CustomerAdapter {

  private static Logger logger = Logger.getLogger(CustomerAdapterImpl.class);

  @Autowired private Dao dao;
  
  @Autowired private PortalUserAdapter portalUserAdapter;
  
  @Autowired CustomAdapter customAdapter;

  private boolean disableCreateCustomerCustomAdapter = false;
  
  public void setDisableCreateCustomerCustomAdapter(boolean disableCreateCustomerCustomAdapter) {
    this.disableCreateCustomerCustomAdapter = disableCreateCustomerCustomAdapter;
  }

  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  public List<Customer> findAll() {
    return CloneUtils.clone(dao.findAll(CustomerDs.class), CustomerTx.class, Customer.class);
  }
  
  public Customer findByPortalUser(PortalUser portalUser) {
    portalUser = CloneUtils.clone(portalUser, PortalUserDs.class);
    CustomerDs clienteDs = dao.findSingle(CustomerDs.class, "portalUserId == portalUserIdParam", Key.class.getName() + " portalUserIdParam", null, KeyFactory.stringToKey(portalUser.getId()));
    return CloneUtils.clone(clienteDs, CustomerTx.class);
  }

  public Customer update(Customer entity) {
    Customer customerDs = CloneUtils.clone(entity, CustomerDs.class);
    if (customerDs.getIndirizzoSpedizione() != null)
      customerDs.setIndirizzoSpedizione(dao.update(customerDs.getIndirizzoSpedizione()));
    if (customerDs.getIndirizzoFatturazione() != null)
      customerDs.setIndirizzoFatturazione(dao.update(customerDs.getIndirizzoFatturazione()));
    return CloneUtils.clone(dao.update(customerDs), CustomerTx.class);
  }

  public void delete(Customer entity) {
    Customer customer = CloneUtils.clone(entity, CustomerDs.class);
    customAdapter.onDeleteCustomer(customer);
    if (customer.getIndirizzoSpedizione() != null)
      dao.delete(CloneUtils.clone(customer.getIndirizzoSpedizione(), IndirizzoSpedizioneDs.class));
    if (customer.getIndirizzoFatturazione() != null)
      dao.delete(CloneUtils.clone(customer.getIndirizzoFatturazione(), IndirizzoFatturazioneDs.class));
    dao.delete(customer);
  }
  
  public Customer create(Customer entity) {
    return create(entity, null);
  }

  public Customer create(Customer entity, Date date) {
    Customer customerDs = CloneUtils.clone(entity, CustomerDs.class);
    if (customerDs.getPortalUser().getId() == null) {
      customerDs.setPortalUser(CloneUtils.clone(portalUserAdapter.create(customerDs.getPortalUser()), PortalUserDs.class));
    }
    customerDs.setIndirizzoSpedizione(dao.create(CloneUtils.clone(customerDs.getIndirizzoSpedizione(), IndirizzoSpedizioneDs.class)));
    customerDs.setIndirizzoFatturazione(dao.create(CloneUtils.clone(customerDs.getIndirizzoFatturazione(), IndirizzoFatturazioneDs.class)));
    customerDs = dao.create(customerDs);
    if (!disableCreateCustomerCustomAdapter) {
      customAdapter.onCreateCustomer(customerDs, date);
    }
    return CloneUtils.clone(customerDs, CustomerTx.class);
  }
  
  @Override
  public Customer register(Customer entity) {
    Customer cliente = create(entity);
    return cliente;
  }
  
}
