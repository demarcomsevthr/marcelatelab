package it.mate.copymob.server.services;

import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.DevInfo;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.Timbro;

import java.util.Date;
import java.util.List;


public interface MainAdapter {
  
  public void scheduledChecks();
  
  public DevInfo sendDevInfo(DevInfo devInfo);  

  public Account saveAccount(Account tx);
  
  public List<Timbro> getTimbri() throws Exception;
  
  public Order saveOrder(Order order);
  
  public List<Order> findAllOrders() throws Exception;
  
  public void uploadOrderItemPreview(String orderItemId, byte[] previewImage) throws Exception;
  
  public Order findOrderById(String id) throws Exception;  
  
  public List<Order> findOrdersByAccount(String accountId, Date lastUpdate) throws Exception;
  
  public List<Account> findAllAccounts() throws Exception;
  
  public void sendPushNotification(Account account, String message, String regId) throws Exception;
  
  public List<Order> findUpdatedOrdersByAccount(String accountId) throws Exception;
  
}
