package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.ui.SmartButton;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.constants.AppProperties;
import it.mate.stickmail.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.Button;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Panel outputPanel;
  @UiField Label outputLbl;
  
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

  
}
