package it.mate.ckd.client.view;

import it.mate.ckd.client.i18n.LocalizedMessages;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.ckd.client.view.CKDOutputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.Button;

public class CKDOutputView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDOutputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomMainCss style;

  @UiField Label cockcroftGfrBox;
  @UiField Label cockcroftGfrStadium;
  @UiField Label cockcroftRiskBox;
  
  @UiField Label mdrdGfrBox;
  @UiField Label mdrdGfrStadium;
  @UiField Label mdrdRiskBox;
  
  @UiField Label epiGfrBox;
  @UiField Label epiGfrStadium;
  @UiField Label epiRiskBox;
  
  @UiField Panel cockcroftRiskPanel1;
  @UiField Panel cockcroftRiskPanel2;
  @UiField Panel mdrdRiskPanel1;
  @UiField Panel mdrdRiskPanel2;
  @UiField Panel epiRiskPanel1;
  @UiField Panel epiRiskPanel2;
  
  @UiField Panel bsaPanel1;
  @UiField Panel bsaPanel2;
  @UiField Button useSbsBtn;
  @UiField Button useBsaBtn;
  @UiField HTML bsaHtml;
  
  private CKD ckd;
  
  public CKDOutputView() {
    style = (CustomMainCss)MGWTCustomTheme.getInstance().getMGWTClientBundle().getMainCss();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getHeaderBackButton().setText(LocalizedMessages.IMPL.CKDOutputView_headerBackButton_text());
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToCkdInput();
      }
    });
    useSbsBtn.getElement().setInnerHTML("1.73 m&#178;");
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      this.ckd = (CKD)model;
      applyCKD(ckd, ckd.getCockcroftGFR(), cockcroftGfrBox, cockcroftGfrStadium, cockcroftRiskBox, cockcroftRiskPanel1, cockcroftRiskPanel2);
      applyCKD(ckd, ckd.getMdrdGFR(), mdrdGfrBox, mdrdGfrStadium, mdrdRiskBox, mdrdRiskPanel1, mdrdRiskPanel2);
      applyCKD(ckd, ckd.getCkdEpiGFR(), epiGfrBox, epiGfrStadium, epiRiskBox, epiRiskPanel1, epiRiskPanel2);
      
      Double bsa = ckd.getBSA();
      if (bsa != null) {
        bsaPanel1.setVisible(true);
        bsaPanel2.setVisible(true);
        bsaHtml.setHTML("BSA = " + GwtUtils.formatDecimal(bsa, 2) + " m&#178;");

        useBsaBtn.removeStyleName("ckd-BsaBtnSelected");
        useSbsBtn.removeStyleName("ckd-BsaBtnSelected");
        if (ckd.isUseBsa()) {
          useBsaBtn.addStyleName("ckd-BsaBtnSelected");
        } else {
          useSbsBtn.addStyleName("ckd-BsaBtnSelected");
        }
        
      } else {
        bsaPanel1.setVisible(false);
        bsaPanel2.setVisible(false);
      }
      
    }
  }
  
  private void applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel1, Panel riskPanel2) {
    gfrBox.setText(GwtUtils.formatDecimal(gfr, 2));
    gfrStadiumBox.setText(CKD.getGFRStadium(gfr));
    int risk = ckd.getRiskStadium(gfr);
    String irc = "";
    String ircBckCol = "white";
    String ircCol = "black";
    if (risk == -1) {
      riskPanel1.setVisible(false);
      riskPanel2.setVisible(false);
    } else {
      riskPanel1.setVisible(true);
      riskPanel2.setVisible(true);
    }
    if (risk == CKD.VERY_LOW_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_veryLowRisk_text();
      ircBckCol = "#00FF00";
    } else if (risk == CKD.LOW_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_lowRisk_text();
      ircBckCol = "#FFFF00";
    } else if (risk == CKD.MIDDLE_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_middleRisk_text();
      ircBckCol = "#FFCC00";
    } else if (risk == CKD.HIGH_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_highRisk_text();
      ircBckCol = "#FF0000";
    } else if (risk == CKD.VERY_HIGH_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_veryHighRisk_text();
      ircBckCol = "#990000";
      ircCol = "white";
    }
    riskBox.setText(irc);
    riskBox.getElement().getStyle().setColor(ircCol);
    riskBox.getElement().getStyle().setBackgroundColor(ircBckCol);
  }

  @UiHandler ("useBsaBtn")
  public void useBsaBtn(TapEvent event) {
    if (ckd != null) {
      ckd.setUseBsa(true);
      setModel(ckd, null);
    }
  }
  
  @UiHandler ("useSbsBtn")
  public void useSbsBtn(TapEvent event) {
    if (ckd != null) {
      ckd.setUseBsa(false);
      setModel(ckd, null);
    }
  }
  
}
