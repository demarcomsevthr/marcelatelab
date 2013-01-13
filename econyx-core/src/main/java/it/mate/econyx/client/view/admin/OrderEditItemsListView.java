package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.QuantitaBox;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ProvidesKey;

public class OrderEditItemsListView extends AbstractAdminTabPage<OrderEditView.Presenter> implements OrderEditView, IsAdminTabPage<OrderEditView.Presenter> {

  public interface ViewUiBinder extends UiBinder<Widget, OrderEditItemsListView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  
  @UiField (provided=true) CellTableExt<OrderItem> itemsTable;
  
  
  public OrderEditItemsListView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    itemsTable = new CellTableExt<OrderItem>(new ProvidesKey<OrderItem>() {
      public Object getKey(OrderItem item) {
        return item.getId();
      }
    });
    
    itemsTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    itemsTable.addColumnExt("produttore", new CellTableExt.ColumnInfo<OrderItem, String>()
        .setGetter(new CellTableExt.ValueGetter<OrderItem, String>() {
          public String getValue(OrderItem item) {
            return item.getOrder().getProducer().getNome();
          }
        })
        .setCell(new TextCell())
        .setHeaderText("Produttore")
    );
    
    itemsTable.addColumnExt("prodotto", new CellTableExt.ColumnInfo<OrderItem, OrderItem>()
        .setGetter(new CellTableExt.SimpleValueGetter<OrderItem>())
        .setCell(new AnchorCell<OrderItem>() {
          protected String getCellValue(OrderItem item) {
            return item.getProduct().getName();
          }
          protected void onConsumedEvent(NativeEvent event, OrderItem item) {
            getPresenter().editItem(item);
          }
        })
        .setHeaderText("Prodotto")
    );
    
    if (PropertiesHolder.getBoolean("client.OrderEditItemsListView.quantityEditingEnabled")) {
      itemsTable.addColumnExt("quantita", new CellTableExt.ColumnInfo<OrderItem, OrderItem>()
          .setGetter(new CellTableExt.SimpleValueGetter<OrderItem>())
          .setCell(new AnchorCell<OrderItem>() {
            protected String getCellValue(OrderItem item) {
              return GwtUtils.formatCurrency(item.getQuantity());
            }
            protected void onConsumedEvent(NativeEvent event, final OrderItem item) {
              
              if (Order.Utils.isOrderInState(item.getOrder(), OrderStateConfig.CONFIRMED)
                  || Order.Utils.isOrderInState(item.getOrder(), OrderStateConfig.INSERTED)) {
                // OK
              } else {
                Window.alert("Modifica ammessa solo nello stato di ordine CONFERMATO");
                return;
              }
              
              UnitaDiMisura um = item.getProduct().getUnitaDiMisura();
              HorizontalPanel popupPanel = new HorizontalPanel();
              popupPanel.add(new Spacer("1px", "2em"));
              final QuantitaBox quantitaBox = new QuantitaBox(item.getQuantity(), um.getNome(), um.getDecimali(), popupPanel, null);
              MessageBoxUtils.popupOkCancel("Rettifica quantita " + item.getProduct().getNome(), popupPanel, "400px", new Delegate<MessageBox.Callbacks>() {
                public void execute(Callbacks callbacks) {
                  Double qta = quantitaBox.getQuantita();
                  if (qta != null) {
                    item.setQuantity(qta);
                    getPresenter().updateOrderItem(item);
                  }
                }
              });
              GwtUtils.deferredExecution(500, new Delegate<Void>() {
                public void execute(Void element) {
                  quantitaBox.setFocus(true);
                }
              });
              
            }
          })
          .setHeaderText("Quantita")
      );
    } else {
      itemsTable.addColumnExt("quantita", new CellTableExt.ColumnInfo<OrderItem, String>()
          .setGetter(new CellTableExt.ValueGetter<OrderItem, String>() {
            public String getValue(OrderItem item) {
              return GwtUtils.formatCurrency(item.getQuantity());
            }
          })
          .setCell(new TextCell())
          .setHeaderText("Quantita")
      );
    }
    
    itemsTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<OrderItem, String>() {
      public String getValue(OrderItem item) {
        Double qta = item.getQuantity();
        Double prz = item.getProduct().getPrezzo();
        Double imp = (qta != null && prz != null) ? qta * prz : 0d;
        return GwtUtils.formatCurrency(imp);
      }
    }, new TextCell(), null), "Importo");
    
    itemsTable.addCellPreviewHandler(new CellPreviewEvent.Handler<OrderItem>() {
      public void onCellPreview(CellPreviewEvent<OrderItem> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().editItem(event.getValue());
        }
      }
    });
    
    itemsTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    Order order = (Order)model;
    List<OrderItem> items = order.getItems();
    itemsTable.setRowCount(items.size());
    itemsTable.setRowData(items);
    
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
}
