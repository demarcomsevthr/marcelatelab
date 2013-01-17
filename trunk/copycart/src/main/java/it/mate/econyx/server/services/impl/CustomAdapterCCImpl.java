package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractOrderItemDetailDs;
import it.mate.econyx.server.model.impl.OrderItemStampDetailDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.Timbro;
import it.mate.econyx.shared.model.impl.OrderItemStampDetailTx;
import it.mate.econyx.shared.util.FontAlignment;
import it.mate.econyx.shared.util.FontTypes;
import it.mate.econyx.shared.utils.StampUtils;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.utils.BlobUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.server.utils.DateUtils;
import it.mate.gwtcommons.server.utils.PdfSession;
import it.mate.gwtcommons.server.utils.PropertiesHolderConfigurer;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Blob;

@Service
public class CustomAdapterCCImpl implements CustomAdapter {

  private static Logger logger = Logger.getLogger(CustomAdapterCCImpl.class);
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  public String uploadImage(String imageType, String imageSize, String objId, Blob imageBlob, HttpSession session) {
    String result = "";
    if ("orderItemLogo".equals(imageType)) {
      
      if (session != null && "session".equals(objId) ) {
        OrderItemStampDetailTx detail = new OrderItemStampDetailTx();
        detail.setType(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO);
        detail.setLogo(BlobUtils.blobToString(imageBlob));
        session.setAttribute("orderItemStampDetailLogo", detail);
      } else {
        OrderItemStampDetailDs detail = dao.findById(OrderItemStampDetailDs.class, objId);
        detail.setLogoAsBlob(imageBlob);
        dao.update(detail);
      }

      StringBuffer sb = new StringBuffer();
      sb.append(objId);
      sb.append("|");
      sb.append(BlobUtils.blobToString(imageBlob));
      
      result = sb.toString();
      
    }
    
    return result;
    
  }
  
  public AbstractOrderItemDetailDs findOrderItemDetailDs(String id, HttpSession session) {
    if (session != null && "session".equals(id) ) {
      OrderItemStampDetailTx detailTx = (OrderItemStampDetailTx)session.getAttribute("orderItemStampDetailLogo");
      return CloneUtils.clone(detailTx, OrderItemStampDetailDs.class);
    } else {
      return dao.findById(OrderItemStampDetailDs.class, id);
    }
  }
  
