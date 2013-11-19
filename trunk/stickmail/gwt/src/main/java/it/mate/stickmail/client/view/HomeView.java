package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.constants.AppProperties;
import it.mate.stickmail.client.view.HomeView.Presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.ui.client.MGWT;

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
  
  @UiHandler ("timeBox")
  public void onTimeChange (ValueChangeEvent<Time> event) {
    PhonegapUtils.log("new value is " + event.getValue());
  }
  
}
