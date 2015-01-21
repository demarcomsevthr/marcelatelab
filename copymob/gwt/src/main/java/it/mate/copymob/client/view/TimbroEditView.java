package it.mate.copymob.client.view;

import it.mate.copymob.client.view.TimbroEditView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.impl.OrderItemRowTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.ui.OnsTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class TimbroEditView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void saveCurrentOrderItem(OrderItem item, Delegate<Order> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, TimbroEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsTextBox row0;
  @UiField OnsTextBox row1;
  @UiField OnsTextBox row2;
  
  private OrderItem item;
  
  public TimbroEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof OrderItem) {
      item = (OrderItem)model;
      for (int it = 0; it < item.getRows().size(); it++) {
        OrderItemRow row = item.getRows().get(it);
        if (it == 0) row0.setText(row.getText());
        if (it == 1) row1.setText(row.getText());
        if (it == 2) row2.setText(row.getText());
      }
    }
  }
  
  private OrderItem flushModel() {
    if (item.getRows().size() <= 0)
      item.getRows().add(new OrderItemRowTx());
    if (item.getRows().size() <= 1)
      item.getRows().add(new OrderItemRowTx());
    if (item.getRows().size() <= 2)
      item.getRows().add(new OrderItemRowTx());
    item.getRows().get(0).setText(row0.getText());
    item.getRows().get(1).setText(row1.getText());
    item.getRows().get(2).setText(row2.getText());
    return item;
  }

  @UiHandler("btnSave")
  public void onBtnSave(TapEvent event) {
    getPresenter().saveCurrentOrderItem(flushModel(), null);
  }
  
}
