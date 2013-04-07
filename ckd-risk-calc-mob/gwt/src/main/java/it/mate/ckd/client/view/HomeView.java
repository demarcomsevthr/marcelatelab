package it.mate.ckd.client.view;

import it.mate.ckd.client.i18n.LocalizedMessages;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.Button;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;
  
  @UiField Button paramBtn;
  @UiField Label devInfo;

  public HomeView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {

    getHeaderPanel().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    paramBtn.setText(LocalizedMessages.IMPL.HomeView_paramBtn_text());
    
    String info = "Width " + Window.getClientWidth()
    + " Height " + Window.getClientHeight();
    devInfo.setText(info);
    
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
