package it.mate.stickmail.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchAnchor;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.stickmail.client.factories.AppClientFactory;
import it.mate.stickmail.shared.model.RemoteUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class SignPanel extends Composite {

  public interface Presenter {
    
    void setRemoteUserDelegate(Delegate<RemoteUser> delegate);

  }
  
  public interface ViewUiBinder extends UiBinder<Widget, SignPanel> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField TouchAnchor signBtn;
  @UiField Label signLbl;
  
  private RemoteUser remoteUser;
  
  public SignPanel() {
    initUI();
  }

  private void initProvidedElements() {

  }
  
  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));

    /*
    AppClientFactory.IMPL.setRemoteUserDelegate(new Delegate<RemoteUser>() {
      public void execute(RemoteUser remoteUser) {
        SignPanel.this.remoteUser = remoteUser;
        if (remoteUser != null) {
          PhonegapUtils.log("SignPanel::authDelegate: signed in");
          signLbl.setText(remoteUser.getEmail());
          signBtn.setText("Change");
        } else {
          PhonegapUtils.log("SignPanel::authDelegate: signed out");
          signBtn.setText("Sign in");
          signLbl.setText("");
        }
        if (remoteUserDelegate != null) {
          remoteUserDelegate.execute(remoteUser);
        }
      }
    });
    */
    
  }
  
  public void setRemoteUserDelegate(Presenter presenter, final Delegate<RemoteUser> remoteUserDelegate) {
    presenter.setRemoteUserDelegate(new Delegate<RemoteUser>() {
      public void execute(RemoteUser remoteUser) {
        SignPanel.this.remoteUser = remoteUser;
        if (remoteUser != null) {
          PhonegapUtils.log("SignPanel::authDelegate: signed in");
          signLbl.setText(remoteUser.getEmail());
          signBtn.setText("Change");
        } else {
          PhonegapUtils.log("SignPanel::authDelegate: signed out");
          signBtn.setText("Sign in");
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
  
  @UiHandler ("signBtn")
  public void onSignInBtn (TouchEndEvent event) {
    PhonegapUtils.log("SignPanel: signIn Btn pressed");
    AppClientFactory.IMPL.authenticate();
  }
  
  public RemoteUser getRemoteUser() {
    return remoteUser;
  }
  
}
