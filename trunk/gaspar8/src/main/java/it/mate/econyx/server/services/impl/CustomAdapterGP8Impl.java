package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.server.model.impl.ContoUtenteDs;
import it.mate.econyx.server.model.impl.ContoUtenteMovimentoDs;
import it.mate.econyx.server.model.impl.CustomerDs;
import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
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
import it.mate.econyx.shared.model.impl.ContoUtenteMovimentoTx;
import it.mate.econyx.shared.model.impl.ContoUtenteTx;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.econyx.shared.util.FontTypes;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.server.utils.DateUtils;
import it.mate.gwtcommons.server.utils.KeyUtils;
import it.mate.gwtcommons.server.utils.PdfSession;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.itextpdf.text.pdf.PdfPTable;

@Service
public class CustomAdapterGP8Impl implements CustomAdapter {
  
  private static Logger logger = Logger.getLogger(CustomAdapterGP8Impl.class);

  @Autowired CustomerAdapter customerAdapter;
  
  @Autowired OrderAdapter orderAdapter;
  
  @Autowired PortalUserAdapter portalUserAdapter;
  
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
    itemTable.addCell(pdfSession.createCell("UdM", FontTypes.ARIAL_BOLD, 10f, PdfSession.ALIGN_CENTER));
    itemTable.addCell(pdfSession.createCell("Qta", FontTypes.ARIAL_BOLD, 10f, PdfSession.ALIGN_CENTER));
    itemTable.addCell(pdfSession.createCell("Prezzo", FontTypes.ARIAL_BOLD, 10f, PdfSession.ALIGN_CENTER));
    itemTable.addCell(pdfSession.createCell("Importo", FontTypes.ARIAL_BOLD, 10f, PdfSession.ALIGN_CENTER));
    
