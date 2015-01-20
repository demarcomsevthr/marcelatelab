package it.mate.postscriptum.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.postscriptum.client.constants.AppProperties;
import it.mate.postscriptum.client.view.HomeView.Presenter;
import it.mate.postscriptum.shared.model.RemoteUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends AbstractBaseView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void setRemoteUserDelegate(Delegate<RemoteUser> delegate);
    public void goToNewMail();
    public void goToMailList();
    public void goToNewSms();
    public void goToSMSList();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
  
  @UiField Panel firstRunPanel;
  @UiField Label firstRunTitleLbl;
//@UiField TouchHTML firstRunHtml;
  
  private static final int ANIMATION_DURATION = 1000;
  
  private boolean firstRunPanelVisible = false;
  
  private Timer checkFirstRunCompleteTimer = null;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
//  getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.version() + " - " + AppProperties.IMPL.credits());
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    if (isFirstRun()) {
      /*
      doFirstRunTransition("Hello!", 210, "<p>This is the first time you run this app. </p><br/><p>To use this app, you have to log in with your <span style='color:yellow;'>Google account</span> and accept the <br/> permission to use your gmail address.</p>");
      doFirstRunCompleteCheck();
      */
    }
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  private boolean isFirstRun() {
    String remoteUserJson = getRemoteUserImpl();
    return (remoteUserJson == null || !remoteUserJson.contains("{"));
  }
  
  private native String getRemoteUserImpl() /*-{
    return localStorage.remoteUser;
  }-*/;
  
}
