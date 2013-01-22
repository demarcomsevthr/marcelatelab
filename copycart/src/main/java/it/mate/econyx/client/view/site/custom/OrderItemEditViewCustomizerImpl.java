package it.mate.econyx.client.view.site.custom;

import it.mate.econyx.client.ui.StampPreviewPanel;
import it.mate.econyx.client.view.custom.OrderItemEditViewCustomizer;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.Timbro;

import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrderItemEditViewCustomizerImpl implements OrderItemEditViewCustomizer {

  StampPreviewPanel previewPanel;
  
  public void initDetailPanel(Panel detailPanel, OrderItem orderItem) {
    detailPanel.clear();
    VerticalPanel vPanel = new VerticalPanel();
    if (orderItem.getProduct() instanceof Timbro) {
      previewPanel = new StampPreviewPanel();
      vPanel.add(new Label("Anteprima del timbro:"));
      Timbro timbro = (Timbro)orderItem.getProduct();
      previewPanel.setTimbro(timbro);
      List<OrderItemDetail> details = orderItem.getDetails();
      previewPanel.setDetails(details);
      vPanel.add(previewPanel);
    }
    detailPanel.add(vPanel);
  }
  
}
