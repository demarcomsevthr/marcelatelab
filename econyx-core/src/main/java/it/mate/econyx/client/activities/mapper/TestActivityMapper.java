package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.TestActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.TestPlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class TestActivityMapper extends BaseActivityMapper {

  public TestActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "testMvp";
  }

  public Place getDefaultPlace() {
    return new TestPlace();
  }

  public Activity getActivity(Place place) {
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    beforePlaceChange(place);
    return new TestActivity((TestPlace)getDefaultPlace(), (AppClientFactory)clientFactory);
  }

}
