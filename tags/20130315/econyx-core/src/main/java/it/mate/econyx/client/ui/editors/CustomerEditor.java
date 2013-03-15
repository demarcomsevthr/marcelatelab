package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.IndirizzoFatturazioneTx;
import it.mate.econyx.shared.model.impl.IndirizzoSpedizioneTx;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CustomerEditor extends Composite implements Editor<Customer> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, CustomerEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public static int REGISTER_NEW_CUSTOMER = 1;
  
  public static int REVIEW_CUSTOMER_INFORMATIONS = 2;
  
  public static int UPDATE_CUSTOMER_INFORMATIONS = 3;
  
  @UiField PortalUserRegistrationEditor portalUser;
  @UiField IndirizzoEditor indirizzoSpedizione;
  @UiField IndirizzoEditor indirizzoFatturazione;
  
  @UiField Label portalUserLabel;
  
  private int mode = REGISTER_NEW_CUSTOMER;
  
  private Customer originalCustomer;
  
  public CustomerEditor() {
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    indirizzoFatturazione.setTipoControlli(IndirizzoEditor.TIPO_CONTROLLI_FATTURAZIONE);
  }
  
  public void setMode(int mode) {
    this.mode = mode;
    if (this.mode == REVIEW_CUSTOMER_INFORMATIONS) {
      portalUserLabel.setVisible(false);
      portalUser.setVisible(false);
      indirizzoSpedizione.setEnabled(false);
      indirizzoFatturazione.setEnabled(false);
    }
    if (this.mode == UPDATE_CUSTOMER_INFORMATIONS) {
      portalUser.setEnabled(false);
    }
  }
  
  public void setModel(Customer customer) {
    this.originalCustomer = customer;
    portalUser.setModel(customer.getPortalUser());
    if (customer.getIndirizzoSpedizione() == null)
      customer.setIndirizzoSpedizione(new IndirizzoSpedizioneTx());
    indirizzoSpedizione.setModel(customer.getIndirizzoSpedizione());
    if (customer.getIndirizzoFatturazione() == null)
      customer.setIndirizzoFatturazione(new IndirizzoFatturazioneTx());
    indirizzoFatturazione.setModel(customer.getIndirizzoFatturazione());
  }
  
  public Customer flushModel() {
    Customer customer = null;
    if (mode == UPDATE_CUSTOMER_INFORMATIONS) {
      customer = originalCustomer;
    } else {
      customer = new CustomerTx();
    }
    customer.setPortalUser(portalUser.flushPortalUser());
    if (customer.getPortalUser() == null)
      return null;
    customer.setIndirizzoSpedizione(indirizzoSpedizione.flushModel());
    if (customer.getIndirizzoSpedizione() == null)
      return null;
    customer.setIndirizzoFatturazione(indirizzoFatturazione.flushModel());
    if (customer.getIndirizzoFatturazione() == null)
      return null;
    
    if (GwtUtils.isEmpty(customer.getPortalUser().getScreenName())) {
      String cognome = customer.getIndirizzoSpedizione().getCognome().toLowerCase();
      String nome = customer.getIndirizzoSpedizione().getNome().toLowerCase();
      customer.getPortalUser().setScreenName(GwtUtils.purgeSpaces(cognome)+"."+GwtUtils.purgeSpaces(nome));
    }
    
    return customer;
  }
  
  
  
}
