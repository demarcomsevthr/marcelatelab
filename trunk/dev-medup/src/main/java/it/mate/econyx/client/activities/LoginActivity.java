package it.mate.econyx.client.activities;

import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.econyx.client.view.site.LoginView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity extends BaseActivity implements LoginView.Presenter {

  PortalUserPlace place;
  
  public LoginActivity(PortalUserPlace place, BaseClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (PortalUserPlace.LOGIN.equals(place.getToken())) {
      initView(new LoginView(), panel);
    }
  }

}
