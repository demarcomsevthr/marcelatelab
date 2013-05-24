package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderListViewImpl extends AbstractBaseView<OrderListView.Presenter> implements OrderListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  
  @UiField (provided=true) AdminTabPanel<OrderListView.Presenter> adminTab;
  
  @UiField Panel filterPanel;
  @UiField ListBox orderStateFilterBox;
  @UiField Panel additionalWidgetsPanel;
  

  private String width;
  
  private String height;
  
  private Delegate<String> orderStateFilterChangeDelegate;

  
  public OrderListViewImpl() {
    initUI();
  }
  
  public OrderListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new OrderListGeneralView().setOrderListView(this)));
    adminTab.setSections(sections);
  }
  
  @Override
  protected void onAttach() {
    populateOrderStatesListBox(orderStateFilterBox, new Delegate<Void>() {
      public void execute(Void element) {
        orderStateFilterBox.setSelectedIndex(1);
        orderStateFilterBox.addChangeHandler(new ChangeHandler() {
          public void onChange(ChangeEvent event) {
            fireOrderStateFilterChangeDelegate();
          }
        });
        fireOrderStateFilterChangeDelegate();
      }
    });
    super.onAttach();
  }
  
  private void populateOrderStatesListBox(final ListBox listBox, final Delegate<Void> completeDelegate) {
    getPresenter().findAllOrderStates(new Delegate<List<OrderStateConfig>>() {
      public void execute(List<OrderStateConfig> results) {
        if (results != null) {
          for (OrderStateConfig state : results) {
            listBox.addItem(state.getDescription(), state.getCode());
          }
        }
        completeDelegate.execute(null);
      }
    });
  }
  
  private void fireOrderStateFilterChangeDelegate() {
    if (orderStateFilterChangeDelegate != null) {
      String selectedCode = GwtUtils.getSelectedValue(orderStateFilterBox);
      if (selectedCode != null) {
        orderStateFilterChangeDelegate.execute(selectedCode);
      }
    }
  }
  
  public void setOrderStateFilterChangeDelegate(Delegate<String> orderStateFilterChangeDelegate) {
    this.orderStateFilterChangeDelegate = orderStateFilterChangeDelegate;
    fireOrderStateFilterChangeDelegate();
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(false).setWidth(width).setHeight(height)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() { }
    };
  }
  
  @Override
  public void setWidth(String width) {
    adminTab.setWidth(width);
    super.setWidth(width);
  }
  
  @Override
  public void setHeight(String height) {
    adminTab.setHeight(height);
    super.setHeight(height);
  }

  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, tag);
  }
  
  public void addButton (Button button) {
    adminTab.addButton(button);
  }
  
  public void addTopWidget(Widget widget) {
    additionalWidgetsPanel.add(widget);
  }
  
}
