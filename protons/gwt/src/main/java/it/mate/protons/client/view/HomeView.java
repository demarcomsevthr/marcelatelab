package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.nextgen.GWTUtils;
import it.mate.gwtcommons.client.nextgen.HTMLElement;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class HomeView extends BaseMgwtView <Presenter> {

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
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler("btnApplications")
  public void onApplicationsBtn(TouchEndEvent event) {
    getPresenter().goToApplicationListView();
  }
  
  @UiHandler("btnSettings")
  public void onBtnSettings(TouchEndEvent event) {
    getPresenter().goToSettingsView();
  }
  
  @UiHandler("btnTest")
  public void onBtnTest(TouchEndEvent event) {
//  getPresenter().goToTestView();
    
    Document doc = GWTUtils.getDocument();
    PhgUtils.log("doc = "+doc);
    
    HTMLElement elem = doc.createElement("ons-navigator").cast();
    
    GWTUtils.getBody().appendChild(elem);

    
  }
  
}
