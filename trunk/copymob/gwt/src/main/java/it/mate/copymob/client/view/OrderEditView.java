package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderEditView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
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

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void saveOrderOnServer(Order order, Delegate<Order> delegate);
    public void goToOrderItemEditView(OrderItem orderItem);
    public void goToHomeView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  

  @UiField OnsList itemList;
  
  private Order order;
  
  public OrderEditView() {
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
      itemList.add(new OnsLabel("ORDINE VUOTO!"));
    } else if (model instanceof Order) {
      this.order = (Order)model;
      populateList();
    }
  }
  
  private void populateList() {
    
    OnsenUi.suspendCompilations();
    
    HasTapHandlerImpl.setUseDocEventListener(true);
    
    Iterator<OrderItem> it = order.getItems().iterator();
    
    while (it.hasNext()) {
      
      final OrderItem orderItem = it.next();
      
      Timbro timbro = orderItem.getTimbro();
      
      OnsListItem listItem = new OnsListItem();
      
      OnsHorizontalPanel rowPanel = new OnsHorizontalPanel();
      rowPanel.setAddDirect(true);
      
      String html = "<img src='"+ timbro.getImageData() +"' class='app-cart-item-img'/>";
      HTML img = new HTML(html);
      rowPanel.add(img);
      
      OnsLabel nameLbl = new OnsLabel(timbro.getNome());
      nameLbl.addStyleName("app-cart-item-name");
      rowPanel.add(nameLbl);
      
      OnsHorizontalPanel qtaPanel = new OnsHorizontalPanel();
      qtaPanel.setAddDirect(true);
      qtaPanel.add(new Spacer("2em"));

      final OnsLabel qtaFld = new OnsLabel();
      setQtaLbl(qtaFld, orderItem.getQuantity());
      
      OnsButton fillerBtn = new OnsButton("");
      fillerBtn.addStyleName("app-cart-btn-plus");
      fillerBtn.setIcon("fa-hando-o-right");
      fillerBtn.setModifier("quiet");
      
      qtaPanel.add(fillerBtn);
      qtaPanel.add(qtaFld);
      
      rowPanel.add(qtaPanel);
      
      OnsHorizontalPanel actionsPanel = new OnsHorizontalPanel();
      actionsPanel.setAddDirect(true);
      OnsButton editBtn = new OnsButton("");
      editBtn.addStyleName("app-cart-btn-edit");
      editBtn.setIcon("fa-edit");
      editBtn.setModifier("quiet");
      editBtn.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          getPresenter().goToOrderItemEditView(orderItem);
        }
      });
      actionsPanel.add(editBtn);
      
      rowPanel.add(actionsPanel);
      
      listItem.add(rowPanel);
      
      itemList.add(listItem);

    }
    
    OnsenUi.refreshCurrentPage();
    
    GwtUtils.deferredExecution(1000, new Delegate<Void>() {
      public void execute(Void element) {
        HasTapHandlerImpl.setUseDocEventListener(false);
      }
    });
    
  }
  
  private void setQtaLbl(OnsLabel lbl, double qta) {
    lbl.setText(GwtUtils.formatDecimal(qta, 0));
  }
  
}
