package it.mate.gpg.client.view;

import it.mate.gpg.client.i18n.LocalizedMessages;
import it.mate.gpg.client.model.CKD;
import it.mate.gpg.client.ui.theme.custom.CustomMainCss;
import it.mate.gpg.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gpg.client.view.CKDOutputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
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

  @UiField Label cockcroftGfrBox;
  @UiField Label cockcroftGfrStadium;
  @UiField Label cockcroftRiskBox;
  
  @UiField Label mdrdGfrBox;
  @UiField Label mdrdGfrStadium;
  @UiField Label mdrdRiskBox;
  
  @UiField Label epiGfrBox;
  @UiField Label epiGfrStadium;
  @UiField Label epiRiskBox;
  
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
//  getHeaderBackButton().setWidth("5em");
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
      applyCKD(ckd, ckd.getCkdEpiGFR(), epiGfrBox, epiGfrStadium, epiRiskBox);
    }
  }
  
  private void applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox) {
    gfrBox.setText(GwtUtils.formatDecimal(gfr, 2));
    gfrStadiumBox.setText(CKD.getGFRStadium(gfr));
    int risk = ckd.getRiskStadium(gfr);
    String irc = "";
    String ircBckCol = "white";
    String ircCol = "black";
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
  
}
