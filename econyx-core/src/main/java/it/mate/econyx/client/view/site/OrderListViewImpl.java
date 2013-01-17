package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.shared.model.Order;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;

public class OrderListViewImpl extends AbstractBaseView<OrderListView.Presenter> implements OrderListView {

  public interface ViewUiBinder extends UiBinder<Widget, OrderListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
//@UiField FlexTable orderTable;
  
  @UiField (provided=true) CellTableExt<Order> orderCellTable;
  @UiField Panel pagerPanel;
  
  
  public OrderListViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }

  protected void initProvided() {
    
    ProvidesKey<Order> keyProvider = new ProvidesKey<Order>() {
      public Object getKey(Order item) {
        return item.getId();
      }
    };
    
    orderCellTable = new CellTableExt<Order>(keyProvider);
    
    orderCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    orderCellTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return order.getCode();
      }
    }, new TextCell(), null), "Codice");
    
    orderCellTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return GwtUtils.dateToString(order.getCreated(), 10);
      }
    }, new TextCell(), null), "Data");
    
    orderCellTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return order.getProducer().getNome();
      }
    }, new TextCell(), null), "Produttore");
    
    orderCellTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return order.getCurrentState().getConfig().getDescription();
      }
    }, new TextCell(), null), "Stato");
    
    orderCellTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Order>(), 
    new AnchorCell<Order>() {
      protected String getCellValue(Order order) {
        return "stampa";
      }
      protected void onConsumedEvent(NativeEvent event, Order order) {
        DOM.getElementById("orderReportFrame").setAttribute("src", "/re/pdf/order/" + order.getId());
      }
    }, 
    null), "");
    
    orderCellTable.addFillerColumn();
    
  }
  
  public void setModel(Object model, String tag) {
    if (Presenter.ORDER_LIST_BY_CUSTOMER.equals(tag) && model != null) {
      List<Order> orders = (List<Order>)model;
      if (orders != null && orders.size() > 0) {
        Collections.sort(orders, new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o2.getCreated().compareTo(o1.getCreated());
          }
        });
        orderCellTable.setRowDataExt(orders);
        orderCellTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
          public void execute(SimplePager pager) {
            pagerPanel.add(pager);
          }
        });
      } else {
        orderCellTable.setRowCount(0);
      }
    } else {
      orderCellTable.setRowCount(0);
    }
  }

  /*
  public void setModel(Object model, String tag) {
    if (Presenter.ORDER_LIST_BY_CUSTOMER.equals(tag) && model != null) {
      List<Order> orders = (List<Order>)model;
      int row = 0;
      Collections.sort(orders, new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
          return o2.getCreated().compareTo(o1.getCreated());
        }
      });
      for (Order order : orders) {
        orderTable.setText(row, 0, order.getCode());
        orderTable.setText(row, 1, GwtUtils.dateToString(order.getCreated()));
        orderTable.setText(row, 2, order.getProducer().getNome());
        orderTable.setText(row, 3, order.getCurrentState().getConfig().getDescription());
        orderTable.setWidget(row, 4, new Anchor("stampa", "/re/pdf/order/" + order.getId()));
        row++;
      }
    }
  }
  */
  
  @Override
  public void setOrderStateFilterChangeDelegate(Delegate<String> orderStateFilterChangeDelegate) {
    
  }
  
  @Override
  public void addButton(Button button) {
    
  }
  
}
