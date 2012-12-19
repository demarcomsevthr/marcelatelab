package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditCustomerView extends AbstractAdminTabPage<OrderEditView.Presenter> implements OrderEditView, IsAdminTabPage<OrderEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderEditCustomerView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField TextBox emailBox;
  @UiField TextBox spedNomeBox;
  @UiField TextBox spedCognomeBox;
  @UiField TextBox spedAziendaBox;
  @UiField TextBox spedViaBox;
  @UiField TextBox spedCapBox;
  @UiField TextBox spedCittaBox;
  @UiField TextBox spedTelefonoBox;
  
  private Order order;
  
  public OrderEditCustomerView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    this.order = (Order)model;
    emailBox.setText(order.getCustomer().getPortalUser().getEmailAddress());
    if (order.getCustomer().getIndirizzoSpedizione() != null) {
      spedNomeBox.setText(order.getCustomer().getIndirizzoSpedizione().getNome());
      spedCognomeBox.setText(order.getCustomer().getIndirizzoSpedizione().getCognome());
      spedAziendaBox.setText(order.getCustomer().getIndirizzoSpedizione().getAzienda());
      spedViaBox.setText(order.getCustomer().getIndirizzoSpedizione().getVia());
      spedCapBox.setText(order.getCustomer().getIndirizzoSpedizione().getCap());
      spedCittaBox.setText(order.getCustomer().getIndirizzoSpedizione().getCitta());
      spedTelefonoBox.setText(order.getCustomer().getIndirizzoSpedizione().getTelefono());
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
}
