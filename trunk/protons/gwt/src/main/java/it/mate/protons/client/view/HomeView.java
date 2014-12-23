package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.utils.OnsUtils;
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
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
    
    GwtUtils.deferredExecution(1000, new Delegate<Void>() {
      public void execute(Void element) {
        btnSettings.onClickTest(new JSOCallback() {
          public void handle(JavaScriptObject jso) {
            OnsUtils.log("btnSettings touched");
          }
        });
      }
    });
    
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  /*
  @UiHandler("btnSettings")
  public void onBtnSettings(TouchEndEvent event) {
    OnsUtils.log("btnSettings touched");
    getPresenter().goToSettingsView();
  }
  */
  
}
