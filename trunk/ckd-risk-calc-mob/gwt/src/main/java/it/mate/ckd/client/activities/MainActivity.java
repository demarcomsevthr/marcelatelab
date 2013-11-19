package it.mate.ckd.client.activities;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.places.MainPlace;
import it.mate.ckd.client.view.CKDInputViewWrapper;
import it.mate.ckd.client.view.CKDOutputViewWrapper;
import it.mate.ckd.client.view.ExtendedVerView;
import it.mate.ckd.client.view.HomeView;
import it.mate.ckd.client.view.ProtocolStepView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.ui.MyAnchor;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.MgwtDialogs;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, 
  CKDInputViewWrapper.Presenter,  
  CKDOutputViewWrapper.Presenter,
  ExtendedVerView.Presenter,
  ProtocolStepView.Presenter {

  
  private MainPlace place;
  
  private final static String CKD_ATTR = "ckd";
  
  private final static String STEP_ATTR = "step";
  
  private PopinDialog ratingDialog;
  
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
      CKDInputViewWrapper view = AppClientFactory.IMPL.getGinjector().getCKDInputViewWrapper();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      CKD ckd = getCKDAttribute();
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
      view.setModel(getCKDAttribute(), "ckd");
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          goToCkdInput();
        }
      });
    }
    if (place.getToken().equals(MainPlace.CKD_EXTENDED_VIEW)) {
      ExtendedVerView view = AppClientFactory.IMPL.getGinjector().getExtendedVerView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      view.setModel(getCKDAttribute(), "ckd");
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
  public void goToProtocolStep(ProtocolStep protocolStep) {
    if (protocolStep == null) {
      protocolStep = ProtocolStep.START;
    }
    GwtUtils.setClientAttribute(STEP_ATTR, protocolStep);
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_PROTOCOL_STEP));
  }
  
  public void openHelpPage() {
    String appLanguage = GwtUtils.getJSVar("appLanguage", null);
    if ("it".equals(appLanguage)) {
      PhonegapUtils.openInAppBrowser("help-it.html");
    } else {
      PhonegapUtils.openInAppBrowser("help.html");
    }
  }
  
  @Override
  public void goToExtendedView(String selectedGFR) {
    if (selectedGFR != null) {
      CKD ckd = (CKD)GwtUtils.getClientAttribute(CKD_ATTR);
      if (ckd != null) {
        ckd.setSelectedGFR(selectedGFR);
      }
    }
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_EXTENDED_VIEW));
  }
  
  public boolean applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead) {
    boolean riskVisible = false;
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
    } else if (risk == CKD.VERY_HIGH_RISK_DEEP_RED) {
      irc = AppConstants.IMPL.CKDOutputView_veryHighRisk_text();
      ircCol = "yellow";
      ircBckCol = "#8A0808";
    }
    riskBox.setText(irc);
    riskBox.getElement().getStyle().setColor(ircCol);
    riskBox.getElement().getStyle().setBackgroundColor(ircBckCol);
    return riskVisible;
  }
  
  public void checkRatingDialog(final Delegate<PopinDialog> delegate) {
    
    String rating = PhonegapUtils.getLocalStorageItem("ckd-free-rating");
    if (rating == null) {
      rating = "4";
    }
    
    GwtUtils.log("check rating count = " + rating);
    
    int ratingCount = Integer.parseInt(rating);
    if (ratingCount < 0) {
//    getPresenter().goToCkdInput();
      delegate.execute(ratingDialog);
    } else if (ratingCount > 0) {
      ratingCount --;
      rating = "" + ratingCount;
      PhonegapUtils.setLocalStorageItem("ckd-free-rating", rating);
//    getPresenter().goToCkdInput();
      delegate.execute(ratingDialog);
    } else {
      final SimpleContainer dialogPanel = new SimpleContainer();
      dialogPanel.add(new SimplePanel(new MyAnchor("Yes Now", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "-1");
          GwtUtils.log("clicked yes");
          ratingDialog.hide();
          PhonegapUtils.openInAppBrowser("itms-apps://itunes.apple.com/app/id669006296");
        }
      })));
      dialogPanel.add(new SimplePanel(new MyAnchor("Remind Me Later", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "4");
          GwtUtils.log("clicked maybe");
          ratingDialog.hide();
//        getPresenter().goToCkdInput();
          delegate.execute(ratingDialog);
        }
      })));
      dialogPanel.add(new SimplePanel(new MyAnchor("No, Thanks", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "-1");
          GwtUtils.log("clicked no");
          ratingDialog.hide();
//        getPresenter().goToCkdInput();
          delegate.execute(ratingDialog);
        }
      })));
      
      // sono costretto a mettere un delay di 1s (!) perche' ho visto che nel simulatore
      // prende il touch sul bottone e lo propaga agli anchor all'interno del dialog !!!!
      GwtUtils.deferredExecution(1000, new Delegate<Void>() {
        public void execute(Void element) {
          
          // ratingDialog = ratingDialog("If you like this App, take a moment to rate it", dialogPanel);
          ratingDialog = MgwtDialogs.popin("If you like this App, take a moment to rate it", dialogPanel);
          
        }
      });
      
    }
    
  }

  private CKD getCKDAttribute() {
    CKD ckd = (CKD)GwtUtils.getClientAttribute(CKD_ATTR);
    if (ckd == null && AppProperties.IMPL.useDebugCKD()) {
      GwtUtils.log("generate a debug CKD");
      ckd = new CKD();
      ckd.setAge(50);
      ckd.setScr(1d);
      ckd.setWeight(70);
      ckd.setHeight(178);
      ckd.setAlbumin(30);
      ckd.setSelectedGFR("cockroft");
    }
    return ckd;
  }

}
