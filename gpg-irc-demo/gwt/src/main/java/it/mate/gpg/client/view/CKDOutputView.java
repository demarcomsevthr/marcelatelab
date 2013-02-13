package it.mate.gpg.client.view;

import it.mate.gpg.client.model.CKD;
import it.mate.gpg.client.ui.theme.custom.CustomMainCss;
import it.mate.gpg.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gpg.client.view.CKDOutputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class CKDOutputView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDOutputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomMainCss style;

  @UiField TextBox cockcroftGfrBox;
  @UiField TextBox cockcroftGfrStadium;
  @UiField TextBox cockcroftRiskBox;
  
  @UiField TextBox mdrdGfrBox;
  @UiField TextBox mdrdGfrStadium;
  @UiField TextBox mdrdRiskBox;
  
  public CKDOutputView() {
    style = (CustomMainCss)MGWTCustomTheme.getInstance().getMGWTClientBundle().getMainCss();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getHeaderBackButton().setText("Parametri");
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToCkdInput();
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      CKD ckd = (CKD)model;
      applyCKD(ckd, ckd.getCockcroftGFR(), cockcroftGfrBox, cockcroftGfrStadium, cockcroftRiskBox);
      applyCKD(ckd, ckd.getMdrdGFR(), mdrdGfrBox, mdrdGfrStadium, mdrdRiskBox);
    }
  }
  
  private void applyCKD(CKD ckd, double gfr, TextBox gfrBox, TextBox gfrStadiumBox, TextBox riskBox) {
    gfrBox.setValue(GwtUtils.formatDecimal(gfr, 2));
    gfrStadiumBox.setText(CKD.getGFRStadium(gfr));
    int risk = ckd.getRiskStadium(gfr);
    String irc = "";
    String ircCol = "white";
    if (risk == CKD.VERY_LOW_RISK) {
      irc = "molto basso";
      ircCol = "#00FF00";
    } else if (risk == CKD.LOW_RISK) {
      irc = "basso";
      ircCol = "#FFFF00";
    } else if (risk == CKD.MIDDLE_RISK) {
      irc = "medio";
      ircCol = "#FFCC00";
    } else if (risk == CKD.HIGH_RISK) {
      irc = "alto";
      ircCol = "#FF0000";
    } else if (risk == CKD.VERY_HIGH_RISK) {
      irc = "molto alto";
      ircCol = "#990000";
    }
    riskBox.setValue(irc);
    riskBox.getElement().getStyle().setBackgroundColor(ircCol);
  }

  /*
  public void onCalcBtn(ClickEvent event) {
    if (!isSet(etaSpinBox.getValue()))
      return;
    if (!isSet(pesoSpinBox.getValue()))
      return;
    if (!isSet(creatininaSpinBox.getValue()))
      return;
    if (!isSet(albuminuriaSpinBox.getValue()))
      return;
    double vfg = (140 - etaSpinBox.getValue()) * pesoSpinBox.getValue() / (72d * creatininaSpinBox.getValue());
    if (fBtn.getValue())
      vfg *= 0.85;
    vfgBox.setValue(GwtUtils.formatDecimal(vfg, 2));
    String stadioVfg = "";
    if (vfg >= 90) {
      stadioVfg = "G1";
    } else if (vfg >= 60) {
      stadioVfg = "G2";
    } else if (vfg >= 30) {
      stadioVfg = "G3";
    } else if (vfg >= 15) {
      stadioVfg = "G4";
    } else {
      stadioVfg = "G5";
    }
    stadioVfgBox.setText(stadioVfg);
    String irc = "";
    int alb = albuminuriaSpinBox.getValue();
    String ircCol = "white";
    if (alb < 10) {
      if (vfg >= 60) {
        irc = "molto basso";
        ircCol = "#00FF00";
      } else if (vfg >= 45) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 30) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 29) {
      if (vfg >= 60) {
        irc = "molto basso";
        ircCol = "#00FF00";
      } else if (vfg >= 45) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 30) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 299) {
      if (vfg >= 60) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 45) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 1999) {
      if (vfg >= 60) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else {
      irc = "molto alto";
      ircCol = "#990000";
    }
    ircBox.setValue(irc);
    ircBox.getElement().getStyle().setBackgroundColor(ircCol);
  }
  
  private boolean isSet(Integer value) {
    return value != null && value > 0;
  }

  private boolean isSet(Double value) {
    return value != null && value > 0;
  }
  */

}
