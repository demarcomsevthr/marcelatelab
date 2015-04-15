package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderListView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.HasTapHandlerImpl;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsHorizontalPanel;
import it.mate.onscommons.client.ui.OnsLabel;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class OrderListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void goToOrderEditView(Order order);
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
      
      OnsListItem listItem = new OnsListItem();
      
      OnsHorizontalPanel rowPanel = new OnsHorizontalPanel();
      rowPanel.setAddDirect(true);
      
      OnsLabel nameLbl = new OnsLabel("Ordine " + order.getCodice());
      nameLbl.addStyleName("app-cart-item-name");
      rowPanel.add(nameLbl);
      
      OnsLabel dateLbl = new OnsLabel("del " + GwtUtils.dateToString(new Date(), "dd/MM/yyyy") );
      dateLbl.addStyleName("app-cart-item-name");
      rowPanel.add(dateLbl);
      
      OnsButton editBtn = new OnsButton("");
      editBtn.addStyleName("app-cart-btn-edit");
      editBtn.setIcon("fa-edit");
      editBtn.setModifier("quiet");
      editBtn.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          getPresenter().goToOrderEditView(order);
        }
      });
      rowPanel.add(editBtn);
      
      listItem.add(rowPanel);
      
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
