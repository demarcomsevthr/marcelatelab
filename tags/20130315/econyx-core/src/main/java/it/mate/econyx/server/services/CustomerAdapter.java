package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;

import java.util.Date;
import java.util.List;

public interface CustomerAdapter {
  
  public List<Customer> findAll();

  public Customer update(Customer entity);

  public void delete(Customer entity);

  public Customer create(Customer entity);
  
  public Customer create(Customer entity, Date date);
  
  public Customer register(Customer entity);
  
  public Customer findByPortalUser(PortalUser portalUser);

}
