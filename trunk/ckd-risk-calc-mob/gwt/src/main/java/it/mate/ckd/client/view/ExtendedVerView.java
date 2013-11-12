package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.ExtendedVerView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;

public class ExtendedVerView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToProtocolStep(ProtocolStep protocolStep);
    void goToCkdOutput(CKD ckd);
    boolean applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ExtendedVerView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField Label gfrBox;
  @UiField Label gfrStadium;
  @UiField Label riskBox;
  @UiField Panel riskPanel;
  
  @UiField Label albBox;
  
  @UiField Panel suggestionPanel;
  @UiField Label suggestedIndicationTitleLbl;
  @UiField Label suggestedIndicationLbl;
  
  @UiField Label referralBtn$suggestionPanelTitle;
  @UiField Label monitoringBtn$suggestionPanelTitle;
  
  boolean suggestionPanelVisible = false;
  
  private CKD ckd;
  
  private int referralDecisionCode;
  
  private int monitoringFrequencyCode;
  
  private static final int ANIMATION_DURATION = 800;
  
  public ExtendedVerView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    AppClientFactory.IMPL.initTitle(this);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    AppClientFactory.IMPL.adaptWrapperPanel(wrapperPanel, "extendedWrapperPanel", true, 40, null);
    initHeaderBackButton("Back", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToCkdOutput(null);
      }
    });
    
  }
  
  @Override
  public void onAttach(AttachEvent event) {

  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      this.ckd = (CKD)model;
      if (CKD.SELECTED_GFR_COCKROFT.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getCockcroftGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
        referralDecisionCode = ckd.getReferralDecision(ckd.getCockcroftGFR());
        monitoringFrequencyCode = ckd.getMonitoringFrequency(ckd.getCockcroftGFR());
      }
      if (CKD.SELECTED_GFR_MDRD1.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getMdrdGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
        referralDecisionCode = ckd.getReferralDecision(ckd.getMdrdGFR());
        monitoringFrequencyCode = ckd.getMonitoringFrequency(ckd.getMdrdGFR());
      }
      if (CKD.SELECTED_GFR_MDRD2.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getMdrdNcGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
        referralDecisionCode = ckd.getReferralDecision(ckd.getMdrdNcGFR());
        monitoringFrequencyCode = ckd.getMonitoringFrequency(ckd.getMdrdNcGFR());
      }
      if (CKD.SELECTED_GFR_EPI.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getCkdEpiGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
        referralDecisionCode = ckd.getReferralDecision(ckd.getCkdEpiGFR());
        monitoringFrequencyCode = ckd.getMonitoringFrequency(ckd.getCkdEpiGFR());
      }
      albBox.setText(""+ckd.getAlbumin());
    }
  }

  @UiHandler ("referralBtn")
  public void onReferralBtn (TouchStartEvent event) {
    String text = "";
    String bckColor = null;
    if (referralDecisionCode == CKD.REFERRAL_DECISION_NONE) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_None_text());
      bckColor = "green";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_MONITOR_YELLOW) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_Monitor_text());
      bckColor = "yellow";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_MONITOR_ORANGE) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_Monitor_text());
      bckColor = "orange";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_MONITOR_RED) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_Monitor_text());
      bckColor = "red";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_REFER_WITH_NEPHROLOGY_SERVICE_ORANGE) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_ReferWithNephrologyService_text());
      bckColor = "orange";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_REFER_WITH_NEPHROLOGY_SERVICE_RED) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_ReferWithNephrologyService_text());
      bckColor = "red";
    } else if (referralDecisionCode == CKD.REFERRAL_DECISION_REFER_RED) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_referralDecision_Refer_text());
      bckColor = "red";
    }
    showSuggestedIndications(referralBtn$suggestionPanelTitle.getText(), text, bckColor, null);
  }
  
  @UiHandler ("protocolBtn")
  public void onProtocolBtn (TouchStartEvent event) {
    getPresenter().goToProtocolStep(null);
  }
  
  @UiHandler ("monitoringBtn")
  public void onMonitoringBtn (TouchStartEvent event) {
    String text = "";
    String bckColor = null;
    String txtColor = null;
    if (monitoringFrequencyCode == CKD.MONITORING_FREQUENCY_1_GREEN) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_monitoringFrequency_1IfCKD_text());
      bckColor = "green";
    } else if (monitoringFrequencyCode == CKD.MONITORING_FREQUENCY_1_YELLOW) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_monitoringFrequency_1_text());
      bckColor = "yellow";
    } else if (monitoringFrequencyCode == CKD.MONITORING_FREQUENCY_2_ORANGE) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_monitoringFrequency_2_text());
      bckColor = "orange";
    } else if (monitoringFrequencyCode == CKD.MONITORING_FREQUENCY_3_RED) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_monitoringFrequency_3_text());
      bckColor = "red";
      txtColor = "yellow";
    } else if (monitoringFrequencyCode == CKD.MONITORING_FREQUENCY_4_DEEP_RED) {
      text = GwtUtils.unescapeHtml(AppConstants.IMPL.ExtendedView_monitoringFrequency_4_text());
      bckColor = "#8A0808";
      txtColor = "yellow";
    }
    showSuggestedIndications(monitoringBtn$suggestionPanelTitle.getText(), text, bckColor, txtColor);
  }
  
  private static int getSuggestionPanelWidthPct() {
    if (OsDetectionUtils.isTablet()) {
      return 80;
    } else {
      return 90;
    }
  }
  
  public static String getSuggestionPanelWidth() {
    return getSuggestionPanelWidthPct() + "%";
  }
  
  private void showSuggestedIndications(final String title, final String text, final String bckColor, final String txtColor) {
    final Element suggestionPanelElement = suggestionPanel.getElement();
    
    Delegate<Void> showDelegate = new Delegate<Void>() {
      public void execute(Void element) {
        suggestedIndicationTitleLbl.setText(title);
        suggestedIndicationLbl.setText(text);
        suggestedIndicationLbl.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);
        suggestedIndicationLbl.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
        suggestedIndicationLbl.getElement().getStyle().setBorderColor("black");
        if (bckColor != null) {
          suggestedIndicationLbl.getElement().getStyle().setBackgroundColor(bckColor);
        } else {
          suggestedIndicationLbl.getElement().getStyle().clearBackgroundColor();
        }
        if (txtColor != null) {
          suggestedIndicationLbl.getElement().getStyle().setColor(txtColor);
        } else {
          suggestedIndicationLbl.getElement().getStyle().clearColor();
        }
        int startTop = 0;
        if (OsDetectionUtils.isTablet()) {
          String mts = wrapperPanel.getElement().getStyle().getMarginTop();
          mts = mts.substring(0, mts.indexOf("px"));
          int wrapperPanelMargin = Integer.parseInt(mts);
          startTop = Window.getClientHeight() - wrapperPanelMargin * 2;
        } else {
          startTop = Window.getClientHeight() - getHeaderPanel().getOffsetHeight();
        }
        int startLeft = ( wrapperPanel.getOffsetWidth() - (wrapperPanel.getOffsetWidth() * getSuggestionPanelWidthPct() / 100) ) / 2 - 8; 
        JQuery.StyleProperties startProperties = JQuery.createStyleProperties();
        startProperties.setPosition(Style.Position.ABSOLUTE);
        startProperties.setTop( startTop, Style.Unit.PX);
        startProperties.setLeft(startLeft, Style.Unit.PX);
        startProperties.setDisplay(Style.Display.BLOCK);
        int endHeight = 120;
        if (OsDetectionUtils.isTabletLandscape()) {
          endHeight = 180;
        } else if (OsDetectionUtils.isTabletPortrait()) {
          endHeight = 240;
        } else {
          endHeight = 120;
        }
        JQuery.StyleProperties endProperties = JQuery.createStyleProperties();
        endProperties.setTop( (startTop - endHeight) , Style.Unit.PX);
        endProperties.setHeight(endHeight, Style.Unit.PX);
        JQuery.withElement(suggestionPanelElement).css(startProperties)
            .fadeIn(JQuery.createOptions().setDuration(ANIMATION_DURATION).setQueue(false))
            .animate(endProperties, ANIMATION_DURATION);
        suggestionPanelVisible = true;
      }
    };
    
    if (suggestionPanelVisible) {
      JQuery.withElement(suggestionPanelElement).fadeOut(ANIMATION_DURATION, showDelegate);
    } else {
      showDelegate.execute(null);
    }
    
  }

}
