package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.activities.ProductActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.places.ProductPlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class PortalPageBodyActivityMapper extends BaseActivityMapper {

  public PortalPageBodyActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "bodyMvp";
  }

  public Place getDefaultPlace() {
    return new PortalPagePlace(PortalPagePlace.VIEW);
  }

  public Activity getActivity(Place place) {
    GwtUtils.log(getClass(), "getActivity", "place = " + place + " this = " + this);
    // 10/03/2012
    // trucco per impedire che venga istanziata due volte l'activity per lo stesso activity manager 
    // (vedi it.mate.gwtcommons.client.history.MappedActivityManager.onPlaceChange) 
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof ProductPlace) {
      beforePlaceChange(place);
      return new ProductActivity((ProductPlace)place, (AppClientFactory)clientFactory);
    }
    if (place instanceof PortalPagePlace) {
      beforePlaceChange(place);
      return new PortalPageActivity((PortalPagePlace)place, (AppClientFactory)clientFactory);
//    return new PortalPageActivity(AppClientFactory.IMPL).setPlace((PortalPagePlace)place);
//    return singletonPortalPageActivity.setPlace((PortalPagePlace)place);
    }
    return null;
  }

}
