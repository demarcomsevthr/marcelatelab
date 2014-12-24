package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToApplicationListView();
    public void goToSettingsView();
    public void doTestFile();
    public void goToTestView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
  @UiField OnsButton btnSettings;
  @UiField Label counterLbl;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
    
    btnSettings.onClickTest(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        CdvUtils.log("btnSettings touched");
        getPresenter().goToSettingsView();
      }
    });

  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof String) {
      counterLbl.setText((String)model);
    }
  }

  /*
  @UiHandler("btnSettings")
  public void onBtnSettings(TouchEndEvent event) {
    CdvUtils.log("btnSettings touched");
    getPresenter().goToSettingsView();
  }
  */
  
}
