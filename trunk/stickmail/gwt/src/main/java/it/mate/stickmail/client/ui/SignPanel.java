package it.mate.stickmail.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchAnchor;
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
    
    AppClientFactory.IMPL.initEndpointProxy(null, new Delegate<Boolean>() {
      public void execute(Boolean isSignedIn) {
        if (isSignedIn) {
          signBtn.setText("Change");
          signLbl.setText("Connecting...");
          AppClientFactory.IMPL.getRemoteUser(new Delegate<RemoteUser>() {
            public void execute(RemoteUser remoteUser) {
              SignPanel.this.remoteUser = remoteUser;
              signLbl.setText(remoteUser.getEmail());
            }
          });
        } else {
          signBtn.setText("Sign in");
          signLbl.setText("");
          remoteUser = null;
        }
      }
    });
    
  }
  
  @UiHandler ("signBtn")
  public void onSignInBtn (TouchEndEvent event) {
    AppClientFactory.IMPL.getStickMailEPProxy().auth();
  }
  
  public RemoteUser getRemoteUser() {
    return remoteUser;
  }
  
  public boolean isSigned() {
    return remoteUser != null;
  }
  
}
