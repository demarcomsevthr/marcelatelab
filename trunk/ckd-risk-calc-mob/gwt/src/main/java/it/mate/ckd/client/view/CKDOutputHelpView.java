package it.mate.ckd.client.view;

import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.ckd.client.view.CKDOutputHelpView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class CKDOutputHelpView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDOutputHelpView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomMainCss style;

  public CKDOutputHelpView() {
    style = (CustomMainCss)MGWTCustomTheme.getInstance().getMGWTClientBundle().getMainCss();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getHeaderBackButton().setText("Back");
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToCkdOutput(null);
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }
  
  
}
