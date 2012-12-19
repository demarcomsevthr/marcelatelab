package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.editors.CustomerEditor;
import it.mate.econyx.client.view.CustomerEditView;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.IndirizzoFatturazioneTx;
import it.mate.econyx.shared.model.impl.IndirizzoSpedizioneTx;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class CustomerEditViewImpl extends AbstractBaseView<CustomerEditView.Presenter> implements CustomerEditView {

  public interface ViewUiBinder extends UiBinder<Widget, CustomerEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel registrationFormStatePanel;
  @UiField Panel successRegistrationStatePanel;
  @UiField Panel roundedPanel;
  @UiField Panel infoPanel;
  @UiField Label infoLabel;
  
  @UiField Button okBtn;
  
  @UiField CustomerEditor customerEditor;
  
  private int mode;

  public CustomerEditViewImpl() {
    super();
    initUI();
  }

  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    
    GwtUtils.setStyleAttribute(roundedPanel, "border", "2px solid lightblue");
    GwtUtils.setBorderRadius(roundedPanel, "8px");
    
//  GwtUtils.setBorderRadius(okBtn, "2px");
    
  }

  @Override
  public void setModel(Object model, String tag) {
    if (Presenter.REGISTRATION_SUCCESS.equals(tag)) {
      setState(successRegistrationStatePanel);
    } else if (Presenter.REVIEW_CUSTOMER_INFORMATIONS.equals(tag)) {
      setState(registrationFormStatePanel);
      Customer customer = (Customer)model;
      this.mode = CustomerEditor.REVIEW_CUSTOMER_INFORMATIONS;
      customerEditor.setMode(this.mode);
      customerEditor.setModel(customer);
      okBtn.setVisible(false);
    } else if (Presenter.UPDATE_CUSTOMER_INFORMATIONS.equals(tag)) {
      setState(registrationFormStatePanel);
      Customer customer = (Customer)model;
      this.mode = CustomerEditor.UPDATE_CUSTOMER_INFORMATIONS;
      customerEditor.setMode(this.mode);
      customerEditor.setModel(customer);
      okBtn.setText("Aggiorna account");
      infoPanel.setVisible(true);
    } else {
      setState(registrationFormStatePanel);
      Customer customer = new CustomerTx();
      customer.setPortalUser(new PortalUserTx());
      customer.setIndirizzoSpedizione(new IndirizzoSpedizioneTx());
      customer.setIndirizzoFatturazione(new IndirizzoFatturazioneTx());
      this.mode = CustomerEditor.REGISTER_NEW_CUSTOMER;
      customerEditor.setMode(this.mode);
      customerEditor.setModel(customer);
      infoLabel.setText("Inserisci i dati per la creazione di un nuovo account");
      infoPanel.setVisible(true);
    }
  }
  
  private void setState(Panel statePanel) {
    registrationFormStatePanel.setVisible(registrationFormStatePanel.equals(statePanel));
    successRegistrationStatePanel.setVisible(successRegistrationStatePanel.equals(statePanel));
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn(ClickEvent event) {
    Customer customer = customerEditor.flushModel();
    GwtUtils.log(getClass(), "onOkBtn", "cliente = " + customer);
    if (customer != null) {
      if (mode == CustomerEditor.UPDATE_CUSTOMER_INFORMATIONS) {
        getPresenter().updateCustomer(customer);
      } else {
        getPresenter().registerNewCustomer(customer);
      }
    }
  }
  
}
