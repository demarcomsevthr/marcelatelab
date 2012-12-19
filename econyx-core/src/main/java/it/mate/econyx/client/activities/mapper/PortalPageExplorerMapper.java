package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class PortalPageExplorerMapper extends BaseActivityMapper {

  public PortalPageExplorerMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "portalPageExplorer";
  }

  public Place getDefaultPlace() {
    return new PortalPagePlace(PortalPagePlace.LIST_EXPLORER);
  }

  public Activity getActivity(Place place) {
    // 10/03/2012
    // trucco per impedire che venga istanziata due volte l'activity per lo stesso activity manager 
    // (vedi it.mate.gwtcommons.client.history.MappedActivityManager.onPlaceChange) 
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof PortalPagePlace && ((PortalPagePlace)place).getToken().equals(PortalPagePlace.LIST_EXPLORER)) {
      return new PortalPageActivity((PortalPagePlace)getDefaultPlace(), (AppClientFactory)clientFactory);
    }
    return null;
  }

}
