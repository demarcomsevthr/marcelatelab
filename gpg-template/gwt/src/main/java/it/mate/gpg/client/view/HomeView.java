package it.mate.gpg.client.view;

import it.mate.gpg.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeView extends AbstractBaseView<Presenter> {
  
  public interface Presenter extends BasePresenter {
    void goToNotificationsPlace();
  }
  
  public HomeView() {
    VerticalPanel panel = new VerticalPanel();
    panel.add(new Button("Notifications", new ClickHandler() {
      public void onClick(ClickEvent event) {
        getPresenter().goToNotificationsPlace();
      }
    }));
    initWidget(panel);
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }

}
