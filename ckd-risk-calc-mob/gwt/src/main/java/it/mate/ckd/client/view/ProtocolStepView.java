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
import it.mate.phgcommons.client.utils.WebkitCssUtil;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.LinkedList;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
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

  @UiField ProtocolHeaderPanel protocolHeaderPanel;
  
  private ProtocolStep currentProtocolStep;
  
  private LinkedList<ProtocolStep> history = new LinkedList<ProtocolStep>();
  
  private final static int PROTOCOL_STEP_PANEL_INITIAL_LEFT_PCT = 9;
  
  private int animationDuration;
  
  public ProtocolStepView() {
    if (OsDetectionUtils.isAndroid()) {
      animationDuration = 1000;
    } else {
      animationDuration = 200;
    }
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

  private void addBackBtnHandler() {
    protocolHeaderPanel.addBackBtnTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
        doTransition1(+1, new Delegate<Void>() {
          public void execute(Void element) {
            ProtocolStep step = history.getLast();
            history.removeLast();
            switchPanelsVisibility(false);
            setModel(step, "step");
            doTransition2();
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
    return PROTOCOL_STEP_PANEL_INITIAL_LEFT_PCT + "%";
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
    doTransition1(-1, new Delegate<Void>() {
      public void execute(Void element) {
        history.add(currentProtocolStep);
        setModel(fNextStep, "step");
        doTransition2();
      }
    });
    
  }

  private void doTransition1(final int versus, final Delegate<Void> delegate) {
    
    if (OsDetectionUtils.isAndroid()) {
      doTransition1Css(versus, delegate);
      return;
    }
    
    JQuery.StyleProperties step1Properties = JQuery.createStyleProperties();
    step1Properties.setLeft(versus * 100, Style.Unit.PCT);
    JQuery.withElement(protocolStepPanel.getElement())
        .animate(step1Properties, animationDuration, new Delegate<Void>() {
          public void execute(Void element) {
            JQuery.StyleProperties step2Properties = JQuery.createStyleProperties();
            step2Properties.setLeft((-1) * versus * 100, Style.Unit.PCT);
            JQuery.withElement(protocolStepPanel.getElement())
                .css(step2Properties);
            delegate.execute(null);
          }
        });
  }
  
  private void doTransition2() {
    if (OsDetectionUtils.isAndroid()) {
      doTransition2Css();
      return;
    }
    JQuery.StyleProperties step3Properties = JQuery.createStyleProperties();
    step3Properties.setLeft(PROTOCOL_STEP_PANEL_INITIAL_LEFT_PCT, Style.Unit.PCT);
    JQuery.withElement(protocolStepPanel.getElement())
        .animate(step3Properties, animationDuration);
  }

  
  private int transition2StartX;
  
  private void doTransition1Css(final int versus, final Delegate<Void> delegate) {
    
    final long startTime = System.currentTimeMillis();
    
    final AnimationCallback animationCallback = new AnimationCallback() {
      public void execute(double now) {
        if (now >= startTime + animationDuration) {
          int endX = (-1) * versus * 100;
          protocolStepPanel.getElement().getStyle().setLeft(endX, Style.Unit.PCT);
          transition2StartX = 0 - endX;
          delegate.execute(null);
          return;
        }
        double currDeltaTm = (now - startTime) / animationDuration;
        int currX = (int)Math.round(currDeltaTm * versus * 100);
        WebkitCssUtil.translatePct(protocolStepPanel.getElement(), currX, 0);
        AnimationScheduler.get().requestAnimationFrame(this, protocolStepPanel.getElement());
      }
    };

    animationCallback.execute(startTime);
    
  }
  

  private void doTransition2Css() {
    
    final long startTime = System.currentTimeMillis();

    final AnimationCallback animationCallback = new AnimationCallback() {
      public void execute(double now) {
        if (now >= startTime + animationDuration) {
          WebkitCssUtil.resetTransform(protocolStepPanel.getElement());
          protocolStepPanel.getElement().getStyle().clearLeft();
          return;
        }
        double currDeltaTm = (now - startTime) / animationDuration;
        int currX = (int)Math.round(currDeltaTm * transition2StartX);
        WebkitCssUtil.translatePct(protocolStepPanel.getElement(), currX, 0);
        AnimationScheduler.get().requestAnimationFrame(this, protocolStepPanel.getElement());
      }
    };

    animationCallback.execute(startTime);
    
  }
  
}
