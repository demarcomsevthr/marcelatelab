package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderState;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditStateView extends AbstractAdminTabPage<OrderEditView.Presenter> implements OrderEditView, IsAdminTabPage<OrderEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderEditStateView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField FlexTable statesTable;
  
  private Order order;
  
  private class OrderStateViewModel {
    OrderState state;
    int row;
    public OrderStateViewModel(OrderState state, int row) {
      super();
      this.state = state;
      this.row = row;
    }
  }
  
  private List<OrderStateViewModel> orderStateViewModels = new ArrayList<OrderEditStateView.OrderStateViewModel>();
  
  public OrderEditStateView() {
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
    updateStatesTable();
  }
  
  private void updateStatesTable() {
    statesTable.removeAllRows();
    List<OrderState> states = order.getStates();
    Collections.sort(states, new Comparator<OrderState>() {
      public int compare(OrderState s1, OrderState s2) {
        return s1.getConfig().getOrderNm().compareTo(s2.getConfig().getOrderNm());
      }
    });
    int row = 0;
    statesTable.setCellSpacing(0);
    statesTable.setCellPadding(4);
    statesTable.setText(row, 0, "Stato");
    GwtUtils.setFlexCellColSpan(statesTable, row, 0, 2);
    statesTable.setText(row, 1, "Data");
    statesTable.setText(row, 2, "User");
    GwtUtils.setFlexCellColSpan(statesTable, row, 2, 2);
    GwtUtils.setFlexRowClassName(statesTable, row, "econyx-StatesTableHeader");
    row++;
    orderStateViewModels.clear();
    for (OrderState state : states) {
      CheckBox stateCheckBox = new CheckBox();
      stateCheckBox.setValue(state.getChecked());
      stateCheckBox.setEnabled(checkRequiredOrderStateCodes(state, states) && !checkDisablingOrderStateCodes(state, states));
      statesTable.setWidget(row, 0, stateCheckBox);
      statesTable.setText(row, 1, state.getConfig().getDescription());
      if (state.getChecked()) {
        statesTable.setText(row, 2, GwtUtils.dateToString(state.getDate()));
        if (state.getPortalUser() != null) {
          statesTable.setText(row, 3, state.getPortalUser().getScreenName());
          statesTable.setText(row, 4, state.getPortalUser().getEmailAddress());
        }
      }
      orderStateViewModels.add(new OrderStateViewModel(state, row));
      row++;
    }
  }
  
  private boolean checkRequiredOrderStateCodes(OrderState state, List<OrderState> otherStates) {
    String requiredOrderStateCodes = state.getConfig().getRequiredOrderStateCodes();
    if (requiredOrderStateCodes != null && requiredOrderStateCodes.trim().length() > 0) {
      String[] codes = requiredOrderStateCodes.split(",");
      for (String requiredCode : codes) {
        for (OrderState otherState : otherStates) {
          if (otherState.getChecked() && requiredCode.equals(otherState.getCode())) {
            return true;
          }
        }
      }
      return false;
    } else {
      return true;
    }
  }
  
  private boolean checkDisablingOrderStateCodes(OrderState state, List<OrderState> otherStates) {
    String disablingOrderStateCodes = state.getConfig().getDisablingOrderStateCodes();
    if (disablingOrderStateCodes != null && disablingOrderStateCodes.trim().length() > 0) {
      String[] codes = disablingOrderStateCodes.split(",");
      for (String requiredCode : codes) {
        for (OrderState otherState : otherStates) {
          if (otherState.getChecked() && requiredCode.equals(otherState.getCode())) {
            return true;
          }
        }
      }
      return false;
    } else {
      return false;
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    List<OrderState> statesToUpdate = new ArrayList<OrderState>();
    for (OrderStateViewModel stateViewModel : orderStateViewModels) {
      CheckBox checkBox = (CheckBox)statesTable.getWidget(stateViewModel.row, 0);
      stateViewModel.state.setChecked(checkBox.getValue());
      statesToUpdate.add(stateViewModel.state);
    }
    Order orderToUpdate = (Order)model;
    orderToUpdate.setStates(statesToUpdate);
    delegate.execute(orderToUpdate);
  }
  
}
