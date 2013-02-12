package it.mate.gpg.client.view;

import it.mate.gpg.client.model.CKD;
import it.mate.gpg.client.ui.SpinnerDoubleBox;
import it.mate.gpg.client.ui.SpinnerIntegerBox;
import it.mate.gpg.client.utils.IPhoneScrollPatch;
import it.mate.gpg.client.view.CKDInputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class CKDInputView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
    void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDInputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField SpinnerIntegerBox etaSpinBox;
  @UiField SpinnerDoubleBox creatininaSpinBox;
  @UiField SpinnerIntegerBox pesoSpinBox;
//@UiField SpinnerIntegerBox altezzaSpinBox;
  @UiField RadioButton mBtn;
  @UiField RadioButton fBtn;
  @UiField SpinnerIntegerBox albuminuriaSpinBox;
  
  public CKDInputView() {
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

    /*
    ValueChangeHandler<Integer> iHandler = new ValueChangeHandler<Integer>() {
      public void onValueChange(ValueChangeEvent<Integer> event) {
        onCalcBtn(null);
      }
    };
    ValueChangeHandler<Double> dHandler = new ValueChangeHandler<Double>() {
      public void onValueChange(ValueChangeEvent<Double> event) {
        onCalcBtn(null);
      }
    };
    ValueChangeHandler<Boolean> bHandler = new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        onCalcBtn(null);
      }
    };
    etaSpinBox.addValueChangeHandler(iHandler);
    creatininaSpinBox.addValueChangeHandler(dHandler);
    pesoSpinBox.addValueChangeHandler(iHandler);
    mBtn.addValueChangeHandler(bHandler);
    fBtn.addValueChangeHandler(bHandler);
    albuminuriaSpinBox.addValueChangeHandler(iHandler);
    */
    
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
    }
  }

  @UiHandler ("ckdOutputBtn")
  public void onCalcBtn(TapEvent event) {
    if (!isSet(etaSpinBox.getValue()))
      return;
    if (!isSet(pesoSpinBox.getValue()))
      return;
    if (!isSet(creatininaSpinBox.getValue()))
      return;
    if (!isSet(albuminuriaSpinBox.getValue()))
      return;
    
    CKD ckd = new CKD()
      .setAge(etaSpinBox.getValue())
      .setWeight(pesoSpinBox.getValue())
      .setScr(creatininaSpinBox.getValue())
      .setAlbumin(albuminuriaSpinBox.getValue())
      .setFemale(fBtn.getValue())
      .setHeight(170);
    
    getPresenter().goToCkdOutput(ckd);
  }
  
  private boolean isSet(Integer value) {
    return value != null && value > 0;
  }

  private boolean isSet(Double value) {
    return value != null && value > 0;
  }

}
