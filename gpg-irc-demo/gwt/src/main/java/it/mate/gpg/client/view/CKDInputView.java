package it.mate.gpg.client.view;

import it.mate.gpg.client.model.CKD;
import it.mate.gpg.client.ui.SpinnerDoubleBox;
import it.mate.gpg.client.ui.SpinnerIntegerBox;
import it.mate.gpg.client.ui.theme.custom.CustomMainCss;
import it.mate.gpg.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gpg.client.utils.IPhoneScrollPatch;
import it.mate.gpg.client.view.CKDInputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;

public class CKDInputView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
    void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDInputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomMainCss style;

  @UiField SpinnerIntegerBox etaSpinBox;
  @UiField SpinnerDoubleBox creatininaSpinBox;
  @UiField SpinnerIntegerBox pesoSpinBox;
  @UiField SpinnerIntegerBox altezzaSpinBox;
  @UiField CheckBox fBtn;
  @UiField CheckBox bBtn;
  @UiField SpinnerIntegerBox albuminuriaSpinBox;
  
  public CKDInputView() {
    style = (CustomMainCss)MGWTCustomTheme.getInstance().getMGWTClientBundle().getMainCss();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    getHeaderBackButton().setText("Home");
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToHome();
      }
    });
    
    creatininaSpinBox.setValue(1d);
    
    creatininaSpinBox.setIncrement(0.1);

    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        IPhoneScrollPatch.apply();
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      CKD ckd = (CKD)model;
      etaSpinBox.setValue(ckd.getAge());
      pesoSpinBox.setValue(ckd.getWeight());
      creatininaSpinBox.setValue(ckd.getScr());
      albuminuriaSpinBox.setValue((int)ckd.getAlbumin());
      fBtn.setValue(ckd.isFemale());
      bBtn.setValue(ckd.isBlack());
      altezzaSpinBox.setValue(ckd.getHeight());
    }
  }

  @UiHandler ("ckdOutputBtn")
  public void onCalcBtn(TouchStartEvent event) {
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        if (!isSet(etaSpinBox.getValue()))
          return;
        if (!isSet(pesoSpinBox.getValue()))
          return;
        if (!isSet(creatininaSpinBox.getValue()))
          return;
        if (!isSet(albuminuriaSpinBox.getValue()))
          return;
        if (!isSet(altezzaSpinBox.getValue()))
          return;
        CKD ckd = new CKD()
          .setAge(etaSpinBox.getValue())
          .setWeight(pesoSpinBox.getValue())
          .setScr(creatininaSpinBox.getValue())
          .setAlbumin(albuminuriaSpinBox.getValue())
          .setFemale(fBtn.getValue())
          .setBlack(bBtn.getValue())
          .setHeight(altezzaSpinBox.getValue());
        getPresenter().goToCkdOutput(ckd);
      }
    });
    
  }
  
  private boolean isSet(Integer value) {
    return value != null && value > 0;
  }

  private boolean isSet(Double value) {
    return value != null && value > 0;
  }

}
