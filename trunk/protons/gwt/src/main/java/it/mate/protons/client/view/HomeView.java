package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsToolbarButton;
import it.mate.protons.client.constants.AppProperties;
import it.mate.protons.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToSettingsView();
    public void goToSearchView();
    public void showMenu();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
  @UiField Label counterLbl;
  @UiField OnsToolbarButton btnMenu;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
    btnMenu.setVisible(OnsenUi.isSlidingMenuLayout());
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof String) {
      counterLbl.setText((String)model);
    }
  }

  @UiHandler("btnSettings")
  public void onBtnSettings(TapEvent event) {
    getPresenter().goToSettingsView();
  }
  
  @UiHandler("btnSearch")
  public void onBtnSearch(TapEvent event) {
    getPresenter().goToSearchView();
  }
  
  @UiHandler("btnMenu")
  public void onBtnMenu(TapEvent event) {
    getPresenter().showMenu();
  }
  
}
