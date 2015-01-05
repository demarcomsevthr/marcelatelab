package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.protons.client.view.MenuView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class MenuView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void goToSettingsView();
    public void goToSearchView();
    public void goToApplicationListView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, MenuView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  public MenuView() {
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

  }

  @UiHandler("btnHome")
  public void onBtnHome(TapEvent event) {
    getPresenter().goToHomeView();
  }
  
  @UiHandler("btnSettings")
  public void onBtnSettings(TapEvent event) {
    getPresenter().goToSettingsView();
  }
  
  @UiHandler("btnSearch")
  public void onBtnSearch(TapEvent event) {
    getPresenter().goToSearchView();
  }
  
  @UiHandler("btnApplications")
  public void onBtnApplications(TapEvent event) {
    getPresenter().goToApplicationListView();
  }
  
}
