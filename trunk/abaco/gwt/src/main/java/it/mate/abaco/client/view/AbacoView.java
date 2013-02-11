package it.mate.abaco.client.view;

import it.mate.abaco.client.view.AbacoView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class AbacoView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, AbacoView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  
  public AbacoView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getTitle().setHTML("IRC Test Demo");
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToHome();
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }
  
  @UiHandler ("greenBtn")
  public void onGreenBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onGreenBtn", "one green please");
  }

  @UiHandler ("redBtn")
  public void onRedBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onRedBtn", "one red please");
  }

  @UiHandler ("blueBtn")
  public void onBlueBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onBlueBtn", "one blue please");
  }

}
