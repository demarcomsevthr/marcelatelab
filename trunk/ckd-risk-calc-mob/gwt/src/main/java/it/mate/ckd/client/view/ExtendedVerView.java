package it.mate.ckd.client.view;

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
  
  boolean suggestionPanelVisible = false;
  
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
  public void onAttach(AttachEvent event) {
    // 09/10/2013
    // se non faccio cosi' su iphone non si vede il suggestion panel
    // (rimane a 0 la height del parent)
    AppClientFactory.IMPL.applyWrapperPanelIPhonePatch(this, wrapperPanel);
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
  
  @UiHandler ("protocolBtn")
  public void onProtocolBtn (TouchStartEvent event) {
    getPresenter().goToProtocolStep(null);
  }
  
  @UiHandler ("monitoringBtn")
  public void onMonitoringBtn (TouchStartEvent event) {
    showSuggestedIndications("Suggested monitoring frequency", "1 time per year");
  }
  
  private void showSuggestedIndications(final String title, final String indication) {

    GwtUtils.log("start method");
    
    final Element suggestionPanelElement = suggestionPanel.getElement();
    
    Delegate<Void> showDelegate = new Delegate<Void>() {
      public void execute(Void element) {
        
        GwtUtils.log("start delegate");
        
        suggestedIndicationTitleLbl.setText(title);
        suggestedIndicationLbl.setText(indication);
        
        int startTop = 0;
        
        if (OsDetectionUtils.isTablet()) {
          String mts = wrapperPanel.getElement().getStyle().getMarginTop();
          mts = mts.substring(0, mts.indexOf("px"));
          int wrapperPanelMargin = Integer.parseInt(mts);
          startTop = Window.getClientHeight() - wrapperPanelMargin * 2;
        } else {
          startTop = Window.getClientHeight() - getHeaderPanel().getOffsetHeight();
        }
        
        GwtUtils.log("wrapperPanelWidth = " + wrapperPanel.getOffsetWidth());
        int startLeft = ( wrapperPanel.getOffsetWidth() - (wrapperPanel.getOffsetWidth() * 80 / 100) ) / 2; 
        
        GwtUtils.log("startTop = " + startTop);
        GwtUtils.log("startLeft = " + startLeft);

        JQuery.StyleProperties startProperties = JQuery.createStyleProperties();
        startProperties.setPosition(Style.Position.ABSOLUTE);
        startProperties.setTop( startTop, Style.Unit.PX);
        startProperties.setLeft(startLeft, Style.Unit.PX);
        startProperties.setDisplay(Style.Display.BLOCK);
        
        int endHeight = 120;
        
        if (OsDetectionUtils.isTabletLandscape()) {
          endHeight = 240;
        } else if (OsDetectionUtils.isTabletPortrait()) {
          endHeight = 240;
        } else {
          endHeight = 120;
        }
        
        JQuery.StyleProperties endProperties = JQuery.createStyleProperties();
        endProperties.setTop( (startTop - endHeight) , Style.Unit.PX);
        endProperties.setHeight(endHeight, Style.Unit.PX);

        JQuery.withElement(suggestionPanelElement).css(startProperties)
            .fadeIn(JQuery.createOptions().setDuration("2000").setQueue(false))
//          .animate(endProperties, JQuery.createOptions().setDuration("3000").setQueue(true));
            .animate(endProperties, 2000);
        
        suggestionPanelVisible = true;

      }
    };
    
    if (suggestionPanelVisible) {
//  if (suggestionPanel.isVisible()) {
      GwtUtils.log("suggestionPanel is Visible !");
      int hideDuration = 2000;
      JQuery.withElement(suggestionPanelElement).fadeOut(hideDuration);
      GwtUtils.deferredExecution(hideDuration, showDelegate);
    } else {
      showDelegate.execute(null);
    }
    
  }

}
