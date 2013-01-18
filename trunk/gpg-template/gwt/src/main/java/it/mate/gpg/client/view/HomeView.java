package it.mate.gpg.client.view;

import it.mate.gpg.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.user.client.ui.Label;

public class HomeView extends AbstractBaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public HomeView() {
    initWidget(new Label("HomeView"));
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }

}
