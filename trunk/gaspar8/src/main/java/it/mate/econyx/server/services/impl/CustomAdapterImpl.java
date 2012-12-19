package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.server.model.impl.ContoUtenteDs;
import it.mate.econyx.server.model.impl.ContoUtenteMovimentoDs;
import it.mate.econyx.server.model.impl.CustomerDs;
import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.impl.ContoUtenteTx;
import it.mate.econyx.shared.util.FontTypes;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.server.utils.DateUtils;
import it.mate.gwtcommons.server.utils.KeyUtils;
import it.mate.gwtcommons.server.utils.PdfSession;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.itextpdf.text.pdf.PdfPTable;

@Service
public class CustomAdapterImpl implements CustomAdapter {
  
  private static Logger logger = Logger.getLogger(CustomAdapterImpl.class);

  @Autowired CustomerAdapter customerAdapter;
  
  @Autowired OrderAdapter orderAdapter;
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void onPostConstruct() {
    logger.debug("initialized " + this);
  }
  
  public String uploadImage(String imageType, String imageSize, String objId, Blob imageBlob, HttpSession session) {
    return null;
  }

  
  public void printOrder(PdfSession pdfSession, Order order) {
    
    pdfSession.addParaghraph();
    pdfSession.addParaghraph("STAMPA ORDINE N. " + order.getCode(), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addParaghraph("DATA ORDINE: " + DateUtils.dateToString(order.getCreated()), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addParaghraph("SOCIO: " + order.getCustomer().getPortalUser().getScreenName(), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addParaghraph("PRODUTTORE: " + order.getProducer().getNome(), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addNewLine();
    pdfSession.addParaghraph("        ELENCO ARTICOLI ORDINATI", FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addNewLine();
    
    PdfPTable itemTable = new PdfPTable(5);
    
    itemTable.addCell(pdfSession.createCell("Articolo", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("UdM", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Qta", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Prezzo", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Importo", FontTypes.ARIAL_BOLD, 10f));
    
    List<OrderItem> items = order.getItems();
    for (int it = 0; it < items.size(); it++) {
      OrderItem orderItem = items.get(it);
      itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getName(), FontTypes.ARIAL, 10f));
      itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getUnitaDiMisura().getNome(), FontTypes.ARIAL, 10f));
      itemTable.addCell(pdfSession.createCell(""+orderItem.getQuantity(), FontTypes.ARIAL, 10f));
      itemTable.addCell(pdfSession.createCell(""+orderItem.getProduct().getPrezzo(), FontTypes.ARIAL, 10f));
      itemTable.addCell(pdfSession.createCell(""+orderItem.getQuantity() * orderItem.getProduct().getPrezzo(), FontTypes.ARIAL, 10f));
    }
    
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell("TOTALE", FontTypes.ARIAL_BOLD, 13f));
    itemTable.addCell(pdfSession.createCell("" + Order.Utils.computeImportoTotale(order), FontTypes.ARIAL_BOLD, 13f));
    
    pdfSession.addTable(itemTable);
  }
  
  
  public void printOrdersToProducer (PdfSession pdfSession, Produttore producer, List<Order> orders) {
    
    Collections.sort(orders, new Comparator<Order>() {
      public int compare(Order o1, Order o2) {
        return o1.getCustomer().getPortalUser().getScreenName().compareTo(o2.getCustomer().getPortalUser().getScreenName());
      }
    });
    
    pdfSession.addParaghraph();
    pdfSession.addParaghraph("STAMPA DISTINTA AL PRODUTTORE DEL " + DateUtils.dateToString(new Date()), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addParaghraph("PRODUTTORE: " + producer.getNome(), FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addNewLine();
    pdfSession.addParaghraph("        ELENCO ARTICOLI ORDINATI", FontTypes.ARIAL_BOLD, 12f);
    pdfSession.addNewLine();
    
    PdfPTable itemTable = new PdfPTable(7);
    
    itemTable.addCell(pdfSession.createCell("Socio", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Ordine", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Articolo", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("UdM", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Qta", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Prezzo", FontTypes.ARIAL_BOLD, 10f));
    itemTable.addCell(pdfSession.createCell("Importo", FontTypes.ARIAL_BOLD, 10f));

    double importoTotaleFornitore = 0d;
    
    for (Order order : orders) {
      List<OrderItem> items = order.getItems();
      String customerName = order.getCustomer().getPortalUser().getScreenName();
      String orderCode = order.getCode();
      for (OrderItem orderItem : items) {
        itemTable.addCell(pdfSession.createCell(customerName, FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(orderCode, FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getName(), FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getUnitaDiMisura().getNome(), FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(""+orderItem.getQuantity(), FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(""+orderItem.getProduct().getPrezzo(), FontTypes.ARIAL, 10f));
        itemTable.addCell(pdfSession.createCell(""+orderItem.getQuantity() * orderItem.getProduct().getPrezzo(), FontTypes.ARIAL, 10f));
        customerName = "";
        orderCode = "";
      }
      
      importoTotaleFornitore += Order.Utils.computeImportoTotale(order);
      
    }
    
    itemTable.addCell(pdfSession.createCell("TOTALI"));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell("" + importoTotaleFornitore, FontTypes.ARIAL_BOLD, 13f));
    
    pdfSession.addTable(itemTable);
  }


  public ContoUtente findContoUtenteByPortalUser(String portalUserId) {
    PortalUser portalUser = dao.findById(new FindContext<PortalUserDs>(PortalUserDs.class).setId(portalUserId));
    Customer customer = customerAdapter.findByPortalUser(portalUser);
    if (customer != null) {
      ContoUtente contoUtente = findContoUtenteByCustomer(customer, true);
      return CloneUtils.clone(contoUtente, ContoUtenteTx.class);
    }
    return null;
  }
  
  public Double getSaldoByPortalUser(String portalUserId) {
    PortalUser portalUser = dao.findById(new FindContext<PortalUserDs>(PortalUserDs.class).setId(portalUserId));
    Customer customer = customerAdapter.findByPortalUser(portalUser);
    if (customer != null) {
      ContoUtente contoUtente = findContoUtenteByCustomer(customer, false);
      if (contoUtente != null)
        return contoUtente.getSaldo();
    }
    return null;
  }
  
  private ContoUtente findContoUtenteByCustomer(Customer customer, boolean createIfNotFound) {
    ContoUtente contoUtente = dao.findSingle(new FindContext<ContoUtenteDs>(ContoUtenteDs.class)
        .setFilter("customerId == customerIdParam")
        .setParameters(Key.class.getName() + " customerIdParam")
        .setParamValues(new Object[] {KeyUtils.serializableToKey(customer.getId())})
        .includedField("movimentiKeys")
      );
    if (contoUtente == null && createIfNotFound) {
      contoUtente = new ContoUtenteDs();
      contoUtente.setCustomer(CloneUtils.clone(customer, CustomerDs.class));
      contoUtente.setSaldo(0d);
      contoUtente = dao.create(contoUtente);
    }
    return contoUtente;
  }
  
  public ContoUtente updateContoUtente(ContoUtente contoUtente) {
    contoUtente = CloneUtils.clone(contoUtente, ContoUtenteDs.class);
    List<ContoUtenteMovimento> movimenti = contoUtente.getMovimenti();
    double saldo = 0d;
    if (movimenti != null) {
      for (int it = 0; it < movimenti.size(); it++) {
        ContoUtenteMovimento movimento = movimenti.get(it);
        movimento = dao.update(movimento);
        movimenti.set(it, movimento);
        if (ContoUtenteMovimento.POSITIVO.equals(movimento.getSegno())) {
          saldo += movimento.getImporto();
        } else {
          saldo -= movimento.getImporto();
        }
      }
      contoUtente.setMovimenti(movimenti);
    }
    contoUtente.setSaldo(saldo);
    contoUtente = dao.update(contoUtente);
    return CloneUtils.clone(contoUtente, ContoUtenteTx.class);
  }

  @Override
  public void orderStateChanged(Order order) {
    
    if (order.getCurrentState().getCode().equals(OrderStateConfig.SHIPPED)) {
      
      ContoUtente contoUtente = findContoUtenteByCustomer(order.getCustomer(), true);
      
      ContoUtenteMovimento movimento = new ContoUtenteMovimentoDs();
      
      if (order.getItems() == null) {
        order = CloneUtils.clone(orderAdapter.findById(order.getId()), OrderDs.class);
      }
      
      movimento.setImporto(Order.Utils.computeImportoTotale(order));
      movimento.setSegno(ContoUtenteMovimento.NEGATIVO);
      movimento.setData(new Date());
      movimento.setCausale("CONSEGNA ORDINE " + order.getCode());
      movimento.setOrder(order);
      PortalSessionState portalSessionState = PortalSessionStateServerUtils.getFromThread();
      if (portalSessionState != null) {
        movimento.setRegisteringPortalUser(CloneUtils.clone(portalSessionState.getLoggedUser(), PortalUserDs.class));
      }
      
      List<ContoUtenteMovimento> movimenti = contoUtente.getMovimenti();
      if (movimenti == null) {
        movimenti = new ArrayList<ContoUtenteMovimento>();
      }
      movimenti.add(movimento);
      contoUtente.setMovimenti(movimenti);
      
      updateContoUtente(contoUtente);
      
    }
    
  }

  @Override
  public void onDeleteCustomer(Customer customer) {
    ContoUtente contoUtente = findContoUtenteByCustomer(customer, false);
    if (contoUtente != null) {
      for (ContoUtenteMovimento movimento : contoUtente.getMovimenti()) {
        dao.delete(CloneUtils.clone(movimento, ContoUtenteMovimentoDs.class));
      }
      dao.delete(CloneUtils.clone(contoUtente, ContoUtenteDs.class));
    }
  }

  @Override
  public void onCreateCustomer(Customer customer) {
    ContoUtente contoUtente = findContoUtenteByCustomer(customer, true);
    int saldoIniziale = PropertiesHolder.getInt("customAdapter.debug.saldoIniziale", 0);
    if (saldoIniziale > 0) {
      
      ContoUtenteMovimento movimento = new ContoUtenteMovimentoDs();
      movimento.setImporto((double)saldoIniziale);
      movimento.setSegno(ContoUtenteMovimento.POSITIVO);
      movimento.setData(new Date());
      movimento.setCausale("SALDO DI TEST GENERATO AUTOMATICAMENTE");
      PortalSessionState portalSessionState = PortalSessionStateServerUtils.getFromThread();
      if (portalSessionState != null) {
        movimento.setRegisteringPortalUser(CloneUtils.clone(portalSessionState.getLoggedUser(), PortalUserDs.class));
      }
      List<ContoUtenteMovimento> movimenti = contoUtente.getMovimenti();
      if (movimenti == null) {
        movimenti = new ArrayList<ContoUtenteMovimento>();
      }
      movimenti.add(movimento);
      contoUtente.setMovimenti(movimenti);
      updateContoUtente(contoUtente);
      
    }
  }


  @Override
  public AbstractOrderItemDetailDs findOrderItemDetailDs(String id, HttpSession session) {
    return null;
  }

  @Override
  public void validateProductToOrder(Order order) throws ServiceException {
    Double importoPrevisto = Order.Utils.computeImportoTotale(order, true);
    ContoUtente contoUtente = findContoUtenteByCustomer(order.getCustomer(), false);
    if (contoUtente == null || contoUtente.getSaldo() < importoPrevisto) {
      throw new ServiceException("Non hai credito sufficiente per ordinare questo prodotto.");
    }
  }

}
