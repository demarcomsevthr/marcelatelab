package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.view.CKDOutputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.HasTag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;

public class CKDOutputView extends DetailView<Presenter> /* BaseMgwtView <Presenter> */ {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
    void goToProtocolStep(ProtocolStep protocolStep);
    void goToExtendedView(String selectedGFR);
    void goToReferralDecision();
    void openHelpPage();
    boolean applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead);
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDOutputView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;
  
  @UiField Panel wrapperPanel;

  @UiField Label cockcroftGfrBox;
  @UiField Label cockcroftGfrStadium;
  @UiField Label cockcroftRiskBox;
  @UiField Panel cockcroftRiskPanel;
  
  @UiField Label mdrdGfrBox;
  @UiField Label mdrdGfrStadium;
  @UiField Label mdrdRiskBox;
  @UiField Panel mdrdRiskPanel;
  
  @UiField Label mdrdNcGfrBox;
  @UiField Label mdrdNcGfrStadium;
  @UiField Label mdrdNcRiskBox;
  @UiField Panel mdrdNcRiskPanel;
  
  @UiField Label epiGfrBox;
  @UiField Label epiGfrStadium;
  @UiField Label epiRiskBox;
  @UiField Panel epiRiskPanel;
  
  @UiField Panel bsaPanel;
  @UiField HTML bsaHtml;
  
  @UiField Panel workflowHelpPanel;
  
