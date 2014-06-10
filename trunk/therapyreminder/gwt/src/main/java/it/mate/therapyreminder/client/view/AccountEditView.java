package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.view.AccountEditView.Presenter;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.impl.AccountTx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class AccountEditView extends BaseMgwtView <Presenter> implements HasClosingViewHandler  {
  
  public static final String TAG_ACCOUNT = "account";

  public interface Presenter extends BasePresenter {
    public void goToHome();
    public void saveAccount(Account account, Delegate<Account> successDelegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, AccountEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField PhTextBox nameBox;
  //@UiField PhTextBox pwdBox;
  @UiField PhTextBox emailBox;
  
  Account account;
  
  public AccountEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    wrapperPanel.getElement().getStyle().clearHeight();
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
    if (TAG_ACCOUNT.equals(tag)) {
      
      account = (Account)model;
      
      nameBox.setValue(account.getName());
      emailBox.setValue(account.getEmail());
      
    }
      
  }

  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    PhonegapUtils.log("save btn");
    flushAndSaveAccount(new Delegate<Account>() {
      public void execute(Account account) {
        getPresenter().goToPrevious();
      }
    });
  }
  
  @Override
  public void onClosingView(final ClosingHandler handler) {
    PhonegapUtils.log("close delegate");
    flushAndSaveAccount(new Delegate<Account>() {
      public void execute(Account account) {
        handler.doClose();
      }
    });
  }
  
  private void flushAndSaveAccount(Delegate<Account> successDelegate) {
    Account modifiedAccount = new AccountTx(account);
    modifiedAccount.setName(nameBox.getValue());
    modifiedAccount.setEmail(emailBox.getValue());
    if (modifiedAccount.equals(account)) {
      successDelegate.execute(modifiedAccount);
    } else {
      getPresenter().saveAccount(modifiedAccount, successDelegate);
    }
  }

}
