package it.mate.gwtcommons.server.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfSession {
  
  private Document document;
  
  private PdfWriter writer;
  
  private static final Map<String, BaseFont> fontMap = new HashMap<String, BaseFont>();
  
  public static final int ALIGN_LEFT = Element.ALIGN_LEFT;

  public static final int ALIGN_CENTER = Element.ALIGN_CENTER;

  public static final int ALIGN_RIGHT = Element.ALIGN_RIGHT;

  public static final int ALIGN_JUSTIFIED = Element.ALIGN_JUSTIFIED;
  
  
  private static final Rectangle DEFAULT_PAGE_SIZE = PageSize.LETTER;
  
  
  public PdfSession(OutputStream os) {
    openDocument(os);
  }

  public void closeDocument() {
    document.close();
  }
  
  public void addTable(PdfPTable table) {
    try {
      document.add(table);
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addNewLine() {
    try {
      document.add(Chunk.NEWLINE);
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public PdfPCell createCell(String text) {
    return createCell(text, null, 0);
  }
  
  public PdfPCell createCell(String text, String fontName, float fontSize) {
    PdfPCell cell;
    Font font = null;
    if (fontName != null) {
      BaseFont baseFont = getBaseFont(fontName);
      font = new Font(baseFont, fontSize);
    }
    Chunk chunk = null;
    if (font != null) {
      chunk = new Chunk(text, font);
    } else {
      chunk = new Chunk(text);
    }
    Phrase phrase = new Phrase(chunk);
    cell = new PdfPCell(phrase);
    return cell;
  }
  
  public void addParaghraph() {
    addParaghraph("");
  }
  
  public void addParaghraph(String text) {
    addParaghraph(text, null);
  }
  
  public void addParaghraph(String text, String fontName) {
    addParaghraph(text, fontName, null);
  }
  
  public void addParaghraph(String text, String fontName, Float fontSize) {
    try {
      if (fontName != null) {
        Font font = new Font(getBaseFont(fontName));
        if (fontSize != null) {
          font.setSize(fontSize);
        }
        Paragraph par = new Paragraph(text, font);
        document.add(par);
      } else {
        document.add(new Paragraph(text));
      }
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addColumnText(String text, String fontName, float fontSize, float x1, float x2, float y, int textAlignment) {
    try {
      PdfContentByte dc = writer.getDirectContent();
      ColumnText ct = new ColumnText(dc);
      Font font = null;
      if (fontName != null) {
        BaseFont baseFont = getBaseFont(fontName);
        font = new Font(baseFont, fontSize);
      }
      Chunk chunk = null;
      if (font != null) {
        chunk = new Chunk(text, font);
      } else {
        chunk = new Chunk(text);
      }
      ct.setSimpleColumn(x1, y - fontSize, x2, y, fontSize, textAlignment);
      if (textAlignment == ALIGN_JUSTIFIED) {
        float cs = (x2 - x1 - chunk.getWidthPoint()) / text.length();
        System.out.println("CharacterSpacing = " + cs);
        chunk.setCharacterSpacing(cs);
      }
      ct.addText(chunk);
      ct.go();
    } catch (Exception ex) {

    }
  }
  
  public void addAbsoluteText(String text, String fontName, float fontSize, float x1, float x2, float y, int textAlignment) {
    addColumnText(text, fontName, fontSize, x1, x2, y, textAlignment);
  }
  
  public void addAbsoluteText_OLD(String text, String fontName, float fontSize, float x1, float x2, float y, int textAlignment) {
    try {
      PdfContentByte dc = writer.getDirectContent();
      BaseFont font = getBaseFont(fontName);
      dc.beginText();
      dc.setFontAndSize(font, fontSize);
      float x = textAlignment == ALIGN_LEFT ? x1 :
                  (textAlignment == ALIGN_RIGHT ? x2 :
                    (textAlignment == ALIGN_CENTER ? (x1 + (x2 - x1) / 2) : x1));
      dc.showTextAligned(textAlignment, text, x, y - fontSize, 0);
      dc.endText();
    } catch (Exception ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addRectangle(float x, float y, float w, float h) {
    PdfContentByte dc = writer.getDirectContent();
    dc.setColorStroke(BaseColor.BLACK);
    dc.rectangle(x, y, w, (-1) * h);
    dc.stroke();
  }
  
  public void createFont(String fontName, byte[] ttfAfm) {
    try {
      if (!fontMap.containsKey(fontName)) {
        BaseFont font = BaseFont.createFont(fontName + ".ttf", BaseFont.WINANSI, false, true, ttfAfm, null);
        fontMap.put(fontName, font);
      }
    } catch (IOException ex) {
      throw new PdfException(ex);
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addAbsoluteImage(byte[] content, float x, float y) {
    try {
      PdfContentByte dc = writer.getDirectContent();
      Image image = Image.getInstance(content);
      y = y - image.getHeight();
      dc.addImage(image, image.getWidth(), 0, 0, image.getHeight(), x, y);
    } catch (Exception ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addImage(byte[] content) {
    try {
      Image image = Image.getInstance(content);
      document.add(image);
    } catch (BadElementException ex) {
      throw new PdfException(ex);
    } catch (MalformedURLException ex) {
      throw new PdfException(ex);
    } catch (IOException ex) {
      throw new PdfException(ex);
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public void newPage() {
    document.newPage();
  }

  /*
  public PdfPTable createTable(int numColumns) {
    PdfPTable table = new PdfPTable(numColumns);
    return table;
  }
  
  public void addTable(PdfPTable table) {
    try {
      document.add(table);
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  public void addCell(PdfPTable table, String text) {
    PdfPCell cell = new PdfPCell(new Phrase(text));
    table.addCell(cell);
  }
  */
  
  private void openDocument(OutputStream os) {
    try {
      this.document = new Document();
      this.writer = PdfWriter.getInstance(document, os);
      document.open();
    } catch (DocumentException ex) {
      throw new PdfException(ex);
    }
  }
  
  private BaseFont getBaseFont(String fontName) {
    return fontMap.get(fontName);
  }
  
  @SuppressWarnings("serial")
  public class PdfException extends RuntimeException {
    public PdfException() {
      super();
    }
    public PdfException(String message, Throwable cause) {
      super(message, cause);
    }
    public PdfException(String message) {
      super(message);
    }
    public PdfException(Throwable cause) {
      super(cause);
    }
  }

}
