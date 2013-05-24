package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ProvidesKey;

public class OrderListGeneralView extends AbstractAdminTabPage<OrderListView.Presenter> implements OrderListView, IsAdminTabPage<OrderListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

//private static final boolean doFetchItemsOnRangeChange = false;
  
  @UiField (provided=true) CellTableExt<Order> ordersTable;
  @UiField Panel pagerPanel;
  
  private Panel topPagerPanel;
  
  public OrderListGeneralView() {
    initUI();
  }
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ordersTable = new CellTableExt<Order>(new ProvidesKey<Order>() {
      public Object getKey(Order order) {
        return order.getId();
      }
    });
    
    ordersTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    ordersTable.addColumnExt("codice", new CellTableExt.ColumnInfo<Order, Order>()
        .setGetter(new CellTableExt.SimpleValueGetter<Order>())
        .setCell(new AnchorCell<Order>() {
          protected String getCellValue(Order order) {
            return (order instanceof EmptyOrderTx) ? "" : order.getCode();
          }
          protected void onConsumedEvent(NativeEvent event, Order value) {
            getPresenter().edit(value);
          }
        })
        .setHeaderText("Codice")
        .setComparator(new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o1.getCode().compareTo(o2.getCode());
          }
        })
    );
    
    ordersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return (order instanceof EmptyOrderTx) ? "" : order.getDescription();
      }
    }, new TextCell(), null), "Descrizione");
    
    ordersTable.addColumnExt("utente", new CellTableExt.ColumnInfo<Order, String>()
        .setGetter(new CellTableExt.ValueGetter<Order, String>() {
          public String getValue(Order order) {
            return (order instanceof EmptyOrderTx) ? "" : (order.getCustomer().getPortalUser().getScreenName());
          }
        })
        .setCell(new TextCell())
        .setHeaderText("Utente")
        .setComparator(new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o1.getCustomer().getPortalUser().getScreenName().compareTo(o2.getCustomer().getPortalUser().getScreenName());
          }
        })
    );

    ordersTable.addColumnExt("produttore", new CellTableExt.ColumnInfo<Order, String>()
        .setGetter(new CellTableExt.ValueGetter<Order, String>() {
          public String getValue(Order order) {
            return (order instanceof EmptyOrderTx) ? "" : (order.getProducer().getNome());
          }
        })
        .setCell(new TextCell())
        .setHeaderText("Produttore")
        .setComparator(new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o1.getProducer().getNome().compareTo(o2.getProducer().getNome());
          }
        })
    );

    ordersTable.addColumnExt("data", new CellTableExt.ColumnInfo<Order, String>()
        .setGetter(new CellTableExt.ValueGetter<Order, String>() {
          public String getValue(Order order) {
            return (order instanceof EmptyOrderTx) ? "" : GwtUtils.dateToString(order.getCreated(), 10);
          }
        })
        .setCell(new TextCell())
        .setHeaderText("Data")
        .setComparator(new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o1.getCreated().compareTo(o2.getCreated());
          }
        })
    );

    ordersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return (order instanceof EmptyOrderTx) ? "" : GwtUtils.formatCurrency(Order.Utils.computeImportoTotale(order)); 
      }
    }, new TextCell(), null), "Importo");
    
    ordersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return (order instanceof EmptyOrderTx) ? "" : (order.getCurrentState().getConfig().getDescription()); 
      }
    }, new TextCell(), null), "Stato");
    
    ordersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        return (order instanceof EmptyOrderTx) ? "" : GwtUtils.dateToString(order.getCurrentState().getDate()); 
      }
    }, new TextCell(), null), "Data Agg.");
    
    ordersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Order, String>() {
      public String getValue(Order order) {
        if ((order instanceof EmptyOrderTx) || OrderStateConfig.SHIPPED.equals(order.getCurrentState().getCode())) {
          return "";
        }
        return (order instanceof EmptyOrderTx) ? "" : GwtUtils.formatCurrency(order.getCustomer().getPortalUser().getBillingAccount()); 
      }
    }, new TextCell(), null), "Saldo Utente Attuale");
    
    ordersTable.addFillerColumn();
    
    ordersTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Order>() {
      public void onCellPreview(CellPreviewEvent<Order> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    ordersTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  public OrderListGeneralView setOrderListView(OrderListView orderListView) {
    topPagerPanel = new HorizontalPanel();
    orderListView.addTopWidget(topPagerPanel);
    return this;
  }

  public void setModel(Object model, String tag) {
    
    if (Presenter.ORDER_IDS_LIST.equals(tag)) {
      
      List<String> orderIds = (List<String>)model;
      List<Order> orders = new ArrayList<Order>();
      for (String id : orderIds) {
        orders.add(new EmptyOrderTx(id));
      }
      setModel(orders, null);
      asyncFetchOrders();
      
    } else if (model instanceof List) {
      
      List<Order> orders = (List<Order>)model;
      Collections.sort(orders, new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
          return (o1 instanceof EmptyOrderTx) ? 0 : (o2.getCode().compareTo(o1.getCode()));
        }
      });
      ordersTable.setRowDataExt(orders, "codice");
      ordersTable.adaptToViewHeight(this, pagerPanel);
      
      /*
      SimplePager pager = ordersTable.createPager();
      if (topPagerPanel != null && pager != null) {
        topPagerPanel.clear();
        topPagerPanel.add(pager);
      }
      */
      
      for (Order order : orders) {
        updateSaldoOrderCustomer(order);
      }
      
    }
    
  }
  
  private void asyncFetchOrders() {
    List<Order> orders = ordersTable.getModel();
    List<String> idsToFetch = new ArrayList<String>();
    for (Order order : orders) {
      if (order instanceof EmptyOrderTx) {
        idsToFetch.add(order.getId());
        if (idsToFetch.size() >= 10) {
          break;
        }
      }
    }
    if (idsToFetch.size() > 0) {
      getPresenter().findOrdersByIds(idsToFetch, new Delegate<List<Order>>() {
        public void execute(List<Order> fetchedOrders) {
          for (Order fetchedOrder : fetchedOrders) {
            updateSaldoOrderCustomer(fetchedOrder);
            List<Order> ordersInTable = ordersTable.getModel();
            for (int it = 0; it < ordersInTable.size(); it++) {
              Order orderInTable = ordersInTable.get(it);
              if (orderInTable.getId().equals(fetchedOrder.getId())) {
                ordersInTable.set(it, fetchedOrder);
              }
            }
          }
          ordersTable.refreshDataProvider();
          ordersTable.adaptToViewHeight(OrderListGeneralView.this, pagerPanel);
          asyncFetchOrders();
        }
      });
    }
  }
  
  private void updateSaldoOrderCustomer(final Order order) {
    if (!(order instanceof EmptyOrderTx)) {
      if (!OrderStateConfig.SHIPPED.equals(order.getCurrentState().getCode())) {
        getPresenter().getSaldoByPortalUserId(order.getCustomer().getPortalUser().getId(), new Delegate<Double>() {
          public void execute(Double value) {
            order.getCustomer().getPortalUser().setBillingAccount(value);
            ordersTable.refreshDataProvider();
          }
        });
      }
    }
  }
  
  /*
  private void fetchOrdersInRange(final int start, final int end) {
    List<String> idsToFetch = new ArrayList<String>(orderIds.subList(start, end));
    for (Iterator<String> it = idsToFetch.iterator(); it.hasNext();) {
      String idToFetch = it.next();
      if (fetchedOrders.containsKey(idToFetch)) {
        it.remove();
      }
    }
    if (idsToFetch.size() > 0) {
      getPresenter().findOrdersByIds(idsToFetch, new Delegate<List<Order>>() {
        public void execute(List<Order> results) {
          for (Order order : results) {
            fetchedOrders.put(order.getId(), order);
          }
//        ordersTable.setRowDataExt(results, "codice", start);
          fetchOrdersInRange(start, end);
        }
      });
    } else {
      List<String> idsFetched = new ArrayList<String>(orderIds.subList(start, end));
      List<Order> ordersToShow = new ArrayList<Order>();
      for (String id : idsFetched) {
        ordersToShow.add(fetchedOrders.get(id));
      }
      ordersTable.setRowDataExt(ordersToShow, "codice", start);
    }
  }
  */
  
  
  /* 24/05/2013
  private void fetchNextOrderItems(final List<Order> ordersRetrieved, final Iterator<Order> iterator, final HasData<Order> display) {
    if (iterator.hasNext()) {
      if (doFetchItemsOnRangeChange) {
        getPresenter().fetchItems(iterator.next(), new Delegate<Order>() {
          public void execute(Order order) {
            ordersRetrieved.add(order);
            fetchNextOrderItems(ordersRetrieved, iterator, display);
          }
        });
      } else {
        ordersRetrieved.add(iterator.next());
        fetchNextOrderItems(ordersRetrieved, iterator, display);
      }
    } else {
      int start = display.getVisibleRange().getStart();
      display.setRowData(start, ordersRetrieved);
    }
  }
  */

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {
    
  }

  @Override
  public void setOrderStateFilterChangeDelegate(Delegate<String> orderStateFilterChangeDelegate) {
    
  }
  
  @Override
  public void addButton(Button button) {
    
  }
  
  @Override
  public void addTopWidget(Widget widget) {
    
  }
  
  @SuppressWarnings("serial")
  private class EmptyOrderTx extends OrderTx {
    public EmptyOrderTx(String id) {
      setId(id);
    }
  }
  
}
