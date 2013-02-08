package it.mate.gpg.client.activities;

import it.mate.gpg.client.places.HomePlace;
import it.mate.gpg.client.view.HomeView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class Main2Activity extends BaseActivity implements HomeView.Presenter {
  
  private HomePlace place;
  
  @SuppressWarnings("rawtypes")
  public Main2Activity(BaseClientFactory clientFactory, HomePlace place) {
    super(clientFactory);
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
//  initView(AppClientFactory.IMPL.getGinjector().getHomeView(), panel);
  }

  @Override
  public void goToNotificationsPlace() {

  }

}
