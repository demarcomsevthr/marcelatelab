package it.mate.postscriptum.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebkitCssUtil;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.constants.AppProperties;
import it.mate.postscriptum.client.view.HomeView.Presenter;
import it.mate.postscriptum.shared.model.RemoteUser;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToNewMail();
    public void goToMailList();
    public void setRemoteUserDelegate(Delegate<RemoteUser> delegate);
    public void sendSmsTest(RemoteUser remoteUser, String to, String msg);
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
  
  @UiField Panel firstRunPanel;
  @UiField Label firstRunTitleLbl;
  /*
  @UiField HTML firstRunHtml;
  */
  @UiField TouchHTML firstRunHtml;
  
  private static final int ANIMATION_DURATION = 1000;
  
  private boolean firstRunPanelVisible = false;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText(AppProperties.IMPL.versionCredits());
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    if (isFirstRun()) {
      doFirstRunTransition("Hello!", 210, "<p>This is the first time you run this app. </p><br/><p>To use this app, you have to log in with your <span style='color:yellow;'>Google account</span> and accept the <br/> permission to use your gmail address.</p>");
      doFirstRunCompleteCheck();
    }
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("mailListBtn2")
  public void onMailListBtn (TouchEndEvent event) {
    if (isFirstRun()) {
      onHelloBtn(event);
    } else {
      getPresenter().goToMailList();
    }
  }
  
  @UiHandler ("newMailBtn2")
  public void onNewMailBtn (TouchEndEvent event) {
    if (isFirstRun()) {
      onHelloBtn(event);
    } else {
      getPresenter().goToNewMail();
    }
  }
  
  @UiHandler ("helloBtn")
  public void onHelloBtn (TouchEndEvent event) {
    getPresenter().setRemoteUserDelegate(new Delegate<RemoteUser>() {
      public void execute(RemoteUser result) { }
    });
  }
  
  @UiHandler ("smsTestBtn")
  public void onSmsTestBtn (TouchEndEvent event) {
    getPresenter().sendSmsTest(null, "+393924565268", "ciao marce");
  }
  
  private static int getFirstRunPanelWidthPct() {
    if (OsDetectionUtils.isTablet()) {
      return 80;
    } else {
      return 90;
    }
  }
  
  public static String getFirstRunPanelWidth() {
    return getFirstRunPanelWidthPct() + "%";
  }
  
  public static String getFirstRunPanelLeft() {
    int startLeft = ( Window.getClientWidth() - (Window.getClientWidth() * getFirstRunPanelWidthPct() / 100) ) / 2 - 8; 
    return startLeft + "px";
  }
  
  private boolean isFirstRun() {
    String remoteUserJson = getRemoteUserImpl();
    PhonegapUtils.log("localStorage.remoteUser = " + remoteUserJson);
    return (remoteUserJson == null || !remoteUserJson.contains("{"));
  }
  
  private native String getRemoteUserImpl() /*-{
    return localStorage.remoteUser;
  }-*/;
  
  private void doFirstRunTransition(String title, final int endHeight, String text) {
    firstRunTitleLbl.setText(title);
    firstRunHtml.setHtml(SafeHtmlUtils.fromTrustedString(text));
    final long startTime = System.currentTimeMillis();
    final int startTop = firstRunPanel.getAbsoluteTop();
    final AnimationCallback animationCallback = new AnimationCallback() {
      public void execute(double now) {
        if (now >= startTime + ANIMATION_DURATION) {
          return;
        }
        double currDeltaTm = (now - startTime) / ANIMATION_DURATION;
        int currY = startTop - (int)Math.round(currDeltaTm * endHeight);
        WebkitCssUtil.translatePx(firstRunPanel.getElement(), 0, currY);
        AnimationScheduler.get().requestAnimationFrame(this, firstRunPanel.getElement());
      }
    };
    animationCallback.execute(startTime);
    
    firstRunHtml.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        onHelloBtn(event);
      }
    });
    
  }
  
  Timer checkFirstRunCompleteTimer = null;
  private void doFirstRunCompleteCheck() {
    
    checkFirstRunCompleteTimer = GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (!isFirstRun()) {
          firstRunPanel.setVisible(false);
          if (checkFirstRunCompleteTimer != null) {
            checkFirstRunCompleteTimer.cancel();
          }
        }
      }
    });
  }
  
}
