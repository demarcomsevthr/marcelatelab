package it.mate.gpg.client.view;

import it.mate.gpg.client.i18n.LocalizedMessages;
import it.mate.gpg.client.ui.theme.custom.CustomMainCss;
import it.mate.gpg.client.ui.theme.custom.MGWTCustomClientBundle;
import it.mate.gpg.client.ui.theme.custom.MGWTCustomTheme;
import it.mate.gpg.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.Button;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) MGWTCustomClientBundle bundle;
  @UiField (provided=true) CustomMainCss style;
  
  @UiField Button paramBtn;

  public HomeView() {
    bundle = MGWTCustomTheme.getInstance().getMGWTClientBundle();
    style = (CustomMainCss)bundle.getMainCss();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    /*
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(new Spacer("0.8em"));
    hp.add(new Image(MGWTCustomTheme.getInstance().getMGWTClientBundle().headerImage()));
    getHeaderPanel().setLeftWidget(hp);
    */
    getHeaderPanel().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    paramBtn.setText(LocalizedMessages.IMPL.HomeView_paramBtn_text());
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler("paramBtn")
  public void onParamBtn(TouchStartEvent event) {
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().goToCkdInput();
      }
    });
  }

}
