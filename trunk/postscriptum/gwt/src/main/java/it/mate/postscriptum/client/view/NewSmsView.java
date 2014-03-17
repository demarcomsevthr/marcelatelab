package it.mate.postscriptum.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.ph.PhCalendarBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.ui.SignPanel;
import it.mate.postscriptum.client.view.NewSmsView.Presenter;
import it.mate.postscriptum.shared.model.StickMail;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class NewSmsView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void postNewMail(StickMail stickMail, Delegate<StickMail> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, NewSmsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField MTextArea bodyArea;
  @UiField PhCalendarBox calBox;
  @UiField PhTimeBox timeBox;
  @UiField TouchButton sendBtn;
  @UiField MTextBox receiverBox;
  @UiField SignPanel signPanel;
  
  private Date scheduledDate;
  private Time scheduledTime;
  
  public NewSmsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initHeaderBackButton(SafeHtmlUtils.fromTrustedString("<img src='main/images/home-back.png'/>"), new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToHome();
      }
    });
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    calBox.setValue(new Date());
    timeBox.setValue(Time.fromDate(new Date()).incHours(1).setMinutes(0));
//  signPanel.setSignLblText("Send to");
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    signPanel.setRemoteUserDelegate(presenter, null);
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

  @UiHandler ("sendBtn")
  public void onTouchBtn (TapEvent event) {
    
    TouchUtils.applyQuickFixFocusPatch();
    
    if (signPanel.getRemoteUser() == null) {
      PhgDialogUtils.showMessageDialog("You must be signed");
      return;
    }
    
    if (receiverBox.getValue().trim().length() == 0) {
      PhgDialogUtils.showMessageDialog("Receiver is empty");
      return;
    }
    
    if (!receiverBox.getValue().trim().startsWith("+")) {
      PhgDialogUtils.showMessageDialog("The receiver phone number must include the international prefix");
      return;
    }
    
    Date scheduledDateTime = scheduledTime.setInDate(scheduledDate);
    
    Date NOW = new Date();
    
    if (scheduledDateTime.before(NOW)) {
      PhgDialogUtils.showMessageDialog("You cannot send a SMS in the past");
      return;
    }
    
    if (bodyArea.getValue() == null || bodyArea.getValue().length() == 0) {
      PhgDialogUtils.showMessageDialog("You cannot send a message with empty body");
      return;
    }

    /*
    StickMail stickMail = new StickMailTx();
    stickMail.setSubject(subjectBox.getValue());
    stickMail.setBody(bodyArea.getValue());
    stickMail.setUser(signPanel.getRemoteUser());
    stickMail.setScheduled(scheduledTime.setInDate(scheduledDate));
    stickMail.setCreated(new Date());
    stickMail.setState(StickMail.STATE_NEW);
    getPresenter().postNewMail(stickMail, new Delegate<StickMail>() {
      public void execute(StickMail element) {
        getPresenter().goToHome();
      }
    });
    */

  }
  
}
