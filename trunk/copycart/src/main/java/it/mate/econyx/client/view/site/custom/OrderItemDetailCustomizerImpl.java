package it.mate.econyx.client.view.site.custom;

import it.mate.econyx.client.ui.StampPreviewPanel;
import it.mate.econyx.client.ui.TextControlBar;
import it.mate.econyx.client.ui.TextControlBar.Settings;
import it.mate.econyx.client.ui.UploadFileDialog;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizer;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.Timbro;
import it.mate.econyx.shared.model.impl.OrderItemStampDetailTx;
import it.mate.econyx.shared.utils.StampUtils;
import it.mate.gwtcommons.client.ui.ListPanel;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.ui.SpinnerIntegerBox;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class OrderItemDetailCustomizerImpl implements OrderItemDetailCustomizer {

  Timbro timbro = null;
  
  List<OrderItemDetail> details = null;
  
  Panel detailPanel;
  
  List<TextBox> textBoxs;
  List<TextControlBar> textControlBars;
  StampPreviewPanel stampPreviewPanel;
  
  Button logoUploadBtn;
  HorizontalPanel logoUpdatePanel;
  SpinnerIntegerBox logoX;
  SpinnerIntegerBox logoY;
  
  SpinnerIntegerBox borderSizeBox;
  
  OrderItemStampDetailTx logoDetail = null;
  OrderItemStampDetailTx borderDetail = null;
  
  public void setDetailPanel(Panel detailPanel) {
    this.detailPanel = detailPanel;
  }
  
  public void setArticolo(Articolo articolo) {
    if (articolo instanceof Timbro) {
      this.timbro = (Timbro)articolo;
      this.details = null;
      initDetailPanel();
    }
  }
  
  public void setOrderItem(OrderItem orderItem) {
    if (orderItem.getProduct() instanceof Timbro) {
      this.timbro = (Timbro)orderItem.getProduct();
      this.details = orderItem.getDetails();
      for (OrderItemDetail detail : orderItem.getDetails()) {
        if (detail instanceof OrderItemStampDetailTx) {
          OrderItemStampDetailTx stampDetail = (OrderItemStampDetailTx)detail;
          if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO.equals(stampDetail.getType())) {
            logoDetail = stampDetail;
          }
          if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_BORDER.equals(stampDetail.getType())) {
            borderDetail = stampDetail;
          }
        }
      }
      initDetailPanel();
    }
  }

  public List<OrderItemDetail> getDetails() {
    List<OrderItemDetail> details = new ArrayList<OrderItemDetail>();
    for (int it = 0; it < textBoxs.size(); it++) {
      TextBox textBox = textBoxs.get(it);
      TextControlBar textControlBar = textControlBars.get(it);
      OrderItemDetail detail = StampUtils.convertSettingsToOrderItemDetail(textBox.getText(), textControlBar.getSettings());
      details.add(detail);
    }
    if (logoDetail != null) {
      logoDetail.setLogoX(logoX.getValue());
      logoDetail.setLogoY(logoY.getValue());
      details.add(logoDetail);
    }
    if (borderDetail != null) {
      borderDetail.setBorderSize(borderSizeBox.getValue());
      details.add(borderDetail);
    }
    return details;
  }
  
  private void init() {
    textBoxs = new ArrayList<TextBox>();
    textControlBars = new ArrayList<TextControlBar>();
    stampPreviewPanel = new StampPreviewPanel();
    logoUploadBtn = new Button("Inserisci logo", new ClickHandler() {
      public void onClick(ClickEvent event) {
        showUploadFileDialog();
      }
    });
    logoX = new SpinnerIntegerBox(0, 1, 0);
    logoX.setWidth("4em");
    logoY = new SpinnerIntegerBox(0, 1, 0);
    logoY.setWidth("4em");
    if (logoDetail != null) {
      logoX.setValue(logoDetail.getLogoX());
      logoY.setValue(logoDetail.getLogoY());
    }
  }
  
  private void initDetailPanel() {
    
    init();
    
    detailPanel.clear();
    
    ListPanel detailListPanel = new ListPanel();
    
    int numRighe = timbro.getNumRighe() != null ? timbro.getNumRighe() : 3;
    
    if (details != null) {
      numRighe = Math.max(numRighe, details.size());
    }
    
    for (int it = 0; it < numRighe; it++) {
      addDetailListRow(detailListPanel, it);
    }
    
    detailPanel.add(detailListPanel);

    detailPanel.add(new Spacer("1px", "20px"));
    
    HorizontalPanel logoHPanel = new HorizontalPanel();
    logoHPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    logoHPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    
    logoHPanel.add(new Label("Bordo:"));
    logoHPanel.add(new Spacer("10px"));
    borderSizeBox = new SpinnerIntegerBox(0, 1, 0);
    borderSizeBox.setWidth("3em");
    if (borderDetail != null) {
      borderSizeBox.setValue(borderDetail.getBorderSize());
    }
    logoHPanel.add(borderSizeBox);
    logoHPanel.add(new Spacer("20px"));
    
    logoHPanel.add(logoUploadBtn);
    logoUpdatePanel = new HorizontalPanel();
    logoUpdatePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    logoUpdatePanel.setVisible(logoDetail != null);
    logoUpdatePanel.add(new Spacer("10px"));
    /*
    logoUpdatePanel.add(logoX);
    logoUpdatePanel.add(logoY);
    */
    logoUpdatePanel.add(new Button("rimuovi logo", new ClickHandler() {
      public void onClick(ClickEvent event) {
        logoDetail = null;
        logoUpdatePanel.setVisible(false);
      }
    }));
    logoUpdatePanel.add(new Spacer("10px"));
    Label logoNote = new Label("Trascina l'immagine con il mouse per posizionarla dove vuoi");
    GwtUtils.setStyleAttribute(logoNote, "fontSize", "9px");
    logoUpdatePanel.add(logoNote);
    logoHPanel.add(logoUpdatePanel);
    detailPanel.add(logoHPanel);
    
    detailPanel.add(new Spacer("1px", "20px"));
    
    detailPanel.add(new Label("Anteprima:"));
    
    detailPanel.add(stampPreviewPanel);
    
    stampPreviewPanel.setTimbro(timbro);

    GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        updatePreview();
      }
    });
    
  }
  
  private void addDetailListRow(ListPanel detailListPanel, int index) {
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

    final TextBox textBox = new TextBox();
    textBox.setWidth("240px");
    textBox.setHeight("26px");
    hPanel.add(textBox);
    textBoxs.add(textBox);
    
    textBox.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        updatePreview();
      }
    });
    
    TextControlBar.Settings initalSettings = new TextControlBar.Settings(true);    
    
    if (this.details != null && index < this.details.size()) {
      OrderItemDetail orderItemDetail = this.details.get(index);
      if (orderItemDetail != null && orderItemDetail instanceof OrderItemStampDetailTx) {
        OrderItemStampDetailTx orderItemStampDetail = (OrderItemStampDetailTx)orderItemDetail;
        if (StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT.equals(orderItemStampDetail.getType())) {
          textBox.setText(orderItemStampDetail.getText());
          initalSettings = StampUtils.convertOrderItemDetailToSettings(orderItemStampDetail);
        }
      }
    }
    
    TextControlBar textControlBar = new TextControlBar(initalSettings);
    textControlBar.setSettingsChangeDelegate(new Delegate<TextControlBar.Settings>() {
      public void execute(Settings settings) {
        StampUtils.applySettingsOnWidget(textBox, settings);
        updatePreview();
      }
    });
    hPanel.add(textControlBar);
    
    textControlBars.add(textControlBar);
    
    detailListPanel.add(hPanel);
  }

  private void updatePreview () {
    if (stampPreviewPanel == null)
      return;
    List<OrderItemDetail> details = new ArrayList<OrderItemDetail>(); 
    for (int it = 0; it < textBoxs.size(); it++) {
      TextBox textBox = textBoxs.get(it);
      if (textControlBars.size() > it) {
        TextControlBar textControlBar = textControlBars.get(it);
        details.add(StampUtils.convertSettingsToOrderItemDetail(textBox.getText(), textControlBar.getSettings()));
      }
    }
    if (logoDetail != null) {
      if (stampPreviewPanel.getDragX() > -1) {
        logoX.setValue(stampPreviewPanel.getDragX());
      }
      if (stampPreviewPanel.getDragY() > -1) {
        logoY.setValue(stampPreviewPanel.getDragY());
      }
      logoDetail.setLogoX(logoX.getValue());
      logoDetail.setLogoY(logoY.getValue());
      details.add(logoDetail);
    }
    stampPreviewPanel.update(details);
    logoX.setMaxvalue(stampPreviewPanel.getOffsetWidth() - stampPreviewPanel.getLogoWidth());
    logoY.setMaxvalue(stampPreviewPanel.getOffsetHeight() - stampPreviewPanel.getLogoHeight());
  }
  
  
  private void showUploadFileDialog() {
    new UploadFileDialog("orderItemLogo", logoDetail != null && logoDetail.getId() != null ? logoDetail.getId() : "session", "", true, new Delegate<String>() {
      public void execute(String results) {
        results = results.replace("<pre>", "");
        results = results.replace("</pre>", "");
        String[] fields = results.split("\\|");
        for (String field : fields) {
          GwtUtils.log(getClass(), "showUploadFileDialog", "received field " + field);
        }
        if (logoDetail == null) {
          logoDetail = new OrderItemStampDetailTx();
        }
        logoDetail.setLogo(fields[1]);
        logoDetail.setType(StampUtils.ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO);
        logoUpdatePanel.setVisible(true);
      }
    });
  }
  
}
