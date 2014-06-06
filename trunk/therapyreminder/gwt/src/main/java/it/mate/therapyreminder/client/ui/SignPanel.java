package it.mate.therapyreminder.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.therapyreminder.shared.model.Account;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class SignPanel extends Composite {

  public interface Presenter {
    
    void getAccount(Delegate<Account> delegate);

  }
  
  public interface ViewUiBinder extends UiBinder<Widget, SignPanel> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label signLbl;
  @UiField Label signPromptLbl;
  
  private Account remoteUser;
  
  private String signLblText = "Signed as";
  
  public SignPanel() {
    initUI();
  }

  private void initProvidedElements() {

  }
  
  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setSignLblText(String signLblText) {
    this.signLblText = signLblText;
  }
  
  public void setRemoteUserDelegate(Presenter presenter, final Delegate<Account> remoteUserDelegate) {
    presenter.getAccount(new Delegate<Account>() {
      public void execute(Account remoteUser) {
        SignPanel.this.remoteUser = remoteUser;
        if (remoteUser != null) {
          PhonegapUtils.log("SignPanel::authDelegate: signed in");
          signPromptLbl.setText(signLblText);
          signLbl.setText(remoteUser.getEmail());
        } else {
          PhonegapUtils.log("SignPanel::authDelegate: signed out");
          signPromptLbl.setText("Not signed");
          signLbl.setText("");
        }
        if (remoteUserDelegate != null) {
          remoteUserDelegate.execute(remoteUser);
        }
      }
    });
    if (remoteUserDelegate != null)
      remoteUserDelegate.execute(remoteUser);
  }
  
  public void onSignInBtn (TouchEndEvent event) {
    PhonegapUtils.log("SignPanel: signIn Btn pressed");
//  AppClientFactory.IMPL.authenticate();
  }
  
  public Account getRemoteUser() {
    return remoteUser;
  }
  
}
