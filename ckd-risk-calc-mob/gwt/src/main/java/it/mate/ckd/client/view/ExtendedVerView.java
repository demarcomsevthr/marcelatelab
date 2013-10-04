package it.mate.ckd.client.view;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.ExtendedVerView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
  
  @UiField Panel suggestedIndicationPanel;
  @UiField Label suggestedIndicationTitleLbl;
  @UiField Label suggestedIndicationLbl;
  
  public ExtendedVerView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    AppClientFactory.IMPL.initTitle(this);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    AppClientFactory.IMPL.adaptWrapperPanelOnTablet(wrapperPanel, "protocolWrapperPanel", true, null);
    initHeaderBackButton("Back", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToCkdOutput(null);
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof CKD) {
      CKD ckd = (CKD)model;
      if (CKD.SELECTED_GFR_COCKROFT.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getCockcroftGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
      }
      if (CKD.SELECTED_GFR_MDRD1.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getMdrdGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
      }
      if (CKD.SELECTED_GFR_MDRD2.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getMdrdNcGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
      }
      if (CKD.SELECTED_GFR_EPI.equals(ckd.getSelectedGFR())) {
        getPresenter().applyCKD(ckd, ckd.getCkdEpiGFR(), gfrBox, gfrStadium, riskBox, riskPanel, null);
      }
      albBox.setText(""+ckd.getAlbumin());
    }
  }

  @UiHandler ("referralBtn")
  public void onReferralBtn (TouchStartEvent event) {
    showSuggestedIndications("Suggested referral decision", "Refer");
  }
  
  @UiHandler ("monitoringBtn")
  public void onMonitoringBtn (TouchStartEvent event) {
    showSuggestedIndications("Suggested monitoring frequency", "1 time per year");
  }
  
  private void showSuggestedIndications(final String title, final String indication) {
    
    final Element panelElement = suggestedIndicationPanel.getElement();
    
    Delegate<Void> showDelegate = new Delegate<Void>() {
      public void execute(Void element) {
        
        suggestedIndicationTitleLbl.setText(title);
        suggestedIndicationLbl.setText(indication);
        
        JQueryUtils.StyleProperties startProperties = JQueryUtils.createStyleProperties();
        startProperties.setPosition(Style.Position.ABSOLUTE);
        startProperties.setTop(440, Style.Unit.PX);
        startProperties.setLeft(30, Style.Unit.PX);
        
        JQueryUtils.StyleProperties endProperties = JQueryUtils.createStyleProperties();
        endProperties.setTop(320, Style.Unit.PX);
        endProperties.setHeight(120, Style.Unit.PX);
        
        JQueryUtils.animate(
            JQueryUtils.fadeIn(
                JQueryUtils.css(JQueryUtils.castElement(panelElement), startProperties), 0), 
            endProperties, 3000);
        
      }
    };
    
    if (suggestedIndicationPanel.isVisible()) {
      int hideDuration = 2000;
      JQueryUtils.fadeOut(JQueryUtils.castElement(panelElement), hideDuration);
      GwtUtils.deferredExecution(hideDuration, showDelegate);
    } else {
      showDelegate.execute(null);
    }
    
  }

}
