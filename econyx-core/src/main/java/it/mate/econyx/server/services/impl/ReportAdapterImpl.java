package it.mate.econyx.server.services.impl;

import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.PdfSession;
import it.mate.econyx.server.model.impl.OrderDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.ReportAdapter;
import it.mate.econyx.server.util.FontUtils;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.Produttore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportAdapterImpl implements ReportAdapter {
  
  private static final Logger logger = Logger.getLogger(ReportAdapterImpl.class);
  
  @Autowired CustomAdapter customAdapter;
  
  @Autowired FontUtils fontResourceFactory;
  
  public byte[] printOrder (Order order) {
    order = CloneUtils.clone(order, OrderDs.class);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfSession pdfSession = createPdfSesison(baos);
    customAdapter.printOrder(pdfSession, order);
    pdfSession.closeDocument();
    return baos.toByteArray();
  }
  
  public byte[] printOrdersToProducer (Produttore producer, List<Order> orders) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfSession pdfSession = createPdfSesison(baos);
    customAdapter.printOrdersToProducer(pdfSession, producer, orders);
    pdfSession.closeDocument();
    return baos.toByteArray();
  }
  
  private PdfSession createPdfSesison(OutputStream os) {
    PdfSession pdfSession = new PdfSession(os);
    setupFonts(pdfSession);
    return pdfSession;
  }
  
  private void setupFonts(PdfSession pdfSession) {
    try {
      Map<String, File> fonts = fontResourceFactory.getFontResourceMap();
      for (String name : fonts.keySet()) {
        File fontFile = fonts.get(name);
        pdfSession.createFont(name, FileUtils.readFileToByteArray(fontFile));
      }
    } catch (IOException ex) {
      logger.error("error", ex);
    }
  }
  
}
