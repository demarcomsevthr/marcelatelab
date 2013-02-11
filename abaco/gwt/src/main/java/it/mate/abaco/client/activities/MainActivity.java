package it.mate.abaco.client.activities;

import it.mate.abaco.client.factories.AppClientFactory;
import it.mate.abaco.client.places.MainPlace;
import it.mate.abaco.client.view.AbacoView;
import it.mate.abaco.client.view.HomeView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, AbacoView.Presenter {
  
  private MainPlace place;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    if (place.getToken().equals(MainPlace.ABACO)) {
      AbacoView view = AppClientFactory.IMPL.getGinjector().getAbacoView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }

  @Override
  public void goToAbaco() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ABACO));
  }

  @Override
  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

}
