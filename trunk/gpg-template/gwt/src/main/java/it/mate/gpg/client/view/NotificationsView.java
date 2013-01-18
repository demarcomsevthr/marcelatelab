package it.mate.gpg.client.view;

import it.mate.gpg.client.view.NotificationsView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.user.client.ui.Label;

public class NotificationsView extends AbstractBaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public NotificationsView() {
    initWidget(new Label("NotificationsView"));
  }



  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  

}
