package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.EmailComposerPlugin;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.TouchCombo;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.activities.MainActivity;
import it.mate.protoph.client.ui.SignPanel;
import it.mate.protoph.client.view.SettingsView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class SettingsView extends BaseMgwtView <Presenter> {
  
  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void clearALL();
    public void downloadIngredients(final Delegate<String> delegate);
    public void resetPrincipiAttivi();
    public void doTestFile();
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField TouchCombo langCmb;
  
  /*
  @UiField TouchButton traceBtn;
  @UiField PhCheckBox cbxOnlineMode;
  @UiField Panel accountPanel;
  @UiField PhCheckBox cbxDoneBtnAddon;
  */
  
  @UiField PhCheckBox cbxUseDownloadedFiles;
  @UiField TouchButton downloadBtn;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();
    /*
    if (PhgUtils.getTrace() != null) {
      traceBtn.setText("Dump trace");
    }
    */
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    String localLanguage = PhgUtils.getAppLocalLanguage();
    langCmb.addItem("en", "English", "en".equals(localLanguage));
    langCmb.addItem("it", "Italiano", "it".equals(localLanguage));
//  cbxDoneBtnAddon.setValue(MainActivity.isEnabledDoneBtnAddon(), false);
    cbxUseDownloadedFiles.setValue(MainActivity.isUseDownloadedFiles(), false);
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("resetBtn")
  public void onResetBtn (TouchEndEvent event) {
    PhgDialogUtils.showMessageDialog("Are you sure you want to clear all data?", "Alert", PhgDialogUtils.BUTTONS_OKCANCEL, new Delegate<Integer>() {
      public void execute(Integer btnIndex) {
        if (btnIndex == 1) {
          getPresenter().clearALL();
        }
      }
    });
  }
  
  @UiHandler ("resetIngredientsBtn")
  public void onResetIngredientsBtn (TouchEndEvent event) {
    PhgDialogUtils.showMessageDialog("Are you sure you want to reset ingredients?", "Alert", PhgDialogUtils.BUTTONS_OKCANCEL, new Delegate<Integer>() {
      public void execute(Integer btnIndex) {
        if (btnIndex == 1) {
          getPresenter().resetPrincipiAttivi();
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

  /*
  @UiHandler ("dumpBtn")
  public void onDumpBtn (TouchEndEvent event) {
    final MainDao dao = AppClientFactory.IMPL.getMainDao();
    final EmailWrapper email = new EmailWrapper("THR db dump");
    final Delegate<String> previousErrorDelegate = dao.getErrorDelegate();
    dao.setErrorDelegate(new Delegate<String>() {
      public void execute(String error) {
        PhgUtils.log(">>> intercettato errore db " + error);
        dao.setErrorDelegate(previousErrorDelegate);
        email.println(error);
        email.showMailComposer();
      }
    });
    email.println(">>> START DUMP <<<");
  }
  */

  /*
  @UiHandler ("traceBtn")
  public void onTraceBtn (TouchEndEvent event) {
    if (PhgUtils.getTrace() == null) {
      PhgUtils.setLocalStorageItem(AppClientFactory.KEY_TRACE_ACTIVE, "true");
      Window.Location.assign("index.html");
    } else {
      EmailWrapper email = new EmailWrapper("THR trace dump");
      for (String line : PhgUtils.getTrace()) {
        email.println(line);
      }
      email.showMailComposer();
      PhgUtils.clearTrace();
      PhgUtils.setLocalStorageItem(AppClientFactory.KEY_TRACE_ACTIVE, "false");
    }
    getPresenter().goToHome();
  }
  */

  /*
  @UiHandler ("cbxOnlineMode")
  public void onCkbOnlineMode(ValueChangeEvent<Boolean> event) {
    boolean onlineMode = event.getValue();
    accountPanel.setVisible(onlineMode);
    if (onlineMode) {
      getPresenter().getAccount(new Delegate<Account>() {
        public void execute(Account account) {
          if (account == null) {
            
            PhgDialogUtils.showMessageDialog(AppMessages.IMPL.SettingsView_onCkbOnlineMode_msg1(), "Alert", PhgDialogUtils.BUTTONS_OKCANCEL, new Delegate<Integer>() {
              public void execute(Integer btn) {
                if (btn == 1) {

                } else {
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
  */

  /*
  @UiHandler ("accountBtn")
  public void onAccountBtn (TouchEndEvent event) {
    getPresenter().getAccount(new Delegate<Account>() {
      public void execute(Account account) {

      }
    });
  }
  */
  
  @UiHandler ("aboutBtn")
  public void onAboutBtn (TouchEndEvent event) {
//  getPresenter().goToAbout();
  }

  @UiHandler ("langCmb")
  public void onLangCmb(ValueChangeEvent<String> event) {
    PhgUtils.setAppLocalLanguageAndReload(event.getValue());
  }

  /*
  @UiHandler ("cbxDoneBtnAddon")
  public void onCbxDoneBtnAddon(ValueChangeEvent<Boolean> event) {
    MainActivity.setEnableDoneBtnAddon(event.getValue());
  }
  */
  
  @UiHandler ("cbxUseDownloadedFiles")
  public void onCbxUseCloudServer(ValueChangeEvent<Boolean> event) {
    MainActivity.setUseDownloadedFiles(event.getValue());
  }
  
  @UiHandler ("downloadBtn")
  public void onDownloadBtn (TouchEndEvent event) {
    getPresenter().downloadIngredients(new Delegate<String>() {
      public void execute(String element) {
        PhgDialogUtils.showMessageDialog("DONE");
      }
    });
  }

  @UiHandler ("testBtn")
  public void onTestBtn (TouchEndEvent event) {
    getPresenter().doTestFile();
  }
  
}
