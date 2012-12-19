package it.mate.econyx.client.ui.editors;

import it.mate.econyx.client.ui.editors.PortalUserRegistrationEditor.PortalUserRegistrationTx;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.PortalUserTx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserRegistrationEditor extends Composite implements PortalUserSiteEditor, Editor<PortalUserRegistrationTx> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserRegistrationEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<PortalUserRegistrationTx, PortalUserRegistrationEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox emailAddress;
  @UiField PasswordTextBox password;
  @UiField PasswordTextBox passwordConfirmation;
  
  @UiField Panel passwordGroupPanel;
  
  private boolean enabled = true;
  
  private PortalUser originalUser;
  
  public PortalUserRegistrationEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(PortalUser user) {
    PortalUserRegistrationTx editedUser = new PortalUserRegistrationTx();
    if (user != null) {
      editedUser.setScreenName(user.getScreenName());
      editedUser.setEmailAddress(user.getEmailAddress());
      editedUser.setPassword(user.getPassword());
      this.originalUser = user;
    }
    driver.edit(editedUser);
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    emailAddress.setEnabled(enabled);
    passwordGroupPanel.setVisible(enabled);
  }
  
  public PortalUser flushModel() {
    PortalUser user = null;
    if (emailAddress.getText().length() == 0) {
      Window.alert("Inserire un indirizzo email");
      return null;
    }
    /*
    if (password.getText().length() < 8) {
      Window.alert("Inserire una password di almeno 8 caratteri");
      return null;
    }
    */
    if (!password.getText().equals(passwordConfirmation.getText())) {
      Window.alert("La password di conferma non coincide");
      return null;
    }
    user = driver.flush();
    return user;
  }
  
  public PortalUser flushPortalUser() {
    PortalUser portalUser = null;
    if (enabled) {
      portalUser = new PortalUserTx();
      PortalUser flushedPortalUser = flushModel();
      portalUser.setScreenName(flushedPortalUser.getScreenName());
      portalUser.setEmailAddress(flushedPortalUser.getEmailAddress());
      portalUser.setPassword(flushedPortalUser.getPassword());
    } else {
      portalUser = originalUser;
    }
    return portalUser;
  }
  
  @SuppressWarnings("serial")
  public class PortalUserRegistrationTx extends PortalUserTx {
    public void setPasswordConfirmation(String password) { }
    public String getPasswordConfirmation() {return ""; }
  }
  
}
