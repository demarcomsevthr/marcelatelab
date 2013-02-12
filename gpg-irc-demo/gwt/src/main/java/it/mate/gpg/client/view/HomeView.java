package it.mate.gpg.client.view;

import it.mate.gpg.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(new Spacer("0.8em"));
    hp.add(new Image(UriUtils.fromTrustedString("images/creatinine.png")));
    getHeaderPanel().setLeftWidget(hp);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  
  @UiHandler("paramBtn")
  public void onParamBtn(TapEvent event) {
    getPresenter().goToCkdInput();
  }
  

  /*
  public void onCalcBtn(ClickEvent event) {
    if (!isSet(etaSpinBox.getValue()))
      return;
    if (!isSet(pesoSpinBox.getValue()))
      return;
    if (!isSet(creatininaSpinBox.getValue()))
      return;
    if (!isSet(albuminuriaSpinBox.getValue()))
      return;
    double vfg = (140 - etaSpinBox.getValue()) * pesoSpinBox.getValue() / (72d * creatininaSpinBox.getValue());
    if (fBtn.getValue())
      vfg *= 0.85;
    vfgBox.setValue(GwtUtils.formatDecimal(vfg, 2));
    String stadioVfg = "";
    if (vfg >= 90) {
      stadioVfg = "G1";
    } else if (vfg >= 60) {
      stadioVfg = "G2";
    } else if (vfg >= 30) {
      stadioVfg = "G3";
    } else if (vfg >= 15) {
      stadioVfg = "G4";
    } else {
      stadioVfg = "G5";
    }
    stadioVfgBox.setText(stadioVfg);
    String irc = "";
    int alb = albuminuriaSpinBox.getValue();
    String ircCol = "white";
    if (alb < 10) {
      if (vfg >= 60) {
        irc = "molto basso";
        ircCol = "#00FF00";
      } else if (vfg >= 45) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 30) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 29) {
      if (vfg >= 60) {
        irc = "molto basso";
        ircCol = "#00FF00";
      } else if (vfg >= 45) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 30) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 299) {
      if (vfg >= 60) {
        irc = "basso";
        ircCol = "#FFFF00";
      } else if (vfg >= 45) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else if (alb <= 1999) {
      if (vfg >= 60) {
        irc = "medio";
        ircCol = "#FFCC00";
      } else if (vfg >= 15) {
        irc = "alto";
        ircCol = "#FF0000";
      } else {
        irc = "molto alto";
        ircCol = "#990000";
      }
    } else {
      irc = "molto alto";
      ircCol = "#990000";
    }
    ircBox.setValue(irc);
    ircBox.getElement().getStyle().setBackgroundColor(ircCol);
  }
  
  private boolean isSet(Integer value) {
    return value != null && value > 0;
  }

  private boolean isSet(Double value) {
    return value != null && value > 0;
  }
  */

}
