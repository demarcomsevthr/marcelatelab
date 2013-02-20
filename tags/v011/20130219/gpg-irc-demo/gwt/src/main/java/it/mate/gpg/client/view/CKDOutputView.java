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
  
  @UiField TextBox epiGfrBox;
  @UiField TextBox epiGfrStadium;
  @UiField TextBox epiRiskBox;
  
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
  
  private void applyCKD(CKD ckd, double gfr, TextBox gfrBox, TextBox gfrStadiumBox, TextBox riskBox) {
    gfrBox.setValue(GwtUtils.formatDecimal(gfr, 2));
    gfrStadiumBox.setText(CKD.getGFRStadium(gfr));
    int risk = ckd.getRiskStadium(gfr);
    String irc = "";
    String ircCol = "white";
    if (risk == CKD.VERY_LOW_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_veryLowRisk_text();
      ircCol = "#00FF00";
    } else if (risk == CKD.LOW_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_lowRisk_text();
      ircCol = "#FFFF00";
    } else if (risk == CKD.MIDDLE_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_middleRisk_text();
      ircCol = "#FFCC00";
    } else if (risk == CKD.HIGH_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_highRisk_text();
      ircCol = "#FF0000";
    } else if (risk == CKD.VERY_HIGH_RISK) {
      irc = LocalizedMessages.IMPL.CKDOutputView_veryHighRisk_text();
      ircCol = "#990000";
    }
    riskBox.setValue(irc);
    riskBox.getElement().getStyle().setBackgroundColor(ircCol);
  }
  
}
