package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".customerService")
public interface CustomerService extends RemoteService {

  public List<Customer> findAll() throws ServiceException;

  public Customer update(Customer entity) throws ServiceException;

  public void delete(Customer entity) throws ServiceException;

  public Customer create(Customer entity) throws ServiceException;

  public Customer register(Customer entity) throws ServiceException;
  
  public Customer findByPortalUser(PortalUser portalUser) throws ServiceException;
  
}
