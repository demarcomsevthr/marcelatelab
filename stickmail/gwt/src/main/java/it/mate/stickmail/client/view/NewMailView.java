package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.ui.ph.PhCalendarBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.constants.AppProperties;
import it.mate.stickmail.client.factories.AppClientFactory;
import it.mate.stickmail.client.ui.SignPanel;
import it.mate.stickmail.client.view.NewMailView.Presenter;
import it.mate.stickmail.shared.model.StickMail;
import it.mate.stickmail.shared.model.impl.StickMailTx;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.MTextArea;

public class NewMailView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, NewMailView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField MTextArea bodyArea;
  @UiField PhCalendarBox calBox;
  @UiField PhTimeBox timeBox;
  @UiField TouchButton sendBtn;

  @UiField SignPanel signPanel;
  /*
  @UiField TouchAnchor signBtn;
  @UiField Label signLbl;
  private RemoteUser remoteUser;
  */
  
  private Date scheduledDate;
  private Time scheduledTime;
  
  public NewMailView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    if (OsDetectionUtils.isTablet()) {
      setTitleHtml(AppProperties.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppProperties.IMPL.phoneAppName());
    }
    initHeaderBackButton("Back", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToHome();
      }
    });
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    TouchImage optionsBtn = new TouchImage();
    optionsBtn.addStyleName("ui-optionsBtn");
    getHeaderPanel().setRightWidget(optionsBtn);
    optionsBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        PhonegapUtils.log("optons tapped");
      }
    });

    /*
    AppClientFactory.IMPL.initEndpointProxy(null, new Delegate<Boolean>() {
      public void execute(Boolean isSignedIn) {
        if (isSignedIn) {
          signBtn.setText("Change");
          signLbl.setText("Connecting...");
          sendBtn.setEnabled(true);
          AppClientFactory.IMPL.getStickMailEPProxy().getRemoteUser(new Delegate<RemoteUser>() {
            public void execute(RemoteUser remoteUser) {
              NewMailView.this.remoteUser = remoteUser;
              signLbl.setText(remoteUser.getEmail());
            }
          });
        } else {
          signBtn.setText("Sign in");
          signLbl.setText("");
          sendBtn.setEnabled(false);
        }
      }
    });
    */
    
    onNowBtn(null);
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler ("calBox")
  public void onCalChange (ValueChangeEvent<Date> event) {
    PhonegapUtils.log("new value is " + event.getValue());
    this.scheduledDate = event.getValue();
  }
  
  @UiHandler ("timeBox")
  public void onTimeChange (ValueChangeEvent<Time> event) {
    PhonegapUtils.log("new value is " + event.getValue());
    this.scheduledTime = event.getValue();
  }

  /*
  @UiHandler ("signBtn")
  public void onSignInBtn (TouchEndEvent event) {
    AppClientFactory.IMPL.getStickMailEPProxy().auth();
  }
  */
  
  @UiHandler ("nowBtn")
  public void onNowBtn (TouchEndEvent event) {
    Date now = new Date();
    calBox.setValue(now);
    timeBox.setValue(Time.fromDate(now));
  }
  
  @UiHandler ("sendBtn")
  public void onTouchBtn (TapEvent event) {
    PhonegapUtils.log("SEND BTN tapped");
    PhonegapUtils.log("body = " + bodyArea.getValue());
    
    if (!signPanel.isSigned()) {
      PhgDialogUtils.showMessageDialog("You must be signed");
      return;
    }
    
    AppClientFactory.IMPL.getStickFacade().getServerTime(new AsyncCallback<Date>() {
      public void onFailure(Throwable caught) {
        PhonegapUtils.log("FAILURE");
        caught.printStackTrace();
      }
      public void onSuccess(Date serverTime) {
        
        Date clientTime = new Date();
        
        long deltaTime = clientTime.getTime() - serverTime.getTime();
        
        PhonegapUtils.log("serverTime = " + serverTime);
        
        PhonegapUtils.log("clientTime = " + clientTime);
        
        PhonegapUtils.log("delta time = " + deltaTime);
        
        Date serverScheduled = new Date(scheduledTime.setToDate(scheduledDate).getTime() - deltaTime);
        Date serverCreated = new Date(clientTime.getTime() - deltaTime);
        
        StickMail stickMail = new StickMailTx();
        stickMail.setBody(bodyArea.getValue());
        stickMail.setUser(signPanel.getRemoteUser());
        stickMail.setScheduled(serverScheduled);
        stickMail.setCreated(serverCreated);
        stickMail.setState(StickMail.STATE_NEW);
        
        AppClientFactory.IMPL.getStickFacade().create(stickMail, new AsyncCallback<StickMail>() {
          public void onSuccess(StickMail result) {
            PhonegapUtils.log("SUCCESS");
          }
          public void onFailure(Throwable caught) {
            PhonegapUtils.log("FAILURE");
          }
        });
        
      }
    });

    
    
  }
  
}
