package it.mate.gpg.client.activities;

import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gpg.client.places.NotificationsPlace;
import it.mate.gpg.client.view.NotificationsView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class NotificationsActivity extends BaseActivity implements NotificationsView.Presenter {

  private NotificationsPlace place;
  
  @SuppressWarnings("rawtypes")
  public NotificationsActivity(BaseClientFactory clientFactory, NotificationsPlace place) {
    super(clientFactory);
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    
    initView(AppClientFactory.IMPL.getGinjector().getNotificationsView(), panel);
    
  }
  
}
