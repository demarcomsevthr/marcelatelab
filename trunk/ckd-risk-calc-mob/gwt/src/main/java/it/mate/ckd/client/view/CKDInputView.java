package it.mate.ckd.client.view;

import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.ui.SpinnerDoubleBox;
import it.mate.ckd.client.ui.SpinnerIntegerBox;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.utils.IPhoneScrollPatch;
import it.mate.ckd.client.utils.OsDetectionPatch;
import it.mate.ckd.client.view.CKDInputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.Button;

public class CKDInputView extends DetailView<Presenter> /* BaseMgwtView <Presenter> */ {

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
    void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDInputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  /*
  @UiField (provided=true) MGWTCustomClientBundle bundle;
  */
  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;

  @UiField SpinnerIntegerBox etaSpinBox;
  @UiField SpinnerDoubleBox creatininaSpinBox;
  @UiField SpinnerIntegerBox pesoSpinBox;
  @UiField SpinnerIntegerBox altezzaSpinBox;
  @UiField HasValue<Boolean> fBtn;
  @UiField HasValue<Boolean> bBtn;
  @UiField SpinnerIntegerBox albuminuriaSpinBox;
  @UiField Anchor creatinineUmAnc;
  @UiField Anchor albuminUmAnc;
  
  @UiField Button ckdOutputBtn;
  
  private CKD ckd = new CKD();
  
  public CKDInputView() {
    /*
    bundle = MGWTCustomTheme.getInstance().getMGWTClientBundle();
    style = (CustomMainCss)MGWTCustomTheme.getInstance().getMGWTClientBundle().getMainCss();
    */
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    /*
    getHeaderBackButton().setText("Home");
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToHome();
      }
    });
    */
    
    creatininaSpinBox.setValue(1d);
    
    creatininaSpinBox.setIncrement(0.1);

    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        IPhoneScrollPatch.apply();
      }
    });
    
    if (OsDetectionPatch.isTablet()) {
      
      ckdOutputBtn.setVisible(false);
      
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
      altezzaSpinBox.addValueChangeHandler(iHandler);
      albuminuriaSpinBox.addValueChangeHandler(iHandler);
      fBtn.addValueChangeHandler(bHandler);
      bBtn.addValueChangeHandler(bHandler);
      
      onCalcBtn(null);
      
    }
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      this.ckd = (CKD)model;
      etaSpinBox.setValue(ckd.getAge());
      pesoSpinBox.setValue(ckd.getWeight());
      creatininaSpinBox.setValue(ckd.getScr());
      if (ckd.getAlbumin() != null) {
        albuminuriaSpinBox.setValue(ckd.getAlbumin().intValue());
      }
      fBtn.setValue(ckd.isFemale());
      bBtn.setValue(ckd.isBlack());
      altezzaSpinBox.setValue(ckd.getHeight());
      updateCreatinineUmAnc();
      updateAlbuminUmAnc();
    }
  }
  
  
  @UiHandler ("creatinineUmAnc")
  public void onCreatinineUmAnc (ClickEvent event) {
    ckd.setScrUnit(ckd.getScrUnit() == CKD.MG_DL_UNIT ? CKD.PMOL_L_UNIT : CKD.MG_DL_UNIT);
    updateCreatinineUmAnc();
  }
  private void updateCreatinineUmAnc() {
    creatinineUmAnc.setHTML(ckd.getScrUnit() == CKD.MG_DL_UNIT ? "mg/dl" : "mmol/l");
  }

  @UiHandler ("albuminUmAnc")
  public void onAlbuminUmAnc (ClickEvent event) {
    ckd.setAlbUnit(ckd.getAlbUnit() == CKD.MG_G_UNIT ? CKD.MG_MMOL_UNIT : CKD.MG_G_UNIT);
    updateAlbuminUmAnc();
  }
  private void updateAlbuminUmAnc () {
    albuminUmAnc.setHTML(ckd.getAlbUnit() == CKD.MG_G_UNIT ? "mg/g" : "<div>mg/</div><div>mmol</div>");
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
        ckd.setAge(etaSpinBox.getValue())
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
