package it.mate.econyx.server.services;

import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.server.utils.PdfSession;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Blob;

public interface CustomAdapter {

  public String uploadImage(String imageType, String imageSize, String objId, Blob imageBlob, HttpSession session);
  
  public void printOrder (PdfSession pdfSession, Order order);
  
  public void printOrdersToProducer (PdfSession pdfSession, Produttore producer, List<Order> orders);
  
  public void orderStateChanged(Order order);
  
  public void onDeleteCustomer(Customer customer);
  
  public Double getSaldoByPortalUser(String portalUserId);
  
  public void onCreateCustomer(Customer customer);

  public AbstractOrderItemDetailDs findOrderItemDetailDs(String id, HttpSession session);
  
  public void validateProductToOrder(Order order) throws ServiceException;
  
  public byte[] exportPortalUsersToExcel();
  
}
