package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.EmailComposerPlugin;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.TouchCombo;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.logic.MainDao;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.SettingsView.Presenter;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class SettingsView extends BaseMgwtView <Presenter> {
  
  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void dropDB();
    public void setOnlineMode(boolean onlineMode);
    public boolean isOnlineMode();
    public void goToAccountEditView(Account account);
    public void goToAbout();
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField TouchButton traceBtn;
  @UiField PhCheckBox cbxOnlineMode;
  @UiField Panel accountPanel;
  @UiField TouchCombo langCmb;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();
    if (PhonegapUtils.getTrace() != null) {
      traceBtn.setText("Dump trace");
    }
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    if (getPresenter().isOnlineMode()) {
      cbxOnlineMode.setValue(true);
      accountPanel.setVisible(true);
    }
    String localLanguage = PhonegapUtils.getAppLocalLanguage();
    langCmb.addItem("en", "English", "en".equals(localLanguage));
    langCmb.addItem("it", "Italiano", "it".equals(localLanguage));
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("resetBtn")
  public void onResetBtn (TouchEndEvent event) {
    PhgDialogUtils.showMessageDialog("Are you sure you want to clear all data?", "Alert", PhgDialogUtils.BUTTONS_OKCANCEL, new Delegate<Integer>() {
      public void execute(Integer btnIndex) {
        if (btnIndex == 1) {
          getPresenter().dropDB();
        }
      }
    });
  }
  
  private class EmailWrapper {
    private String subject;
    private String body = "";
    private String[] toRecipients = new String[] {"dev.medup@gmail.com"};
    public EmailWrapper(String subject) {
      this.subject = subject;
    }
    protected void println(String row) {
      body += row + "<br/>";
    }
    protected void showMailComposer() {
      EmailComposerPlugin.showEmailComposer(subject, body, toRecipients, null, null, true, null, null);
    }
  }

  @UiHandler ("dumpBtn")
  public void onDumpBtn (TouchEndEvent event) {
    final MainDao dao = AppClientFactory.IMPL.getPrescrizioniDao();
    final EmailWrapper email = new EmailWrapper("THR db dump");
    final Delegate<String> previousErrorDelegate = dao.getErrorDelegate();
    dao.setErrorDelegate(new Delegate<String>() {
      public void execute(String error) {
        PhonegapUtils.log(">>> intercettato errore db " + error);
        dao.setErrorDelegate(previousErrorDelegate);
        email.println(error);
        email.showMailComposer();
      }
    });
    email.println(">>> START DUMP <<<");
    email.println("finding all prescrizioni...");
    dao.findAllPrescrizioni(new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioni) {
        if (prescrizioni == null)
          prescrizioni = new ArrayList<Prescrizione>();
        email.println("found " + prescrizioni.size() + " prescrizioni");
        for (Prescrizione prescrizione : prescrizioni) {
          email.println("found " + prescrizione);
          email.println("finding all somministrazioni by prescrizione...");
          
          dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
            public void execute(List<Somministrazione> somministrazioni) {
              if (somministrazioni == null)
                somministrazioni = new ArrayList<Somministrazione>();
              email.println("found " + somministrazioni.size() + " somministrazioni");
              for (Somministrazione somministrazione : somministrazioni) {
                email.println("found " + somministrazione);
              }
              
              email.println(">>> END DUMP <<<");
              dao.setErrorDelegate(previousErrorDelegate);
              email.showMailComposer();
              
            }
          });
          
        }
      }
    });

  }

  @UiHandler ("traceBtn")
  public void onTraceBtn (TouchEndEvent event) {
    if (PhonegapUtils.getTrace() == null) {
      PhonegapUtils.setLocalStorageProperty(AppClientFactory.KEY_TRACE_ACTIVE, "true");
      Window.Location.assign("index.html");
    } else {
      EmailWrapper email = new EmailWrapper("THR trace dump");
      for (String line : PhonegapUtils.getTrace()) {
        email.println(line);
      }
      email.showMailComposer();
      PhonegapUtils.clearTrace();
      PhonegapUtils.setLocalStorageProperty(AppClientFactory.KEY_TRACE_ACTIVE, "false");
    }
    getPresenter().goToHome();
  }

  @UiHandler ("cbxOnlineMode")
  public void onCkbOnlineMode(ValueChangeEvent<Boolean> event) {
    boolean onlineMode = event.getValue();
    getPresenter().setOnlineMode(onlineMode);
    accountPanel.setVisible(onlineMode);
    if (onlineMode) {
      getPresenter().getAccount(new Delegate<Account>() {
        public void execute(Account account) {
          if (account == null) {
            
            String msg = "In online mode you can send notifications to the tutors: in order to use this feature you must have mobile data connection TURNED ON and you have to create a personal account.";
            PhgDialogUtils.showMessageDialog(msg, "Alert", PhgDialogUtils.BUTTONS_OKCANCEL, new Delegate<Integer>() {
              public void execute(Integer btn) {
                if (btn == 1) {
                  getPresenter().goToAccountEditView(null);
                } else {
                  getPresenter().setOnlineMode(false);
                  cbxOnlineMode.setValue(false);
                  accountPanel.setVisible(false);
                }
              }
            });
            
          }
        }
      });
    }
  }
  
  @UiHandler ("accountBtn")
  public void onAccountBtn (TouchEndEvent event) {
    getPresenter().getAccount(new Delegate<Account>() {
      public void execute(Account account) {
        getPresenter().goToAccountEditView(account);
      }
    });
  }
  
  @UiHandler ("aboutBtn")
  public void onAboutBtn (TouchEndEvent event) {
    getPresenter().goToAbout();
  }

  @UiHandler ("langCmb")
  public void onLangCmb(ValueChangeEvent<String> event) {
    PhonegapUtils.setAppLocalLanguageAndReload(event.getValue());
  }
  
}