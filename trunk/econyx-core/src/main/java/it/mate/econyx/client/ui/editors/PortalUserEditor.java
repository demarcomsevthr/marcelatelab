package it.mate.econyx.client.ui.editors;

import it.mate.econyx.client.text.CurrencyBox;
import it.mate.econyx.shared.model.PortalUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserEditor extends Composite implements Editor<PortalUser> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<PortalUser, PortalUserEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox screenName;
  @UiField TextBox emailAddress;
  @UiField CurrencyBox billingAccount;
  
  public PortalUserEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(PortalUser user) {
    driver.edit(user);
  }
  
  public PortalUser flushModel() {
    PortalUser user = driver.flush();
    return user;
  }
  
}
