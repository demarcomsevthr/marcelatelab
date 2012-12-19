package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.OrderItemEditView;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemEditViewImpl extends AbstractBaseView<OrderItemEditView.Presenter> implements OrderItemEditView {

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<OrderItemEditView.Presenter> adminTab;

  public OrderItemEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }

  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) {  }
      @Override
      public void onNewModelRequested() { }
    };
    
  }

  private void initSections(OrderItem orderItem) {
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<OrderItemEditView.Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Dettagli")
        .setView(new OrderItemEditGeneralView()));
    adminTab.setSections(sections);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    OrderItem orderItem = (OrderItem)model;
    nameLabel.setText("Editing order item: " + orderItem.getOrder().getDescription()+" / " + orderItem.getProduct().getName());
    initSections(orderItem);
    adminTab.setPresenter(getPresenter());
    adminTab.setModel(model, null);
  }
  
}
