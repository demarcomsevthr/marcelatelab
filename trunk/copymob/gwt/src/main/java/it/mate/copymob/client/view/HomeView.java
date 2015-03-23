package it.mate.copymob.client.view;

import it.mate.copymob.client.constants.AppProperties;
import it.mate.copymob.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsToolbarButton;

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
    public void showMenu();
    public void goToTimbriListView();
    public void goToCartListView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
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
    btnMenu.setVisible(OnsenUi.isSlidingMenuLayoutPattern());
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }

  @UiHandler("btnSettings")
  public void onBtnSettings(TapEvent event) {
    getPresenter().goToSettingsView();
  }
  
  @UiHandler("btnMenu")
  public void onBtnMenu(TapEvent event) {
    getPresenter().showMenu();
  }
  
  @UiHandler("btnTimbri")
  public void onBtnTimbri(TapEvent event) {
    getPresenter().goToTimbriListView();
  }
  
  @UiHandler("btnCart")
  public void onBtnCart(TapEvent event) {
    getPresenter().goToCartListView();
  }
  
  /*
  @UiHandler("btnTimbri2")
  public void onBtnTimbri2(TapEvent event) {
    getPresenter().goToTimbriListView();
  }
  */
  
}
