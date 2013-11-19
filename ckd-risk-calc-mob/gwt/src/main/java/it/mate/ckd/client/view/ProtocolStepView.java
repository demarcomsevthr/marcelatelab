package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.ui.ProtocolHeaderPanel;
import it.mate.ckd.client.view.ProtocolStepView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.ui.SmartButton;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.WhiteSpace;
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
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;

public class ProtocolStepView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToProtocolStep(ProtocolStep protocolStep);
    void goToCkdOutput(CKD ckd);
    void goToExtendedView(String selectedGFR);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ProtocolStepView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML questionHtml;
  @UiField SmartButton answer1Btn;
  @UiField SmartButton answer2Btn;
  @UiField SmartButton answer3Btn;
  @UiField HTML endHtml;
  @UiField SmartButton finishBtn;
  @UiField Label stepNumberLbl;
  @UiField Panel protocolStepPanel;
  @UiField Panel protocolStepPanelCenter;

  /*
  @UiField Widget protocolHeaderButton;
  @UiField Panel protocolHeaderPanel;
  @UiField HTML protocolHeaderPanelCenterHtml;
  */
  @UiField ProtocolHeaderPanel protocolHeaderPanel;
  
  
  private ProtocolStep currentProtocolStep;
  
  private LinkedList<ProtocolStep> history = new LinkedList<ProtocolStep>();
  
  private final static int PROTOCOL_STEP_PANEL_LEFT_PCT = 9;
  
  private final static int ANIMATION_DURATION = 200;
  
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
    initHeaderBackButton("GFR", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToExtendedView(null);
      }
    });
    
    /*
    answer1Btn.setChangeColorOnClick(true);
    answer2Btn.setChangeColorOnClick(true);
    answer3Btn.setChangeColorOnClick(true);
    */
    
    if (OsDetectionUtils.isTablet()) {
      protocolHeaderPanel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
    }
    
    addBackBtnHandler();
    
  }
  
  @Override
  public void onAttach(AttachEvent event) {
    wrapperPanel.getElement().getStyle().setOverflow(Style.Overflow.HIDDEN);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof ProtocolStep) {
      currentProtocolStep = (ProtocolStep)model;
      if (currentProtocolStep == ProtocolStep.END) {
        protocolStepPanelCenter.getElement().getStyle().clearHeight();
        finishBtn.setText(AppConstants.IMPL.ProtocolStep_FinishText());
        stepNumberLbl.setText(AppConstants.IMPL.ProtocolStep_FinishText());

        /*
        protocolHeaderButton.setVisible(true);
        */
        protocolHeaderPanel.getHeaderButton().setVisible(true);
        
        switchPanelsVisibility(true);
        endHtml.setHTML(currentProtocolStep.getBodyText());
      } else {
        stepNumberLbl.setText("Step " + currentProtocolStep.getId());
        questionHtml.setHTML(currentProtocolStep.getBodyText());
        answer1Btn.setText(currentProtocolStep.getAnswer1Text());
        answer2Btn.setText(currentProtocolStep.getAnswer2Text());
        if (!"".equals(currentProtocolStep.getAnswer3Text())) {
          protocolStepPanelCenter.getElement().getStyle().setHeight(30, Style.Unit.PCT);
          answer3Btn.setText(currentProtocolStep.getAnswer3Text());
          answer3Btn.setVisible(true);
        } else {
          protocolStepPanelCenter.getElement().getStyle().clearHeight();
          answer3Btn.setVisible(false);
        }
        arrangeBtnFontSize(answer1Btn);
        arrangeBtnFontSize(answer2Btn);
        arrangeBtnFontSize(answer3Btn);
        
        answer1Btn.setOriginalColor();
        answer2Btn.setOriginalColor();
        answer3Btn.setOriginalColor();
        
      }
      /*
      if (history.size() > 0) {
        protocolHeaderButton.setVisible(true);
        protocolHeaderPanelCenterHtml.getElement().getStyle().clearMarginLeft();
        protocolHeaderPanelCenterHtml.getElement().getStyle().clearMarginTop();
        protocolHeaderPanelCenterHtml.getElement().getStyle().clearWidth();
      } else {
        protocolHeaderButton.setVisible(false);
        protocolHeaderPanelCenterHtml.getElement().getStyle().setMarginLeft(-40, Style.Unit.PX);
        protocolHeaderPanelCenterHtml.getElement().getStyle().setMarginTop(30, Style.Unit.PX);
        if (OsDetectionUtils.isTabletPortrait()) {
          protocolHeaderPanelCenterHtml.getElement().getStyle().setWidth(490, Style.Unit.PX);
        } else {
          protocolHeaderPanelCenterHtml.getElement().getStyle().setWidth(280, Style.Unit.PX);
        }
      }
      */
      if (history.size() > 0) {
        protocolHeaderPanel.getHeaderButton().setVisible(true);
        protocolHeaderPanel.getHeaderHTML().getElement().getStyle().clearMarginLeft();
        protocolHeaderPanel.getHeaderHTML().getElement().getStyle().clearMarginTop();
        protocolHeaderPanel.getHeaderHTML().getElement().getStyle().clearWidth();
      } else {
        protocolHeaderPanel.getHeaderButton().setVisible(false);
        protocolHeaderPanel.getHeaderHTML().getElement().getStyle().setMarginLeft(-40, Style.Unit.PX);
        protocolHeaderPanel.getHeaderHTML().getElement().getStyle().setMarginTop(30, Style.Unit.PX);
        if (OsDetectionUtils.isTabletPortrait()) {
          protocolHeaderPanel.getHeaderHTML().getElement().getStyle().setWidth(490, Style.Unit.PX);
        } else {
          protocolHeaderPanel.getHeaderHTML().getElement().getStyle().setWidth(280, Style.Unit.PX);
        }
      }
      
    }
  }
  
  private void arrangeBtnFontSize(SmartButton btn) {
    if (btn.getText().length() > 15) {
      if (OsDetectionUtils.isTablet()) {
        btn.getElement().getStyle().setFontSize(14, Style.Unit.PX);
      } else {
        btn.getElement().getStyle().setFontSize(12, Style.Unit.PX);
      }
      btn.getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);
    } else {
      btn.getElement().getStyle().clearFontSize();
      btn.getElement().getStyle().clearWhiteSpace();
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
  
  @UiHandler ("answer3Btn")
  public void onAnswer3Btn(TouchStartEvent event) {
    goToNextStep(currentProtocolStep.getAnswer3EndText(), currentProtocolStep.getAnswer3Step());
  }

  /*
  @UiHandler ("backBtn")
  public void onBackBtn(TouchStartEvent event) {
    doAnimation1(+1, new Delegate<Void>() {
      public void execute(Void element) {
        ProtocolStep step = history.getLast();
        history.removeLast();
        switchPanelsVisibility(false);
        setModel(step, "step");
        doAnimation2();
      }
    });
  }
  */
  private void addBackBtnHandler() {
    protocolHeaderPanel.addBackBtnTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
        doAnimation1(+1, new Delegate<Void>() {
          public void execute(Void element) {
            ProtocolStep step = history.getLast();
            history.removeLast();
            switchPanelsVisibility(false);
            setModel(step, "step");
            doAnimation2();
          }
        });
      }
    });
  }
  
  
  private void switchPanelsVisibility(boolean isEndStep) {
    questionHtml.setVisible(!isEndStep);
    answer1Btn.setVisible(!isEndStep);
    answer2Btn.setVisible(!isEndStep);
    answer3Btn.setVisible(!isEndStep);
    endHtml.setVisible(isEndStep);
    finishBtn.setVisible(isEndStep);
  }

  @UiHandler ("finishBtn")
  public void onGfrBtn(TouchStartEvent event) {
    getPresenter().goToExtendedView(null);
  }
  
  public static String getProtocolStepPanelLeft() {
    return PROTOCOL_STEP_PANEL_LEFT_PCT + "%";
  }
  
  private void goToNextStep(final String endText, int nextStepId) {
    ProtocolStep nextStep = null;
    if (endText != null && !"".equals(endText.trim())) {
      nextStep = ProtocolStep.END;
      nextStep.setBodyText(endText);
    } else {
      nextStep = ProtocolStep.getProtocolStepById(nextStepId);
    }
    
    final ProtocolStep fNextStep = nextStep;
    doAnimation1(-1, new Delegate<Void>() {
      public void execute(Void element) {
        history.add(currentProtocolStep);
        setModel(fNextStep, "step");
        doAnimation2();
      }
    });
    
  }

  private void doAnimation1(final int versus, final Delegate<Void> delegate) {
    JQuery.StyleProperties step1Properties = JQuery.createStyleProperties();
    step1Properties.setLeft(versus * 100, Style.Unit.PCT);
    JQuery.withElement(protocolStepPanel.getElement())
        .animate(step1Properties, ANIMATION_DURATION, new Delegate<Void>() {
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
        .animate(step3Properties, ANIMATION_DURATION);
  }

  /*
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
  
  public static String getBackStepImage1() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-1.png" + "')";
  }
  
  public static String getBackStepImage2() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-2.png" + "')";
  }
  
  public static String getBackStepImage3() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-3.png" + "')";
  }
  */
  
}
