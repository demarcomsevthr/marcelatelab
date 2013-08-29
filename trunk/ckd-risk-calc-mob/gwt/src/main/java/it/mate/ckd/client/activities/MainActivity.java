package it.mate.ckd.client.activities;

import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.places.MainPlace;
import it.mate.ckd.client.view.CKDInputViewWrapper;
import it.mate.ckd.client.view.CKDOutputViewWrapper;
import it.mate.ckd.client.view.HomeView;
import it.mate.ckd.client.view.ProtocolStepView;
import it.mate.ckd.client.view.ReferralDecisionView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, 
  /* CKDInputView.Presenter, */  CKDInputViewWrapper.Presenter, 
  /* CKDOutputView.Presenter, */ CKDOutputViewWrapper.Presenter,
  ReferralDecisionView.Presenter,
  ProtocolStepView.Presenter {

  
  
  
  private MainPlace place;
  
  private final static String CKD_ATTR = "ckd";
  
  private final static String STEP_ATTR = "step";
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          AppClientFactory.IMPL.getPhoneGap().exitApp();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CKD_INPUT)) {
      CKDInputViewWrapper view = AppClientFactory.IMPL.getGinjector().getCKDInputView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      CKD ckd = (CKD)GwtUtils.getClientAttribute(CKD_ATTR);
      if (ckd != null) {
        view.setModel(ckd, "ckd");
      }
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToHome();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CKD_OUTPUT)) {
      CKDOutputViewWrapper view = AppClientFactory.IMPL.getGinjector().getCKDOutputView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      view.setModel(GwtUtils.getClientAttribute(CKD_ATTR), "ckd");
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToCkdInput();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CKD_REFERRAL_DECISION)) {
      ReferralDecisionView view = AppClientFactory.IMPL.getGinjector().getReferralDecisionView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      view.setModel(GwtUtils.getClientAttribute(CKD_ATTR), "ckd");
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToCkdOutput(null);
        }
      });
    }
    if (place.getToken().equals(MainPlace.CKD_PROTOCOL_STEP)) {
      ProtocolStepView view = AppClientFactory.IMPL.getGinjector().getProtocolStepView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      ProtocolStep step = (ProtocolStep)GwtUtils.getClientAttribute(STEP_ATTR);
      if (step == null) {
        step = ProtocolStep.START;
      }
      view.setModel(step, "step");
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToCkdOutput(null);
        }
      });
    }
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }
  
  @Override
  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  @Override
  public void goToCkdInput() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_INPUT));
  }

  @Override
  public void goToCkdOutput(CKD ckd) {
    if (ckd != null) {
      GwtUtils.setClientAttribute(CKD_ATTR, ckd);
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_OUTPUT));
  }
  
  @Override
  public void goToReferralDecision() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_REFERRAL_DECISION));
  }
  
  @Override
  public void goToProtocolStep(ProtocolStep protocolStep) {
    if (protocolStep == null) {
      protocolStep = ProtocolStep.START;
    }
    GwtUtils.setClientAttribute(STEP_ATTR, protocolStep);
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_PROTOCOL_STEP));
  }

}