//@UiField Button ckdHelpBtn;
  
  private CKD ckd;
  
  private boolean riskVisible = false;
  
  public CKDOutputView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();

    AppClientFactory.IMPL.adaptWrapperPanel(wrapperPanel, "outputWrapperPanel", false, 0, new Delegate<Element>() {
      public void execute(final Element wrapperPanelElem) {
        
        if (AppProperties.IMPL.CKDOutputView_adjust_ckdHelpBtn_position()) {
          /*
          int width = wrapperPanelElem.getClientWidth();
          int height = wrapperPanelElem.getClientHeight();
          Element btnElem = ckdHelpBtn.getElement();
          int btnLeft = (width - btnElem.getOffsetWidth()) / 2;
          int btnTop = (height - btnElem.getOffsetHeight() * 3 / 2);
          btnElem.getStyle().setLeft(btnLeft, Unit.PX);
          btnElem.getStyle().setTop(btnTop, Unit.PX);
          */
        }
        
      }
    });
    
    /*
    wrapperPanel.getElement().setId("outputWrapperPanel");
    if (OsDetectionPatch.isTablet()) {
      GwtUtils.onAvailable("outputWrapperPanel", new Delegate<Element>() {
        public void execute(final Element wrapperPanelElem) {
          int wrapperPct = AppClientFactory.IMPL.getWrapperPct();
          
          int height = Window.getClientHeight() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setHeight(height, Unit.PX);
          int width = Window.getClientWidth() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setWidth(width, Unit.PX);
          
          int horMargin = ( Window.getClientWidth() - width ) / 2;
          wrapperPanelElem.getStyle().setMarginLeft(horMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginRight(horMargin, Unit.PX);
          
          Element btnElem = ckdHelpBtn.getElement();
          int btnLeft = (width - btnElem.getOffsetWidth()) / 2;
          int btnTop = (height - btnElem.getOffsetHeight() * 3 / 2);
          btnElem.getStyle().setLeft(btnLeft, Unit.PX);
          btnElem.getStyle().setTop(btnTop, Unit.PX);

        }
      });
    }
    */
    
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      this.ckd = (CKD)model;
      Double bsa = ckd.getBSA();
      ckd.setUseBsa(bsa != null);
      
      riskVisible = getPresenter().applyCKD(ckd, ckd.getCockcroftGFR(), cockcroftGfrBox, cockcroftGfrStadium, cockcroftRiskBox, cockcroftRiskPanel, null) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getMdrdGFR(), mdrdGfrBox, mdrdGfrStadium, mdrdRiskBox, mdrdRiskPanel, 60d) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getMdrdNcGFR(), mdrdNcGfrBox, mdrdNcGfrStadium, mdrdNcRiskBox, mdrdNcRiskPanel, 60d) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getCkdEpiGFR(), epiGfrBox, epiGfrStadium, epiRiskBox, epiRiskPanel, null) || riskVisible;

      /*
      applyCKD(ckd, ckd.getCockcroftGFR(), cockcroftGfrBox, cockcroftGfrStadium, cockcroftRiskBox, cockcroftRiskPanel, null);
      applyCKD(ckd, ckd.getMdrdGFR(), mdrdGfrBox, mdrdGfrStadium, mdrdRiskBox, mdrdRiskPanel, 60d);
      applyCKD(ckd, ckd.getMdrdNcGFR(), mdrdNcGfrBox, mdrdNcGfrStadium, mdrdNcRiskBox, mdrdNcRiskPanel, 60d);
      applyCKD(ckd, ckd.getCkdEpiGFR(), epiGfrBox, epiGfrStadium, epiRiskBox, epiRiskPanel, null);
      */
      
      if (bsa != null) {
        bsaPanel.setVisible(true);
        bsaHtml.setHTML("BSA = " + GwtUtils.formatDecimal(bsa, 2) + " m&#178;");
      } else {
        bsaPanel.setVisible(false);
      }
      workflowHelpPanel.setVisible(riskVisible);
    }
  }

  /*
  private void applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead) {
    if (overhead != null && gfr > overhead) {
      gfrBox.setText(">"+GwtUtils.formatDecimal(overhead, 0));
      gfrStadiumBox.setText("");
    } else {
      gfrBox.setText(GwtUtils.formatDecimal(gfr, 2));
      gfrStadiumBox.setText(CKD.getGFRStadium(gfr));
    }
    int risk = ckd.getRiskStadium(gfr);
    String irc = "";
    String ircBckCol = "white";
    String ircCol = "black";
    if (risk == -1) {
      riskPanel.setVisible(false);
    } else {
      riskPanel.setVisible(true);
      riskVisible = true;
    }
    if (risk == CKD.LOW_RISK) {
      irc = AppConstants.IMPL.CKDOutputView_lowRisk_text();
      ircBckCol = "#00FF00";
    } else if (risk == CKD.MIDDLE_RISK) {
      irc = AppConstants.IMPL.CKDOutputView_middleRisk_text();
      ircBckCol = "#FFFF00";
    } else if (risk == CKD.HIGH_RISK) {
      irc = AppConstants.IMPL.CKDOutputView_highRisk_text();
      ircBckCol = "#FFCC00";
    } else if (risk == CKD.VERY_HIGH_RISK) {
      irc = AppConstants.IMPL.CKDOutputView_veryHighRisk_text();
      ircBckCol = "#FF0000";
    }
    riskBox.setText(irc);
    riskBox.getElement().getStyle().setColor(ircCol);
    riskBox.getElement().getStyle().setBackgroundColor(ircBckCol);
  }
  */

  /*
  @UiHandler ("ckdHelpBtn")
  public void onHelpBtn(TouchStartEvent event) {
    getPresenter().openHelpPage();
  }
  */
  
  @UiHandler ({"manageRiskCockBtn", "manageRiskMdrd1Btn", "manageRiskMdrd2Btn", "manageRiskEpiBtn"})
  public void onReferralDecisionMakingBtn (TouchStartEvent event) {
    Widget w = (Widget)event.getSource();
    HasTag btn = null;
    if (event.getSource() instanceof HasTag) {
      btn = (HasTag)event.getSource();
    } else if (w.getParent() instanceof HasTag) {
      btn = (HasTag)w.getParent();
    }
    GwtUtils.log("pressed " + btn.getTag());
//  getPresenter().goToReferralDecision();
    getPresenter().goToExtendedView(btn.getTag());
  }
  
}