    List<OrderItem> items = order.getItems();
    for (int it = 0; it < items.size(); it++) {
      OrderItem orderItem = items.get(it);
      itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getName(), FontTypes.ARIAL, 10f));
      itemTable.addCell(pdfSession.createCell(orderItem.getProduct().getUnitaDiMisura().getNome(), FontTypes.ARIAL, 10f, PdfSession.ALIGN_CENTER));
      itemTable.addCell(pdfSession.createCell(""+orderItem.getQuantity(), FontTypes.ARIAL, 10f, PdfSession.ALIGN_CENTER));
      itemTable.addCell(pdfSession.createCell(""+ String.format("%.2f", orderItem.getProduct().getPrezzo()), FontTypes.ARIAL, 10f, PdfSession.ALIGN_RIGHT));
      itemTable.addCell(pdfSession.createCell(""+ String.format("%.2f", orderItem.getQuantity() * orderItem.getProduct().getPrezzo()), FontTypes.ARIAL, 10f, PdfSession.ALIGN_RIGHT));
    }
    
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell(""));
    itemTable.addCell(pdfSession.createCell("TOTALE", FontTypes.ARIAL_BOLD, 13f));
    itemTable.addCell(pdfSession.createCell("" + String.format("%.2f", Order.Utils.computeImportoTotale(order)), FontTypes.ARIAL_BOLD, 13f, PdfSession.ALIGN_RIGHT));
    
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
    PortalUser portalUser = dao.findWithContext(new FindContext<PortalUserDs>(PortalUserDs.class).setId(portalUserId));
    Customer customer = customerAdapter.findByPortalUser(portalUser);
    if (customer != null) {
      ContoUtente contoUtente = findContoUtenteByCustomer(customer, true, true);
      return CloneUtils.clone(contoUtente, ContoUtenteTx.class);
    }
    return null;
  }
  
  public Double getSaldoByPortalUser(String portalUserId) {
    PortalUser portalUser = dao.findWithContext(new FindContext<PortalUserDs>(PortalUserDs.class).setId(portalUserId));
    Customer customer = customerAdapter.findByPortalUser(portalUser);
    if (customer != null) {
      ContoUtente contoUtente = findContoUtenteByCustomer(customer, false);
      if (contoUtente != null)
        return contoUtente.getSaldo();
    }
    return null;
  }
  
  private ContoUtente findContoUtenteByCustomer(Customer customer, boolean createIfNotFound) {
    return findContoUtenteByCustomer(customer, createIfNotFound, false);
  }
  
  private ContoUtente findContoUtenteByCustomer(Customer customer, boolean createIfNotFound, boolean includeOrderId) {
    FindContext<ContoUtenteDs> findContoContext = new FindContext<ContoUtenteDs>(ContoUtenteDs.class)
        .setFilter("customerId == customerIdParam")
        .setParameters(Key.class.getName() + " customerIdParam")
        .setParamValues(new Object[] {KeyUtils.serializableToKey(customer.getId())})
        .includedField("movimentiKeys");
    if (includeOrderId) {
      findContoContext.includedField("orderId");
    } else {
      findContoContext.excludedField("orderId");
    }
    ContoUtente contoUtente = dao.findSingle(findContoContext);
    if (contoUtente == null && createIfNotFound) {
      contoUtente = new ContoUtenteDs();
      contoUtente.setCustomer(CloneUtils.clone(customer, CustomerDs.class));
      contoUtente.setSaldo(0d);
      contoUtente = dao.create(contoUtente);
    }

    // 16/05/2013
    if (contoUtente.getId() != null) {
      FindContext<ContoUtenteMovimentoDs> findMovimentiContext = new FindContext<ContoUtenteMovimentoDs>(ContoUtenteMovimentoDs.class)
          .setFilter("contoId == contoIdParam")
          .setParameters(Key.class.getName() + " contoIdParam")
          .setParamValues(new Object[] {KeyUtils.serializableToKey(contoUtente.getId())});
      if (includeOrderId) {
        findMovimentiContext.includedField("orderId");
      } else {
        findMovimentiContext.excludedField("orderId");
      }
      List<? extends ContoUtenteMovimento> movimenti = dao.findList(findMovimentiContext);
      contoUtente.setMovimenti((List<ContoUtenteMovimento>)movimenti);
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
      movimento.setCausale("CONSEGNA ORDINE " + order.getCode() + " " + order.getProducer().getNome());
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
  public void onCreateCustomer(Customer customer, Date date) {
    ContoUtente contoUtente = findContoUtenteByCustomer(customer, true);
    int saldoIniziale = PropertiesHolder.getInt("customAdapter.debug.saldoIniziale", 0);
    if (saldoIniziale > 0) {
      
      ContoUtenteMovimento movimento = new ContoUtenteMovimentoDs();
      movimento.setImporto((double)saldoIniziale);
      movimento.setSegno(ContoUtenteMovimento.POSITIVO);
      if (date == null) {
        date = new Date();
      }
      movimento.setData(date);
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
  
  @Override
  public byte[] exportPortalUsersToExcel() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      WritableWorkbook workbook = Workbook.createWorkbook(baos);
      WritableSheet sheet = workbook.createSheet("SintesiPrepagato", 0);
      
      WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
      WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
      headerFormat.setBackground(Colour.GREY_25_PERCENT);
      
      sheet.setColumnView(0, ExcelAdapterImpl.getCellViewSizeChars(25));
      sheet.setColumnView(1, ExcelAdapterImpl.getCellViewSizeChars(12));
      
      sheet.addCell(new Label(0, 0, "Socio", headerFormat));
      sheet.addCell(new Label(1, 0, "Saldo disponibile", headerFormat));
      
      List<PortalUser> portalUsers = portalUserAdapter.findAll();
      
      double saldoTotale = 0d;
      
      WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 11);
      WritableCellFormat dataFormat = new WritableCellFormat(dataFont);
      
      NumberFormat decimalNumberFormat = new NumberFormat("#.00");
      WritableCellFormat decimalFormat = new WritableCellFormat(decimalNumberFormat);
      decimalFormat.setFont(dataFont);
      
      Collections.sort(portalUsers, new Comparator<PortalUser>() {
        public int compare(PortalUser u1, PortalUser u2) {
          return u1.getScreenName().compareTo(u2.getScreenName());
        }
      });
      
      int row = 1;
      for (PortalUser portalUser : portalUsers) {
        Double saldo = getSaldoByPortalUser(portalUser.getId());
        if (saldo == null) {
          continue;
        }
        sheet.addCell(new Label(0, row, portalUser.getScreenName(), dataFormat));
        sheet.addCell(new Number(1, row, saldo, decimalFormat));
        saldoTotale += saldo;
        row++;
      }
      
      WritableCellFormat footerFormat = new WritableCellFormat(headerFont);
      decimalFormat.setFont(headerFont);
      
      row++;
      row++;
      sheet.addCell(new Label(0, row, "TOTALE", footerFormat));
      sheet.addCell(new Number(1, row, saldoTotale, decimalFormat));
      
      workbook.write();
      workbook.close();
      
      return baos.toByteArray();
    } catch (Exception ex) {
      logger.error("error", ex);
    }
    return null;
  }

  @Override
  public void unloadExtraData(PortalDataExportModel model) {
    List<ContoUtente> data = new ArrayList<ContoUtente>();
    for (Customer customer : model.customers) {
      ContoUtenteTx conto = CloneUtils.clone(findContoUtenteByCustomer(customer, false, true), ContoUtenteTx.class);
      conto.setId(null);
      Customer reducedCustomer = new CustomerTx();
      PortalUser reducedPortalUser = new PortalUserTx();
      reducedPortalUser.setEmailAddress(conto.getCustomer().getPortalUser().getEmailAddress());
      reducedCustomer.setPortalUser(reducedPortalUser);
      conto.setCustomer(reducedCustomer);
      List<ContoUtenteMovimentoTx> movimenti = new ArrayList<ContoUtenteMovimentoTx>();
      for (int it = 0; it < conto.getMovimenti().size(); it++) {
        ContoUtenteMovimentoTx movimento = CloneUtils.clone(conto.getMovimenti().get(it), ContoUtenteMovimentoTx.class);
        movimento.setId(null);
        movimento.setConto(null);
        movimento.setOrder(null);
        movimenti.add(movimento);
      }
      conto.setMovimentiTx(movimenti);
      data.add(conto);
    }
    model.customData = data;
  }

}
