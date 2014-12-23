package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.nextgen.GWTUtils;
import it.mate.gwtcommons.client.nextgen.HTMLElement;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.TouchEndEvent;
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
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

//@UiHandler("btnTest")
  public void onBtnTest(TouchEndEvent event) {
    
//  getPresenter().goToTestView();

    /*
    Document doc = GWTUtils.getDocument();
    PhgUtils.log("doc = "+doc);
    HTMLElement elem = doc.createElement("ons-navigator").cast();
    */
    
    HTMLElement elem = GWTUtils.createElement("ons-navigator");
    
    GWTUtils.getBody().appendChild(elem);

    
  }
  
}
