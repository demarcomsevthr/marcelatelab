package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomerServiceAsync {

  void findAll(AsyncCallback<List<Customer>> callback);

  void create(Customer entity, AsyncCallback<Customer> callback);

  void delete(Customer entity, AsyncCallback<Void> callback);

  void update(Customer entity, AsyncCallback<Customer> callback);

  void register(Customer entity, AsyncCallback<Customer> callback);

  void findByPortalUser(PortalUser portalUser, AsyncCallback<Customer> callback);
  
}
