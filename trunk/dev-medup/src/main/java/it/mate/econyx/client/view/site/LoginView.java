package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.site.LoginView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    
  }

  public interface ViewUiBinder extends UiBinder<Widget, LoginView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public LoginView() {
    initUI();
  }

  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    
  }
  
}
