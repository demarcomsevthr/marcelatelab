package it.mate.copymob.client.view;

import it.mate.copymob.client.view.SettingsView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.utils.OnsDialogUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class SettingsView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void resetDB();
    public void goToAccountEditView();
    public void testWaitingState(boolean flag);
    public void updateOrdersFromServer();
    public void registerPushNotifications();
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label counterLbl;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof String) {
      counterLbl.setText((String)model);
    }
  }

  @UiHandler("btnReset")
  public void onBtnReset(TapEvent event) {
    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().resetDB();
      }
    });
  }
  
  @UiHandler("btnDialog")
  public void onBtnDialog(TapEvent event) {
    /*
    OnsDialogUtils.alert("Attenzione", "Marcello è grande!", null, "OK", null, new Delegate<Void>() {
      public void execute(Void element) {
        PhgUtils.log("ma va!");
      }
    });
    */
    /*
    OnsDialogUtils.confirm("Attenzione", "Marcello è grande!", null, new String[] {"Ma si", "Ma no"}, null, false, new Delegate<Integer>() {
      public void execute(Integer index) {
        PhgUtils.log("index = " + index);
      }
    });
    */
    OnsDialogUtils.showWaitingDialog();
  }
  
  @UiHandler("btnAccount")
  public void onBtnAccount(TapEvent event) {
    getPresenter().goToAccountEditView();
  }
  
  boolean waiting = false;
  
  @UiHandler("btnWaiting")
  public void onBtnWaiting(TapEvent event) {
    waiting = !waiting;
    getPresenter().testWaitingState(waiting);
  }
  
  @UiHandler("btnUpdate")
  public void onBtnUpdate(TapEvent event) {
    getPresenter().updateOrdersFromServer();
  }
  
  @UiHandler("btnPush")
  public void onBtnPush(TapEvent event) {
    getPresenter().registerPushNotifications();
  }
  
}
