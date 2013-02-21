package it.mate.abaco.client.view;

import it.mate.abaco.client.ui.BallWidget;
import it.mate.abaco.client.view.AbacoView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.Button;

public class AbacoView extends BaseMgwtView<Presenter> {

  private static final int LINES_TOP = 108;
  
  private static final int BALL_DIAM = 32;
  
  private static final int GREEN_LINE_X = 50;
  
  private static final int RED_LINE_X = 126;
  
  private static final int BLUE_LINE_X = 206;
  
  private static final int BALLS_BOTTOM = LINES_TOP + (BALL_DIAM * 8);
  
  public interface Presenter extends BasePresenter {
    public void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, AbacoView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTMLPanel ballContainer;
  @UiField HTMLPanel linesContainer;
  @UiField Label numberLbl;
  
  @UiField Button greenBtn;
  
  private int gCount = 0;
  private int rCount = 0;
  private int bCount = 0;
  
  private BallWidget[] greenBalls = new BallWidget[10];
  private BallWidget[] redBalls = new BallWidget[10];
  private BallWidget[] blueBalls = new BallWidget[10];
  
  int matchNumber;
  
  
  public AbacoView() {
    initUI();
    GwtUtils.log("init view");
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getTitle().setHTML("Abaco");
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToHome();
      }
    });

    String linesHtml = "";
    linesHtml += "<div style=\"width: 2px; height: "+(BALL_DIAM * 9)+"px; background-color: black; position: absolute; top: "+LINES_TOP+"px; left: "+GREEN_LINE_X+"px;\"></div>"; 
    linesHtml += "<div style=\"width: 2px; height: "+(BALL_DIAM * 9)+"px; background-color: black; position: absolute; top: "+LINES_TOP+"px; left: "+RED_LINE_X+"px;\"></div>"; 
    linesHtml += "<div style=\"width: 2px; height: "+(BALL_DIAM * 9)+"px; background-color: black; position: absolute; top: "+LINES_TOP+"px; left: "+BLUE_LINE_X+"px;\"></div>"; 
    linesContainer.getElement().setInnerHTML(linesHtml);
    
    matchNumber = (int)Math.round(Math.random() * 999d);
    numberLbl.setText(""+matchNumber);
    
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }
  
  @UiHandler ("greenBtn")
  public void onGreenBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onGreenBtn", "one green please");
    if (gCount < 9) {
      gCount ++;
      ballContainerUpdate();
    }
  }

  @UiHandler ("redBtn")
  public void onRedBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onRedBtn", "one red please");
    if (rCount < 9) {
      rCount ++;
      ballContainerUpdate();
    }
  }

  @UiHandler ("blueBtn")
  public void onBlueBtn(TapEvent event) {
    GwtUtils.log(getClass(), "onBlueBtn", "one blue please");
    if (bCount < 9) {
      bCount ++;
      ballContainerUpdate();
    }
  }
  
  private void ballContainerUpdate() {
    for (int g = 1; g <= gCount; g++) {
      BallWidget ball = ballUpdate("ball$g", g, (GREEN_LINE_X - (BALL_DIAM - 2) / 2), BallWidget.GREEN);
      if (ball != null) {
        greenBalls[g - 1] = ball;
      }
    }
    for (int r = 1; r <= rCount; r++) {
      BallWidget ball = ballUpdate("ball$r", r, (RED_LINE_X - (BALL_DIAM - 2) / 2), BallWidget.RED);
      if (ball != null) {
        redBalls[r - 1] = ball;
      }
    }
    for (int b = 1; b <= bCount; b++) {
      BallWidget ball = ballUpdate("ball$b", b, (BLUE_LINE_X - (BALL_DIAM - 2) / 2), BallWidget.BLUE);
      if (ball != null) {
        blueBalls[b - 1] = ball;
      }
    }
  }
  
  private BallWidget ballUpdate(String prefix, int count, int left, int color) {
    Element el = DOM.getElementById(prefix+count);
    if (el == null) {
      int top = BALLS_BOTTOM - BALL_DIAM * (count - 1);
      BallWidget ball = new BallWidget(prefix, count, top, left, color);
      ballContainer.add(ball);
      ball.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          BallWidget ball = (BallWidget)event.getSource();
          GwtUtils.log(getClass(), "tapEvent", "tap on " + ball);
          if (ball.getColor() == BallWidget.GREEN) {
            for (int g = ball.getCount(); g <= gCount; g++) {
              greenBalls[g - 1].setVisible(false);
              greenBalls[g - 1] = null;
            }
            gCount = ball.getCount() - 1;
          } else if (ball.getColor() == BallWidget.RED) {
            for (int r = ball.getCount(); r <= rCount; r++) {
              redBalls[r - 1].setVisible(false);
              redBalls[r - 1] = null;
            }
            rCount = ball.getCount() - 1;
          } else if (ball.getColor() == BallWidget.BLUE) {
            for (int b = ball.getCount(); b <= bCount; b++) {
              blueBalls[b - 1].setVisible(false);
              blueBalls[b - 1] = null;
            }
            bCount = ball.getCount() - 1;
          }
          ballContainerUpdate();
        }
      });
      return ball;
    }
    return null;
  }

}
