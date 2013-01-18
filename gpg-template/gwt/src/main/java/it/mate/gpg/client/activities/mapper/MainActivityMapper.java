package it.mate.gpg.client.activities.mapper;

import it.mate.gpg.client.activities.MainActivity;
import it.mate.gpg.client.activities.NotificationsActivity;
import it.mate.gpg.client.places.HomePlace;
import it.mate.gpg.client.places.NotificationsPlace;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class MainActivityMapper extends BaseActivityMapper {
  
  @SuppressWarnings("rawtypes")
  public MainActivityMapper(BaseClientFactory clientFactory) {
    super(clientFactory);
  }

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof HomePlace) {
      return new MainActivity(clientFactory, (HomePlace)place);
    }
    if (place instanceof NotificationsPlace) {
      return new NotificationsActivity(clientFactory, (NotificationsPlace)place);
    }
    return null;
  }

  @Override
  public Place getDefaultPlace() {
    return new HomePlace();
  }

  @Override
  public String getHistoryName() {
    return "main";
  }
  
}
