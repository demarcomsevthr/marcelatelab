package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.CustomerActivity;
import it.mate.econyx.client.activities.OrderActivity;
import it.mate.econyx.client.activities.PortalUserActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CustomerPlace;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class PortalUserActivityMapper extends BaseActivityMapper {

  public PortalUserActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "portalUserMvp";
  }

  public Place getDefaultPlace() {
    return new PortalUserPlace(PortalUserPlace.VIEW);
  }

  public Activity getActivity(Place place) {
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof PortalUserPlace) {
      beforePlaceChange(place);
      return new PortalUserActivity((PortalUserPlace)place, (AppClientFactory)clientFactory);
    }
    if (place instanceof CustomerPlace) {
      beforePlaceChange(place);
      return new CustomerActivity((CustomerPlace)place, (AppClientFactory)clientFactory);
    }
    if (place instanceof OrderPlace) {
      beforePlaceChange(place);
      return new OrderActivity((OrderPlace)place, (AppClientFactory)clientFactory);
    }
    return new PortalUserActivity((PortalUserPlace)getDefaultPlace(), (AppClientFactory)clientFactory);
    //return null;
  }

}
