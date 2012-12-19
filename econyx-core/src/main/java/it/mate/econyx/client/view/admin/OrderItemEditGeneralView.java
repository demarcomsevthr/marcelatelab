package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.OrderItemEditView;
import it.mate.econyx.client.view.custom.OrderItemEditViewCustomizer;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemEditGeneralView extends AbstractAdminTabPage<OrderItemEditView.Presenter> implements OrderItemEditView, IsAdminTabPage<OrderItemEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderItemEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private OrderItemEditViewCustomizer customizer = AppClientFactory.Customizer.cast().getOrderItemEditViewCustomizer();
  
  @UiField Panel detailPanel;
  
  private OrderItem orderItem;
  
  public OrderItemEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    this.orderItem = (OrderItem)model;
    customizer.initDetailPanel(detailPanel, orderItem);
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    
  }
  
}