  public void orderStateChanged(Order order) {
    
  }
  
  
  public void printOrder (PdfSession pdfSession, Order order) {
    float y;
    float marginX = 36;
    y = 800;
    pdfSession.addAbsoluteText(PropertiesHolder.getString("azienda.ragioneSociale"), FontTypes.ARIAL_BOLD, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(PropertiesHolder.getString("azienda.indirizzo"), FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(PropertiesHolder.getString("azienda.cap") + " " + 
                               PropertiesHolder.getString("azienda.citta"), FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(String.format("Tel %s / fax %s", 
                PropertiesHolder.getString("azienda.telefono"), 
                PropertiesHolder.getString("azienda.fax")), FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(String.format("Partita iva %s", 
                PropertiesHolder.getString("azienda.partitaIva")), FontTypes.ARIAL, 10, marginX + 1, 400, y - 2, PdfSession.ALIGN_LEFT);
    float orderItemY = y;
    List<OrderItem> items = order.getItems();
    int pagTot = items.size();
    for (int it = 0; it < items.size(); it++) {
      OrderItem orderItem = items.get(it);
      printOrderItem(pdfSession, orderItemY, marginX, order, orderItem, it + 1, pagTot);
      if (it < items.size()) {
        pdfSession.newPage();
      }
    }
  }
  
  private void printOrderItem(PdfSession pdfSession, float y, float marginX, Order order, OrderItem orderItem, int pagN, int pagTot) {
    pdfSession.addAbsoluteText("Cliente", FontTypes.ARIAL_BOLD_ITALIC, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(order.getCustomer().getIndirizzoSpedizione().getCognome() + " "
        + order.getCustomer().getIndirizzoSpedizione().getNome(), FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText(order.getCustomer().getIndirizzoSpedizione().getVia(), FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Ordine n.", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(order.getCode(), FontTypes.ARIAL, 13, marginX + 70, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(order.getCustomer().getIndirizzoSpedizione().getCap()+" "+
        order.getCustomer().getIndirizzoSpedizione().getCitta() , FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("del", FontTypes.ARIAL, 13, marginX, 400, y - 2, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(DateUtils.dateToString(order.getCreated()), FontTypes.ARIAL, 13, marginX + 70, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("Piva / codice fiscale", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 42;
    pdfSession.addAbsoluteText(String.format("Pag. %s di %s", pagN, pagTot), FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    y -= 70;
    pdfSession.addAbsoluteText("Articolo:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(orderItem.getProduct().getName(), FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Pezzi ordinati:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(String.format("%.0f", orderItem.getQuantity()), FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Prezzo unitario:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(String.format("%.2f €", orderItem.getProduct().getPrezzo()), FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Importo:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText(String.format("%.2f €", Order.Utils.computeImportoItem(orderItem)), FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    printOrderItemDetails(pdfSession, orderItem);
  }

  private void printOrderItemDetails (PdfSession pdfSession, OrderItem orderItem) {

    // la rilettura serve solo per debug
    PropertiesHolderConfigurer.reloadFromClassPathResource("econyx.properties");
    
    if (orderItem.getProduct() instanceof Timbro) {
      
      Timbro timbro = (Timbro)orderItem.getProduct();
      logger.debug("printing timbro " + timbro);

      float xFactor = Float.parseFloat(PropertiesHolder.getString("timbro.print.xFactor", "5.25"));
      float yFactor = Float.parseFloat(PropertiesHolder.getString("timbro.print.yFactor", "8.33"));
      
      float detailW = xFactor * timbro.getLarghezza();
      float detailH = yFactor * timbro.getAltezza();
      float detailY = PropertiesHolder.getInt("timbro.print.defaultDetailY", 700);
      float detailX = (PropertiesHolder.getInt("timbro.print.pageWidth", 600) - detailW) / 2;
      
      float currentY = detailY;
      
      pdfSession.addAbsoluteText("Anteprima del timbro:", FontTypes.ARIAL, 13, 116, 400, currentY, PdfSession.ALIGN_LEFT);
      
      detailY -= 28;

      currentY = detailY;

      if (timbro.isOval()) {
        pdfSession.addEllipse(detailX, currentY , detailW, detailH, 0.7f, PdfSession.GRAY);
      } else {
        pdfSession.addRectangle(detailX, currentY , detailW, detailH, 0.7f, PdfSession.GRAY);
      }
      
      for (OrderItemDetail orderItemDetail : orderItem.getDetails()) {
        if (orderItemDetail instanceof OrderItemStampDetailDs) {
          OrderItemStampDetailDs orderItemStampDetail = (OrderItemStampDetailDs)orderItemDetail;
          if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_BORDER.equals(orderItemStampDetail.getType())) {
            currentY -= orderItemStampDetail.getBorderSize();
          }
        }
      }
      
      for (OrderItemDetail orderItemDetail : orderItem.getDetails()) {
        if (orderItemDetail instanceof OrderItemStampDetailDs) {
          
          OrderItemStampDetailDs orderItemStampDetail = (OrderItemStampDetailDs)orderItemDetail;
          logger.debug("printing detail " + orderItemStampDetail);

          if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT.equals(orderItemStampDetail.getType())) {
            float fontSize = (orderItemStampDetail.getFontSize() > 0 ? orderItemStampDetail.getFontSize() : PropertiesHolder.getInt("timbro.defaultFontSize", 10) );
            String fontName = translateFontName(orderItemStampDetail);
            int textAlignment = translateTextAlignment(orderItemStampDetail);
            pdfSession.addAbsoluteText(orderItemStampDetail.getText(), fontName, fontSize, detailX, detailX + detailW, currentY, textAlignment);
            currentY -= fontSize + PropertiesHolder.getInt("timbro.print.interlinea", 6);
            
          } else if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO.equals(orderItemStampDetail.getType())) {
            byte[] content = orderItemStampDetail.getLogoAsBlob().getBytes();
            pdfSession.addAbsoluteImage(content, 
                detailX + orderItemStampDetail.getLogoX(), 
                detailY - orderItemStampDetail.getLogoY());
            
          }
          
        }
      }
      
      if (timbro.isDatario()) {
        float fontSize = 10f;
        String fontName = FontTypes.ARIAL_BOLD;
        int textAlignment = PdfSession.ALIGN_CENTER;
        pdfSession.addAbsoluteText(DateUtils.dateToString(new Date(), "dd MMM yyyy"), fontName, fontSize, detailX, 
            detailX + detailW, detailY - (detailH / 2) + 8, textAlignment, PdfSession.RED);
      }
      
    }
    
  }
  
  private int translateTextAlignment(OrderItemStampDetailDs orderItemStampDetail) {
    int textAlignment = PdfSession.ALIGN_CENTER;
    if (FontAlignment.LEFT.equals(orderItemStampDetail.getAlign())) {
      textAlignment = PdfSession.ALIGN_LEFT;
    } else if (FontAlignment.CENTER.equals(orderItemStampDetail.getAlign())) {
      textAlignment = PdfSession.ALIGN_CENTER;
    } else if (FontAlignment.RIGHT.equals(orderItemStampDetail.getAlign())) {
      textAlignment = PdfSession.ALIGN_RIGHT;
    }
    return textAlignment;
  }
  
  private String translateFontName(OrderItemStampDetailDs orderItemStampDetail) {
    String fontName = null;
    if (orderItemStampDetail.getFontType() != null) {
      if (orderItemStampDetail.getFontType() == FontTypes.TYPE_ARIAL) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.ARIAL, FontTypes.ARIAL_BOLD, FontTypes.ARIAL_ITALIC, FontTypes.ARIAL_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_ARIAL_NARROW) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.ARIAL_NARROW, FontTypes.ARIAL_NARROW_BOLD, FontTypes.ARIAL_NARROW_ITALIC, FontTypes.ARIAL_NARROW_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_ARIAL_BLACK) {
        fontName = FontTypes.ARIAL_BLACK;
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_COMIC_SANS_MS) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.COMIC, FontTypes.COMIC_BOLD, FontTypes.COMIC, FontTypes.COMIC_BOLD);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_COURIER_NEW) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.COURIER, FontTypes.COURIER_BOLD, FontTypes.COURIER_ITALIC, FontTypes.COURIER_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_GARAMOND) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.GARAMOND, FontTypes.GARAMOND_BOLD, FontTypes.GARAMOND_ITALIC, FontTypes.GARAMOND_BOLD);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_GEORGIA) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.GEORGIA, FontTypes.GEORGIA_BOLD, FontTypes.GEORGIA_ITALIC, FontTypes.GEORGIA_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_TAHOMA) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.TAHOMA, FontTypes.TAHOMA_BOLD, FontTypes.TAHOMA, FontTypes.TAHOMA_BOLD);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_TIMES_NEW_ROMAN) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.TIMES, FontTypes.TIMES_BOLD, FontTypes.TIMES_ITALIC, FontTypes.TIMES_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_TREBUCHET) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.TREBUCHET, FontTypes.TREBUCHET_BOLD, FontTypes.TREBUCHET_ITALIC, FontTypes.TREBUCHET_BOLD_ITALIC);
      } else if (orderItemStampDetail.getFontType() == FontTypes.TYPE_VERDANA) {
        fontName = translateFontSubFamily(orderItemStampDetail, FontTypes.VERDANA, FontTypes.VERDANA_BOLD, FontTypes.VERDANA_ITALIC, FontTypes.VERDANA_BOLD_ITALIC);
      }
    }
    return fontName;
  }
  
  private String translateFontSubFamily(OrderItemStampDetailDs orderItemStampDetail, String fontNormal, String fontBold, String fontItalic, String fontBoldItalic) {
    String fontName = null;
    if (!orderItemStampDetail.getBold() && !orderItemStampDetail.getItalic()) {
      fontName = fontNormal;
    } else if (orderItemStampDetail.getBold() && !orderItemStampDetail.getItalic()) {
      fontName = fontBold;
    } else if (!orderItemStampDetail.getBold() && orderItemStampDetail.getItalic()) {
      fontName = fontItalic;
    } else if (orderItemStampDetail.getBold() && orderItemStampDetail.getItalic()) {
      fontName = fontBoldItalic;
    }
    return fontName;
  }

  public void onDeleteCustomer(Customer customer) {
    
  }

  public Double getSaldoByPortalUser(String portalUserId) {
    return null;
  }

  public void onCreateCustomer(Customer customer, Date date) {
    
  }

  public void printOrdersToProducer(PdfSession pdfSession, Produttore producer, List<Order> orders) {
    
  }

  public void validateProductToOrder(Order order) throws ServiceException {
    
  }
  
  public byte[] exportPortalUsersToExcel() {
    return null;
  }

}
