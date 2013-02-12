package it.mate.gpg.client.view;

import it.mate.gpg.client.ui.SpinnerDoubleBox;
import it.mate.gpg.client.ui.SpinnerIntegerBox;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CKDParamsInsertView extends BaseMgwtView {

  public interface Presenter extends BasePresenter {
    void goToNotificationsPlace();
  }

  public interface ViewUiBinder extends UiBinder<Widget, CKDParamsInsertView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField SpinnerIntegerBox etaSpinBox;
  @UiField SpinnerDoubleBox creatininaSpinBox;
  @UiField SpinnerIntegerBox pesoSpinBox;
//@UiField SpinnerIntegerBox altezzaSpinBox;
  @UiField RadioButton mBtn;
  @UiField RadioButton fBtn;
  @UiField SpinnerIntegerBox albuminuriaSpinBox;
  @UiField TextBox vfgBox;
  @UiField TextBox stadioVfgBox;
  @UiField TextBox ircBox;
//@UiField Button calcBtn;
  @UiField Label valoriLbl;
  
  private Timer calcTimer = null;

  public CKDParamsInsertView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getTitle().setHTML("IRC Test Demo");
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(new Spacer("0.8em"));
    hp.add(new Image(UriUtils.fromTrustedString("images/kidneys1tr.jpg")));
    getHeaderPanel().setLeftWidget(hp);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    creatininaSpinBox.setIncrement(0.1);
    
    /*
    calcBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        onCalcBtn(null);
        if (calcTimer == null) {
          calcTimer = GwtUtils.createTimer(250, new Delegate<Void>() {
            public void execute(Void element) {
              onCalcBtn(null);
            }
          });
        }
      }
    });
    */

    /*
    calcTimer = GwtUtils.createTimer(200, new Delegate<Void>() {
      public void execute(Void element) {
        onCalcBtn(null);
      }
    });
    */
    
    vfgBox.getElement().getStyle().setFontSize(23, Unit.PX);
    ircBox.getElement().getStyle().setFontSize(23, Unit.PX);
    GwtUtils.setBorderRadius(valoriLbl, "5px");
    
    // from mgwt quirks:
//  getScrollPanel().setUsePos(MGWT.getOsDetection().isAndroid());
    
    ValueChangeHandler<Integer> iHandler = new ValueChangeHandler<Integer>() {
      public void onValueChange(ValueChangeEvent<Integer> event) {
        onCalcBtn(null);
      }
    };
    
    ValueChangeHandler<Double> dHandler = new ValueChangeHandler<Double>() {
      public void onValueChange(ValueChangeEvent<Double> event) {
        onCalcBtn(null);
      }
    };
    
    ValueChangeHandler<Boolean> bHandler = new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        onCalcBtn(null);
      }
    };
    
    etaSpinBox.addValueChangeHandler(iHandler);
    creatininaSpinBox.addValueChangeHandler(dHandler);
    pesoSpinBox.addValueChangeHandler(iHandler);
    mBtn.addValueChangeHandler(bHandler);
    fBtn.addValueChangeHandler(bHandler);
    albuminuriaSpinBox.addValueChangeHandler(iHandler);
    
    onCalcBtn(null);
    
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }

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

}
