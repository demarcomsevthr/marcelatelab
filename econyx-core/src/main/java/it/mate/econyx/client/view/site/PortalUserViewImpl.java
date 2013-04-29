package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.econyx.shared.util.PropertyConstants;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserViewImpl extends AbstractBaseView<PortalUserView.Presenter> implements PortalUserView {

  public interface ViewUiBinder extends UiBinder<Widget, PortalUserViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel loggedUserState;
  @UiField Panel notLoggedUserState;
  @UiField Panel notLoggedGoogleUserState;
  @UiField Panel localLoginFormState;
  @UiField Label screenName;
  @UiField TextBox emailBox;
  @UiField TextBox passwordBox;
  @UiField Anchor registrationBtn;
  @UiField Anchor logoutBtn;
  @UiField CheckBox keepConnectionBox;
  @UiField Anchor googleLoginBtn;
  @UiField Anchor googleLogoutBtn;
  @UiField Anchor localLoginBtn;
  @UiField Button loginBtn;
  
  private boolean googleAccountAuthentication = PropertiesHolder.getBoolean(PropertyConstants.SITE_GOOGLE_ACCOUNT_AUTHENTICATION_ENABLED, false);
  
  private PortalUser portalUser;
  
  private final boolean REGISTRATION_BTN_VISIBLE = PropertiesHolder.getBoolean("client.PortalUserView.registrationBtn.visible", true);
  
  private final boolean USE_LOGIN_DLG = PropertiesHolder.getBoolean("client.PortalUserView.useLoginDialog");
  
  private final String LOGIN_DLG_CAPTION_TEXT = PropertiesHolder.getString("client.PortalUserView.loginDialog.captionText", "Login");
  
  public PortalUserViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    registrationBtn.setVisible(REGISTRATION_BTN_VISIBLE);
  }
  
  @Override
  protected void onAttach() {
    if (googleAccountAuthentication) {
      getPresenter().getGoogleLoginURL(new Delegate<String>() {
        public void execute(String url) {
          googleLoginBtn.setHref(url);
        }
      });
      getPresenter().getGoogleLogoutURL(new Delegate<String>() {
        public void execute(String url) {
          googleLogoutBtn.setHref(url);
        }
      });
    }
    super.onAttach();
  }

  public void setModel(Object model, String tag) {
    if (model != null && model instanceof PortalUser) {
      this.portalUser = (PortalUser)model;
      setLoggedState();
      if (AppClientFactory.IMPL.getPortalSessionState().isGoogleAuthentication()) {
        getPresenter().getGoogleLogoutURL(new Delegate<String>() {
          public void execute(String url) {
//          logoutBtn.setHref(url);
          }
        });
      }
    } else {
      setNotLoggedState();
    }
  }
  
  private void setNotLoggedState() {
    loggedUserState.setVisible(false);
    localLoginFormState.setVisible(false);
    if (googleAccountAuthentication) {
      notLoggedGoogleUserState.setVisible(true);
    }
    emailBox.setText("");
    passwordBox.setText("");
    notLoggedUserState.setVisible(true);
  }
  
  private void setLoggedState() {
    loggedUserState.setVisible(true);
    notLoggedUserState.setVisible(false);
    notLoggedGoogleUserState.setVisible(false);
    localLoginFormState.setVisible(false);
    screenName.setText(portalUser.getScreenName());
  }
  
  @UiHandler ("localLoginBtn")
  public void onLocalLoginBtn(ClickEvent event) {
    
    if (USE_LOGIN_DLG) {
      new LoginDialog();
    } else {
      notLoggedUserState.setVisible(false);
      notLoggedGoogleUserState.setVisible(false);
      localLoginFormState.setVisible(true);
    }
    
  }
  
  @UiHandler ("loginBtn")
  public void onLoginBtn(ClickEvent event) {
    PortalUser portalUser = new PortalUserTx();
    portalUser.setEmailAddress(emailBox.getValue());
    portalUser.setPassword(passwordBox.getValue());
    getPresenter().login(portalUser, keepConnectionBox.getValue());
  }
  
  @UiHandler ("registrationBtn")
  public void onRegistrationBtn(ClickEvent event) {
    getPresenter().goToRegistrationView();
  }
  
  @UiHandler ("logoutBtn")
  public void onLogoutBtn(ClickEvent event) {
    boolean isGoogleAuthentication = AppClientFactory.IMPL.getPortalSessionState().isGoogleAuthentication();
    getPresenter().logout();
    if (isGoogleAuthentication) {
      GwtUtils.deferredExecution(1000, new Delegate<Void>() {
        public void execute(Void element) {
          getPresenter().getGoogleLogoutURL(new Delegate<String>() {
            public void execute(String url) {
              Window.Location.assign(url);
            }
          });
        }
      });
    }
  }
  
  @UiHandler ("profileBtn")
  public void onProfileBtn(ClickEvent event) {
    getPresenter().goToProfileView();
  }
  
  public class LoginDialog {
    public LoginDialog() {
      
      VerticalPanel dialogPanel = new VerticalPanel();
      dialogPanel.add(localLoginFormState);
      
      MessageBox.create(new MessageBox.Configuration()
      .setCaptionText(LOGIN_DLG_CAPTION_TEXT)
      .setButtonType(MessageBox.BUTTONS_OKCANCEL)
      .setIconType(MessageBox.ICON_INFO)
      .setBodyWidget(dialogPanel)
      .setBodyWidth("400px")
      .setObjectRelativeTo(localLoginBtn)
      .setCallbacks(new MessageBox.Callbacks() {
        public void onOk() {
          PortalUser portalUser = new PortalUserTx();
          portalUser.setEmailAddress(emailBox.getValue());
          portalUser.setPassword(passwordBox.getValue());
          setNotLoggedState();
          getPresenter().login(portalUser, keepConnectionBox.getValue());
        }
        public void onCancel() {
          setNotLoggedState();
        }
        public void onLoad(DialogBox dialog) {
          loginBtn.setVisible(false);
          localLoginFormState.setVisible(true);
          emailBox.setFocus(true);
        }
      }));
      
    }
  }
  
}
