package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderListView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.HasTapHandlerImpl;
import it.mate.onscommons.client.ui.OnsHorizontalPanel;
import it.mate.onscommons.client.ui.OnsIcon;
import it.mate.onscommons.client.ui.OnsLabel;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;
import it.mate.onscommons.client.ui.OnsVerticalPanel;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class OrderListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void goToOrderEditView(Order order);
    public void saveOrderOnDevice(Order order, Delegate<Order> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField OnsList orderList;
  
  private List<Order> orders;
  
  public OrderListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setModel(Object model, String tag) {
    if (model == null) {
      orderList.add(new OnsLabel("Nessun ordine presente"));
    } else if (model instanceof List) {
      this.orders = (List)model;
      populateList();
    }
  }
  
  private void populateList() {
    
    OnsenUi.suspendCompilations();
    
    HasTapHandlerImpl.setUseDocEventListener(true);
    
    for (final Order order : orders) {
      
      TapHandler orderTapHandler = new TapHandler() {
        public void onTap(TapEvent event) {
          
          if ("U".equals(order.getUpdateState())) {
            order.setUpdateState("V");
            getPresenter().saveOrderOnDevice(order, new Delegate<Order>() {
              public void execute(Order order) {
                getPresenter().goToOrderEditView(order);
              }
            });
          } else {
            getPresenter().goToOrderEditView(order);
          }
          
        }
      };
      
      OnsListItem listItem = new OnsListItem();
      
      OnsHorizontalPanel itemPanel = new OnsHorizontalPanel();
      
      OnsIcon ordIcon = new OnsIcon();
      OnsenUi.addTapHandler(ordIcon, orderTapHandler);
      ordIcon.setIcon("fa-hand-o-right");
      ordIcon.addStyleName("app-cart-item-icon");
      itemPanel.add(ordIcon);
      
      OnsVerticalPanel dataPanel = new OnsVerticalPanel();
      
      OnsHorizontalPanel row1Panel = new OnsHorizontalPanel();
      row1Panel.setAddDirect(true);
      
      row1Panel.add(new Spacer("1em"));
      
      OnsLabel nameLbl = new OnsLabel("Ordine " + order.getCodice());
      OnsenUi.addTapHandler(nameLbl, orderTapHandler);
      nameLbl.addStyleName("app-cart-item-name");
      row1Panel.add(nameLbl);
      
      OnsLabel dateLbl = new OnsLabel("del " + GwtUtils.dateToString(new Date(), "dd/MM/yyyy") );
      OnsenUi.addTapHandler(dateLbl, orderTapHandler);
      dateLbl.addStyleName("app-cart-item-name");
      row1Panel.add(dateLbl);
      
      dataPanel.add(row1Panel);
      
      OnsHorizontalPanel row2Panel = new OnsHorizontalPanel();
      row2Panel.setAddDirect(true);
      
      row2Panel.add(new Spacer("1em"));
      
      String descStato = "";
      if (order.getState() == Order.STATE_SENT) {
        descStato = "inviato";
      } else if (order.getState() == Order.STATE_RECEIVED) {
        descStato = "registrato";
      } else if (order.getState() == Order.STATE_PREVIEW_IN_PROGRESS) {
        descStato = "preelaborazione in corso";
      } else if (order.getState() == Order.STATE_PREVIEW_AVAILABLE) {
        descStato = "immagine di controllo disponibile";
      } else if (order.getState() == Order.STATE_PREVIEW_PAYED) {
        descStato = "pagato";
      } else if (order.getState() == Order.STATE_WORK_IN_PROGRESS) {
        descStato = "in lavorazione";
      } else if (order.getState() == Order.STATE_SHIPED) {
        descStato = "spedito";
      } else if (order.getState() == Order.STATE_CLOSE) {
        descStato = "chiuso";
      }
      
      OnsLabel stateLbl = new OnsLabel("Stato: " + descStato );
      OnsenUi.addTapHandler(stateLbl, orderTapHandler);
      stateLbl.addStyleName("app-cart-item-state");
      if ("U".equals(order.getUpdateState())) {
        stateLbl.getElement().getStyle().setFontWeight(FontWeight.BOLD);
      }
      row2Panel.add(stateLbl);
      
      dataPanel.add(row2Panel);
      
      itemPanel.add(dataPanel);
      
      listItem.add(itemPanel);
      
      orderList.add(listItem);
      
    }
    
    OnsenUi.refreshCurrentPage();
    
    GwtUtils.deferredExecution(1000, new Delegate<Void>() {
      public void execute(Void element) {
        HasTapHandlerImpl.setUseDocEventListener(false);
      }
    });
    
  }
  
}
