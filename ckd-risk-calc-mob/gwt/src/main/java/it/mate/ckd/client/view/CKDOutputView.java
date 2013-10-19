package it.mate.ckd.client.view;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.view.CKDOutputView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.HasTag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
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
  @UiField Panel ibwPanel;
  @UiField HTML ibwHtml;
  @UiField Anchor useIbwBtn;
  
  @UiField Panel workflowHelpPanel;
  
  private CKD ckd;
  
  private boolean riskVisible = false;
  
  public CKDOutputView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
    AppClientFactory.IMPL.adaptWrapperPanel(wrapperPanel, "outputWrapperPanel", false, 0, null);
  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  private void initProvidedElements() { }

  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      this.ckd = (CKD)model;
      Double bsa = ckd.getBSA();
      Double ibw = ckd.getIBW();
      ckd.setUseBsa(bsa != null);
      
      riskVisible = getPresenter().applyCKD(ckd, ckd.getCockcroftGFR(), cockcroftGfrBox, cockcroftGfrStadium, cockcroftRiskBox, cockcroftRiskPanel, null) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getMdrdGFR(), mdrdGfrBox, mdrdGfrStadium, mdrdRiskBox, mdrdRiskPanel, 60d) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getMdrdNcGFR(), mdrdNcGfrBox, mdrdNcGfrStadium, mdrdNcRiskBox, mdrdNcRiskPanel, 60d) || riskVisible;
      riskVisible = getPresenter().applyCKD(ckd, ckd.getCkdEpiGFR(), epiGfrBox, epiGfrStadium, epiRiskBox, epiRiskPanel, null) || riskVisible;
      
      if (bsa != null) {
        bsaPanel.setVisible(true);
        bsaHtml.setHTML("BSA = " + GwtUtils.formatDecimal(bsa, 2) + " m&#178;");
      } else {
        bsaPanel.setVisible(false);
      }
      if (ibw != null) {
        ibwPanel.setVisible(true);
        ibwHtml.setHTML("IBW = " + GwtUtils.formatDecimal(ibw, 1) + " Kg");
      } else {
        ibwPanel.setVisible(false);
      }
      
      workflowHelpPanel.setVisible(riskVisible);
    }
  }

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
    getPresenter().goToExtendedView(btn.getTag());
  }
  
  @UiHandler ("useIbwBtn")
  public void onUseIbwBtn (ClickEvent event) {
    if (ckd.isUseIbw()) {
      ckd.setUseIbw(false);
      useIbwBtn.setText("Use IBW");
    } else {
      ckd.setUseIbw(true);
      useIbwBtn.setText("Do Not Use IBW");
    }
    setModel(ckd, null);
  }
  
}
