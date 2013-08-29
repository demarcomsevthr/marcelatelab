package it.mate.ckd.client.view;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.ProtocolStepView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.SmartButton;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;

public class ProtocolStepView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToProtocolStep(ProtocolStep protocolStep);
    void goToCkdOutput(CKD ckd);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ProtocolStepView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Panel stepPanel;
  @UiField Panel endPanel;
  @UiField HTML questionHtml;
  @UiField SmartButton answer1Btn;
  @UiField SmartButton answer2Btn;
  @UiField HTML endHtml;
  @UiField SmartButton backBtn;
  
  private ProtocolStep currentProtocolStep;
  
  private LinkedList<ProtocolStep> history = new LinkedList<ProtocolStep>();
  
  public ProtocolStepView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    AppClientFactory.IMPL.initTitle(this);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    AppClientFactory.IMPL.adaptWrapperPanelOnTablet(wrapperPanel, "protocolWrapperPanel", true, null);
    initHeaderBackButton("GFR", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToCkdOutput(null);
      }
    });
    
    
    answer1Btn.setChangeColorOnClick(true);
    answer2Btn.setChangeColorOnClick(true);
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof ProtocolStep) {
      currentProtocolStep = (ProtocolStep)model;
      questionHtml.setHTML(currentProtocolStep.getQuestionText());
      answer1Btn.setText(currentProtocolStep.getAnswer1Text());
      answer2Btn.setText(currentProtocolStep.getAnswer2Text());
      answer1Btn.setOriginalColor();
      answer2Btn.setOriginalColor();
      endPanel.setVisible(false);
      backBtn.setVisible(history.size() > 0);
    }
  }

  @UiHandler ("answer1Btn")
  public void onAnswer1Btn(TouchStartEvent event) {
    checkStep(currentProtocolStep.getAnswer1EndText(), currentProtocolStep.getAnswer1Step());
  }
  
  @UiHandler ("answer2Btn")
  public void onAnswer2Btn(TouchStartEvent event) {
    checkStep(currentProtocolStep.getAnswer2EndText(), currentProtocolStep.getAnswer2Step());
  }
  
  @UiHandler ("backBtn")
  public void onBackBtn(TouchStartEvent event) {
    ProtocolStep step = history.getLast();
    history.removeLast();
    setModel(step, "step");
  }
  
  private void checkStep(String endText, int nextStepId) {
    if (endText != null && !"".equals(endText.trim())) {
      endHtml.setHTML(endText);
      endPanel.setVisible(true);
    }
    ProtocolStep nextStep = ProtocolStep.getProtocolStepById(nextStepId);
    if (nextStep != null) {
//    getPresenter().goToProtocolStep(nextStep);
      history.add(currentProtocolStep);
      setModel(nextStep, "step");
    }
  }
  
}
