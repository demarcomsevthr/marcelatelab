package it.mate.postscriptum.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.Contact;
import it.mate.phgcommons.client.plugins.ContactsPlugin;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.ph.PhCalendarBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox.AfterDialogCloseEvent;
import it.mate.phgcommons.client.ui.ph.PhTimeBox.BeforeDialogOpenEvent;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.constants.AppProperties;
import it.mate.postscriptum.client.ui.SignPanel;
import it.mate.postscriptum.client.view.NewSmsView.Presenter;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.model.impl.StickSmsTx2;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class NewSmsView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void postNewSMS(StickSms2 stickSMS, Delegate<StickSms> delegate);
    public String getLastReceiverNumber();
    public void setLastReceiverNumber(String receiverNumber);
    public String getLastReceiverName();
    public void setLastReceiverName(String receiverName);
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
  @UiField Label recipientLbl;
  
  private Date scheduledDate;
  private Time scheduledTime;
  
  private String receiverNumber;
  private String receiverName;
  
  private StickSms2 editingSms;
  
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
    
    timeBox.addBeforeDialogOpenHandler(new PhTimeBox.BeforeDialogOpenEvent.Handler() {
      public void onBeforeDialogOpen(BeforeDialogOpenEvent event) {
        bodyArea.setVisible(false);
      }
    });
    
    timeBox.addAfterDialogCloseHandler(new PhTimeBox.AfterDialogCloseEvent.Handler() {
      public void onAfterDialogClose(AfterDialogCloseEvent event) {
        bodyArea.setVisible(true);
      }
    });
    
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    signPanel.setRemoteUserDelegate(presenter, null);
    String receiverNumber = getPresenter().getLastReceiverNumber();
    if (receiverNumber != null) {
      this.receiverNumber = receiverNumber;
      receiverBox.setValue(receiverNumber);
    }
    receiverName = getPresenter().getLastReceiverName();
    setRecipientName(receiverName);
  }
  
  private void setRecipientName(String text) {
    if (text != null) {
      receiverName = text;
    } else {
      text = "";
    }
    recipientLbl.setText("Recipient: " + text);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model != null && model instanceof StickSms2) {
      PhgUtils.log("editing " + model);
      editingSms = (StickSms2)model;
      setRecipientName(editingSms.getReceiverName());
      receiverBox.setText(editingSms.getReceiverNumber());
      bodyArea.setText(editingSms.getBody());
      calBox.setValue(editingSms.getScheduled());
      timeBox.setValue(Time.fromDate(editingSms.getScheduled()));
    }
  }

  @UiHandler ("calBox")
  public void onCalChange (ValueChangeEvent<Date> event) {
    PhgUtils.log("new value is " + event.getValue());
    this.scheduledDate = event.getValue();
  }
  
  @UiHandler ("timeBox")
  public void onTimeChange (ValueChangeEvent<Time> event) {
    PhgUtils.log("new value is " + event.getValue());
    this.scheduledTime = event.getValue();
  }
  
  @UiHandler ("contactBtn")
  public void onContactBtn (TapEvent event) {
    if (ContactsPlugin.isInstalled()) {
      ContactsPlugin.pickContact(new Delegate<Contact>() {
        public void execute(Contact contact) {
          PhgUtils.log("picked " + contact);
          if (contact != null && contact.getId() != null) {
            String contactNumber = null;
            if (contact.getPhoneNumbers() != null) {
              for (Contact.PhoneNumber pn : contact.getPhoneNumbers()) {
                if ("mobile".equals(pn.getType())) {
                  contactNumber = pn.getValue();
                  break;
                }
              }
              if (contactNumber == null) {
                if (contact.getPhoneNumbers().size() > 0) {
                  contactNumber = contact.getPhoneNumbers().get(0).getValue();
                }
              }
            }
            if (contactNumber != null) {
              receiverNumber = contactNumber;
              setRecipientName(contact.getDisplayName());
              receiverBox.setValue(contactNumber);
            } else {
              PhgDialogUtils.showMessageDialog("Cannot find phone number for this contact");
            }
          }
        }
      });
    } else {
      PhgUtils.log("Contacts plugin not installed!");
    }
  }
  

  @UiHandler ("sendBtn")
  public void onSendBtn (TapEvent event) {
    
    TouchUtils.applyKeyboardPatch();
    
    if (signPanel.getRemoteUser() == null) {
      PhgDialogUtils.showMessageDialog("You must be signed");
      return;
    }
    
    final String receiverNumber = receiverBox.getValue().trim();
    
    if (receiverNumber.length() == 0) {
      PhgDialogUtils.showMessageDialog("Receiver number is empty");
      return;
    }
    
    // 14/10/2014 - see it.mate.postscriptum.server.utils.Countries.getInternationalPrefixFromLanguage
    /*
    if (!receiverNumber.startsWith("+")) {
      PhgDialogUtils.showMessageDialog("The receiver phone number must include the international prefix");
      return;
    }
    */
    
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
    
//  StickSms2 currentSms = new StickSmsTx2();
    
    if (editingSms == null) {
      editingSms = new StickSmsTx2();
      editingSms.setCreated(NOW);
      editingSms.setState(StickSms.STATE_NEW);
    }
    
    editingSms.setReceiverNumber(receiverNumber);
    editingSms.setBody(bodyArea.getValue());
    editingSms.setUser(signPanel.getRemoteUser());
    editingSms.setScheduled(scheduledDateTime);
    editingSms.setReceiverName(receiverName);
    
    // TEST PAID VERSION
    //editingSms.setClientType("P1");
    editingSms.setClientType("F1");
    editingSms.setClientVersion(AppProperties.IMPL.version());
    
    getPresenter().postNewSMS(editingSms, new Delegate<StickSms>() {
      public void execute(StickSms sms) {
        getPresenter().setLastReceiverNumber(receiverNumber);
        if (receiverNumber.equals(NewSmsView.this.receiverNumber)) {
          getPresenter().setLastReceiverName(receiverName);
        } else {
          getPresenter().setLastReceiverName("");
        }
        getPresenter().goToHome();
      }
    });

  }
  
}
