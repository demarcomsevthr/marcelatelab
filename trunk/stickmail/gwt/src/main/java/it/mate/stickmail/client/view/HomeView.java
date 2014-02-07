package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.ui.TouchImage;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.constants.AppProperties;
import it.mate.stickmail.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.MGWT;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToNewMail();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
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
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    TouchImage optionsBtn = new TouchImage();
    optionsBtn.addStyleName("ui-optionsBtn");
    getHeaderPanel().setRightWidget(optionsBtn);
    optionsBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        PhonegapUtils.log("optons tapped");
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler ("mailListBtn")
  public void onMailListBtn (TouchEndEvent event) {

  }
  
  @UiHandler ("newMailBtn")
  public void onNewMailBtn (TouchEndEvent event) {
    getPresenter().goToNewMail();
  }
  
}
