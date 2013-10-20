package it.mate.ckd.client.view;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.ui.SpinnerDoubleBox;
import it.mate.ckd.client.ui.SpinnerIntegerBox;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.view.CKDInputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.IPhoneScrollPatch;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;

public class CKDInputView extends DetailView<Presenter> /* BaseMgwtView <Presenter> */ {

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
    void goToHome();
    void checkRatingDialog(final Delegate<PopinDialog> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDInputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;

  @UiField Panel wrapperPanel;
  @UiField SpinnerIntegerBox etaSpinBox;
  @UiField SpinnerDoubleBox creatininaSpinBox;
  @UiField SpinnerIntegerBox pesoSpinBox;
  @UiField SpinnerIntegerBox altezzaSpinBox;
  @UiField HasValue<Boolean> fBtn;
  @UiField HasValue<Boolean> bBtn;
  @UiField SpinnerIntegerBox albuminuriaSpinBox;
  @UiField Anchor creatinineUmAnc;
  @UiField Anchor albuminUmAnc;
  
  private CKD ckd = new CKD();
  
  public CKDInputView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    creatininaSpinBox.setValue(1d);
    
    creatininaSpinBox.setIncrement(0.1);

    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        IPhoneScrollPatch.apply();
      }
    });
    
    
    
    AppClientFactory.IMPL.adaptWrapperPanel(wrapperPanel, "inputWrapperPanel", false, 0, new Delegate<Element>() {
      public void execute(final Element wrapperPanelElem) {
        GwtUtils.onAvailable("ckdInputPanelTable", new Delegate<Element>() {
          public void execute(Element inputPanelTableElem) {
            int width = inputPanelTableElem.getClientWidth();
            int horMargin = ( wrapperPanelElem.getClientWidth() - width ) / 2;
            inputPanelTableElem.getStyle().setMarginLeft(horMargin, Unit.PX);
            inputPanelTableElem.getStyle().setMarginRight(horMargin, Unit.PX);
          }
        });
      }
    });
    
    etaSpinBox.setMinValue(0);
    creatininaSpinBox.setMinValue(0d);
    pesoSpinBox.setMinValue(0);
    altezzaSpinBox.setMinValue(0);
    albuminuriaSpinBox.setMinValue(0);
    
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
    if (ckd.getScrUnit() == CKD.PMOL_L_UNIT) {
      creatininaSpinBox.setValue(CKD.convertMgDlToPMoll(creatininaSpinBox.getValue()));
    } else {
      creatininaSpinBox.setValue(CKD.convertPMollToMgDl(creatininaSpinBox.getValue()));
    }
  }
  private void updateCreatinineUmAnc() {
    creatinineUmAnc.setHTML(ckd.getScrUnit() == CKD.MG_DL_UNIT ? "mg/dl" : "mmol/l");
  }

  @UiHandler ("albuminUmAnc")
  public void onAlbuminUmAnc (ClickEvent event) {
    ckd.setAlbUnit(ckd.getAlbUnit() == CKD.MG_G_UNIT ? CKD.MG_MMOL_UNIT : CKD.MG_G_UNIT);
    updateAlbuminUmAnc();
    if (ckd.getAlbUnit() == CKD.MG_MMOL_UNIT) {
      albuminuriaSpinBox.setValue(CKD.convertMgGToMMol(albuminuriaSpinBox.getValue()));
    } else {
      albuminuriaSpinBox.setValue(CKD.convertMMolToMgG(albuminuriaSpinBox.getValue()));
    }
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
        if (etaSpinBox.getValue() < 0 || etaSpinBox.getValue() > 120) {
          MessageBoxUtils.popupOk("Wrong value for age");
          return;
        }
        if (!isSet(pesoSpinBox.getValue()))
          return;
        if (pesoSpinBox.getValue() <= 0) {
          MessageBoxUtils.popupOk("Wrong value for weight");
          return;
        }
        if (!isSet(creatininaSpinBox.getValue()))
          return;
        if (ckd.getScrMgDl(creatininaSpinBox.getValue()) < 0.294) {
          MessageBoxUtils.popupOk("Wrong value for creatinine");
          return;
        }
        if (ckd.getScrMgDl(creatininaSpinBox.getValue()) > 30) {
          MessageBoxUtils.popupOk("Wrong value for creatinine");
          return;
        }
        if (isSet(altezzaSpinBox.getValue())) {
          if (altezzaSpinBox.getValue() <= 0 || altezzaSpinBox.getValue() > 240) {
            MessageBoxUtils.popupOk("Wrong value for heigth");
            return;
          }
        }
        if (isSet(albuminuriaSpinBox.getValue())) {
          if (albuminuriaSpinBox.getValue() < 0) {
            MessageBoxUtils.popupOk("Wrong value for albuminuria");
            return;
          }
        }
        ckd.setAge(etaSpinBox.getValue())
          .setWeight(pesoSpinBox.getValue())
          .setScr(creatininaSpinBox.getValue())
          .setAlbumin(albuminuriaSpinBox.getValueAllowNull())
          .setFemale(fBtn.getValue())
          .setBlack(bBtn.getValue())
          .setHeight(altezzaSpinBox.getValue());
        
        getPresenter().checkRatingDialog(new Delegate<PopinDialog>() {
          public void execute(PopinDialog element) {
            getPresenter().goToCkdOutput(ckd);
          }
        });
        
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
