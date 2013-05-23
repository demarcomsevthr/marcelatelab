package it.mate.test;

import it.mate.commons.server.utils.PdfSession;
import it.mate.econyx.server.util.FontUtils;
import it.mate.econyx.shared.util.FontTypes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PdfTest {

  private FontUtils fontResourceFactory;
  
  public static void main(String[] args) {
    try {
      ApplicationContext context = new ClassPathXmlApplicationContext("classpath:econyx-test-context.xml");
      PdfTest test = context.getBean(PdfTest.class);
      test.doTest();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setFontResourceFactory(FontUtils fontResourceFactory) {
    this.fontResourceFactory = fontResourceFactory;
  }
  
  private void doTest() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfSession pdfSession = new PdfSession(baos);
    setupFonts(pdfSession);
    
    float fontSize, y;
    float marginX = 36;
    
    y = 800;
    pdfSession.addAbsoluteText("Copycart Snc", FontTypes.ARIAL_BOLD, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("Via Martiri della Libertà 150", FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("Cap e città", FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("Tel / fax", FontTypes.ARIAL, 12, marginX, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("Partita iva e registrazione camera di commercio", FontTypes.ARIAL, 10, marginX + 1, 400, y - 2, PdfSession.ALIGN_LEFT);
    
    pdfSession.addAbsoluteText("Cliente", FontTypes.ARIAL_BOLD_ITALIC, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    
    y -= 14;
    pdfSession.addAbsoluteText("Ragione sociale", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("indirizzo", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("Ordine n.", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("ORD1002", FontTypes.ARIAL, 13, marginX + 70, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("cap e città", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    y -= 14;
    pdfSession.addAbsoluteText("del", FontTypes.ARIAL, 13, marginX, 400, y - 2, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("XX/XX/XXXX", FontTypes.ARIAL, 13, marginX + 70, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("Piva / codice fiscale", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    
    y -= 42;
    pdfSession.addAbsoluteText("Pag. X di Y", FontTypes.ARIAL, 12, 360, 580, y, PdfSession.ALIGN_LEFT);
    
    
    y -= 70;
    pdfSession.addAbsoluteText("Articolo:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("NUOVO PRINTY 4908", FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Pezzi ordinati:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("XXXXXX", FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Prezzo unitario:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("XXXXXX", FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    y -= 14;
    pdfSession.addAbsoluteText("Importo:", FontTypes.ARIAL, 13, marginX, 400, y, PdfSession.ALIGN_LEFT);
    pdfSession.addAbsoluteText("XXXXXX", FontTypes.ARIAL, 13, marginX + 120, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= 56;
    pdfSession.addAbsoluteText("Anteprima del timbro:", FontTypes.ARIAL, 13, marginX + 80, 400, y, PdfSession.ALIGN_LEFT);
    
    
    
    fontSize = 18;
    y = 450;
    
    pdfSession.addRectangle(200, y, 200, 150);
    
    pdfSession.addColumnText("centro", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_CENTER);
    
    y -= (fontSize + 10);
    pdfSession.addColumnText("sinistra", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= (fontSize + 10);
    pdfSession.addColumnText("destra", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_RIGHT);
    
    y -= (fontSize + 10);
    pdfSession.addColumnText("giusto a", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_JUSTIFIED);
    
    
    
    
    /*
    
    pdfSession.newPage();
    
    fontSize = 12;
    y = 700;
    
    pdfSession.addRectangle(200, y, 200, 150);
    
    pdfSession.addAbsoluteText("riga uno", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_LEFT);
    
    y -= fontSize + 10;
    pdfSession.addAbsoluteText("riga due", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_CENTER);
    
    y -= fontSize + 10;
    pdfSession.addAbsoluteText("riga tre", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_RIGHT);
    
    y -= fontSize + 10;
    pdfSession.addAbsoluteText("riga qua", "Verdana", fontSize, 200, 400, y, PdfSession.ALIGN_JUSTIFIED);

    
    Resource res = SpringUtils.getClassPathResource("images/pesci.gif");
    byte[] imgContent = FileUtils.readFileToByteArray(res.getFile());
    pdfSession.addAbsoluteImage(imgContent, 300, 700 - 100);
    
    */
    
    
    pdfSession.closeDocument();
    
    File f = new File(String.format("target\\test%s.pdf", System.currentTimeMillis()));
    if (f.exists())
      f.delete();
    FileUtils.writeByteArrayToFile(f, baos.toByteArray());
    
    System.out.println("CREATED FILE " + f);
    
    //Runtime.getRuntime().exec("C:\\Progra~2\\Adobe\\READER~1.0\\Reader\\AcroRd32.exe " + f.getAbsolutePath());

    ProcessBuilder pb = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe", "/C", "C:\\Progra~2\\Adobe\\READER~1.0\\Reader\\AcroRd32.exe", f.getAbsolutePath());
    pb.start();
    
  }

  private void setupFonts(PdfSession pdfSession) throws Exception {
    Map<String, File> fonts = fontResourceFactory.getFontResourceMap();
    for (String name : fonts.keySet()) {
      File fontFile = fonts.get(name);
      pdfSession.createFont(name, FileUtils.readFileToByteArray(fontFile));
    }
  }
  
  
}
