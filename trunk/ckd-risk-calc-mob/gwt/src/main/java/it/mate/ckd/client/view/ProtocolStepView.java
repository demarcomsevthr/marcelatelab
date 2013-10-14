package it.mate.ckd.client.view;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.ProtocolStepView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.ui.SmartButton;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class ProtocolStepView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToProtocolStep(ProtocolStep protocolStep);
    void goToCkdOutput(CKD ckd);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ProtocolStepView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML questionHtml;
  @UiField SmartButton answer1Btn;
  @UiField SmartButton answer2Btn;
  @UiField HTML endHtml;
  @UiField Widget protocolHeaderButton;
  @UiField Widget gfrBtn;
  @UiField Label stepNumberLbl;
  
  @UiField Panel protocolHeaderPanel;
  
  @UiField Panel protocolStepPanel;
  
  private ProtocolStep currentProtocolStep;
  
  private LinkedList<ProtocolStep> history = new LinkedList<ProtocolStep>();
  
  private final static int PROTOCOL_STEP_PANEL_LEFT_PCT = 9;
  
  public ProtocolStepView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    AppClientFactory.IMPL.initTitle(this);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));

    AppClientFactory.IMPL.adaptWrapperPanel(wrapperPanel, "protocolWrapperPanel", true, 40, null);
    
    /*
    if (OsDetectionUtils.isTabletLandscape()) {
      wrapperPanel.getElement().getStyle().setMarginTop(90, Style.Unit.PX);
      wrapperPanel.getElement().getStyle().clearMarginBottom();
    }
    */
    
    initHeaderBackButton("GFR", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToCkdOutput(null);
      }
    });
    
    answer1Btn.setChangeColorOnClick(true);
    answer2Btn.setChangeColorOnClick(true);
    
    if (OsDetectionUtils.isTablet()) {
      /*
      JQuery.StyleProperties protocolHeaderPanelStyles = JQuery.createStyleProperties();
      protocolHeaderPanelStyles.setPosition(Style.Position.ABSOLUTE);
      JQuery.withElement(protocolHeaderPanel.getElement()).css(protocolHeaderPanelStyles);
      */
      protocolHeaderPanel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
    }
    
  }
  
  @Override
  public void onAttach(AttachEvent event) {
    wrapperPanel.getElement().getStyle().setOverflow(Style.Overflow.HIDDEN);
    
    // PATCH 12/10/2013
    /*
    if (OsDetectionUtils.isTabletLandscape()) {
      GwtUtils.deferredExecution(200, new Delegate<Void>() {
        public void execute(Void element) {
          wrapperPanel.getElement().getStyle().setMarginTop(90, Unit.PX);
          wrapperPanel.getElement().getStyle().clearMarginBottom();
        }
      });
    }
    */
    
    if (OsDetectionUtils.isTabletLandscape()) {
      GwtUtils.deferredExecution(200, new Delegate<Void>() {
        public void execute(Void element) {
          protocolHeaderButton.getElement().getStyle().setHeight(43, Style.Unit.PX);
          protocolHeaderButton.getElement().getStyle().setMarginTop(0, Style.Unit.PX);
          protocolHeaderButton.getElement().getStyle().setPaddingTop(0, Style.Unit.PX);
          protocolHeaderButton.getElement().getStyle().setBorderWidth(0, Style.Unit.PX);
        }
      });
    }
    
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof ProtocolStep) {
      currentProtocolStep = (ProtocolStep)model;
      stepNumberLbl.setText("Step " + currentProtocolStep.getId());
      questionHtml.setHTML(currentProtocolStep.getQuestionText());
      answer1Btn.setText(currentProtocolStep.getAnswer1Text());
      answer2Btn.setText(currentProtocolStep.getAnswer2Text());
      answer1Btn.setOriginalColor();
      answer2Btn.setOriginalColor();
      protocolHeaderButton.setVisible(history.size() > 0);
    }
    
    if (OsDetectionUtils.isTabletLandscape()) {
      GwtUtils.deferredExecution(200, new Delegate<Void>() {
        public void execute(Void element) {
          
          PhonegapUtils.log("CKD - applying ckd-ProtocolHeaderButton * margin top patch");
          JQuery.selectFirstElement(".ckd-ProtocolHeaderButton-border-container").getStyle().setMarginTop(0, Style.Unit.PX);
          JQuery.selectFirstElement(".ckd-ProtocolHeaderButton-text").getStyle().setMarginTop(0, Style.Unit.PX);
          
          PhonegapUtils.log("CKD - .ckd-ProtocolHeaderButton absolute top = " + protocolHeaderButton.getAbsoluteTop());
          PhonegapUtils.log("CKD - .ckd-ProtocolHeaderButton height = " + protocolHeaderButton.getElement().getStyle().getHeight());
          PhonegapUtils.log("CKD - .ckd-ProtocolHeaderButton margin top = " + protocolHeaderButton.getElement().getStyle().getMarginTop());
          PhonegapUtils.log("CKD - .ckd-ProtocolHeaderButton padding top = " + protocolHeaderButton.getElement().getStyle().getPaddingTop());
          PhonegapUtils.log("CKD - .ckd-ProtocolHeaderButton border width = " + protocolHeaderButton.getElement().getStyle().getBorderWidth());
        }
      });
    }
    
  }

  @UiHandler ("answer1Btn")
  public void onAnswer1Btn(TouchStartEvent event) {
    goToNextStep(currentProtocolStep.getAnswer1EndText(), currentProtocolStep.getAnswer1Step());
  }
  
  @UiHandler ("answer2Btn")
  public void onAnswer2Btn(TouchStartEvent event) {
    goToNextStep(currentProtocolStep.getAnswer2EndText(), currentProtocolStep.getAnswer2Step());
  }
  
  @UiHandler ("backBtn")
  public void onBackBtn(TouchStartEvent event) {
    doAnimation1(+1, new Delegate<Void>() {
      public void execute(Void element) {
        ProtocolStep step = history.getLast();
        history.removeLast();
        setModel(step, "step");
        doAnimation2();
      }
    });
  }
  
  @UiHandler ("gfrBtn")
  public void onGfrBtn(TouchStartEvent event) {
    getPresenter().goToCkdOutput(null);
  }
  
  public static String getProtocolStepPanelLeft() {
    return PROTOCOL_STEP_PANEL_LEFT_PCT + "%";
  }
  
  private void goToNextStep(final String endText, int nextStepId) {
    if (endText != null && !"".equals(endText.trim())) {
      
      doAnimation1(-1, new Delegate<Void>() {
        public void execute(Void element) {
          stepNumberLbl.setText("Finish");
          protocolHeaderButton.setVisible(false);
          questionHtml.setVisible(false);
          answer1Btn.setVisible(false);
          answer2Btn.setVisible(false);
          endHtml.setHTML(endText);
          endHtml.setVisible(true);
          gfrBtn.setVisible(true);
          doAnimation2();
        }
      });
      
    } else {
      
      final ProtocolStep nextStep = ProtocolStep.getProtocolStepById(nextStepId);
      if (nextStep != null) {
        
        doAnimation1(-1, new Delegate<Void>() {
          public void execute(Void element) {
            history.add(currentProtocolStep);
            setModel(nextStep, "step");
            doAnimation2();
          }
        });
        
      }
    }
  }

  private void doAnimation1(final int versus, final Delegate<Void> delegate) {
    JQuery.StyleProperties step1Properties = JQuery.createStyleProperties();
    step1Properties.setLeft(versus * 100, Style.Unit.PCT);
    JQuery.withElement(protocolStepPanel.getElement())
        .animate(step1Properties, 2000, new Delegate<Void>() {
          public void execute(Void element) {
            JQuery.StyleProperties step2Properties = JQuery.createStyleProperties();
            step2Properties.setLeft((-1) * versus * 100, Style.Unit.PCT);
            JQuery.withElement(protocolStepPanel.getElement())
                .css(step2Properties);
            delegate.execute(null);
          }
        });
  }
  
  private void doAnimation2() {
    JQuery.StyleProperties step3Properties = JQuery.createStyleProperties();
    step3Properties.setLeft(PROTOCOL_STEP_PANEL_LEFT_PCT, Style.Unit.PCT);
    JQuery.withElement(protocolStepPanel.getElement())
        .animate(step3Properties, 2000);
  }
  
  public static class BackStepButton extends TouchWidget {
    Element p;
    public BackStepButton() {
      p = Document.get().createPElement();
      setElement(p);
    }
    public void setText(String text) {
      p.setInnerText(text);
    }
  }
  
}
