package it.mate.econyx.client.ui;

import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.Timbro;
import it.mate.econyx.shared.model.impl.OrderItemStampDetailTx;
import it.mate.econyx.shared.utils.StampUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class StampPreviewPanel extends HTMLPanel {

  AbsolutePanel absolutePanel = null;
  
  private Image logoImg;
  
  private DraggableWidget<Image> draggableLogoImg;
  
  private int logoWidth = 0;
  
  private int logoHeight = 0;
  
  private int dragX = -1;
  private int dragY = -1;
  
  Timbro timbro;
  
  float width, height;
  
  private int dataLblW = -1, dataLblH = -1;
  
  public StampPreviewPanel() {
    super("");
    addStyleName("previewPanel");
    List<OrderItemDetail> details = new ArrayList<OrderItemDetail>();
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    setDetails(details);
  }
  
  public void setTimbro(Timbro timbro) {
    this.timbro = timbro;
    if (timbro.getLarghezza() != null) {
      width = ((float)timbro.getLarghezza() * Float.parseFloat(PropertiesHolder.getString("timbro.preview.xFactor")));
      setWidth(width+"cm");
    }
    if (timbro.getAltezza() != null) {
      height = ((float)timbro.getAltezza() * Float.parseFloat(PropertiesHolder.getString("timbro.preview.yFactor")));
      setHeight(height+"cm");
    }
    setRadius();
  }
  
  public void setDetails (List<OrderItemDetail> details) {
    if (absolutePanel == null) {
      absolutePanel = new AbsolutePanel();
      absolutePanel.setSize("100%", "100%");
      add(absolutePanel);
    } else {
      absolutePanel.clear();
    }
    int currentTop = 0;
    for (OrderItemDetail detail : details) {
      if (detail instanceof OrderItemStampDetailTx) {
        OrderItemStampDetailTx borderDetail = (OrderItemStampDetailTx)detail;
        if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_BORDER.equals(borderDetail.getType())) {
          HTML border = new HTML(SafeHtmlUtils.fromTrustedString("&nbsp;"));
          border.setHeight(borderDetail.getBorderSize()+"px");
          absolutePanel.add(border, 0, currentTop);
          currentTop += border.getOffsetHeight();
        }
      }
    }
    for (OrderItemDetail detail : details) {
      if (detail instanceof OrderItemStampDetailTx) {
        OrderItemStampDetailTx stampDetail = (OrderItemStampDetailTx)detail;
        if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT.equals(stampDetail.getType())) {
          if (stampDetail.getText() != null && stampDetail.getText().length() > 0) {
            String html = stampDetail.getText().replace(" ", "&nbsp;");
            HTML row = new HTML(SafeHtmlUtils.fromTrustedString(html));
            row.setWidth("100%");
            StampUtils.applySettingsOnWidget(row, StampUtils.convertOrderItemDetailToSettings(stampDetail));
            absolutePanel.add(row, 0, currentTop);
            currentTop += row.getOffsetHeight();
          } else {
            int h = getRowHeight(stampDetail);
            Spacer row = new Spacer("1px", h + "px");
            try {
              absolutePanel.add(row, 0, currentTop);
            } catch (IllegalStateException ex) { /* DO NOTHING */ }
            currentTop += h;
          }
        } else if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO.equals(stampDetail.getType())) {
          if (logoImg == null) {
            int v = Random.nextInt();
            String url = "/re/cu/oil/" + (stampDetail.getId() != null ? stampDetail.getId() : "session") + "?v="+v;
            logoImg = new Image(url);
            
            // 28/12/2012
            draggableLogoImg = new DraggableWidget<Image>(logoImg);
            draggableLogoImg.setDraggingCursor(Cursor.MOVE);
            draggableLogoImg.setDraggingOpacity(0.8f);
            
            draggableLogoImg.addDragHandler(new DragEvent.DragEventHandler() {
              public void onDrag(DragEvent event) {
                dragX = draggableLogoImg.getAbsoluteLeft() - absolutePanel.getAbsoluteLeft();
                dragY = draggableLogoImg.getAbsoluteTop() - absolutePanel.getAbsoluteTop();
              }
            });
            
            logoImg.addMouseDownHandler(new MouseDownHandler() {
              public void onMouseDown(MouseDownEvent event) {
                GwtUtils.setStyleAttribute(logoImg, "cursor", Cursor.MOVE.getCssName());
              }
            });
            
            logoImg.addMouseUpHandler(new MouseUpHandler() {
              public void onMouseUp(MouseUpEvent event) {
                GwtUtils.setStyleAttribute(logoImg, "cursor", Cursor.DEFAULT.getCssName());
              }
            });
            
          }
          int left = stampDetail.getLogoX();
          int top = stampDetail.getLogoY();
//        absolutePanel.add(logoImg, left, top);
          absolutePanel.add(draggableLogoImg, left, top);
          logoWidth = logoImg.getOffsetWidth();
          logoHeight = logoImg.getOffsetHeight();
        }
      }
    }
    
    if (timbro != null && timbro.isDatario()) {
      if (dataLblW > -1 && dataLblH > -1) {
        Label dataLbl = createDataLbl();
        int cX = StampPreviewPanel.this.getOffsetWidth() / 2;
        int cY = StampPreviewPanel.this.getOffsetHeight() / 2;
        absolutePanel.add(dataLbl, cX - (dataLblW / 2), cY - (dataLblH / 2));
        GwtUtils.setStyleAttribute(dataLbl, "color", "red");
      } else {
        final Label dataLbl = createDataLbl();
        GwtUtils.setStyleAttribute(dataLbl, "color", "white");
        dataLbl.getElement().setId("dataLbl");
        absolutePanel.add(dataLbl, 0, 0);
        GwtUtils.onAvailable("dataLbl", new Delegate<Element>() {
          public void execute(Element element) {
            dataLblW = dataLbl.getOffsetWidth();
            dataLblH = dataLbl.getOffsetHeight();
          }
        });
      }
    }
    
    setRadius();
    
  }
  
  private void setRadius() {
    if (timbro != null && timbro.isOval()) {
      int radX = StampPreviewPanel.this.getOffsetWidth() / 2;
      int radY = StampPreviewPanel.this.getOffsetHeight() / 2;
      String radius = radX+"px / "+radY+"px";
      GwtUtils.setBorderRadius(StampPreviewPanel.this, radius);
      GwtUtils.setBorderRadius(absolutePanel, radius);
    }
  }

  private Label createDataLbl() {
    Label dataLbl = new Label(GwtUtils.dateToString(new Date(), DateTimeFormat.getFormat("dd MMM yyyy")));
    return dataLbl;
  }
  
  
  public int getLogoWidth() {
    return logoWidth;
  }
  
  public int getLogoHeight() {
    return logoHeight;
  }
  
  private int getRowHeight(OrderItemStampDetailTx stampDetail) {
    return (stampDetail.getFontSize() != null && stampDetail.getFontSize() > 0) ? stampDetail.getFontSize() : 10;
  }
  
  public int getDragX() {
    return dragX;
  }
  
  public int getDragY() {
    return dragY;
  }
  
}
