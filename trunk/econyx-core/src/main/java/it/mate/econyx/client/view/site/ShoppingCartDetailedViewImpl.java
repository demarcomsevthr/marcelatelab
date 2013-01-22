package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.QuantitaBox;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.client.view.ShoppingCartView;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizer;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.ImageButton;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.StatePanel;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.StatePanelUtil;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingCartDetailedViewImpl extends AbstractBaseView<ShoppingCartView.Presenter> implements ShoppingCartView {

  public interface ViewUiBinder extends UiBinder<Widget, ShoppingCartDetailedViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private OrderItemDetailCustomizer orderItemDetailCustomizer = AppClientFactory.Customizer.cast().getOrderItemDetailCustomizer();

  interface Style extends CssResource {
    String qtaBox();
  }
  
  @UiField Style style;
  
  @UiField FlexTable orderTable1;
  @UiField FlexTable orderTable3;
  @UiField StatePanel step0StatePanel;
  @UiField StatePanel step1StatePanel;
  @UiField StatePanel step2StatePanel;
  @UiField StatePanel step3StatePanel;
  @UiField StatePanel finalStatePanel;
  @UiField StatePanel itemDetailStatePanel;
  @UiField ImageButton step1OkBtn;
  
  @UiField CustomerEditView customerView;
  
  @UiField ListBox modalitaSpedizioneBox;
  @UiField Label prezzoModalitaSpedizioneLabel;
  
  @UiField ListBox modalitaPagamentoBox;
  @UiField Panel modalitaPagamentoNotePanel;
  
  @UiField Panel itemDetailPanel;
  @UiField Label itemDetailNameLabel;
  @UiField HorizontalPanel qtaBoxPanel;
  QuantitaBox quantitaBox;

  @UiField Label totaleOrdineLbl;
  
  StatePanelUtil statePanelUtil = new StatePanelUtil();
  
  private Order order;
  
  private List<ModalitaSpedizione> listaModalitaSpedizione;
  private ModalitaSpedizione modalitaSpedizioneSelected;
  
  private List<ModalitaPagamento> listaModalitaPagamento;
  private ModalitaPagamento modalitaPagamentoSelected;
  
  private OrderItem orderItemSelected = null;
  
  public ShoppingCartDetailedViewImpl() {
    super();
    initUI();
    statePanelUtil.setCurrentState(step1StatePanel.getStateId());
    statePanelUtil.add(step0StatePanel);
    statePanelUtil.add(step1StatePanel);
    statePanelUtil.add(step2StatePanel);
    statePanelUtil.add(step3StatePanel);
    statePanelUtil.add(finalStatePanel);
    statePanelUtil.add(itemDetailStatePanel);
    
    if (AppClientFactory.IMPL.getPortalSessionState().getOpenOrderId() == null) {
      statePanelUtil.setCurrentState(step0StatePanel.getStateId());
    }
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    initOrderTable(orderTable1);
    initOrderTable(orderTable3);
    orderItemDetailCustomizer.setDetailPanel(itemDetailPanel);

    String step1OkBtnText = PropertiesHolder.getString("client.ShoppingCartDetailView.step1OkBtn.text");
    if (step1OkBtnText != null) {
      step1OkBtn.setText(step1OkBtnText);
    }
    
  }
  
  private void initOrderTable(FlexTable orderTable) {
    orderTable.setStyleName("basketTable");
    orderTable.setCellSpacing(0);
  }

  public void setModel(Object model, String tag) {
    if (model instanceof Order) {
      this.order = (Order)model;
      setModelInOrderTable(orderTable1);
      setModelInOrderTable(orderTable3);
    } else if (model instanceof List && Presenter.LISTA_MODALITA_SPEDIZIONE.equals(tag)) {
      this.listaModalitaSpedizione = (List<ModalitaSpedizione>)model;
      for (ModalitaSpedizione modalitaSpedizione : listaModalitaSpedizione) {
        modalitaSpedizioneBox.addItem(modalitaSpedizione.getDescrizione(), modalitaSpedizione.getCodice());
      }
      onModalitaSpedizioneBoxChanged(0);
    } else if (model instanceof List && Presenter.LISTA_MODALITA_PAGAMENTO.equals(tag)) {
      this.listaModalitaPagamento = (List<ModalitaPagamento>)model;
      for (ModalitaPagamento modalitaPagamento : listaModalitaPagamento) {
        modalitaPagamentoBox.addItem(modalitaPagamento.getDescrizione(), modalitaPagamento.getCodice());
      }
      onModalitaPagamentoBoxChanged(0);
    } else if (model instanceof OrderItem && Presenter.ORDER_ITEM.equals(tag)) {
      OrderItem freshOrderItem = (OrderItem)model;
      for (int it = 0; it < order.getItems().size(); it++) {
        OrderItem item  = order.getItems().get(it);
        // 19/11/2012
//      if (freshOrderItem.equals(item)) {
        if (freshOrderItem.getProduct().getCodice().equals(item.getProduct().getCodice())) {
          order.getItems().set(it, freshOrderItem);
          setModelInOrderTable(orderTable1);
          setModelInOrderTable(orderTable3);
          break;
        }
      }
    }
  }
  
  private void setModelInOrderTable(FlexTable orderTable) {
    orderTable.clear(true);
    setHeaderHtml(orderTable, 0, 0, "Quantit&agrave;", "80px");
    setHeaderHtml(orderTable, 0, 1, "Descrizione", "220px");
    setHeaderHtml(orderTable, 0, 2, "Prezzo", "80px");
    setHeaderHtml(orderTable, 0, 3, "Importo", "80px");
    setHeaderHtml(orderTable, 0, 4, "", "60px");
    if (order.getItems() != null) {
      int row = 0;
      for (int it = 0; it < order.getItems().size(); it++) {
        OrderItem orderItem = order.getItems().get(it);
        row = it + 1;
        setCellContent(orderTable, row, 0, GwtUtils.formatDecimal(orderItem.getQuantity(), orderItem.getProduct().getUnitaDiMisura().getDecimali()));
        GwtUtils.setFlexCellStyleAttribute(orderTable, row, 0, "textAlign", "right");
        GwtUtils.setFlexCellStyleAttribute(orderTable, row, 0, "paddingRight", "30px");
        final int itemIndex = it;
        Anchor detailAnchor = new Anchor(orderItem.getProduct().getName());
        detailAnchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            OrderItem orderItem = order.getItems().get(itemIndex);
            itemDetailNameLabel.setText(orderItem.getProduct().getName());
//          qtaBox.setValue(orderItem.getQuantity().intValue());
            UnitaDiMisura um = orderItem.getProduct().getUnitaDiMisura();
            quantitaBox = new QuantitaBox(orderItem.getQuantity(), um.getNome(), um.getDecimali(), qtaBoxPanel, style.qtaBox());
            orderItemDetailCustomizer.setOrderItem(orderItem);
            statePanelUtil.setCurrentState(itemDetailStatePanel.getStateId());
            orderItemSelected = orderItem;
          }
        });
        setCellContent(orderTable, row, 1, detailAnchor);
        setCellContent(orderTable, row, 2, GwtUtils.formatCurrency(orderItem.getProduct().getPrezzo()));
        setCellContent(orderTable, row, 3, GwtUtils.formatCurrency(orderItem.getProduct().getPrezzo() * orderItem.getQuantity()));
        Anchor deleteAnchor = new Anchor("cancella");
        deleteAnchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            GwtUtils.messageBox("Confermi la rimozione dell'articolo dal carrello?", MessageBox.BUTTONS_YESNOCANCEL, MessageBox.ICON_QUESTION, 
                new MessageBox.Callbacks() {
                  public void onYes() {
                    OrderItem itemToDelete = order.getItems().get(itemIndex);
                    getPresenter().deleteOrderItem(order, itemToDelete);
                  }
            });
          }
        });
        setCellContent(orderTable, row, 4, deleteAnchor);
      }
      
      row++;
      Label totLab = new Label("TOTALE ARTICOLI :     ");
      GwtUtils.setStyleAttribute(totLab, "fontWeight", "bold");
      GwtUtils.setStyleAttribute(totLab, "textAlign", "right");
      setCellContent(orderTable, row, 1, totLab);
      GwtUtils.setFlexCellColSpan(orderTable, row, 1, 2);
      setCellContent(orderTable, row, 2, GwtUtils.formatCurrency(Order.Utils.computeImportoTotale(order,true)));
      
      
    }
  }

  private void aggiornaTotaleOrdine() {
    double totaleOrdine = Order.Utils.computeImportoTotale(order,true);
    if (modalitaSpedizioneSelected != null) {
      totaleOrdine += modalitaSpedizioneSelected.getPrezzo();
    }
    totaleOrdineLbl.setText(GwtUtils.formatCurrency(totaleOrdine));
  }
  
  private void setHeaderHtml(FlexTable orderTable, int row, int col, String htmlText, String width) {
    orderTable.setHTML(row, col, SafeHtmlUtils.fromTrustedString(htmlText));
    GwtUtils.setFlexCellClassName(orderTable, row, col, "basketTableHeader");
    if (width != null)
      GwtUtils.setFlexCellWidth(orderTable, row, col, width);
  }
  
  private void setCellContent(FlexTable orderTable, int row, int col, String text) {
    setCellContent(orderTable, row, col, text, null);
  }
  
  private void setCellContent(FlexTable orderTable, int row, int col, Widget widget) {
    setCellContent(orderTable, row, col, null, widget);
  }
  
  private void setCellContent(FlexTable orderTable, int row, int col, String text, Widget widget) {
    if (text != null) {
      orderTable.setText(row, col, text);
    } else  if (widget != null) {
      orderTable.setWidget(row, col, widget);
    }
    GwtUtils.setFlexCellClassName(orderTable, row, col, "basketTableCell");
  }
  
  @UiHandler ("step1OkBtn")
  public void step1OkBtn (ClickEvent event) {
    if (PropertiesHolder.getBoolean("client.ShoppingCartDetailView.closeOrderOnStep1", false)) {
      closeOrder();
    } else {
      setStep2();
    }
  }
  
  @UiHandler ("step2BackBtn")
  public void step2BackBtn (ClickEvent event) {
    statePanelUtil.setCurrentState(step1StatePanel.getStateId());
  }
  
  @UiHandler ("step2OkBtn")
  public void step2OkBtn (ClickEvent event) {
    statePanelUtil.setCurrentState(step3StatePanel.getStateId());
  }

  @UiHandler ("step3BackBtn")
  public void step3BackBtn (ClickEvent event) {
    setStep2();
  }
  
  @UiHandler ("step3OkBtn")
  public void step3OkBtn (ClickEvent event) {
    closeOrder();
  }
  
  private void closeOrder() {
    getPresenter().closeOrder(order, modalitaSpedizioneSelected, modalitaPagamentoSelected, new Delegate<Void>() {
      public void execute(Void element) {
        statePanelUtil.setCurrentState(finalStatePanel.getStateId());
      }
    });
  }

  @UiHandler ("itemDetailStateOkBtn")
  public void itemDetailStateOkBtn (ClickEvent event) {
    if (orderItemSelected != null) {
      orderItemSelected.setDetails(orderItemDetailCustomizer.getDetails());
      Double qta = quantitaBox.getQuantita();
      if (qta == null) {
        return;
      }
      orderItemSelected.setQuantity(qta);
      getPresenter().updateOrderItem(orderItemSelected);
      statePanelUtil.setCurrentState(step1StatePanel.getStateId());
      orderItemSelected = null;
    }
  }
  
  @UiHandler ("step2AddressUpdateBtn")
  public void step2AddressUpdateBtn (ClickEvent event) {
    getPresenter().goToUpdateCustomerInformations();
  }

  private void setStep2() {
    customerView.setPresenter(new CustomerEditView.VoidPresenter() {
      public void registerNewCustomer(Customer cliente) { }
      public void updateCustomer(Customer cliente) { }
    });
    customerView.setModel(order.getCustomer(), CustomerEditView.Presenter.REVIEW_CUSTOMER_INFORMATIONS);
    statePanelUtil.setCurrentState(step2StatePanel.getStateId());
  }
  
  @UiHandler ("modalitaSpedizioneBox")
  public void onModalitaSpedizioneBoxChanged (ChangeEvent event) {
    onModalitaSpedizioneBoxChanged(modalitaSpedizioneBox.getSelectedIndex());
  }
  
  private void onModalitaSpedizioneBoxChanged(int index) {
    if (index >= 0) {
      if (listaModalitaSpedizione != null && listaModalitaSpedizione.size() > 0) {
        modalitaSpedizioneSelected = listaModalitaSpedizione.get(index);
        prezzoModalitaSpedizioneLabel.setText(GwtUtils.formatCurrency(modalitaSpedizioneSelected.getPrezzo()));
        aggiornaTotaleOrdine();
      }
    }
  }
  
  @UiHandler ("modalitaPagamentoBox")
  public void onModalitaPagamentoBoxChanged (ChangeEvent event) {
    onModalitaPagamentoBoxChanged(modalitaPagamentoBox.getSelectedIndex());
  }
  
  private void onModalitaPagamentoBoxChanged(int index) {
    if (index >= 0) {
      if (listaModalitaPagamento != null && listaModalitaPagamento.size() > 0) {
        modalitaPagamentoSelected = listaModalitaPagamento.get(index);
        HTML html = new HTML(SafeHtmlUtils.fromTrustedString(modalitaPagamentoSelected.getNota()));
        modalitaPagamentoNotePanel.clear();
        modalitaPagamentoNotePanel.add(html);
      }
    }
  }
  
}
