package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.ShoppingCartView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingCartSummaryViewImpl extends AbstractBaseView<ShoppingCartView.Presenter> implements ShoppingCartView {

  public interface ViewUiBinder extends UiBinder<Widget, ShoppingCartSummaryViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField HTML info;
  @UiField FlexTable orderTable;
  
  public ShoppingCartSummaryViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    resetState();
  }

  public void setModel(Object model, String tag) {
    if (model instanceof Order) {
      info.setText("");
      orderTable.clear(true);
      Order order = (Order)model;
      if (order.getItems() != null && order.getItems().size() > 0) {
        int row = 0;
        for (int it = 0; it < order.getItems().size(); it++) {
          OrderItem item = order.getItems().get(it);
          row = it;
          orderTable.setText(row, 0, item.getProduct().getName());
          orderTable.setText(row, 1, GwtUtils.formatCurrency(item.getQuantity()));
        }
        row += 2;
        Anchor anchor = new Anchor("Visualizza il carrello");
        orderTable.setWidget(row, 0, anchor);
        GwtUtils.setFlexCellColSpan(orderTable, row, 0, 2);
        anchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            getPresenter().goToDetailedView();
          }
        });
      } else {
        resetState();
      }
    } else {
//    resetState();
    }
  }
  
  private void resetState() {
    info.setHTML(SafeHtmlUtils.fromTrustedString("Il carrello &egrave; vuoto"));
    orderTable.clear(true);
  }
  
}
