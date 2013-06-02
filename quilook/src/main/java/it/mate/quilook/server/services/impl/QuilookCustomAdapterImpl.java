package it.mate.quilook.server.services.impl;

import it.mate.commons.server.utils.PdfSession;
import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Blob;

@Service
public class QuilookCustomAdapterImpl implements CustomAdapter {

  @Override
  public String uploadImage(String imageType, String imageSize, String objId, Blob imageBlob, HttpSession session) {
    return null;
  }

  @Override
  public void printOrder(PdfSession pdfSession, Order order) {
    
  }

  @Override
  public void printOrdersToProducer(PdfSession pdfSession, Produttore producer, List<Order> orders) {
    
  }

  @Override
  public void orderStateChanged(Order order) {
    
  }

  @Override
  public void onDeleteCustomer(Customer customer) {
    
  }

  @Override
  public Double getSaldoByPortalUser(String portalUserId) {
    return null;
  }

  @Override
  public void onCreateCustomer(Customer customer, Date date) {
    
  }

  @Override
  public AbstractOrderItemDetailDs findOrderItemDetailDs(String id, HttpSession session) {
    return null;
  }

  @Override
  public void validateProductToOrder(Order order) throws ServiceException {
    
  }

  @Override
  public byte[] exportPortalUsersToExcel() {
    return null;
  }

  @Override
  public void unloadExtraData(PortalDataExportModel model) {
    
  }

  @Override
  public void deleteExtraData() {
    
  }

  @Override
  public void loadExtraData(PortalDataExportModel model) {
    
  }
  
}
