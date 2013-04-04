package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.CalendarActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CalendarPlace;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class PlaceHolderActivityMapper extends BaseActivityMapper {

  public PlaceHolderActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public Activity getActivity(Place place) {
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof CalendarPlace) {
      beforePlaceChange(place);
      return new CalendarActivity((CalendarPlace)place, (AppClientFactory)clientFactory);
    }
    return null;
  }

  public Place getDefaultPlace() {
    return new PortalPagePlace();
  }

  public String getHistoryName() {
    return "placeHolderMvp";
  }

}
