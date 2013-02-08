package it.mate.gpg.client.activities;

import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gpg.client.places.HomePlace;
import it.mate.gpg.client.view.HomeView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements HomeView.Presenter {
  
  private HomePlace place;
  
  public MainActivity(BaseClientFactory clientFactory, HomePlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
    panel.setWidget(view.asWidget());
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }

  @Override
  public void goToNotificationsPlace() {
    
  }
  
  
  

}
