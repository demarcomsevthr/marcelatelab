package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditViewImpl extends AbstractBaseView<OrderEditView.Presenter> implements OrderEditView {

  public interface ViewUiBinder extends UiBinder<Widget, OrderEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<OrderEditView.Presenter> adminTab;

  public OrderEditViewImpl() {
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
      public void onSave(Object model) {  
        getPresenter().update((Order)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }

  private void initSections(Order order) {
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<OrderEditView.Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new OrderEditGeneralView()));
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Acquirente")
        .setView(new OrderEditCustomerView()));
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Stato")
        .setView(new OrderEditStateView()));
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Righe")
        .setView(new OrderEditItemsListView()));
    adminTab.setSections(sections);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    Order order = (Order)model;
    nameLabel.setText("Editing order: " + order.getCode());
    initSections(order);
    adminTab.setPresenter(getPresenter());
    adminTab.setModel(model, null);
  }
  
}
