package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.constants.AppProperties;
import it.mate.stickmail.client.view.HomeView.Presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Panel outputPanel;
  @UiField Label outputLbl;
  
  @UiField PhTimeBox timeBox;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    if (OsDetectionUtils.isTablet()) {
      setTitleHtml(AppProperties.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppProperties.IMPL.phoneAppName());
    }

    getHeaderBackButton().setVisible(!MGWT.getOsDetection().isAndroid());
    /*
    initHeaderBackButton("Home", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        
      }
    });
    */
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler ("testBtn")
  public void onTestBtn (TouchEndEvent event) {
    /*
    DatePickerPluginUtil.showDateDialog(new Delegate<Date>() {
      public void execute(Date value) {
        PhonegapUtils.log("received " + value);
      }
    });
    */
    
    Time time = new Time(21, 30);
    PhonegapUtils.log("setting " + time);
    timeBox.setValue(time);
    
  }
  

  @UiHandler ("dateBox")
  public void onDateChange (ValueChangeEvent<Date> event) {
    PhonegapUtils.log("new value is " + event.getValue());
  }
  
  @UiHandler ("calBox")
  public void onCalChange (ValueChangeEvent<Date> event) {
    PhonegapUtils.log("new value is " + event.getValue());
  }
  
  @UiHandler ("timeBox")
  public void onTimeChange (ValueChangeEvent<Time> event) {
    PhonegapUtils.log("new value is " + event.getValue());
  }

  @UiHandler ("calTest")
  public void onCalTest (TouchEndEvent event) {
    new CalTest();
  }
  
  public class CalTest {

    private PopupPanel popup;
    
    private ScrollPanel scrollPanel;
    
    private int lastX;
    
    private boolean internalScrolling = false;
    
    private boolean needReinitializePopup = false;
    
    private int curMonth;
    
    PopupPanel backPopup = null;
    
    public CalTest() {
      initUI();
    }
    
    private void initUI() {
      curMonth = 2;
      initPopup();
    }
    
    private void initPopup() {
      
      backPopup = popup;
      
      popup = new PopupPanel();
      popup.setAutoHideEnabled(true);

      setHidden(popup);
      
      scrollPanel = new ScrollPanel();
      
      scrollPanel.setWidth("280px");
      scrollPanel.setHeight("300px");
      scrollPanel.setScrollingEnabledY(false);
      scrollPanel.setScrollingEnabledX(true);
      scrollPanel.setHideScrollBar(true);
      
      SimplePanel wrapperPanel = new SimplePanel();
      wrapperPanel.setHeight("300px");
      wrapperPanel.setWidth("840px");
      scrollPanel.setWidget(wrapperPanel);

      HorizontalPanel horizontalPanel = new HorizontalPanel();
      horizontalPanel.setHeight("300px");
      horizontalPanel.setWidth("840px");
      wrapperPanel.setWidget(horizontalPanel);

      horizontalPanel.add(createCalTestLabel("" + (curMonth - 1)));
      horizontalPanel.add(createCalTestLabel("" + (curMonth + 0)));
      horizontalPanel.add(createCalTestLabel("" + (curMonth + 1)));
      
      popup.add(scrollPanel);
      
      popup.center();
      
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          internalScrollTo(280);
          if (backPopup != null) {
            backPopup.hide();
          }
        }
      });
      
      scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
        public void onScrollEnd(ScrollEndEvent event) {
          if (internalScrolling) {
            setVisible(popup);
            internalScrolling = false;
            lastX = getCurrentX();
            if (needReinitializePopup) {
              needReinitializePopup = false;
              initPopup();
            }
          } else {
            GwtUtils.log("user scroll");
            int startX = lastX;
            int endX = getCurrentX();
            GwtUtils.log("startX = " + startX);
            GwtUtils.log("endX = " + endX);
            if (endX < (startX - 50)) {
              curMonth --;
              needReinitializePopup = true;
              internalScrollTo(startX - endX - 280);
            }
            if (endX > (startX + 50)) {
              curMonth ++;
              needReinitializePopup = true;
              internalScrollTo(startX - endX + 280);
            }
          }
        }
      });
      
    }
    
    private void setVisible(Widget w) {
      w.getElement().getStyle().setVisibility(Visibility.VISIBLE);
      w.getElement().getStyle().setZIndex(+1);
    }
  
    private void setHidden(Widget w) {
      w.getElement().getStyle().setZIndex(-1);
      w.getElement().getStyle().setVisibility(Visibility.HIDDEN);
    }
  
    private void internalScrollTo(int x) {
      GwtUtils.log("internal scroll to " + x);
      internalScrolling = true;
      scrollPanel.scrollTo(x, 0, 0, true);
    }
    
    private int getCurrentX() {
      return (scrollPanel.getX() * -1);
    }
    
    private Label createCalTestLabel (String text) {
      Label label = new Label(text);
      label.addStyleName("stm-CalTestLabel");
      return label;
    }
    
  }
  
}
