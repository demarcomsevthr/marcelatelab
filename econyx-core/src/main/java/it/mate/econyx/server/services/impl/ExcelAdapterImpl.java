package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ExcelAdapter;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.Produttore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ExcelAdapterImpl implements ExcelAdapter {
  
  private static Logger logger = Logger.getLogger(ExcelAdapterImpl.class);
  
  public byte[] printOrdersToProducer (Produttore producer, List<Order> orders) throws IOException {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      WritableWorkbook workbook = Workbook.createWorkbook(baos);
      WritableSheet sheet = workbook.createSheet("Distinta", 0);
      
      WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
      WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
      headerFormat.setBackground(Colour.GREY_25_PERCENT);
      
      sheet.setColumnView(1, getCellViewSizeChars(25));
      sheet.setColumnView(2, getCellViewSizeChars(10));
      sheet.setColumnView(3, getCellViewSizeChars(25));
      sheet.setColumnView(4, getCellViewSizeChars(6));
      sheet.setColumnView(5, getCellViewSizeChars(10));
      sheet.setColumnView(6, getCellViewSizeChars(12));
      sheet.setColumnView(7, getCellViewSizeChars(12));
      
      sheet.addCell(new Label(1, 1, "Socio", headerFormat));
      sheet.addCell(new Label(2, 1, "Ordine", headerFormat));
      sheet.addCell(new Label(3, 1, "Articolo", headerFormat));
      sheet.addCell(new Label(4, 1, "UdM", headerFormat));
      sheet.addCell(new Label(5, 1, "Qta", headerFormat));
      sheet.addCell(new Label(6, 1, "Prezzo", headerFormat));
      sheet.addCell(new Label(7, 1, "Importo", headerFormat));
      
      double importoTotaleFornitore = 0d;
      
      WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 11);
      WritableCellFormat dataFormat = new WritableCellFormat(dataFont);
      
      NumberFormat decimalNumberFormat = new NumberFormat("#.00");
      WritableCellFormat decimalFormat = new WritableCellFormat(decimalNumberFormat);
      decimalFormat.setFont(dataFont);
      
      Collections.sort(orders, new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
          return o1.getCustomer().getPortalUser().getScreenName().compareTo(o2.getCustomer().getPortalUser().getScreenName());
        }
      });
      
      int row = 2;
      for (Order order : orders) {
        List<OrderItem> items = order.getItems();
        String customerName = order.getCustomer().getPortalUser().getScreenName();
        String orderCode = order.getCode();
        
        Collections.sort(items, new Comparator<OrderItem>() {
          public int compare(OrderItem i1, OrderItem i2) {
            return i1.getProduct().getName().compareTo(i2.getProduct().getName());
          }
        });
        
        for (OrderItem orderItem : items) {
          
          sheet.addCell(new Label(1, row, customerName, dataFormat));
          sheet.addCell(new Label(2, row, orderCode, dataFormat));
          sheet.addCell(new Label(3, row, orderItem.getProduct().getName(), dataFormat));
          sheet.addCell(new Label(4, row, orderItem.getProduct().getUnitaDiMisura().getNome(), dataFormat));
          sheet.addCell(new Number(5, row, orderItem.getQuantity(), decimalFormat));
          sheet.addCell(new Number(6, row, orderItem.getProduct().getPrezzo(), decimalFormat));
          sheet.addCell(new Number(7, row, orderItem.getQuantity() * orderItem.getProduct().getPrezzo(), decimalFormat));
          
          customerName = "";
          orderCode = "";
          row++;
        }

        importoTotaleFornitore += Order.Utils.computeImportoTotale(order);
        
      }
      
      row++;
      sheet.addCell(new Number(7, row, importoTotaleFornitore, decimalFormat));
      
      workbook.write();
      workbook.close();
      
      return baos.toByteArray();
      
    } catch (WriteException ex) {
      logger.error("error", ex);
    }
    
    return null;
  }
  
  public static CellView getCellViewSizeChars(int size) {
    CellView cv = new CellView();
    cv.setSize(size * 256);
    return cv;
  }
  
  

}
