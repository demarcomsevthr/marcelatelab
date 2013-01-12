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
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

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
import com.google.gwt.user.client.ui.Widget;

public class ProducerEditOrderListView extends AbstractAdminTabPage<ProducerEditView.Presenter> implements ProducerEditView, IsAdminTabPage<ProducerEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProducerEditOrderListView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) OrderListView orderListView;
  
  /* 30/11/2012 @UiField */ Button confermaBtn;
  
  Button stampaBtn;
  
  Button excelBtn;
  
  private Produttore produttore;
  
  private List<Order> ordini;
  
  private String selectedOrderStateCode;
  
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
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));

    confermaBtn = new Button("Conferma ordini in blocco", new ClickHandler() {
      public void onClick(ClickEvent event) {
        GwtUtils.showWaitPanel();
//      confermaOrdineSuccessivo(ordini.iterator());
        confermaListaOrdini(ordini);
        GwtUtils.hideWaitPanel(true);
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
        ProducerEditOrderListView.this.selectedOrderStateCode = selectedOrderStateCode;
        findOrdersByProducer();
        if (selectedOrderStateCode.equals(OrderStateConfig.INSERTED)) {
          confermaBtn.setText("Metti tutti gli ordini in stato CONFERMATO");
          confermaBtn.setVisible(true);
          stampaBtn.setVisible(false);
          excelBtn.setVisible(false);
        } else if (selectedOrderStateCode.equals(OrderStateConfig.CONFIRMED)) {
          confermaBtn.setText("Metti tutti gli ordini in stato CONSEGNATO");
          confermaBtn.setVisible(true);
          stampaBtn.setVisible(true);
          excelBtn.setVisible(true);
        } else {
          confermaBtn.setVisible(false);
          stampaBtn.setVisible(false);
          excelBtn.setVisible(false);
        }
      }
    });
    

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
    });
    
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
  
  private void confermaListaOrdini (List<Order> ordini) {
    String stateCodeToUpdate = null;
    if (selectedOrderStateCode != null) {
      if (selectedOrderStateCode.equals(OrderStateConfig.INSERTED)) {
        stateCodeToUpdate = OrderStateConfig.CONFIRMED;
      } else if (selectedOrderStateCode.equals(OrderStateConfig.CONFIRMED)) {
        stateCodeToUpdate = OrderStateConfig.SHIPPED;
      } else {
        return;
      }
    }
    for (Order order : ordini) {
      boolean updateOrder = false;
      List<OrderState> statesToUpdate = new ArrayList<OrderState>();
      for (OrderState stato : order.getStates()) {
        if (stato.getCode().equals(stateCodeToUpdate) && !stato.getChecked()) {
          stato.setChecked(true);
          updateOrder = true;
        }
        statesToUpdate.add(stato);
      }
      if (updateOrder) {
        order.setStates(statesToUpdate);
      }
    }
    getPresenter().updateOrders(ordini, new Delegate<List<Order>>() {
      public void execute(List<Order> updatedOrders) {
        findOrdersByProducer();
        Window.alert("Operazione completata");
      }
    });
  }
  
  /*
  @UiHandler ("confermaBtn")
  public void onConfermaBtn (ClickEvent event) {
    confermaOrdineSuccessivo(ordini.iterator());
  }
  
  private void confermaOrdineSuccessivo (final Iterator<Order> orderIterator) {
    String stateCodeToUpdate = null;
    if (selectedOrderStateCode != null) {
      if (selectedOrderStateCode.equals(OrderStateConfig.INSERTED)) {
        stateCodeToUpdate = OrderStateConfig.CONFIRMED;
      } else if (selectedOrderStateCode.equals(OrderStateConfig.CONFIRMED)) {
        stateCodeToUpdate = OrderStateConfig.SHIPPED;
      } else {
        return;
      }
    }
    if (orderIterator.hasNext()) {
      Order order = orderIterator.next();
      boolean updateOrder = false;
      List<OrderState> statesToUpdate = new ArrayList<OrderState>();
      for (OrderState stato : order.getStates()) {
        if (stato.getCode().equals(stateCodeToUpdate) && !stato.getChecked()) {
          stato.setChecked(true);
          updateOrder = true;
        }
        statesToUpdate.add(stato);
      }
      if (updateOrder) {
        order.setStates(statesToUpdate);
        getPresenter().updateOrder(order, new Delegate<Order>() {
          public void execute(Order updatedOrder) {
            confermaOrdineSuccessivo(orderIterator);
          }
        });
      }
    } else {
      findOrdersByProducer();
      Window.alert("Operazione completata");
    }
  }
  */
  
}
