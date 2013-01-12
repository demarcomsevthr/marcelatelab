package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.QuantitaBox;
import it.mate.econyx.client.ui.editors.OrderEditor;
import it.mate.econyx.client.view.OrderEditView;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditGeneralView extends AbstractAdminTabPage<OrderEditView.Presenter> implements OrderEditView, IsAdminTabPage<OrderEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField OrderEditor editor;
  @UiField Button closeBtn;
  @UiField Label importoCalcolatoLbl;
  @UiField Label importoTotaleLbl;
  @UiField Anchor printAnchor;
  @UiField Panel importoTotalePanel;
  @UiField Label produttoreLbl;
  
  private Order order;
  
  public OrderEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    
    // 12/01/2013
    printAnchor.setVisible(false);
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    this.order = (Order)model;
    editor.setModel(order);
    if (order.getProducer() != null)
      produttoreLbl.setText(order.getProducer().getNome());
    closeBtn.setVisible(OrderStateConfig.OPENED.equals(order.getCurrentState()));
    importoCalcolatoLbl.setText(GwtUtils.formatCurrency(Order.Utils.computeImportoTotale(order, true)));
    importoTotaleLbl.setText(GwtUtils.formatCurrency(order.getImportoTotale()));
    printAnchor.setHref("/re/pdf/order/" + order.getId());
    
    if (Order.Utils.isOrderInState(order, OrderStateConfig.CONFIRMED)) {
      
      importoTotalePanel.add(new Spacer("1em"));
      Anchor modificaImportoTotaleAnchor = new Anchor("modifica");
      modificaImportoTotaleAnchor.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          HorizontalPanel popupPanel = new HorizontalPanel();
          popupPanel.add(new Spacer("1px", "2em"));
          final QuantitaBox quantitaBox = new QuantitaBox(order.getImportoTotale(), "EUR", 2, popupPanel, null);
          quantitaBox.setWidth("6em");
          MessageBoxUtils.popupOkCancel("Rettifica importo ordine", popupPanel, "400px", new Delegate<MessageBox.Callbacks>() {
            public void execute(Callbacks callbacks) {
              Double importo = quantitaBox.getQuantita();
              if (importo != null) {
                GwtUtils.log(getClass(), "modificaImportoTotaleAnchor.click", "nuovo importo " + importo);
                order.setImportoTotale(importo);
//              getPresenter().updateOrderItem(item);
                getPresenter().updateImportoTotale(order, importo);
              }
            }
          });
          GwtUtils.deferredExecution(500, new Delegate<Void>() {
            public void execute(Void element) {
              quantitaBox.setFocus(true);
            }
          });
        }
      });
      importoTotalePanel.add(modificaImportoTotaleAnchor);
      
    }
    
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
  @UiHandler ("closeBtn")
  public void onCloseBtn(ClickEvent event) {
    getPresenter().closeOrder(order.getId(), null, null);
  }
  
}
