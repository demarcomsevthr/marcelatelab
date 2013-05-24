package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.OrderListView;
import it.mate.econyx.client.view.ProducerEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProducerEditOrderListView extends AbstractAdminTabPage<ProducerEditView.Presenter> implements ProducerEditView, IsAdminTabPage<ProducerEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProducerEditOrderListView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) OrderListView orderListView;
  
  Button confermaBtn;
  
  Button stampaBtn;
  
  Button excelBtn;
  
  CheckBox disableSendEmailBox;
  
  private Produttore produttore;
  
  private List<Order> ordini;
  
  private String selectedOrderStateCode;
  
  private OrderStateConfig selectedOrderStateConfig;
  
  public ProducerEditOrderListView() {
    initUI();
  }

  @Override
  protected void onAttach() {
    
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int w = ProducerEditOrderListView.this.getOffsetWidth();
        int h = ProducerEditOrderListView.this.getOffsetHeight();
        if (w > 0 && h > 0) {
          orderListView.setWidth((w - 50)+"px");
          orderListView.setHeight((h - 50 - 25)+"px");
        }
      }
    });
    
    super.onAttach();
  }
  
  protected void initProvided() {
    orderListView = new OrderListViewImpl("640px", "400px");
    orderListView.setPresenter(new OrderListView.Presenter() {
      public BaseView getView() {
        return null;
      }
      public void goToPrevious() {
      }
      public void fetchItems(Order order, Delegate<Order> delegate) {
      }
      public void edit(Order order) {
        getPresenter().editOrder(order);
      }
      public void findAllOrderStates(Delegate<List<OrderStateConfig>> delegate) {
        getPresenter().findAllOrderStates(delegate);
      }
      public void getSaldoByPortalUserId(String portalUserId, Delegate<Double> delegate) {
        getPresenter().getSaldoByPortalUserId(portalUserId, delegate);
      }
      public void findOrdersByIds(List<String> ids, Delegate<List<Order>> delegate) {
        getPresenter().findOrdersByIds(ids, delegate);
      }
    });
  }
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));

    confermaBtn = new Button("Conferma ordini in blocco", new ClickHandler() {
      public void onClick(ClickEvent event) {
        confermaListaOrdini(ordini);
      }
    });
    orderListView.addButton(confermaBtn);
    
    stampaBtn = new Button("Stampa distinta per il produttore", new ClickHandler() {
      public void onClick(ClickEvent event) {
        String ordersId = "";
        for (Order order : ordini) {
          if (ordersId.length() > 0)
            ordersId += "|";
          ordersId += order.getId();
        }
        DOM.getElementById("ordersToProducerReportFrame").setAttribute("src", "/re/pdf/ordersToProducer/" + produttore.getId() + "/" + ordersId);
      }
    });
    orderListView.addButton(stampaBtn);
    
    excelBtn = new Button("Esporta distinta in excel", new ClickHandler() {
      public void onClick(ClickEvent event) {
        String ordersId = "";
        for (Order order : ordini) {
          if (ordersId.length() > 0)
            ordersId += "|";
          ordersId += order.getId();
        }
        DOM.getElementById("ordersToProducerReportFrame").setAttribute("src", "/re/excel/ordersToProducer/" + produttore.getId() + "/" + ordersId);
      }
    });
    orderListView.addButton(excelBtn);
    
    orderListView.setOrderStateFilterChangeDelegate(new Delegate<String>() {
      public void execute(String selectedOrderStateCode) {
        onSelectedOrderStateCode(selectedOrderStateCode);
      }
    });
    
    disableSendEmailBox = new CheckBox("disabilita invio mail");
    orderListView.addTopWidget(disableSendEmailBox);

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Produttore) {
      this.produttore = (Produttore)model;
      findOrdersByProducer();
    }
  }
  
  private void findOrdersByProducer() {
    if (produttore != null && selectedOrderStateCode != null) {
      getPresenter().findOrdersByProducer(produttore, selectedOrderStateCode, new Delegate<List<Order>>() {
        public void execute(List<Order> results) {
          ordini = results;
          orderListView.setModel(ordini);
        }
      });
    }
  }
  
  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
  private void onSelectedOrderStateCode (String selectedOrderStateCode) {
    this.selectedOrderStateCode = selectedOrderStateCode;
    getPresenter().findOrderStateConfig(selectedOrderStateCode, new Delegate<OrderStateConfig>() {
      public void execute(OrderStateConfig orderStateConfig) {
        selectedOrderStateConfig = orderStateConfig;
        findOrdersByProducer();
        if (!GwtUtils.isEmpty(orderStateConfig.getNextStateCode())) {
          confermaBtn.setText("Metti tutti gli ordini in stato " + orderStateConfig.getNextStateDescription());
          confermaBtn.setVisible(true);
          stampaBtn.setVisible(orderStateConfig.isPrintButtonEnabled());
          excelBtn.setVisible(orderStateConfig.isPrintButtonEnabled());
        }
      }
    });
  }

  private void confermaListaOrdini (final List<Order> ordini) {
    
    String newStateCode = null;
    
    if (selectedOrderStateConfig != null && !GwtUtils.isEmpty(selectedOrderStateConfig.getNextStateCode())) {
      newStateCode = selectedOrderStateConfig.getNextStateCode();
    } else {
      return;
    }
    
    final String fNewStateCode = newStateCode;
    
    Delegate<String> doUpdateDelegate = new Delegate<String>() {
      public void execute(String deliveryInformations) {
        GwtUtils.showWaitPanel(true);
        for (Order order : ordini) {
          boolean updateOrder = false;
          List<OrderState> statesToUpdate = new ArrayList<OrderState>();
          for (OrderState state : order.getStates()) {
            if (state.getCode().equals(fNewStateCode) && !state.getChecked()) {
              state.setChecked(true);

              // 22/05/2013
              if (disableSendEmailBox.getValue()) {
                state.setDisableEmailToCustomerSubmission(true);
              }
              
              updateOrder = true;
            }
            statesToUpdate.add(state);
          }
          if (updateOrder) {
            order.setStates(statesToUpdate);
            if (!GwtUtils.isEmpty(deliveryInformations)) {
              order.setDeliveryInformations(deliveryInformations);
            }
          }
        }
        getPresenter().updateOrders(ordini, new Delegate<List<Order>>() {
          public void execute(List<Order> updatedOrders) {
            findOrdersByProducer();
            GwtUtils.hideWaitPanel(true);
            Window.alert("Operazione completata");
          }
        });
      }
    };
    
    if (selectedOrderStateConfig.askDeliveryInformations()) {
      askDeliveryInformations(doUpdateDelegate);
    } else {
      doUpdateDelegate.execute(null);
    }
    
  }
  
  private void askDeliveryInformations (final Delegate<String> doUpdateDelegate) {
    VerticalPanel popupPanel = new VerticalPanel();
    final TextBox deliveryInformationsBox = new TextBox();
    deliveryInformationsBox.setWidth("20em");
    String defaultDeliveryInformations = PropertiesHolder.getString("client.ProducerEditOrderListView.defaultDeliveryInformations");
    if (ordini != null && ordini.size() > 0 && !GwtUtils.isEmpty(ordini.get(0).getDeliveryInformations())) {
      defaultDeliveryInformations = ordini.get(0).getDeliveryInformations();
    }
    if (defaultDeliveryInformations != null) {
      deliveryInformationsBox.setText(defaultDeliveryInformations);
    }
    popupPanel.add(GwtUtils.createPopupPanelItem("Informazioni per la consegna:", deliveryInformationsBox, "2em", "16em"));
    MessageBoxUtils.popupOkCancel("Inserire le informazioni per la consegna", popupPanel, "400px", new Delegate<MessageBox.Callbacks> () {
      public void execute(MessageBox.Callbacks callbacks) {
        doUpdateDelegate.execute(deliveryInformationsBox.getText());
      }
    });
  }
  
}
