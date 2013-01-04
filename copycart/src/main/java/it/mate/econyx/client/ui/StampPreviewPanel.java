package it.mate.econyx.client.ui;

import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.Timbro;
import it.mate.econyx.shared.model.impl.OrderItemStampDetailTx;
import it.mate.econyx.shared.utils.StampUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;

public class StampPreviewPanel extends HTMLPanel {

  AbsolutePanel absolutePanel = null;
  
  private Image logoImg;
  
  private DraggableWidget<Image> draggableLogoImg;
  
  private int logoWidth = 0;
  
  private int logoHeight = 0;
  
  private int dragX = -1;
  private int dragY = -1;
  
  float width, height;
  
  public StampPreviewPanel() {
    super("");
    addStyleName("previewPanel");
    List<OrderItemDetail> details = new ArrayList<OrderItemDetail>();
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    details.add(new OrderItemStampDetailTx(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT));
    update(details);
  }
  
  public void setTimbro(Timbro timbro) {
    if (timbro.getLarghezza() != null) {
      width = ((float)timbro.getLarghezza() * Float.parseFloat(PropertiesHolder.getString("timbro.preview.xFactor")));
      setWidth(width+"cm");
    }
    if (timbro.getAltezza() != null) {
      height = ((float)timbro.getAltezza() * Float.parseFloat(PropertiesHolder.getString("timbro.preview.yFactor")));
      setHeight(height+"cm");
    }
    if (timbro.isOval()) {
      int radX = StampPreviewPanel.this.getOffsetWidth() / 2;
      int radY = StampPreviewPanel.this.getOffsetHeight() / 2;
      String radius = radX+"px / "+radY+"px";
      GwtUtils.setBorderRadius(StampPreviewPanel.this, radius);
      GwtUtils.setBorderRadius(absolutePanel, radius);
    }
  }

  public void update (List<OrderItemDetail> details) {
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
  
  /*
  public void update_SAVE (List<OrderItemDetail> details) {
    clear();
    this.getElement().setId("marcello");
    for (OrderItemDetail detail : details) {
      if (detail instanceof OrderItemStampDetailTx) {
        OrderItemStampDetailTx stampDetail = (OrderItemStampDetailTx)detail;
        if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT.equals(stampDetail.getType())) {
          if (stampDetail.getText() != null && stampDetail.getText().length() > 0) {
            Label label = new Label(stampDetail.getText());
            StampUtils.applySettingsOnWidget(label, StampUtils.convertOrderItemDetailToSettings(stampDetail));
            this.add(label);
          } else {
            this.add(new HTML(SafeHtmlUtils.fromTrustedString("&nbsp;")));
          }
        } else if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO.equals(stampDetail.getType()) && stampDetail.getId() != null) {
          Image logoImg = new Image("/re/cu/oil/" + stampDetail.getId());
          GwtUtils.setStyleAttribute(logoImg, "position", "relative");
          int left = stampDetail.getLogoX() - (getOffsetWidth() / 2) + (logoImg.getWidth() / 2) + 2;
          int top = stampDetail.getLogoY() ;
          GwtUtils.setStyleAttribute(logoImg, "left", left + "px");
          GwtUtils.setStyleAttribute(logoImg, "top", top + "px");
          this.add(logoImg);
        }
      }
    }
  }
  */
  
}
